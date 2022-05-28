package Issue.Tracking.Tool.LoginSessionPoint.constants;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static Issue.Tracking.Tool.LoginSessionPoint.util.RoleUtils.StringToBoolean;

public class MiscConfig {

    public final static String USER = "ROLE_USER";
    public final static String ADMIN = "ROLE_ADMIN";
    public final static String GROUP_LEADER = "ROLE_GROUP_LEADER";
    public final static String splitter = ",";

    public final static Integer TOKEN_EXPIRATION_TIME_MINS = 600;
    public final static Integer REFRESH_TOKEN_EXPIRATION_TIME_MINS = 600;

    public final static Map<String,Boolean> CUSTOM_SEARCH_TERMS = Stream.of(new String[][] {
            { "final", "true" },
            { "not final", "false" },
            { "accepted", "true" },
            { "not accepted", "false" },
    }).collect(Collectors.toMap(data -> data[0], data -> StringToBoolean(data[1]) ));

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
            "/user/register",
            "/SessionLogin","/LoginProcess" ,"/login/**", "/token/refresh/**", "/swagger-ui.html#/**"
    };


}
