package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
//import Issue.Tracking.Tool.LoginSessionPoint.Repo.RoleRepo;
import Issue.Tracking.Tool.LoginSessionPoint.domain.UserGroup;
import Issue.Tracking.Tool.LoginSessionPoint.repo.RoleRepo;
import Issue.Tracking.Tool.LoginSessionPoint.repo.UserGroupRepo;
import Issue.Tracking.Tool.LoginSessionPoint.repo.UserRepo;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import Issue.Tracking.Tool.LoginSessionPoint.exception.NoDataFoundException;

//import javax.transaction.Transactional;
import javax.persistence.PreRemove;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
@Slf4j

public class UserServiceImplementation implements  UserService , UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final UserGroupRepo userGroupRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public APIUser saveUser(APIUser user) {
        log.info("Saving user {} to DB",user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }



    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Saving role {} to user {}",roleName,username);
            APIUser user = userRepo.findFirstByUsername(username);


        Role role = roleRepo.findByName(roleName);
        //log.info(roleName + " ___________" + role);

        if(roleRepo.findByName(roleName) == null) throw new NoDataFoundException();
    user.getRoles().add(role);

    userRepo.save(user);
    }

    @Override
    public APIUser getUser(String username) {
        log.info("Getting user {} ",username);
        return userRepo.findFirstByUsername(username);
    }

    @Override                                                                   // 0 - asc , 1 - desc
    public List<APIUser> getUsers(Integer PageIndex, Integer PageSize, String SortBy, Integer SortDirection)  {
        log.info("Getting all users ");



        Pageable givenPage = null;
        if(SortDirection == 0) {
           givenPage = PageRequest.of(PageIndex, PageSize, Sort.by(SortBy).ascending()); // Page has overhead cost (determines how many beforehand)
        }
        else  {
            givenPage = PageRequest.of(PageIndex, PageSize, Sort.by(SortBy).descending()); // Page has overhead cost (determines how many beforehand)
        }

        List<APIUser> users = (userRepo.findAll(givenPage)).getContent();   // 20 max for one
        if(users.isEmpty())
            throw new NoDataFoundException();

        return users;
    }

    @Override
    public List<APIUser> getUsersALL() {

        List<APIUser> users = userRepo.findAll() ;  // 20 max for one
        if(users.isEmpty())
            throw new NoDataFoundException();

        return users;
    }


    @Override
    public List<String> getAllUsernames() {
        return userRepo.getALLUsernames();
    }



    @Override
    @PreRemove
    public void deleteByUsername(String username) {

        if(userRepo.findFirstByUsername(username) != null)
        {

          List<UserGroup> userGroupList =  userGroupRepo.findByUsernameOFUser(username);
          userGroupList.forEach(userGroup -> userGroup.getUsers().remove(userRepo.findFirstByUsername(username)));
          for (UserGroup userGroup: userGroupList)
          {
              if(userGroup.getLeader().getUsername().equals(username)){
                  if(userGroup.getUsers().size() >1) {
                      userGroup.setLeader(new ArrayList<APIUser>(userGroup.getUsers()).get(0));
                      userGroupRepo.saveAll(userGroupList);
                  }
                  else userGroupRepo.deleteByName(userGroup.getName());
              }
                else{
                  userGroupRepo.saveAll(userGroupList);
              }
          }






            userRepo.deleteByUsername(username);
        }
        else throw new NoDataFoundException();

    }


    @Override
    public List<APIUser> findBy(String toSearch) {

        long intValue = 0L;
        try { intValue = Integer.parseInt(toSearch);}
            catch (NumberFormatException ignored){}

         return userRepo.findBy("%" + toSearch + "%",intValue);
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        APIUser user = userRepo.findFirstByUsername(username);
        if(user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            List<Role> roles = (List<Role>) user.getRoles();

            //for (Role role : roles)
              //  role.getPrivileges()
         //   user.getRoles().forEach(role -> log.info(role.getPrivileges().toString()));
            log.info("GETTING ROLES");
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }); // look for each role to check
            UserDetails userD = User.withUsername(user.getUsername()).password(user.getPassword()).authorities(authorities).build();
            return userD;

        }
    }
}
