package Issue.Tracking.Tool.LoginSessionPoint;


import Issue.Tracking.Tool.LoginSessionPoint.Domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.Domain.Role;
import Issue.Tracking.Tool.LoginSessionPoint.Service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@SpringBootApplication
@RestController

public class LoginSessionPointApplication {

	public static void main(String[] args) {

		SpringApplication.run(LoginSessionPointApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}




	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new APIUser(null, "John Travolta", "1234", new ArrayList<>()));
			userService.saveUser(new APIUser(null, "Will Smith","1234", new ArrayList<>()));
			userService.saveUser(new APIUser(null, "Jim Carry", "1234", new ArrayList<>()));
			userService.saveUser(new APIUser(null, "Arnold Schwarzenegger", "1234", new ArrayList<>()));

			userService.addRoleToUser("john","ROLE_USER");
			userService.addRoleToUser("jim", "ROLE_ADMIN");
			userService.addRoleToUser("arnold", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("arnold", "ROLE_ADMIN");
			userService.addRoleToUser("arnold", "ROLE_USER");
		};
	}

}
