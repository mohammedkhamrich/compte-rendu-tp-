const { getChannel } = require("./services/rabbitmq");

const QUEUE_NAME = process.env.QUEUE_NAME || "orders_queue";

async function startConsumer() {
    const channel = await getChannel();
    await channel.assertQueue(QUEUE_NAME, { durable: true });

    console.log(`Consumer listening on queue "${QUEUE_NAME}"`);

    channel.consume(QUEUE_NAME, (message) => {
        if (!message) {
            return;
        }

        console.log(`Received: ${message.content.toString()}`);
        channel.ack(message);
    });
}

startConsumer().catch((error) => {
    console.error("Consumer startup failed:", error);
    process.exit(1);
});
