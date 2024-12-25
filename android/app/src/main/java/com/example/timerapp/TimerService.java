package com.example.timerapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;

public class TimerService extends Service {

    private final IBinder binder = new LocalBinder();
    private final Handler handler = new Handler();
    private long startTime = 0L;
    private long timeInMilliseconds = 0L;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;
    private boolean isRunning = false;

    public class LocalBinder extends Binder {
        TimerService getService() {
            return TimerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void startTimer() {
        if (!isRunning) {
            startTime = SystemClock.uptimeMillis();
            handler.postDelayed(updateTimerThread, 0);
            isRunning = true;
        }
    }

    public void pauseTimer() {
        if (isRunning) {
            timeSwapBuff += timeInMilliseconds;
            handler.removeCallbacks(updateTimerThread);
            isRunning = false;
        }
    }

    public void resetTimer() {
        startTime = 0L;
        timeInMilliseconds = 0L;
        timeSwapBuff = 0L;
        updatedTime = 0L;
        handler.removeCallbacks(updateTimerThread);
        isRunning = false;
    }

    private final Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            // Update the timer display here
            if (updatedTime >= 0) {
                handler.postDelayed(this, 0);
            } else {
                resetTimer();
            }
        }
    };
}
