package Issue.Tracking.Tool.LoginSessionPoint;


import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Privilege;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import Issue.Tracking.Tool.LoginSessionPoint.domain.UserGroup;
import Issue.Tracking.Tool.LoginSessionPoint.repo.PrivRepo;
import Issue.Tracking.Tool.LoginSessionPoint.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
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

	CommandLineRunner run(UserService userService, apiKeyPairService apiKeyPairService, RoleService roleService, UserGroupService userGroupService, PrivService privService) {
		return args -> {
			/*
			roleService.saveRole(new Role(1L, "ROLE_USER", List.of(privService.findByName("ROLE_USER")),null,null));
			roleService.saveRole(new Role(2L, "ROLE_ADMIN",List.of(privService.findByName("ROLE_ADMIN")),null,null));
			roleService.saveRole(new Role(3L, "ROLE_SUPER_ADMIN",List.of(privService.findByName("ROLE_SUPER_ADMIN")),null,null));
*/

			roleService.saveRole(new Role(1L, "ROLE_USER", List.of(privService.createPrivilegeIfNotFound("ROLE_USER")),null,null));
			roleService.saveRole(new Role(2L, "ROLE_ADMIN",List.of(privService.createPrivilegeIfNotFound("ROLE_ADMIN")),null,null));
			roleService.saveRole(new Role(3L, "ROLE_SUPER_ADMIN",List.of(privService.createPrivilegeIfNotFound("ROLE_SUPER_ADMIN")),null,null));
			roleService.saveRole(new Role(4L, "ROLE_USER2",List.of(privService.findByName("GET_USER")),null,null));

/*
			roleService.saveRole(new Role(1L, "ROLE_USER",null,null));
			roleService.saveRole(new Role(2L, "ROLE_ADMIN",null,null));
			roleService.saveRole(new Role(3L, "ROLE_SUPER_ADMIN",null,null));
			roleService.saveRole(new Role(4L, "ROLE_USER2",null,null));
*/
			//userGroupService.saveGroup(new UserGroup(1L,"DEFAULT",null,new ArrayList<APIUser>(),new APIUser(),null));
/*
			userService.saveUser(new APIUser(1L, "John Travolta", "1234","Jt@asdas.com","John", new ArrayList<>(),  new ArrayList<>(),new UserGroup(), new UserGroup(),null,null));
			userService.saveUser(new APIUser(2L, "Will Smith","1234","w@asdas.com","Will",new ArrayList<>(), new ArrayList<>(),new UserGroup(),new UserGroup(),null,null));
			userService.saveUser(new APIUser(3L, "Jim Carry", "1234", "fg@asdas.com","Jim",new ArrayList<>(), new ArrayList<>(),new UserGroup(),new UserGroup(),null,null));
			userService.saveUser(new APIUser(4L, "Arnold Schwarzenegger", "1234","sd@asdas.com","Arnold", new ArrayList<>(), new ArrayList<>(),new UserGroup(),null,null,null));
			userService.saveUser(new APIUser(5L, "admin", "1234","Jt@asdas.com","sd", new ArrayList<>(),  new ArrayList<>(),new UserGroup(),new UserGroup(),null,null));
			*/


			userService.saveUser(new APIUser(10L, "John Travolta", "1234","Jt@asdas.com","John", new ArrayList<>(),  new ArrayList<>(),null,null));
			userService.saveUser(new APIUser(11L, "Will Smith","1234","w@asdas.com","Will",new ArrayList<>(), new ArrayList<>(),null,null));
			userService.saveUser(new APIUser(12L, "Jim Carry", "1234", "fg@asdas.com","Jim",new ArrayList<>(), new ArrayList<>(),null,null));
			userService.saveUser(new APIUser(13L, "Arnold Schwarzenegger", "1234","sd@asdas.com","Arnold", new ArrayList<>(),new ArrayList<>(),null,null));
			userService.saveUser(new APIUser(14L, "admin", "1234","Jtyyy@asdas.com","sd", new ArrayList<>(),  new ArrayList<>(),null,null));


			// IF NAME NOT IN DB  -> CRASH




			userService.addRoleToUser("admin","ROLE_ADMIN");
			userService.addRoleToUser("John Travolta","ROLE_USER");
			userService.addRoleToUser("Jim Carry", "ROLE_ADMIN");
			userService.addRoleToUser("Arnold Schwarzenegger", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("Arnold Schwarzenegger", "ROLE_ADMIN");
			userService.addRoleToUser("Arnold Schwarzenegger", "ROLE_USER");

			apiKeyPairService.generate();

		};
	}


}
