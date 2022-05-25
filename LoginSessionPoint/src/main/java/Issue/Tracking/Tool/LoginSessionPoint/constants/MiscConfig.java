package Issue.Tracking.Tool.LoginSessionPoint.constants;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MiscConfig {

    public final static String USER = "ROLE_USER";
    public final static String ADMIN = "ROLE_ADMIN";
    public final static String GROUP_LEADER = "ROLE_GROUP_LEADER";
    public final static String splitter = ",";
    public static String ALL_ROLES = USER + splitter +  ADMIN + splitter +  GROUP_LEADER;
    public static List<String> DEFAULT_ROLES = List.of(USER,ADMIN,GROUP_LEADER);
    public static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",

            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/token/refresh",
            "user/register",
            "/SessionLogin","/LoginProcess" ,"/login/**", "/token/refresh/**", "/swagger-ui.html#/**"
    };


}
