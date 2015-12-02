package com.ilkun.hospital.controller;

import com.ilkun.hospital.command.AddConcretePrescriptionCommand;
import com.ilkun.hospital.command.AddPatientCommand;
import com.ilkun.hospital.command.Command;
import com.ilkun.hospital.command.DischargeCommand;
import com.ilkun.hospital.command.LoginCommand;
import com.ilkun.hospital.command.LogoutCommand;
import com.ilkun.hospital.command.NoCommand;
import com.ilkun.hospital.command.PerformPrescriptionCommand;
import com.ilkun.hospital.command.RegisterCommand;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

/**
 * This class encapsulates work with searching of
 * appropriate Command pattern object.
 *
 * @author vipuser
 */
public class RequestHelper {

    private final static RequestHelper instance = new RequestHelper();
    private final HashMap<String, Command> commands = new HashMap<>();

    private RequestHelper() {
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("register", new RegisterCommand());
        commands.put("addConcretePrescription", new AddConcretePrescriptionCommand());
        commands.put("addPatient", new AddPatientCommand());
        commands.put("discharge", new DischargeCommand());
        commands.put("performPrescription", new PerformPrescriptionCommand());
    }

    public static RequestHelper getInstance() {
        return instance;
    }

    public Command getCommand(HttpServletRequest request) {
        String action = request.getParameter("command");
        Command command = commands.get(action);
        if (command == null) {
            command = new NoCommand();
        }
        return command;
    }
}
