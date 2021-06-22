package com.example.topoftops.controller.command;

import com.example.topoftops.controller.command.impl.*;
import com.example.topoftops.model.service.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;
import java.util.concurrent.atomic.AtomicBoolean;


public class RequestHelper {
    private static final Logger logger = LogManager.getLogger();
    private static final AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static RequestHelper instance;
    private EnumMap<CommandType, Command> commands =
            new EnumMap<>(CommandType.class);
    private EnumMap<CommandType, AjaxCommand> ajaxCommands =
            new EnumMap<>(CommandType.class);
    private RequestHelper() {
        commands.put(CommandType.LOGIN, new LoginCommand(new LoginService()));
        commands.put(CommandType.LOGOUT, new LogoutCommand());
        commands.put(CommandType.DEFAULT, new NoCommand());
        commands.put(CommandType.TO_ADMIN_PANEL_PAGE, new ToAdminPanelPageCommand(new AdminService()));
        commands.put(CommandType.FIND_USERS_BY_LOGIN, new FindUsersByLoginCommand(new AdminService()));
        commands.put(CommandType.REGISTER, new RegisterCommand(new UserService()));
        commands.put(CommandType.TO_REGISTER_PAGE, new ToRegisterPageCommand());
        commands.put(CommandType.TO_LOGIN_PAGE, new ToLoginPageCommand());
        commands.put(CommandType.CHANGE_LOCAL, new ChangeLocalCommand());
        commands.put(CommandType.TO_CREATE_TOP_PAGE, new ToCreateTopPageCommand());
        commands.put(CommandType.CREATE_TOP, new CreateTopCommand(new CreateTopService()));
        commands.put(CommandType.CREATE_ITEM, new CreateItemCommand(new ItemService(),new TopService()));
        commands.put(CommandType.TO_TOP_PAGE, new ToTopPageCommand(new ItemService(), new TopService()));
        commands.put(CommandType.DELETE_ITEM, new DeleteItemCommand(new ItemService(), new TopService()));
        commands.put(CommandType.CONFIRM_REGISTRATION, new ConfirmRegistrationCommand(new UserService()));
        ajaxCommands.put(CommandType.ADD_REPORT, new AddReportAjaxCommand(new ReportService()));
        ajaxCommands.put(CommandType.ESTIMATE_AS_LIKE, new EstimateTopAjaxCommand(new MarkService(),new TopService(),5));
        ajaxCommands.put(CommandType.ESTIMATE_AS_DISLIKE, new EstimateTopAjaxCommand(new MarkService(),new TopService(),-5));

    }

    public Command getCommand(String commandName) {
        Command command;
        CommandType enumCommandName;
        if (commandName == null) {
            command = new NoCommand();
            return command;
        }
        try {
            enumCommandName = CommandType.valueOf(commandName.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR,"no such command {}", commandName);
            enumCommandName = CommandType.DEFAULT;
        }
        command = commands.get(enumCommandName);
        return command;
    }

    public AjaxCommand getAjaxCommand(String commandName) {
        AjaxCommand command;
        CommandType enumCommandName;
        if (commandName == null) {
            command = new NoAjaxCommand();
            return command;
        }
        try {
            enumCommandName = CommandType.valueOf(commandName.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR,"no such command {}", commandName);
            enumCommandName = CommandType.DEFAULT;
        }
        command = ajaxCommands.get(enumCommandName);
        return command;
    }


    public static RequestHelper getInstance() {
        while(instance == null) {
            if (isInitialized.compareAndSet(false, true)) {
                instance = new RequestHelper();
            }
        }
        return instance;
    }
}
