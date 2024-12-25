package com.example.timerapp;

import java.util.UUID;

public class TimerUtils {

    public static String generateShortPermalink() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
