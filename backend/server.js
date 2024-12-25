const express = require('express');
const app = express();
const port = process.env.PORT || 3000;

app.use(express.json());

let timers = {};

app.post('/create', (req, res) => {
    const { timerId, timeInMilliseconds } = req.body;
    timers[timerId] = timeInMilliseconds;
    res.status(201).send({ message: 'Timer created successfully' });
});

app.get('/get/:timerId', (req, res) => {
    const { timerId } = req.params;
    const timeInMilliseconds = timers[timerId];
    if (timeInMilliseconds !== undefined) {
        res.status(200).send({ timerId, timeInMilliseconds });
    } else {
        res.status(404).send({ message: 'Timer not found' });
    }
});

app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});
