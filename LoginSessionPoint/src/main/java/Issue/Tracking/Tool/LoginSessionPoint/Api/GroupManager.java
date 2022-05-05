package Issue.Tracking.Tool.LoginSessionPoint.Api;

import Issue.Tracking.Tool.LoginSessionPoint.domain.UserGroup;
import Issue.Tracking.Tool.LoginSessionPoint.service.IssueService;
import Issue.Tracking.Tool.LoginSessionPoint.service.SolutionService;
import Issue.Tracking.Tool.LoginSessionPoint.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

import static org.springframework.http.ResponseEntity.created;

@Controller
//@RequestMapping(path = "/LoginSessionPoint")
@RequiredArgsConstructor
public class GroupManager {

    private final Issue.Tracking.Tool.LoginSessionPoint.service.UserService userService;
    private final UserGroupService userGroupService;
    private final IssueService issueService;
    private final SolutionService solutionService;

    @ResponseBody
    @GetMapping("GroupManager")
    public ResponseEntity<List<UserGroup>> getALLGroups() {

        return ResponseEntity.ok().body(userGroupService.getGroups());

    }


    @ResponseBody
    @PostMapping("GroupManager/save")
    public ResponseEntity<UserGroup> saveGroup(@RequestBody UserGroup userGroup) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/GroupManager/save").toUriString());
        return created(uri).body(userGroupService.saveGroup(userGroup));

    }

    @ResponseBody
    @PostMapping("GroupManager/addUser")
    public ResponseEntity<String> saveGroup(@RequestBody String Username, String GroupName) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/GroupManager/addUser").toUriString());
        userGroupService.AddUserToGroup(Username, GroupName);
        return created(uri).body("Nice");
    }


    @ResponseBody
    @GetMapping("GroupManager/get")
    public ResponseEntity<UserGroup> getGroup(@RequestBody String groupName) {

        return ResponseEntity.ok().body(userGroupService.getGroup(groupName));

    }


    @ResponseBody
    @GetMapping("GroupManager/getAll")
    public ResponseEntity<List<UserGroup>> saveGroup() {

        return ResponseEntity.ok().body(userGroupService.getGroups());

    }

    @ResponseBody
    @GetMapping("GroupManager/getTime")
    public ResponseEntity<Date> getTimeGroup(@RequestBody String groupName) {

        return ResponseEntity.ok().body(userGroupService.getTimestamp(groupName));

    }

}
