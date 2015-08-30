package singularity.walkineasy.http;

/**
 * @author Sharath Pandeshwar
 * @since 29/08/15.
 */
public class HttpConstants {

    public static final String HEADER_AUTHORIZATION_FORMAT = "Token token=\"%s\", email=\"%s\"";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_DEVICE_TOKEN = "DeviceToken";
    public static final String HEADER_USER_TYPE = "UserType";

    /**
     * The API version in use by the app
     */
    private static final int API_VERSION = 1;
    private static Server SERVER = Server.LOCAL;

    public static String getApiBaseUrl() {
        return SERVER.mUrl;
    }


    /**
     * Enum to switch between servers
     */
    private enum Server {

        LOCAL("http://192.168.10.106:8888"),

        DEV("http://219.91.200.100:8080/api"),

        SSL("https://www.ftator.com/api"),

        PRODUCTION("http://api.ftator.com/api/v");

        public final String mUrl;


        Server(final String url) {
            mUrl = url;
        }
    }

    /**
     * Empty interface to remember all API endpoints
     */
    public static interface ApiEndpoints {
        public static final String LIST_MY_AGENTS = "/Investor/GetMyDistributors";
    }


    /**
     * Empty interface to store http request identifiers
     */
    public static interface RequestId {
        public static final int GET_MY_ADVISORS = 100;
        public static final int GET_FORM_DETAILS = 101;
    }

}
