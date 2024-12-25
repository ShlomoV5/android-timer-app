package com.example.timerapp;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NetworkInterface {

    private static final String BASE_URL = "https://your-railway-deployment-url.com/";

    public static String shareTimerState(String timerId, long timeInMilliseconds) {
        String response = "";
        try {
            URL url = new URL(BASE_URL + "share?timerId=" + timerId + "&time=" + timeInMilliseconds);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                response += scanner.nextLine();
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String getTimerState(String timerId) {
        String response = "";
        try {
            URL url = new URL(BASE_URL + "get?timerId=" + timerId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                response += scanner.nextLine();
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
