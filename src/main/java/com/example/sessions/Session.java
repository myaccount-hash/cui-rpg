package com.example.sessions;

import java.util.Scanner;
import com.example.commands.Command;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.Map;

// TODO: ログのFIFO構造を削除
public abstract class Session {
    private static final int SCREEN_WIDTH = 80;
    private static final String SEPARATOR = "│";
    
    protected String name;
    protected boolean running;
    protected Scanner scanner;
    protected CommandManager commandManager;
    protected List<String> commandOrder;
    protected Queue<String> logQueue;
    protected boolean logDisplaying;
    protected Session parentSession;
    protected String displayText;

    public Session(String name, String description, Session parentSession) {
        this.name = name;
        this.scanner = new Scanner(System.in);
        this.commandManager = new CommandManager();
        this.commandOrder = new ArrayList<>();
        this.displayText = "";
        this.logQueue = new LinkedList<>();
        this.parentSession = parentSession;
    }

    public void stop() { running = false; }

    // ゲッター
    public String getName() { return name; }
    public boolean isRunning() { return running; }
    public boolean isLogDisplaying() { return logDisplaying; }
    public String getDisplayText() { return displayText; }
    public Session getParentSession() { return parentSession; }
    
    protected void setDisplayText(String text) { 
        this.displayText = text; 
        refreshDisplay(); 
    }
    
    protected void addCommand(Command command) {
        commandManager.registerCommand(command);
        commandOrder.add(command.getName().toLowerCase());
    }
    
    protected void refreshDisplay() {
        System.out.print("\033[H\033[2J");
        String[] display = displayText.split("\n");
        List<String> menu = logDisplaying ? List.of("ログ表示中...") : buildMenu();
        int maxLines = Math.max(display.length, menu.size());
        int half = SCREEN_WIDTH / 2;
        
        System.out.printf("%-" + half + "s " + SEPARATOR + " %s%n", "--- Display ---", "--- Menu ---");
        
        for (int i = 0; i < maxLines; i++) {
            String left = i < display.length ? display[i] : "";
            String right = i < menu.size() ? menu.get(i) : "";
            System.out.printf("%-" + half + "s " + SEPARATOR + " %s%n", left, right);
        }
        System.out.print(logDisplaying ? getLogText() + " (↵で続行) > " : name + "> ");
    }

    protected void showLog(){
        this.logDisplaying = !logQueue.isEmpty();
        refreshDisplay();
    }

    public void setLogText(String text) {
        logQueue.offer(text);
        if (logQueue.size() > 100) logQueue.poll();
        this.logDisplaying = true;
        refreshDisplay();
        scanner.nextLine();
        logQueue.poll();
        this.logDisplaying = !logQueue.isEmpty();
        refreshDisplay();
    }
    
    protected void clearLog() {
        logQueue.clear();
        this.logDisplaying = false;
        refreshDisplay();
    }

    protected void processInput(String input) {
        try {
            int idx = Integer.parseInt(input) - 1;
            if (idx >= 0 && idx < commandOrder.size()) {
                String commandName = commandOrder.get(idx);
                commandManager.executeCommand(commandName, new String[0]);
                Command cmd = commandManager.getCommand(commandName);
                if (cmd != null && cmd.getCommandLog() != null) {
                    setLogText(cmd.getCommandLog());
                }
            } else {
                setDisplayText("無効な番号です");
            }
        } catch (NumberFormatException e) {
            setDisplayText("無効な番号です");
        }
    }

    private List<String> buildMenu() {
        List<String> menu = new ArrayList<>();
        for (int i = 0; i < commandOrder.size(); i++) {
            Command cmd = commandManager.getCommand(commandOrder.get(i));
            if (cmd != null) {
                menu.add((i + 1) + ": " + cmd.getName());
            }
        }
        return menu;
    }

    private String getLogText() {
        return logQueue.isEmpty() ? "" : String.join("\n", logQueue);
    }

    public class CommandManager {
        private final Map<String, Command> commands = new HashMap<>();
        
        public void registerCommand(Command command) {
            commands.put(command.getName().toLowerCase(), command);
        }
        
        Command getCommand(String name) {
            return commands.get(name.toLowerCase());
        }
        
        boolean executeCommand(String name, String[] args) {
            Command command = getCommand(name);
            if (command != null) {
                return command.execute(args);
            }
            System.out.println("不明なコマンド: " + name);
            return false;
        }
    }
}