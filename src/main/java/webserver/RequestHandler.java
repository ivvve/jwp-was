package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpMethod;
import webserver.http.controller.LoginController;
import webserver.http.controller.ResourceController;
import webserver.http.controller.SignUpController;
import webserver.http.controller.UserListController;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final HttpRequest httpRequest = new HttpRequest(in);
            final HttpResponse httpResponse = new HttpResponse(out);

            if (httpRequest.getMethod().equals(HttpMethod.POST) && httpRequest.getPath().equals("/user/create")) {
                new SignUpController().service(httpRequest, httpResponse);
                return;
            }

            if (httpRequest.getMethod().equals(HttpMethod.POST) && httpRequest.getPath().equals("/user/login")) {
                new LoginController().service(httpRequest, httpResponse);
                return;
            }

            if (httpRequest.getMethod().equals(HttpMethod.GET) && httpRequest.getPath().equals("/user/list")) {
                new UserListController().service(httpRequest, httpResponse);
                return;
            }

            new ResourceController().service(httpRequest, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
