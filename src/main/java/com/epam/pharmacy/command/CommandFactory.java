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
        
        //client commands
        commands.put("GET/order-list", new ShowClientOrderDtoListCommand(new OrderService()));
        
        //doctor commands
        commands.put("GET/prescription-form",
        		new ShowPrescriptionCreationFormCommand(new MedicineService(), new UserService()));
        
        //admin commands
        commands.put("GET/admin-order-list", new ShowAdminOrderDtoListCommand(new OrderService()));
        commands.put("GET/change-form", new ShowMedicineChangeFormCommand(new MedicineService()));
        commands.put("GET/creation-form", new ShowMedicineCreationFormCommand());

        //POST request

        //common client-admin commands
        commands.put("POST/login", new LoginCommand(new UserService()));
        commands.put("POST/logout", new LogoutCommand());
        
        //client commands
        commands.put("POST/make-order", new MakeOrderCommand(new OrderService()));
        commands.put("POST/request-extention-prescription", new MakeRequestExtentionPrescriptionCommand(new RequestService()));

        //admin commands
        commands.put("POST/delete-medicine", new DeleteMedicineCommand(new MedicineService()));
        commands.put("POST/change-medicine", new ChangeMedicineCommand(new MedicineService()));
        commands.put("POST/create-medicine", new CreateMedicineCommand(new MedicineService()));
        commands.put("POST/pay-order", new PayOrderCommand(new OrderService()));
        commands.put("POST/cancel-order", new CancelOrderCommand(new OrderService()));
        
        //doctor commads
        commands.put("POST/create-prescription", new CreatePrescriptionCommand(new PrescriptionService()));
        commands.put("POST/reject-request", new RejectExtentionRequestCommand(new RequestService()));
        commands.put("POST/extend-prescription", new ExtendPrescriptionCommand(new PrescriptionService()));
    }
    public Command getCommand(String command) {
        if (commands == null) {
            init();
        }  
        return commands.get(command);
    }
}
