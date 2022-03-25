/*package Issue.Tracking.Tool.LoginSessionPoint.LogAttempt;

import Issue.Tracking.Tool.LoginSessionPoint.LogAttempt.LogAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "IssueTrackindTool/v1.1/LoginSessionPoint")
public class LogAttemptController {


    private final LogAttemptService logService;

    @Autowired
    public LogAttemptController(LogAttemptService logService) {
        this.logService = logService;
    }

    @GetMapping
    public LogAttempt getLogAttempt()
    {
        return logService.getLogAttempt();



    }

}*/
