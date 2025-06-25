package com.example.sessions;

import java.util.Scanner;

import com.example.commands.Command;

import java.util.List;
import java.util.ArrayList;

public abstract class Session {
    private static final int SCREEN_WIDTH = 80;
    private static final String SEPARATOR = "│";
    
    protected String name;
    protected boolean running;
    protected Scanner scanner;
    protected CommandManager commandManager;
    protected List<String> commandOrder;
    protected String displayText;
    protected String logText;
    protected boolean logDisplaying;
    protected Session parentSession;
    public Session(String name, String description, Session parentSession) {
        this.name = name;
        this.scanner = new Scanner(System.in);
        this.commandManager = new CommandManager();
        this.commandOrder = new ArrayList<>();
        this.displayText = "";
        this.logText = "";
        this.parentSession = parentSession;
    }
    
    public String getName() { return name; }
    public boolean isRunning() { return running; }
    public boolean isLogDisplaying() { return logDisplaying; }
    
    public void addCommand(Command command) {
        commandManager.registerCommand(command);
        commandOrder.add(command.getName().toLowerCase());
    }
    
    public String getPrompt() {
        return logDisplaying ? logText + " (↵で続行) > " : name + "> ";
    }
    
    public void setDisplayText(String text) {
        this.displayText = text;
        refreshDisplay();
    }
    
    public void setLogText(String text) {
        this.logText = text;
        this.logDisplaying = true;
        refreshDisplay();
    }
    
    public void clearLog() {
        this.logText = "";
        this.logDisplaying = false;
        refreshDisplay();
    }
    
    protected void refreshDisplay() {
        System.out.print("\033[H\033[2J");
        showLayout();
        System.out.print(getPrompt());
    }
    
    private void showLayout() {
        String[] display = displayText.split("\n");
        List<String> menu = getMenuLines();
        int maxLines = Math.max(display.length, menu.size());
        int half = SCREEN_WIDTH / 2;
        
        System.out.printf("%-" + half + "s " + SEPARATOR + " %s%n", "--- Display ---", "--- Menu ---");
        
        for (int i = 0; i < maxLines; i++) {
            String left = i < display.length ? display[i] : "";
            String right = i < menu.size() ? menu.get(i) : "";
            System.out.printf("%-" + half + "s " + SEPARATOR + " %s%n", left, right);
        }
    }
    
    private List<String> getMenuLines() {
        List<String> lines = new ArrayList<>();
        if (logDisplaying) {
            lines.add("ログ表示中...");
            return lines;
        }
        
        for (int i = 0; i < commandOrder.size(); i++) {
            Command cmd = commandManager.getCommand(commandOrder.get(i));
            if (cmd != null) lines.add((i + 1) + ": " + cmd.getName());
        }
        return lines;
    }
    
    public void start() {
        running = true;
        initializeCommands();
        
        while (running) {
            String input = scanner.nextLine();
            
            if (logDisplaying) {
                clearLog();
                continue;
            }
            
            if (!input.trim().isEmpty()) {
                processInput(input.trim());
            }
            refreshDisplay();
        }
    }
    
    public void stop() { running = false; }
    
    protected abstract void initializeCommands();
    
    protected void processInput(String input) {
        String commandName = null;
        String[] args = new String[0];
        
        if (input.matches("\\d+")) {
            int idx = Integer.parseInt(input) - 1;
            if (idx >= 0 && idx < commandOrder.size()) {
                commandName = commandOrder.get(idx);
            } else {
                setDisplayText("無効な番号です");
                return;
            }
        } else {
            String[] parts = input.split("\\s+");
            commandName = parts[0].toLowerCase();
            if (parts.length > 1) {
                args = new String[parts.length - 1];
                System.arraycopy(parts, 1, args, 0, parts.length - 1);
            }
        }
        
        if (commandName != null) {
            commandManager.executeCommand(commandName, args);
        }
    }
}