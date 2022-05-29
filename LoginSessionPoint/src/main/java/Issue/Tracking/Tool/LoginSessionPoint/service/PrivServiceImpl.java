package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Privilege;
import Issue.Tracking.Tool.LoginSessionPoint.repo.PrivRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class PrivServiceImpl implements PrivService{

    private final PrivRepo privRepo;

    @Override
    public List<Privilege> getAll() {
        return privRepo.findAll();
    }

    @Override
    public Privilege findByName(String p) {

        if(privRepo.findByName(p) != null)
        return privRepo.findByName(p);
        else throw new  IllegalArgumentException("aaaaaaaaaaaaaaaa");
    }

    @Override
    public Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privRepo.findByName(name);
        log.info("I GOT HERE BEFORE NULL CHECK");
        //log.info(privilege.toString());

        if (privilege == null) {
            privilege = new Privilege(null,name,new ArrayList<>());
            log.info("MAKING STUFF IS HARD ");
            log.info(privilege.toString());
            privRepo.save(privilege);
        }
        return privilege;
    }

}
