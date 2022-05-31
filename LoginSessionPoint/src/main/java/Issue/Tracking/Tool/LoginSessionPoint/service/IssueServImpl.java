package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.*;
import Issue.Tracking.Tool.LoginSessionPoint.repo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class IssueServImpl implements  IssueService {
    private final UserRepo userRepo;
    private final UserGroupRepo userGroupRepo;
    private final IssueRepo issueRepo;
    private final SolutionRepo solutionRepo;
    private final PriorityRepo priorityRepo;
    private final StatusRepo statusRepo;

    //private final Priority userRepo;
    //private final Status   userRepo;

    @Override
    public Issue saveIssue(Issue issue) {
        log.info("Saving  Issue  {} to DB", issue.getName());


        //    List<APIUser> users = new ArrayList<APIUser>();
        //       List<UserGroup> groups = new ArrayList<UserGroup>();
        //     List<Solution> sols = new ArrayList<Solution>();

        List<APIUser> users = new ArrayList<>();
        try {
            for (APIUser user : issue.getUsers())
                if (userRepo.findFirstByUsername(user.getUsername()) != null) {
                    users.add(userRepo.findFirstByUsername(user.getUsername()));
                }
            issue.getUsers().clear();
        } catch (NullPointerException ignored) {
        }
//

        List<UserGroup> groups = new ArrayList<>();
        try {
            groups = new ArrayList<UserGroup>();
            for (UserGroup userG : issue.getUserGroups())
                if (userGroupRepo.findByName(userG.getName()) != null) {
                    groups.add(userGroupRepo.findByName(userG.getName()));
                }
            issue.getUserGroups().clear();
        } catch (NullPointerException ignored) {
        }

        List<Solution> sols = new ArrayList<>();
        try {
            sols = new ArrayList<Solution>();
            for (Solution sol : issue.getSolutions())
                if (solutionRepo.findByName(sol.getName()) != null) {
                    sols.add(solutionRepo.findByName(sol.getName()));
                }
            //log.info(issue.getPriority().toString());
            issue.getSolutions().clear();

        } catch (NullPointerException ignored) {
        }
        try {
            if (priorityRepo.findByName(issue.getPriority().getName()) != null) {
                issue.setPriority(priorityRepo.findByName(issue.getPriority().getName()));
                // log.info("aaaaaaaaaaaaaaaaa");
            }

            if (statusRepo.getStatusByName((issue.getStatus().getName())) != null) {
                issue.setStatus(statusRepo.getStatusByName((issue.getStatus().getName())));
            }
            // log.info(users.toString());

            //        issue.getUsers().clear();
            //         issue.getUserGroups().clear();
            //       issue.getSolutions().clear();

        } catch (NullPointerException ignored) {
        }


     //   log.info(issue.toString());

        issue.getUsers().clear();
        issue.getUserGroups().clear();
        issue.getSolutions().clear();

        issue.setUsers(users);
        issue.setUserGroups(groups);
        issue.setSolutions(sols);


        // log.info(userGroup.getUsers().toString());


        return issueRepo.save(issue);
    }

    @Override
    public Issue getIssue(String IssueName) {
        log.info("Getting  Issue  {} ",issueRepo.findByName(IssueName));
        return issueRepo.findByName(IssueName);
    }

    @Override
    public List<Issue> getIssues() {
        log.info("Getting all issues ");
        //org.springframework.data.domain.Pageable givenPage =  PageRequest.of(0, 100, Sort.unsorted()); // Page has overhead cost (determines how many beforehand)
        return issueRepo.findAll();

    }

    @Override
    public List<Solution> getSols(Issue issue) {
        log.info("Getting all sols. of Issue {} ",issue.getName() );
        return issueRepo.findAllSolsByName(issue.getName());
    }

    @Override
    public List<APIUser> getContributors(Issue issue) {
        log.info("Getting all contrib. to  Issue {} ",issue.getName() );
        return issueRepo.findAllUsersByName(issue.getName());
    }

    @Override
    public List<UserGroup> getGroups(Issue issue) {
        log.info("Getting all groups of Issue {} ",issue.getName() );
        return issueRepo.findAllGroupsByName(issue.getName());

    }

    @Override
    public void AddSol(Solution solution , String IssueName) {
        log.info("Adding solution to Issue {} ",IssueName );
        Issue issue = issueRepo.findByName(IssueName);
        issue.getSolutions().add(solution);
    }

    @Override
    public void AddUser(APIUser user, String IssueName) {
        log.info("Adding Contributor to Issue {} ",IssueName );
        Issue issue = issueRepo.findByName(IssueName);
        issue.getUsers().add(user);
    }

    @Override
    public void AddGroup(UserGroup userGroup, String IssueName) {
        log.info("Adding UserGroup to Issue {} ",IssueName );
        Issue issue = issueRepo.findByName(IssueName);
        issue.getUserGroups().add(userGroup);
    }

    @Override
    public void AddPriority(Priority priority, String IssueName) {
        Issue issue = issueRepo.findByName(IssueName);
        issue.setPriority(priority);
    }

    @Override
    public void AddStatus(Status status, String IssueName) {
        Issue issue = issueRepo.findByName(IssueName);
        issue.setStatus(status);
    }

    @Override
    public void deleteByName(String name) {







        issueRepo.deleteByName(name);
    }

    @Override
    public Date getTimestamp(String IssueName) {
        return issueRepo.findTimestampByName(IssueName);
    }


    @Override
    public String getDetails(Issue issue) {
       return issue.getDetails();
    }

    @Override
    public Status getStatus(Issue issue) {
        return issue.getStatus();
    }

    @Override
    public Priority getPrio(Issue issue) {
        return issue.getPriority();
    }


    public boolean matches_Regex(Date date, String regex){

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(date.toString());

        return matcher.find();

    }

    @Override
    public List<Issue> findBy(String toSearch) {

        long intValue = 0L;
        Integer inputDate = null;
        try { intValue = Integer.parseInt(toSearch);}
        catch (NumberFormatException ignored){}

        try { inputDate = Integer.parseInt(toSearch);
             return issueRepo.findAll().stream().filter(issue ->  matches_Regex(issue.getDueDate(),"%" + toSearch + "%")).collect(Collectors.toList());
        }
        catch (NumberFormatException ignored){}


        return  issueRepo.findBy("%" + toSearch + "%",intValue,inputDate);
    }

    @Override
    public List<Issue> findByPrio(String toSearch) {
        return issueRepo.findAllByPriority_NameContains("%" + toSearch + "%");
    }

    @Override
    public List<Issue> findByStatus(String toSearch) {
        return issueRepo.findAllByStatus_NameContains("%" + toSearch + "%");
    }

    @Override
    public List<Issue> findByNameOfUsers(String name) {
        return issueRepo.findByNameOfUsers(name);
    }
/*
    @Override
    public List<Issue> findByProp(String prop, String name) {

        List<Field> privateFields = new ArrayList<>();
        Field[] allFields = Issue.class.getDeclaredFields();
        for (Field field : allFields) {
            if (Modifier.isPrivate(field.getModifiers())) {
                privateFields.add(field);
            }
        }

        if(privateFields.stream().map(Field::getName).collect(Collectors.toList()).contains(prop))
        {
            for (Field  f : privateFields){
                if(f.getName().equals("name"))
                {
                    return List.of(issueRepo.findByName(name));
                }

                if(f.getName().equals("status"))
                {
                    return issueRepo.findAllByStatus_NameContains(name);
                }

                if(f.getName().equals("status"))
                {
                    return issueRepo.findAllByStatus_NameContains(name);
                }


            }}


        }*/

    }

