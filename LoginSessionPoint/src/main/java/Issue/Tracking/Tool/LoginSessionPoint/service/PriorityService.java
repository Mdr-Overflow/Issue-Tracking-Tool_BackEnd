package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Issue;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Priority;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Type;

import java.util.List;

public interface PriorityService {



    Priority getPrio(String name);
    List<Priority> getPrios();


    void SavePriority(Priority priority);
    void deletePrio(String name);

    List<Priority> findBy(String toSearch);
}
