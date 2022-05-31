package Issue.Tracking.Tool.LoginSessionPoint.filter;

import Issue.Tracking.Tool.LoginSessionPoint.constants.SetupDataLoader;
import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Privilege;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import Issue.Tracking.Tool.LoginSessionPoint.domain.apiKeyPair;
import Issue.Tracking.Tool.LoginSessionPoint.repo.PrivRepo;
import Issue.Tracking.Tool.LoginSessionPoint.repo.RoleRepo;
import Issue.Tracking.Tool.LoginSessionPoint.service.PrivService;
import Issue.Tracking.Tool.LoginSessionPoint.service.RoleService;
import Issue.Tracking.Tool.LoginSessionPoint.service.apiKeyPairService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static Issue.Tracking.Tool.LoginSessionPoint.constants.MiscConfig.REFRESH_TOKEN_EXPIRATION_TIME_MINS;
import static Issue.Tracking.Tool.LoginSessionPoint.constants.MiscConfig.TOKEN_EXPIRATION_TIME_MINS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j

public class AuthenFilter extends UsernamePasswordAuthenticationFilter {


    private  final  AuthenticationManager  authenticationManager;



    public AuthenFilter(AuthenticationManager authenticationManager) {
      this.authenticationManager = authenticationManager;

    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("Attempting auth");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("Username is : {} , Pass is  {} :", username , password );
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(authenticationToken);
    } //


    public String ConstructorOfRole(String s){

        Pattern pattern = Pattern.compile("ROLE_(.*?),");
        log.info(s.toString());
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
           return "ROLE_"+ matcher.group();
        }
        else return "NULL";
    }




    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        log.info("Auth. result is  {}", authResult.getPrincipal());


        ServletContext servletContext = request.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        RoleService roleService = webApplicationContext.getBean(RoleService.class);
        PrivService privService = webApplicationContext.getBean(PrivService.class);
        apiKeyPairService apiKeyPairService   = webApplicationContext.getBean(apiKeyPairService.class);

        Collection<Role> AuthRoles = new ArrayList<>();


        String public_key = null;
        if(apiKeyPairService.get_All() != null) {
            public_key = apiKeyPairService.generate();
        }
        else public_key = apiKeyPairService.get_All().get(0).getApiKey();




                ((UserDetails) authResult.getPrincipal()).getAuthorities().forEach(SimpleGrantedAuthority -> {
            AuthRoles.add(new Role(null  ,SimpleGrantedAuthority.toString(), roleService.getRole(SimpleGrantedAuthority.toString()).getPrivileges(), null,null));

        } );

        log.info("Roles are  {}", AuthRoles);
        APIUser user = new APIUser((Long) null,((UserDetails)authResult.getPrincipal()).getUsername(),
                                                ((UserDetails)authResult.getPrincipal()).getPassword(), null,null,
                                                    AuthRoles  ,new ArrayList<>(),null,null);


        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        Set<String> claims = AuthRoles.stream().map(role -> role.getPrivileges().stream().map(Privilege::getName).collect(Collectors.toList())).collect(Collectors.toList()).stream().flatMap(Collection::stream).collect(Collectors.toSet()); //.map(strings -> strings)//.collect(Collectors.joining(","));

        List<String>  claimsOld =  AuthRoles.stream().map(Role::getName).collect(Collectors.toList());  ; //claims.stream().filter(string -> privService.findByName(string) != null).collect(Collectors.toList());

        List<String>  claimsNew = new ArrayList<>(claims);


        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10L * 6000 * TOKEN_EXPIRATION_TIME_MINS))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", claimsNew)
                .sign(algorithm);



        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10L * 6000 * REFRESH_TOKEN_EXPIRATION_TIME_MINS))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", claimsNew)
                .sign(algorithm);



        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

}
