package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Issue;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Priority;

import java.util.List;

public interface PriorityService {



    Priority getPrio(String name);
    List<Priority> getPrios();


    void SavePriority(Priority priority);
    void deletePrio(String name);

}
