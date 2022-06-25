package webserver.http;

import webserver.http.controller.Controller;
import webserver.http.controller.ResourceController;
import webserver.http.request.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestRouter {
    private static final ResourceController resourceController = new ResourceController();

    private final Map<String, Controller> controllers = new HashMap<>();

    public RequestRouter add(final String path, final Controller controller) {
        this.controllers.put(path, controller);
        return this;
    }

    public Controller getRoutedControllerOrNull(final HttpRequest request) {
        final Controller routedController = this.controllers.getOrDefault(request.getPath(), null);

        if (routedController != null) {
            return routedController;
        }

        if (this.isResourceRequest(request)) {
            return resourceController;
        }

        return null;
    }

    private boolean isResourceRequest(final HttpRequest request) {
        final String path = request.getPath();

        return path.endsWith(".html") ||
                path.endsWith(".css") ||
                path.endsWith(".js") ||
                path.endsWith(".ico") ||
                path.contains("/fonts");
    }
}
