package Issue.Tracking.Tool.LoginSessionPoint.constants;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Privilege;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import Issue.Tracking.Tool.LoginSessionPoint.repo.PrivRepo;
import Issue.Tracking.Tool.LoginSessionPoint.repo.RoleRepo;
import Issue.Tracking.Tool.LoginSessionPoint.repo.UserRepo;
import Issue.Tracking.Tool.LoginSessionPoint.service.PrivService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.security.PrivilegedSetAccessControlContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import static Issue.Tracking.Tool.LoginSessionPoint.constants.MiscConfig.*;

@Component
@Slf4j
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

        boolean alreadySetup = false;

@Autowired
private UserRepo userRepository;

@Autowired
private RoleRepo roleRepository;


@Autowired
private PrivRepo privilegeRepository;

@Autowired
private PrivService privService;

@Autowired
private PasswordEncoder passwordEncoder;

@Override
@Transactional
public void onApplicationEvent(ContextRefreshedEvent event) {


        if (alreadySetup)
        return;

        List<String> AvailableEndpoints = EndPointNames.Gen_Endp_Names();

        log.info(AvailableEndpoints.toString());
    List<Privilege> PRIVS = new ArrayList<>();
        for(String endpoint :  AvailableEndpoints){
            log.info(endpoint);
            PRIVS.add(privService.createPrivilegeIfNotFound( GETs + endpoint ));
            PRIVS.add(privService.createPrivilegeIfNotFound( POSTs + endpoint ));
            PRIVS.add(privService.createPrivilegeIfNotFound( DELETEs + endpoint ));
            PRIVS.add(privService.createPrivilegeIfNotFound( PUTs + endpoint ));
        }
       // PRIVS.add(privService.createPrivilegeIfNotFound(ADMIN));
     //   PRIVS.add(privService.createPrivilegeIfNotFound(USER));
       // PRIVS.add(privService.createPrivilegeIfNotFound(GROUP_LEADER));
      //  PRIVS.add(privService.createPrivilegeIfNotFound("ROLE_SUPER_ADMIN"));

        privilegeRepository.saveAll(PRIVS);

    //createRoleIfNotFound("ROLE_SUPER_ADMIN", PRIVS);
    //createRoleIfNotFound(GROUP_LEADER, List.of(createPrivilegeIfNotFound("ROLE_GROUP_LEADER")));
    //createRoleIfNotFound(USER, List.of(createPrivilegeIfNotFound("ROLE_USER")));
    //createRoleIfNotFound(ADMIN, PRIVS);




        alreadySetup = true;
        }


@Transactional
void createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
        role = new Role(null  ,name,privileges, null,null);
        roleRepository.save(role);
        }
}
        }
