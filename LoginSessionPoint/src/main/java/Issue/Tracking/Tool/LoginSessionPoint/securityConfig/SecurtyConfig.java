package Issue.Tracking.Tool.LoginSessionPoint.securityConfig;



import Issue.Tracking.Tool.LoginSessionPoint.filter.ApiKeyAuthenticationFilter;
import Issue.Tracking.Tool.LoginSessionPoint.filter.AuthenFilter;
import Issue.Tracking.Tool.LoginSessionPoint.filter.AuthoFilter;
import Issue.Tracking.Tool.LoginSessionPoint.service.apiKeyPairService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;

import static Issue.Tracking.Tool.LoginSessionPoint.constants.MiscConfig.*;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@EnableWebSecurity
@RequiredArgsConstructor

public class SecurtyConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final apiKeyPairService apiKeyPairService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(11);



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }



    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.
                    antMatcher("/open/**").
                    csrf().disable().
                    sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                    and().addFilter(new ApiKeyAuthenticationFilter()).authorizeRequests().anyRequest().authenticated();
        }
    }


        @Configuration
        public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

            @Override
            protected void configure(HttpSecurity http) throws Exception {

                AuthenFilter authenFilter = new AuthenFilter(authenticationManagerBean());

                http.csrf().disable();
                // authenFilter.setFilterProcessesUrl("/login");
                http.sessionManagement().sessionCreationPolicy(STATELESS);
                http.authorizeRequests();
                http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll();
                http.authorizeRequests().antMatchers(GET, "/user/**").hasAnyAuthority(USER, ADMIN);
                http.authorizeRequests().antMatchers(POST, "/user/save/**").hasAnyAuthority(ADMIN);
                http.authorizeRequests().antMatchers(POST, "/GroupManager/save/**").hasAnyAuthority(ADMIN);
                http.authorizeRequests().antMatchers(POST, "/GroupManager").hasAnyAuthority(ALL_ROLES.split(splitter));
                http.authorizeRequests().antMatchers(POST, "/IssueDashboard/save/**").hasAnyAuthority(ALL_ROLES.split(splitter));
                http.authorizeRequests().antMatchers(POST, "/admin/**").hasAnyAuthority(ADMIN);
                http.authorizeRequests().anyRequest().authenticated();

                http.addFilter(authenFilter);
                http.addFilterBefore(new AuthoFilter(), UsernamePasswordAuthenticationFilter.class);
                http.formLogin()
                        .defaultSuccessUrl("/user");


                //******************   //Authoriz.
                /// ///// HERE

            }
        }


        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {

            return super.authenticationManagerBean();

        }



    }
