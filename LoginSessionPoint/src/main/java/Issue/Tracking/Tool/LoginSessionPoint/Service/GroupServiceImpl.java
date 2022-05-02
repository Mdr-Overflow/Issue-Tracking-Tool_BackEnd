package Issue.Tracking.Tool.LoginSessionPoint.Service;


import Issue.Tracking.Tool.LoginSessionPoint.Domain.Group;
import Issue.Tracking.Tool.LoginSessionPoint.Domain.Issue;
import Issue.Tracking.Tool.LoginSessionPoint.Repo.GroupRepo;
import Issue.Tracking.Tool.LoginSessionPoint.Repo.UserRepo;
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

public class GroupServiceImpl implements  GroupService {

    private final UserRepo userRepo;
    private final GroupRepo groupRepo;

    @Override
    public Group saveGroup(Group group) {
        log.info("Saving  Group  {} to DB",group.getName());
        return  groupRepo.save(group);
    }

    @Override
    public Group getGroup(String groupName) {
        log.info("Getting  Group {} ",groupRepo.findByName(groupName));
        return groupRepo.findByName(groupName);
    }

    @Override
    public List<Group> getGroups() {
        log.info("Getting all groups ");
        org.springframework.data.domain.Pageable givenPage =  PageRequest.of(0, 5, Sort.unsorted()); // Page has overhead cost (determines how many beforehand)
        return (groupRepo.findAll(givenPage)).getContent();
    }

    @Override
    public void AddUserToGroup(String username, String groupName) {
        log.info("Adding Contributor to Issue {} ",username);
        Group group = groupRepo.findByName(groupName);
        group.getUsers().add(userRepo.findByUsername(username));
    }

    @Override
    public Date getTimestamp(String GroupName) {
        return groupRepo.findTimestampByName(GroupName);
    }
}
