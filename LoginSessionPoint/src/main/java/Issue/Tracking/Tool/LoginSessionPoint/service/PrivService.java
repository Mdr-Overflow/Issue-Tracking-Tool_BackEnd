package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Privilege;

import java.util.List;

public interface PrivService {
    List<Privilege> getAll();

    Privilege findByName(String p);

  //  Privilege createPrivilegeIfNotFound(String name);
}
