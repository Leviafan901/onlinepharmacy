package com.epam.pharmacy.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {

	private Map<String, Command> commands;

    private void init() {
        commands = new HashMap<>();

        //GET request
        commands.put("GET/welcome", new ShowPageCommand("welcome"));
        commands.put("GET/set-language", new SelectLanguageCommand());
        

        //common user-admin action
        commands.put("GET/main", new ShowPageCommand("main"));

        //user action

        //admin action

        //POST request

        //common user-admin action
        commands.put("POST/login", new LoginCommand());
        commands.put("POST/logout", new LogoutCommand());
        //user action
        
        

        //admin action

    }
    public Command getCommand(HttpServletRequest request) {
        if (commands == null) {
            init();
        }
        String method = request.getMethod();
        String pathInfo = request.getPathInfo();
        String command = method + pathInfo;
        return commands.get(command);//
    }
}
