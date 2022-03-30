package Issue.Tracking.Tool.LoginSessionPoint.Service;

import Issue.Tracking.Tool.LoginSessionPoint.Domain.Role;
//import Issue.Tracking.Tool.LoginSessionPoint.UserDetails.CustomUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
/*@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    public CustomUserDetail loadUserByUsername(String name) throws UsernameNotFoundException, DataAccessException {
        // returns the get(0) of the user list obtained from the db
        User domainUser = userDAO.getUser(name);


        Set<Role> roles = domainUser.getRole();
        logger.debug("role of the user" + roles);

        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for(Role role: roles){
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
            log.debug("role" + role + " role.getRole()" + ());
        }

        CustomUserDetail customUserDetail=new CustomUserDetail();
        customUserDetail.setUser(domainUser);
        customUserDetail.setAuthorities(authorities);

        return customUserDetail;

    }

}*/