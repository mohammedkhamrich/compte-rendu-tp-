const express = require('express');
const router = express.Router();
const { sendToQueue } = require('../services/rabbitmq');

router.post('/create', async (req, res) => {
    const order = {
        id: Date.now(),
        instance: process.env.INSTANCE_NAME || "Unknown",
        status: "PENDING"
    };
    await sendToQueue('orders_queue', JSON.stringify(order));
    res.status(201).json(order);
});

module.exports = router;