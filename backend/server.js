const express = require('express');
const fs = require('fs');
const app = express();
const port = process.env.PORT || 3000;

app.use(express.json());
app.set('view engine', 'ejs');

let timers = {};

// Load timers from file on server start
if (fs.existsSync('timers.json')) {
    const data = fs.readFileSync('timers.json');
    timers = JSON.parse(data);
}

// Save timers to file
const saveTimers = () => {
    fs.writeFileSync('timers.json', JSON.stringify(timers));
};

app.post('/create', (req, res) => {
    const { timerId, timeInMilliseconds } = req.body;
    timers[timerId] = timeInMilliseconds;
    saveTimers();
    res.status(201).send({ message: 'Timer created successfully' });
});

app.get('/get/:timerId', (req, res) => {
    const { timerId } = req.params;
    const timeInMilliseconds = timers[timerId];
    if (timeInMilliseconds !== undefined) {
        const userAgent = req.headers['user-agent'];
        if (userAgent && userAgent.includes('Mozilla')) {
            res.render('timer', { timerId, timeInMilliseconds });
        } else {
            res.status(200).send({ timerId, timeInMilliseconds });
        }
    } else {
        res.status(404).send({ message: 'Timer not found' });
    }
});

// Auto-kill finished timers
const autoKillTimers = () => {
    const currentTime = Date.now();
    for (const timerId in timers) {
        if (timers[timerId] <= currentTime) {
            delete timers[timerId];
        }
    }
    saveTimers();
};

// Run auto-kill every minute
setInterval(autoKillTimers, 60000);

app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});
