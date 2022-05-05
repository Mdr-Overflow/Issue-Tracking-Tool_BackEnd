package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
//import Issue.Tracking.Tool.LoginSessionPoint.Repo.RoleRepo;
import Issue.Tracking.Tool.LoginSessionPoint.repo.RoleRepo;
import Issue.Tracking.Tool.LoginSessionPoint.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static Issue.Tracking.Tool.LoginSessionPoint.constants.MiscConfig.ALL_ROLES;
import static Issue.Tracking.Tool.LoginSessionPoint.constants.MiscConfig.splitter;

@Service @RequiredArgsConstructor  @Transactional
@Slf4j

public class UserServiceImplementation implements  UserService , UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public APIUser saveUser(APIUser user) {
        log.info("Saving user {} to DB",user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving role {} to DB",role.getName());
        ALL_ROLES += splitter + role.getName();
        return roleRepo.save(role);

    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Saving role {} to user {}",roleName,username);
        APIUser user = userRepo.findByUsername(username);
    Role role = roleRepo.findByName(roleName);
    user.getRoles().add(role);
    }

    @Override
    public APIUser getUser(String username) {
        log.info("Getting user {} ",username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<APIUser> getUsers() {
        log.info("Getting all users ");
        org.springframework.data.domain.Pageable givenPage =  PageRequest.of(0, 5, Sort.unsorted()); // Page has overhead cost (determines how many beforehand)
        return (userRepo.findAll(givenPage)).getContent();
    }

   /* @Override
    public List<Role> getRoles() {
        log.info("Getting all roles of user ");
        return userRepo.findAllByUsername();
    }*/

    @Override
    public List<Role> getALLRoles() {
        log.info("Getting all roles ");
        return roleRepo.findAll();
    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        APIUser user = userRepo.findByUsername(username);
        if(user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }); // look for each role to check
            UserDetails userD = User.withUsername(user.getUsername()).password(user.getPassword()).authorities(authorities).build();
            return userD;

        }
    }
}
