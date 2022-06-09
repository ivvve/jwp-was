package webserver;

public class RequestLine {
    public static RequestLine parse(final String httpRequestStartLine) {
        final String[] tokens = httpRequestStartLine.split(" ");
        final String method = tokens[0];

        final String[] pathAndQueryString = tokens[1].split("\\?");
        final String path = pathAndQueryString[0];
        final String queryString = (1 < pathAndQueryString.length) ? pathAndQueryString[1] : "";

        final String[] protocolAndVersion = tokens[2].split("/");
        final String protocol = protocolAndVersion[0];
        final String version = protocolAndVersion[1];

        return new RequestLine(method, path, queryString, protocol, version);
    }

    private RequestLine(final String method, final String path, final String queryString,
                        final String protocol, final String version) {
        this.method = method;
        this.path = path;
        this.queryString = queryString;
        this.protocol = protocol;
        this.version = version;
    }

    private final String method;
    private final String path;
    private final String queryString;
    private final String protocol;
    private final String version;

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public String getQueryString() {
        return this.queryString;
    }

    public String getProtocole() {
        return this.protocol;
    }

    public String getVersion() {
        return this.version;
    }
}
