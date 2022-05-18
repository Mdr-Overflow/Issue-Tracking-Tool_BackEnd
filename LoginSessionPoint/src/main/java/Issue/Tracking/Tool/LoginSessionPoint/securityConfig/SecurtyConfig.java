package Issue.Tracking.Tool.LoginSessionPoint.securityConfig;


import Issue.Tracking.Tool.LoginSessionPoint.filter.AuthenFilter;
import Issue.Tracking.Tool.LoginSessionPoint.filter.AuthoFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static Issue.Tracking.Tool.LoginSessionPoint.constants.MiscConfig.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurtyConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(11);



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

         http.cors();
        AuthenFilter authenFilter = new AuthenFilter(authenticationManagerBean());
        //security.httpBasic().disable();
        http.httpBasic().disable();



        http.csrf().disable();
        //http.authorizeRequests();

       // authenFilter.setFilterProcessesUrl("/login");
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests();
        http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll();

        //http.authorizeRequests().antMatchers("/SessionLogin","/LoginProcess" ,"/login/**", "/token/refresh/**", "/swagger-ui.html#/**").permitAll();

        http.authorizeRequests().antMatchers(GET, "/user/**").hasAnyAuthority(USER,ADMIN);
        http.authorizeRequests().antMatchers(POST, "/user/save/**").hasAnyAuthority(ADMIN);
        http.authorizeRequests().antMatchers(POST, "/GroupManager/save/**").hasAnyAuthority(ADMIN);
        http.authorizeRequests().antMatchers(POST, "/GroupManager").hasAnyAuthority(ALL_ROLES.split(splitter));
        http.authorizeRequests().antMatchers(POST, "/IssueDashboard/save/**").hasAnyAuthority( ALL_ROLES.split(splitter));
        http.authorizeRequests().antMatchers(POST, "/admin/**").hasAnyAuthority(ADMIN);
        http.authorizeRequests().anyRequest().authenticated();

        http.addFilter(authenFilter);
        http.addFilterBefore(new AuthoFilter(), UsernamePasswordAuthenticationFilter.class);

       // http.formLogin()
               // .defaultSuccessUrl("/user");


        //******************   //Authoriz.
                                                                /// ///// HERE

    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws  Exception{

        return  super.authenticationManagerBean();

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
