package Issue.Tracking.Tool.LoginSessionPoint.securityConfig;



import Issue.Tracking.Tool.LoginSessionPoint.filter.ApiKeyAuthenticationFilter;
import Issue.Tracking.Tool.LoginSessionPoint.filter.AuthenFilter;
import Issue.Tracking.Tool.LoginSessionPoint.filter.AuthoFilter;
import Issue.Tracking.Tool.LoginSessionPoint.service.apiKeyPairService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static Issue.Tracking.Tool.LoginSessionPoint.constants.MiscConfig.*;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;




@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurtyConfig extends WebSecurityConfigurerAdapter {


    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.
                    antMatcher("/open/**").
                    csrf().disable().
                    sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                    and().addFilterBefore(new ApiKeyAuthenticationFilter(), AnonymousAuthenticationFilter.class).authorizeRequests().anyRequest().authenticated();
        }
    }


        @Configuration

        @Order(2)
        public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

            private final UserDetailsService userDetailsService;
            private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(11);

            public FormLoginWebSecurityConfigurerAdapter(UserDetailsService userDetailsService) {
                this.userDetailsService = userDetailsService;
            }

            @Override
            protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
            }

            @Override
            protected void configure(HttpSecurity http) throws Exception {

                AuthenFilter authenFilter = new AuthenFilter(authenticationManagerBean());
                authenFilter.setAuthenticationFailureHandler(authenticationFailureHandler());

                http.cors();

                http.httpBasic().disable();


                http.csrf().disable();

                http.sessionManagement().sessionCreationPolicy(STATELESS);
                http.authorizeRequests();
                http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll();
                http.authorizeRequests().antMatchers(GET, "/user/**").hasAnyAuthority(USER, ADMIN,GROUP_LEADER,"GET_USER");
                http.authorizeRequests().antMatchers(POST, "/user/save/**").hasAnyAuthority(ADMIN,"POST_USER");
                http.authorizeRequests().antMatchers(PUT,"/user/update/**").hasAnyAuthority(ADMIN,"PUT_USER");
                http.authorizeRequests().antMatchers(PUT,"/user/changePass/**").hasAnyAuthority(USER,ADMIN,GROUP_LEADER,
                        "PUT_USER");
                http.authorizeRequests().antMatchers(POST,"/users").hasAnyAuthority(USER,ADMIN,GROUP_LEADER,"POST_USERS");

                http.authorizeRequests().antMatchers(DELETE,"/user/delete/**").hasAnyAuthority(ADMIN,USER,GROUP_LEADER,"DELETE_USER");
                http.authorizeRequests().antMatchers(GET,"/user/^get").hasAnyAuthority(ADMIN,GROUP_LEADER,USER,"GET_USER");


                http.authorizeRequests().antMatchers(POST, "/GroupManager").hasAnyAuthority(USER,ADMIN,GROUP_LEADER,"POST_GROUPMANAGER");
                http.authorizeRequests().antMatchers(GET, "/GroupManager/**").hasAnyAuthority(USER,ADMIN,GROUP_LEADER,"GET_GROUPMANAGER");
                http.authorizeRequests().antMatchers(POST, "/GroupManager/save/**").hasAnyAuthority(ADMIN,GROUP_LEADER,"POST_GROUPMANAGER");
                http.authorizeRequests().antMatchers(GET,"/GroupManager/^get").hasAnyAuthority(ADMIN,GROUP_LEADER,USER,"GET_GROUPMANAGER");
                http.authorizeRequests().antMatchers(DELETE,"/GroupManager/delete/**").hasAnyAuthority(ADMIN,"DELETE_GROUPMANAGER");
                http.authorizeRequests().antMatchers(POST, "/GroupManager/addUser").hasAnyAuthority(ADMIN,GROUP_LEADER,"POST_GROUPMANAGER");

                http.authorizeRequests().antMatchers(PUT, "/GroupManager/changeLeader/**").hasAnyAuthority(ADMIN,"PUT_GROUPMANAGER");
                http.authorizeRequests().antMatchers(PUT, "/GroupManager/AddUser/**").hasAnyAuthority(ADMIN,GROUP_LEADER,"PUT_GROUPMANAGER");
                http.authorizeRequests().antMatchers(DELETE, "/GroupManager/DelUser/**").hasAnyAuthority(ADMIN,GROUP_LEADER,"DELETE_GROUPMANAGER");

                http.authorizeRequests().antMatchers(GET, "/IssueDashboard/**").hasAnyAuthority(USER,ADMIN,GROUP_LEADER,"GET_ISSUEDASHBOARD");
                http.authorizeRequests().antMatchers(POST, "/IssueDashboard/save/**").hasAnyAuthority(USER,ADMIN,GROUP_LEADER,"POST_ISSUEDASHBOARD");
                http.authorizeRequests().antMatchers(DELETE, "/IssueDashboard/delete/**").hasAnyAuthority(ADMIN,GROUP_LEADER,"DELETE_ISSUEDASHBOARD");
                http.authorizeRequests().antMatchers(PUT,"/IssueDashboard/admin/update/**").hasAnyAuthority(ADMIN,"PUT_ISSUEDASHBOARD");
                http.authorizeRequests().antMatchers(PUT,"/IssueDashboard/user/update/**").hasAnyAuthority(USER,ADMIN,GROUP_LEADER,"PUT_ISSUEDASHBOARD");
                http.authorizeRequests().antMatchers(PUT,"/IssueDashboard/leader/update/**").hasAnyAuthority(GROUP_LEADER,ADMIN,"PUT_ISSUEDASHBOARD");
                http.authorizeRequests().antMatchers(GET,"/IssueDashboard/^get").hasAnyAuthority(ADMIN,GROUP_LEADER,USER,"GET_ISSUEDASHBOARD");
                http.authorizeRequests().antMatchers(GET,"/IssueDashboard/getSol/**").hasAnyAuthority(ADMIN,GROUP_LEADER,USER,"GET_ISSUEDASHBOARD");


                http.authorizeRequests().antMatchers(GET,"/Extras/**/^get").hasAnyAuthority(ADMIN,GROUP_LEADER,USER,"GET_EXTRAS");
                http.authorizeRequests().antMatchers(PUT,"/Extras/**/update").hasAnyAuthority(ADMIN,GROUP_LEADER,"PUT_EXTRAS");
                http.authorizeRequests().antMatchers(POST,"/Extras/**/save").hasAnyAuthority(ADMIN,GROUP_LEADER,"POST_EXTRAS");
                http.authorizeRequests().antMatchers(DELETE,"/Extras/**/delete").hasAnyAuthority(ADMIN,GROUP_LEADER,"DELETE_EXTRAS");


                http.authorizeRequests().antMatchers(POST, "/admin/**").hasAnyAuthority(ADMIN);
                http.authorizeRequests().anyRequest().authenticated();

                http.addFilter(authenFilter);


                http.addFilterBefore(new AuthoFilter(), UsernamePasswordAuthenticationFilter.class);


            }

            @Bean
            public AuthenticationFailureHandler authenticationFailureHandler() {
                return new CustomAuthenticationFailureHandler();
            }
    }




        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {


            return super.authenticationManagerBean();

        }





    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
