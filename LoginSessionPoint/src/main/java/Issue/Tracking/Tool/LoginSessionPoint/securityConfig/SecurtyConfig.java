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

import static Issue.Tracking.Tool.LoginSessionPoint.constants.MiscConfig.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurtyConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                 http.csrf().disable();


               http.formLogin()
               .loginPage("/SessionLogin")
                       .loginProcessingUrl("/LoginProcess")
                       .defaultSuccessUrl("/user");




        AuthenFilter authenFilter = new AuthenFilter(authenticationManagerBean());

        authenFilter.setFilterProcessesUrl("/SessionLogin");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/login/**", "/token/refresh/**").permitAll();
        http.authorizeRequests().antMatchers(GET, "/user/**").hasAnyAuthority(USER);
        http.authorizeRequests().antMatchers(POST, "/user/save/**").hasAnyAuthority(ADMIN);
        http.authorizeRequests().antMatchers(POST, "/GroupManager/save/**").hasAnyAuthority(ADMIN);
        http.authorizeRequests().antMatchers(POST, "/IssueDashboard/save/**").hasAnyAuthority( ALL_ROLES.split(splitter));
        http.authorizeRequests().antMatchers(POST, "/admin/**").hasAnyAuthority(ADMIN);
        http.authorizeRequests().anyRequest().authenticated();

        http.addFilter(authenFilter);
        http.addFilterBefore(new AuthoFilter(), UsernamePasswordAuthenticationFilter.class);


                                                                //******************   //Authoriz.
                                                                /// ///// HERE

    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws  Exception{

        return  super.authenticationManagerBean();

    }
}
