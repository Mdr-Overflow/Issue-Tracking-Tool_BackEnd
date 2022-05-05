package Issue.Tracking.Tool.LoginSessionPoint.service;


import Issue.Tracking.Tool.LoginSessionPoint.domain.UserGroup;
import Issue.Tracking.Tool.LoginSessionPoint.repo.UserGroupRepo;
import Issue.Tracking.Tool.LoginSessionPoint.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
        org.springframework.data.domain.Pageable givenPage =  PageRequest.of(0, 1, Sort.unsorted()); // Page has overhead cost (determines how many beforehand)
        return (userGroupRepo.findAll(givenPage)).getContent();
    }

    @Override
    public void AddUserToGroup(String username, String groupName) {
        log.info("Adding Contributor to Issue {} ",username);
        UserGroup userGroup = userGroupRepo.findByName(groupName);
        userGroup.getUsers().add(userRepo.findByUsername(username));
    }

    @Override
    public Date getTimestamp(String GroupName) {
        return userGroupRepo.findTimestampByName(GroupName);
    }
}
