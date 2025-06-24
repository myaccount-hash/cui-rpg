package com.example;

import com.example.sessions.MainSession;

public class Main {
    public static void main(String[] args) {
        MainSession mainSession = new MainSession(null);
        mainSession.start();
    }
}