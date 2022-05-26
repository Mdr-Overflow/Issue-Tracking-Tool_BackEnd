package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Priority;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Status;

import java.util.List;

public interface StatusService {

    Status getStatus(String name);

    List<Status> getStatuses();

    void SaveStatus(Status status);

    void deleteStatus(String name);

}
