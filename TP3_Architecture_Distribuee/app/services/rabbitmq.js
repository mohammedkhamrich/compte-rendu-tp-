const amqp = require("amqplib");

const RABBITMQ_URL =
    process.env.RABBITMQ_URL || "amqp://admin:admin@rabbitmq:5672";

let channelPromise;

async function createChannel() {
    const connection = await amqp.connect(RABBITMQ_URL);
    connection.on("error", (error) => {
        console.error("RabbitMQ connection error:", error.message);
        channelPromise = undefined;
    });

    const channel = await connection.createChannel();
    return channel;
}

async function getChannel() {
    if (!channelPromise) {
        channelPromise = createChannel();
    }

    return channelPromise;
}

async function sendToQueue(queueName, message) {
    const channel = await getChannel();
    await channel.assertQueue(queueName, { durable: true });
    channel.sendToQueue(queueName, Buffer.from(message), { persistent: true });
}

module.exports = { sendToQueue, getChannel };
