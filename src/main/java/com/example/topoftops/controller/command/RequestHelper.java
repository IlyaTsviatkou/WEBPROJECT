package com.example.topoftops.controller.command;

import com.example.topoftops.controller.command.impl.*;
import com.example.topoftops.model.service.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * RequestHelper is used for defining command
 *
 * @author Ilya Tsvetkov
 */
public class RequestHelper {
    private static final Logger logger = LogManager.getLogger();
    private static final AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static RequestHelper instance;
    private EnumMap<CommandType, Command> commands =
            new EnumMap<>(CommandType.class);
    private EnumMap<CommandType, AjaxCommand> ajaxCommands =
            new EnumMap<>(CommandType.class);

    private RequestHelper() {
        ItemService itemService = new ItemService();
        UserService userService = new UserService();
        MarkService markService = new MarkService();
        ReportService reportService = new ReportService();
        TopService topService = new TopService();
        commands.put(CommandType.LOGIN, new LoginCommand(userService));
        commands.put(CommandType.LOGOUT, new LogoutCommand());
        commands.put(CommandType.DEFAULT, new NoCommand());
        commands.put(CommandType.TO_ADMIN_PANEL_PAGE, new ToAdminPanelPageCommand());
        commands.put(CommandType.REGISTER, new RegisterCommand(userService));
        commands.put(CommandType.TO_REGISTER_PAGE, new ToRegisterPageCommand());
        commands.put(CommandType.TO_LOGIN_PAGE, new ToLoginPageCommand());
        commands.put(CommandType.CHANGE_LOCAL, new ChangeLocalCommand());
        commands.put(CommandType.TO_CREATE_TOP_PAGE, new ToCreateTopPageCommand());
        commands.put(CommandType.CREATE_TOP, new CreateTopCommand(topService, itemService));
        commands.put(CommandType.CREATE_ITEM, new CreateItemCommand(itemService, topService));
        commands.put(CommandType.TO_TOP_PAGE, new ToTopPageCommand(itemService, topService));
        commands.put(CommandType.DELETE_ITEM, new DeleteItemCommand(itemService, topService));
        commands.put(CommandType.CHANGE_ITEM_PLACE, new ChangeItemPlaceCommand(itemService, topService));
        commands.put(CommandType.UPDATE_ITEM, new UpdateItemCommand(itemService, topService));
        commands.put(CommandType.CONFIRM_REGISTRATION, new ConfirmRegistrationCommand(userService));
        commands.put(CommandType.TO_TOPS_PAGE, new ToTopsPageCommand(topService));
        commands.put(CommandType.TO_PROFILE_PAGE, new ToProfilePageCommand(topService));
        commands.put(CommandType.DELETE_TOP, new DeleteTopCommand(reportService, topService, itemService));
        ajaxCommands.put(CommandType.ADD_REPORT, new AddReportAjaxCommand(reportService));
        ajaxCommands.put(CommandType.ESTIMATE_AS_LIKE, new EstimateTopAjaxCommand(markService, topService, 5));
        ajaxCommands.put(CommandType.ESTIMATE_AS_DISLIKE, new EstimateTopAjaxCommand(markService, topService, -5));
        ajaxCommands.put(CommandType.BLOCK_USER, new BlockUserAjaxCommand(userService));
        ajaxCommands.put(CommandType.UNBLOCK_USER, new UnblockUserAjaxCommand(userService));
        ajaxCommands.put(CommandType.DELETE_USER, new DeleteUserAjaxCommand(userService));
        ajaxCommands.put(CommandType.GET_REPORTS, new FindReportsAjaxCommand(reportService));
        ajaxCommands.put(CommandType.ACCEPT_REPORT, new AcceptReportAjaxCommand(reportService, topService, itemService));
        ajaxCommands.put(CommandType.DECLINE_REPORT, new DeclineReportAjaxCommand(reportService));
        ajaxCommands.put(CommandType.SEARCH_TOPS, new SearchTopsAjaxCommand(topService));
        ajaxCommands.put(CommandType.SEARCH_TOPS_RATING, new SearchTopsWithRatingAjaxCommand(topService));
        ajaxCommands.put(CommandType.FIND_USERS_BY_LOGIN, new FindUsersByLoginAjaxCommand(userService));
    }

    /**
     * Defines command
     *
     * @param commandName {@link String} command name
     * @return {@link Command}
     */
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
            logger.log(Level.ERROR, "no such command {}", commandName);
            enumCommandName = CommandType.DEFAULT;
        }
        command = commands.get(enumCommandName);
        return command;
    }

    /**
     * Defines ajax command
     *
     * @param commandName {@link String} command name
     * @return {@link AjaxCommand}
     */
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
            logger.log(Level.ERROR, "no such command {}", commandName);
            enumCommandName = CommandType.DEFAULT;
        }
        command = ajaxCommands.get(enumCommandName);
        return command;
    }

    /**
     * Get instance
     *
     * @return {@link RequestHelper}
     */
    public static RequestHelper getInstance() {
        while (instance == null) {
            if (isInitialized.compareAndSet(false, true)) {
                instance = new RequestHelper();
            }
        }
        return instance;
    }
}
