package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.Api.FilePoint;
import Issue.Tracking.Tool.LoginSessionPoint.Api.UserServiceClass;
import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Privilege;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Solution;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Type;
import Issue.Tracking.Tool.LoginSessionPoint.exception.NoDataFoundException;
import Issue.Tracking.Tool.LoginSessionPoint.repo.SolutionRepo;
import Issue.Tracking.Tool.LoginSessionPoint.repo.TypeRepo;
import Issue.Tracking.Tool.LoginSessionPoint.repo.UserRepo;
import Issue.Tracking.Tool.LoginSessionPoint.util.RoleUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static Issue.Tracking.Tool.LoginSessionPoint.constants.MiscConfig.CUSTOM_SEARCH_TERMS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class SolutionServImpl implements SolutionService {


    private final SolutionRepo solutionRepo;
    private final UserRepo userRepo;
    private final TypeRepo typeRepo;

    @Override
    public Solution saveSolution(Solution solution, Boolean update) throws IOException {
        log.info("Saving  solution  {} to DB",solution.getName());

        List<APIUser> users = solution.getCollaborators().stream().filter(apiUser -> userRepo.findFirstByUsername(apiUser.getName()) != null )
                .map(apiUser -> userRepo.findFirstByUsername(apiUser.getName())).collect(Collectors.toList());

        if(typeRepo.findByName(solution.getType().getName()) != null)
        {
            solution.setType(typeRepo.findByName(solution.getType().getName()));
        }
        else solution.setType(null);

        // solution.getOwner()

        if(userRepo.findFirstByUsername(solution.getOwner().getUsername()) != null)
        {
            solution.setOwner(userRepo.findFirstByUsername(solution.getOwner().getUsername()));
        }
        //

        if(solution.getType().getName().equals("file")){
            if(update)
            solution.setContent(linkTo(methodOn(FilePoint.class).downloadFiles(solution.getContent())).withRel("upload/").toString());
            else solution.setContent(linkTo(methodOn(FilePoint.class).downloadFiles(solution.getContent())).withRel("download/").toString());

        }


        solution.getCollaborators().clear();
        solution.setCollaborators(users);


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
        APIUser user =  userRepo.findFirstByUsername(username);
        solution.setOwner(user); // maybe change

    }

    @Override
    public void addToCollabs(Solution solution, String username) {
        log.info("Saving User {} as collab. of {}", username,solution.getName());
        APIUser user =  userRepo.findFirstByUsername(username);
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

    @Override
    public List<Solution> getSolutionByOwner(String ownerName) {
        List<APIUser> owner = userRepo.findByUsernameContains(ownerName);
        if (owner != null)
        {
            ArrayList<String> names = new ArrayList<String>();

            Stream<APIUser> userStream = RoleUtils.convertListToStream(owner);

            userStream.forEach(user -> names.add(user.getUsername()));

            return solutionRepo.findByOwner_UsernameIn(names);

        }
        else throw new NoDataFoundException();

    }

    @Override
    public List<Solution> getSolutionByDescription(String description_) {
        List<Solution> description = solutionRepo.findByDescriptionContains(description_);
        if (description != null)
        {
            return description;

        }
        else throw new NoDataFoundException();
    }

    @Override
    public List<Solution> getSolutionByContent(String content_) {
        List<Solution> content = solutionRepo.findByContentContains(content_);
        if (content != null)
        {
            return content;

        }
        else throw new NoDataFoundException();
    }

    @Override
    public List<Solution> getSolutionByType(String type_) {
        List<Type> types = typeRepo.findAllByNameContains(type_);
        if (types != null)
        {
            ArrayList<String> names = new ArrayList<String>();

            Stream<Type> typeStream = RoleUtils.convertListToStream(types);

            typeStream.forEach(type -> names.add(type.getName()));

            return solutionRepo.findByType_NameIn(names);

        }
        else throw new NoDataFoundException();

    }

    @Override
    public void deleteSol(Solution sol) {

        solutionRepo.delete(sol);
    }

    @Override
    public List<Solution> findBy(String toSearch) {

        if(CUSTOM_SEARCH_TERMS.containsKey(toSearch))
            return solutionRepo.findByFinalOrAccepted(CUSTOM_SEARCH_TERMS.get(toSearch));

            long intValue = 0L;
        try { intValue = Integer.parseInt(toSearch);}
        catch (NumberFormatException ignored){}



        return  solutionRepo.findBy("%"+toSearch +"%",intValue);
    }


}
