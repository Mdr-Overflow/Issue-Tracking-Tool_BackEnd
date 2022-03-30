package Issue.Tracking.Tool.LoginSessionPoint.securityConfig;


import Issue.Tracking.Tool.LoginSessionPoint.filter.CustomAuthenticationFilter;
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

        http.authorizeRequests()
                .antMatchers("/LoginSession")

                .permitAll();
                //.antMatchers("/*");


               http.formLogin()
               .loginPage("/LoginSession")
                       //.loginProcessingUrl("/LoginProcess")
                       .defaultSuccessUrl("/user");


        http.sessionManagement().sessionCreationPolicy(STATELESS);
        //http.authorizeRequests().antMatchers("/LoginSessionPoint").permitAll();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/index").permitAll();
        http.authorizeRequests().antMatchers("/LoginProcess").permitAll();
        //http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers(GET, "/user/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(POST, "/user/save/**").hasAnyAuthority("ROLE_ADMIN");
        //http.authorizeRequests().anyRequest().permitAll(); // sds
        http.authorizeRequests().anyRequest().authenticated();
//        http.authorizeRequests().anyRequest().permitAll();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean())); // Authent.

        http.addFilterBefore(new CustomAuthenticationFilter(authenticationManagerBean()), UsernamePasswordAuthenticationFilter.class);
                                                                //******************   //Authoriz.
                                                                /// ///// HERE

    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws  Exception{

        return  super.authenticationManagerBean();

    }
}
