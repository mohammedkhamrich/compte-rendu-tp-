const express = require("express");

const app = express();
const PORT = Number(process.env.PORT) || 3000;
const INSTANCE_NAME = process.env.INSTANCE_NAME || "Unknown";

require("./services/mongodb");
require("./services/redis");

app.use(express.json());
app.use("/api/commandes", require("./routes/commandes"));

app.get("/health", (req, res) => {
    res.json({
        status: "UP",
        instance: INSTANCE_NAME,
        port: PORT,
    });
});

app.get("/", (req, res) => {
    res.json({
        message: "Distributed app is running",
        instance: INSTANCE_NAME,
        port: PORT,
    });
});

app.listen(PORT, "0.0.0.0", () => {
    console.log(`Server started on port ${PORT} (${INSTANCE_NAME})`);
});
