const Redis = require("ioredis");

const client = new Redis(process.env.REDIS_URL || "redis://redis:6379");

client.on("connect", () => {
    console.log("Connected to Redis");
});

client.on("error", (error) => {
    console.error("Redis error:", error.message);
});

module.exports = client;
