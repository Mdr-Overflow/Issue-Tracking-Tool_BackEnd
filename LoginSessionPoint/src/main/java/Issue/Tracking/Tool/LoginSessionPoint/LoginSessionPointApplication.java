package Issue.Tracking.Tool.LoginSessionPoint;


import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import Issue.Tracking.Tool.LoginSessionPoint.service.UserService;
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
			userService.saveRole(new Role(1L, "ROLE_USER",null,null));
			userService.saveRole(new Role(2L, "ROLE_ADMIN",null,null));
			userService.saveRole(new Role(3L, "ROLE_SUPER_ADMIN",null,null));

			userService.saveUser(new APIUser(1L, "John Travolta", "1234", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),null,null));
			userService.saveUser(new APIUser(2L, "Will Smith","1234",new ArrayList<>(),new ArrayList<>(), new ArrayList<>(),null,null));
			userService.saveUser(new APIUser(3L, "Jim Carry", "1234", new ArrayList<>(),new ArrayList<>(), new ArrayList<>(),null,null));
			userService.saveUser(new APIUser(4L, "Arnold Schwarzenegger", "1234", new ArrayList<>(),new ArrayList<>(), new ArrayList<>(),null,null));
             // IF NAME NOT IN DB  -> CRASH
			userService.addRoleToUser("John Travolta","ROLE_USER");
			userService.addRoleToUser("Jim Carry", "ROLE_ADMIN");
			userService.addRoleToUser("Arnold Schwarzenegger", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("Arnold Schwarzenegger", "ROLE_ADMIN");
			userService.addRoleToUser("Arnold Schwarzenegger", "ROLE_USER");
		};
	}

}
