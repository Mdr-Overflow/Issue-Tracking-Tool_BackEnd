package Issue.Tracking.Tool.LoginSessionPoint;


import Issue.Tracking.Tool.LoginSessionPoint.domain.*;
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

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
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

	CommandLineRunner run(UserService userService, apiKeyPairService apiKeyPairService, RoleService roleService, UserGroupService userGroupService,
						  PrivService privService, PriorityService priorityService , StatusService statusService , TypeService typeService ) {
		return args -> {


			Date now = Date.from(Instant.now());

			priorityService.SavePriority(new Priority(1L,"VERY LOW",null,null));
			priorityService.SavePriority(new Priority(2L,"LOW",null,null));
			priorityService.SavePriority(new Priority(3L,"MEDIUM",null,null));
			priorityService.SavePriority(new Priority(4L,"HIGH",null,null));
			priorityService.SavePriority(new Priority(5L,"VERY HIGH",null,null));



			statusService.SaveStatus(new Status(1L,"TO DO",null,null));
			statusService.SaveStatus(new Status(2L,"IN PROGRESS",null,null));
			statusService.SaveStatus(new Status(3L,"IN REVIEW",null,null));
			statusService.SaveStatus(new Status(4L,"BLOCKED",null,null));
			statusService.SaveStatus(new Status(5L,"DONE",null,null));



			typeService.SaveType(new Type(1L,"text",null,null));
			typeService.SaveType(new Type(2L,"file",null,null));


			roleService.saveRole(new Role(1L, "ROLE_USER", List.of(privService.createPrivilegeIfNotFound("ROLE_USER")),now ,null));
			roleService.saveRole(new Role(2L, "ROLE_ADMIN",List.of(privService.createPrivilegeIfNotFound("ROLE_ADMIN")),now,null));
			roleService.saveRole(new Role(3L, "ROLE_SUPER_ADMIN",List.of(privService.createPrivilegeIfNotFound("ROLE_SUPER_ADMIN"),privService.createPrivilegeIfNotFound("ROLE_ADMIN")),now,null));
			roleService.saveRole(new Role(4L, "ROLE_USER2",List.of(privService.findByName("GET_USER")),now,null));



			userService.saveUser(new APIUser(10L, "John Travolta", "1234","Jt@asdas.com","John", new ArrayList<>(),  new ArrayList<>(),null,null));
			userService.saveUser(new APIUser(11L, "Will Smith","1234","w@asdas.com","Will",new ArrayList<>(), new ArrayList<>(),null,null));
			userService.saveUser(new APIUser(12L, "Jim Carry", "1234", "fg@asdas.com","Jim",new ArrayList<>(), new ArrayList<>(),null,null));
			userService.saveUser(new APIUser(13L, "Arnold Schwarzenegger", "1234","sd@asdas.com","Arnold", new ArrayList<>(),new ArrayList<>(),null,null));
			userService.saveUser(new APIUser(14L, "admin", "1234","Jtyyy@asdas.com","sd", new ArrayList<>(),  new ArrayList<>(),null,null));



			userService.addRoleToUser("admin","ROLE_ADMIN");
			userService.addRoleToUser("John Travolta","ROLE_USER");
			userService.addRoleToUser("Jim Carry", "ROLE_ADMIN");
			userService.addRoleToUser("Arnold Schwarzenegger", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("Arnold Schwarzenegger", "ROLE_ADMIN");
			userService.addRoleToUser("Arnold Schwarzenegger", "ROLE_USER");



		};
	}


}
