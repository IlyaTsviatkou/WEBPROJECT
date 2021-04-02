package com.example.WEB_App.command;

import com.example.WEB_App.command.impl.LoginCommand;
import com.example.WEB_App.command.impl.NoCommand;
import com.example.WEB_App.command.impl.RegisterCommand;
import com.example.WEB_App.command.impl.FindAllUsersCommand;
import com.example.WEB_App.service.LoginService;
import com.example.WEB_App.service.RegisterService;
import com.example.WEB_App.service.SortService;

import java.util.EnumMap;

public class RequestHelper {
    private static RequestHelper instance = null;
    EnumMap<CommandType, Command> commands =
            new EnumMap<>(CommandType.class);
    private RequestHelper() {
        commands.put(CommandType.LOGIN, new LoginCommand(new LoginService()));
        commands.put(CommandType.FIND_ALL_USERS, new FindAllUsersCommand(new SortService()));
        commands.put(CommandType.REGISTER, new RegisterCommand(new RegisterService()));
    }
    public Command getCommand(String commandName ) {
        CommandType enumCommandName = CommandType.valueOf(commandName.toUpperCase());
        Command command = commands.get(enumCommandName);
        if (command == null) {
            command = new NoCommand();
        }
        return command;
    }

    public static RequestHelper getInstance() {
        if (instance == null) {
            instance = new RequestHelper();
        }
        return instance;
    }
}
