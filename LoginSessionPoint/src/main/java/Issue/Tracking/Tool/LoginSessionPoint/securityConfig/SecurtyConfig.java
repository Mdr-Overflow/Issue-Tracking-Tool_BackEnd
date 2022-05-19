package Issue.Tracking.Tool.LoginSessionPoint.securityConfig;



import Issue.Tracking.Tool.LoginSessionPoint.filter.ApiKeyAuthenticationFilter;
import Issue.Tracking.Tool.LoginSessionPoint.filter.AuthenFilter;
import Issue.Tracking.Tool.LoginSessionPoint.filter.AuthoFilter;
import Issue.Tracking.Tool.LoginSessionPoint.service.apiKeyPairService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static Issue.Tracking.Tool.LoginSessionPoint.constants.MiscConfig.*;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
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
                http.cors();


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
                //http.formLogin()
                       // .defaultSuccessUrl("/user");


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
