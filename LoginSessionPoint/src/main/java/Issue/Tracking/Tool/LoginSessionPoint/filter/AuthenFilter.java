package Issue.Tracking.Tool.LoginSessionPoint.filter;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
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



    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        log.info("Auth. result is  {}", authResult.getPrincipal());

        Collection<Role> AuthRoles = new ArrayList<>();

                ((UserDetails) authResult.getPrincipal()).getAuthorities().forEach(SimpleGrantedAuthority -> {
            AuthRoles.add(new Role(null  ,SimpleGrantedAuthority.toString(), null,null));

        } );
        log.info("Roles are  {}", AuthRoles);
        APIUser user = new APIUser((Long) null,((UserDetails)authResult.getPrincipal()).getUsername(),
                                                ((UserDetails)authResult.getPrincipal()).getPassword(), null,null,
                                                    AuthRoles  ,new ArrayList<>(),null,null);


        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10L * 6000 * TOKEN_EXPIRATION_TIME_MINS))  // Token Expiress at 20 mins
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .sign(algorithm);


        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10L * 6000 * REFRESH_TOKEN_EXPIRATION_TIME_MINS))  // Token Expiress at 60 mins
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .sign(algorithm);

        /*response.setHeader("access_token", access_token);
        response.setHeader("refresh_token", refresh_token);*/

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
/*
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
       // super.unsuccessfulAuthentication(request, response, failed);
        log.info("Illegal Credentials");


        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Authentication Failed");

        // for catching failed auth request  -> block acc. , etc
    }*/
}
