package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import Issue.Tracking.Tool.LoginSessionPoint.exception.NoDataFoundException;
import Issue.Tracking.Tool.LoginSessionPoint.repo.RoleRepo;
import Issue.Tracking.Tool.LoginSessionPoint.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static Issue.Tracking.Tool.LoginSessionPoint.constants.MiscConfig.ALL_ROLES;
import static Issue.Tracking.Tool.LoginSessionPoint.constants.MiscConfig.splitter;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;

    @Override
    public List<Role> getALLRoles() {
        log.info("Getting all roles ");
        List<Role> roles = roleRepo.findAll();
        if(roles.isEmpty())
            throw new NoDataFoundException();
        return roles;
    }

    @Override
    public Role getRole(String name) {
        return roleRepo.findByName(name);
    }

    @Override
    public void deleteByName(String name) {
        if(roleRepo.findByName(name) != null) {

            List<APIUser> users = userRepo.findAllByRoles_Name(name);

           users.forEach(user -> user.getRoles().remove(roleRepo.findByName(name)));
        userRepo.saveAll(users);
        roleRepo.removeByName(name);
        }
        else throw new NoDataFoundException();
    }

    @Override
    public List<Role> findBy(String name) {
        return roleRepo.findByNameContaining(name);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving role {} to DB",role.getName());
        ALL_ROLES += splitter + role.getName();
        return roleRepo.save(role);

    }

    @Override
    public List<Role> findByTerm(String toSearch) {
        long intValue = 0L;
        try { intValue = Integer.parseInt(toSearch);}
        catch (NumberFormatException ignored){}

        log.info(Integer.parseInt(toSearch) + " ___VAL");
        return roleRepo.findBy("%"+toSearch +"%",intValue);
    }


}
