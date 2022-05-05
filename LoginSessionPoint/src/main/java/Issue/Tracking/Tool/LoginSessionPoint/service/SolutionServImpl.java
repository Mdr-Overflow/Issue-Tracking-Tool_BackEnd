package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Solution;
import Issue.Tracking.Tool.LoginSessionPoint.repo.SolutionRepo;
import Issue.Tracking.Tool.LoginSessionPoint.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class SolutionServImpl implements SolutionService {


    private final SolutionRepo solutionRepo;
    private final UserRepo userRepo;

    @Override
    public Solution saveSolution(Solution solution) {
        log.info("Saving  solution  {} to DB",solution.getName());
        return solutionRepo.save(solution);
    }

    @Override
    public Solution getSolution(String name) {
        log.info("Getting solution name {} ", name);
        return solutionRepo.findByName(name);
    }

    @Override
    public List<Solution> getSolutions() {
        log.info("Getting all solutions");
        return solutionRepo.findAll();
    }

    @Override
    public void saveOwner(Solution solution, String username) {
        log.info("Saving User {} as Owner of {}", username,solution.getName());
        APIUser user =  userRepo.findByUsername(username);
        solution.setOwner(user); // maybe change

    }

    @Override
    public void addToCollabs(Solution solution, String username) {
        log.info("Saving User {} as collab. of {}", username,solution.getName());
        APIUser user =  userRepo.findByUsername(username);
        solution.getCollaborators().add(user);
    }

    @Override
    public APIUser getOwner(String solname) {
        log.info("Getting owner of solution {} ", solutionRepo.findByName(solname) );
        return solutionRepo.findOwnerByName(solname);
    }

    @Override
    public List<APIUser> getCollabs(String solname) {
        log.info("Getting all Collabs. of solution {} ", solutionRepo.findByName(solname) );
        return solutionRepo.findAllCollaboratorsByName(solname);
    }

    @Override
    public Date getTimestamp(String solname) {
        return solutionRepo.findTimestampByName(solname);
    }


}
