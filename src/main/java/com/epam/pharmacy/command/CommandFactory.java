package com.epam.pharmacy.command;

import java.util.HashMap;
import java.util.Map;

import com.epam.pharmacy.services.MedicineService;
import com.epam.pharmacy.services.OrderService;
import com.epam.pharmacy.services.PrescriptionService;
import com.epam.pharmacy.services.RequestService;
import com.epam.pharmacy.services.UserService;

public class CommandFactory {

	private Map<String, Command> commands;

    private void init() {
        commands = new HashMap<>();

        //GET request
        
        //common client-doctor-admin commands
        commands.put("GET/welcome", new ShowPageCommand("welcome"));
        commands.put("GET/set-language", new SelectLanguageCommand());
        commands.put("GET/main", new ShowPageCommand("main"));
        commands.put("GET/medicine-list", new ShowMedicineListCommand(new MedicineService()));
        commands.put("GET/prescription-list", new ShowPrescriptionListCommand(new PrescriptionService()));
        commands.put("GET/request-list", new ShowUserRequestDtoListCommand(new RequestService()));
        
        //client command
        commands.put("GET/order-list", new ShowClientOrderDtoListCommand(new OrderService()));
        
        //doctor
        
        //admin command

        //POST request

        //common client-admin commands
        commands.put("POST/login", new LoginCommand(new UserService()));
        commands.put("POST/logout", new LogoutCommand());
        
        //client command
        commands.put("POST/make-order", new MakeOrderCommand(new OrderService()));
        commands.put("POST/extend-prescription", new ExtendPrescriptionCommand(new RequestService()));

        //admin command
        commands.put("POST/delete-medicine", new DeleteMedicineCommand(new MedicineService()));
    }
    public Command getCommand(String command) {
        if (commands == null) {
            init();
        }  
        return commands.get(command);
    }
}
