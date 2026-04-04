const mongoose = require("mongoose");

const MONGODB_URL =
    process.env.MONGODB_URL || "mongodb://mongodb:27017/tp_distribue";

mongoose
    .connect(MONGODB_URL)
    .then(() => {
        console.log("Connected to MongoDB");
    })
    .catch((error) => {
        console.error("MongoDB connection error:", error.message);
    });

module.exports = mongoose;
