package com.epam.pharmacy.command;

import java.util.HashMap;
import java.util.Map;

import com.epam.pharmacy.services.MedicineService;
import com.epam.pharmacy.services.OrderService;
import com.epam.pharmacy.services.UserService;

public class CommandFactory {

	private Map<String, Command> commands;

    private void init() {
        commands = new HashMap<>();

        //GET request
        
        //common user-admin action
        commands.put("GET/welcome", new ShowPageCommand("welcome"));
        commands.put("GET/set-language", new SelectLanguageCommand());
        commands.put("GET/main", new ShowPageCommand("main"));
        commands.put("GET/medicine-list", new ShowMedicineListCommand(new MedicineService()));

        //user action
        commands.put("GET/order-list", new ShowClientOrderDtoListCommand(new OrderService()));
        
        //admin action

        //POST request

        //common user-admin action
        commands.put("POST/login", new LoginCommand(new UserService()));
        commands.put("POST/logout", new LogoutCommand());
        //user action
        commands.put("POST/make-order", new MakeOrderCommand(new OrderService()));

        //admin action
    }
    public Command getCommand(String command) {
        if (commands == null) {
            init();
        }  
        return commands.get(command);
    }
}
