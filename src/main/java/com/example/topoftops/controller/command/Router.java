package com.example.topoftops.controller.command;

/**
 * Describes the router
 *
 * @author Ilya Tsvetkov
 */
public class Router {

    /**
     * Describes all route types
     *
     * @author Ilya Tsvetkov
     */
    public enum RouteType {
        FORWARD, REDIRECT
    }

    private String pagePath;
    private RouteType routeType;


    /**
     * Constructs a new Router with the specified page path and route type
     *
     * @param pagePath  {@link PagePath} path to page
     * @param routeType {@link RouteType} route type
     */
    public Router(String pagePath, RouteType routeType) {
        this.pagePath = pagePath;
        this.routeType = routeType;
    }

    public String getPagePath() {
        return pagePath;
    }

    public RouteType getRouteType() {
        return routeType;
    }
}
