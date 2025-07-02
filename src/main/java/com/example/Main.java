package com.example;

import com.example.entities.Player;
import com.example.sessions.MainSession;

public class Main {
  public static void main(String[] args) {
    new MainSession(null, new Player()).run();
  }
}
