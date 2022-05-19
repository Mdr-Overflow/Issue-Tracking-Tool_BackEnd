package Issue.Tracking.Tool.LoginSessionPoint.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
<<<<<<< HEAD
=======
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
>>>>>>> cec9bd6474726f4926e787d2601eb2e9e24f17fe
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration
{
    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200");
            }
        };
    }
}