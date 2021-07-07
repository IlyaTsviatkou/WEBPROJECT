package com.example.topoftops.controller.command;

/**
 * Describes all command types
 *
 * @author Ilya Tsvetkov
 */
public enum CommandType {
    LOGIN("ANY"),
    FIND_USERS_BY_LOGIN("ADMIN"),
    TO_LOGIN_PAGE("ANY"),
    TO_REGISTER_PAGE("ANY"),
    CHANGE_LOCAL("ANY"),
    LOGOUT("ANY"),
    DEFAULT("ANY"),
    REGISTER("ANY"),
    TO_CREATE_TOP_PAGE("AUTH"),
    TO_ADMIN_PANEL_PAGE("ADMIN"),
    CREATE_TOP("AUTH"),
    CREATE_ITEM("AUTH"),
    TO_TOP_PAGE("ANY"),
    DELETE_ITEM("AUTH"),
    CONFIRM_REGISTRATION("ANY"),
    ADD_REPORT("AUTH"),
    ESTIMATE_AS_LIKE("AUTH"),
    ESTIMATE_AS_DISLIKE("AUTH"),
    BLOCK_USER("ADMIN"),
    DELETE_USER("ADMIN"),
    UNBLOCK_USER("ADMIN"),
    GET_REPORTS("ADMIN"),
    ACCEPT_REPORT("ADMIN"),
    DECLINE_REPORT("ADMIN"),
    TO_TOPS_PAGE("ANY"),
    TO_PROFILE_PAGE("AUTH"),
    SEARCH_TOPS("ANY"),
    SEARCH_TOPS_RATING("ANY"),
    CREATE_ITEM_AJAX("AUTH"),
    CHANGE_ITEM_PLACE("AUTH"),
    UPDATE_ITEM("AUTH"),
    DELETE_TOP("AUTH");

    private String access;


    private CommandType(String access) {
        this.access = access;
    }

    /**
     * @return access {@link String}
     */
    public String getAccess() {
        return access;
    }

}
