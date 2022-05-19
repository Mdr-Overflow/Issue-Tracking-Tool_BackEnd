package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import Issue.Tracking.Tool.LoginSessionPoint.exception.NoDataFoundException;
import Issue.Tracking.Tool.LoginSessionPoint.repo.RoleRepo;
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
        roleRepo.deleteByName(name);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving role {} to DB",role.getName());
        ALL_ROLES += splitter + role.getName();
        return roleRepo.save(role);

    }


}
