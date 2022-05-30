package Issue.Tracking.Tool.LoginSessionPoint.service;


import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.UserGroup;
import Issue.Tracking.Tool.LoginSessionPoint.repo.UserGroupRepo;
import Issue.Tracking.Tool.LoginSessionPoint.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Synchronize;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.PreUpdate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional

@Slf4j

public class UserGroupServiceImpl implements UserGroupService {

    private final UserRepo userRepo;
    private final UserGroupRepo userGroupRepo;



    @Override
    public UserGroup saveGroup(UserGroup userGroup) {
        log.info("Saving  UserGroup  {} to DB", userGroup.getName());
        ArrayList<APIUser> users = new ArrayList<>();
        for (APIUser user: userGroup.getUsers()) {
            log.info("call nr x");
            if (userRepo.findFirstByUsername(user.getUsername()) != null) {
                log.info("Something here");
                users.add(userRepo.findFirstByUsername(user.getUsername()));
            }
        }
        //HERE

        log.info("we found them users");
        if(userRepo.findFirstByUsername(userGroup.getLeader().getUsername()) != null)
        {
            userGroup.setLeader(userRepo.findFirstByUsername(userGroup.getLeader().getUsername()));
        }
       // userRepo.flush();
        log.info("MAYBE HERE BREAKS ");

     //   userRepo.findAll().forEach(user -> user);

        log.info(users.toString());
        userGroup.getUsers().clear();

        userGroup.setUsers(users);

        log.info(userGroup.getUsers().toString());

     //   userGroupRepo.flush();

        //log.info(userRepo.findAll().toString());
        return  userGroupRepo.save(userGroup);
    }

    @Override
    public UserGroup getGroup(String groupName) {
        log.info("Getting  UserGroup {} ", userGroupRepo.findByName(groupName));
        return userGroupRepo.findByName(groupName);
    }

    @Override
    public List<UserGroup> getGroups() {
        log.info("Getting all groups ");
      //  org.springframework.data.domain.Pageable givenPage =  PageRequest.of(0, 1, Sort.unsorted()); // Page has overhead cost (determines how many beforehand)
        return userGroupRepo.findAll();
    }

    @Override
    public List<APIUser> getUsers(String name) {
        return (userGroupRepo.getALLUsers(name));
    }

    @Override
    public APIUser getLeader(String name) {
        return (userGroupRepo.findLeader(name));
    }

    @Override
    public void AddUserToGroup(String username, String groupName) {
        log.info("Adding Contributor to Issue {} ",username);
        UserGroup userGroup = userGroupRepo.findByName(groupName);
        userGroup.getUsers().add(userRepo.findFirstByUsername(username));
    }

    @Override
    public void deleteByName(String name) {




        userGroupRepo.deleteByName(name);
    }

    @Override
    public Date getTimestamp(String GroupName) {
        return userGroupRepo.findTimestampByName(GroupName);
    }

    @Override
    public List<UserGroup> findBy(String toSearch) {

        long intValue = 0L;
        try { intValue = Integer.parseInt(toSearch);}
        catch (NumberFormatException ignored){}

        return userGroupRepo.findBy("%"+toSearch +"%",intValue);
    }

    @Override
    public List<UserGroup> findByLeader(String toSearch) {
        return userGroupRepo.findByLeader_NameContains("%" + toSearch + "%");
    }

    @Override
    public  List<UserGroup> findByUsernameOFUser(String username) {
        return userGroupRepo.findByUsernameOFUser(username);


    }
}
