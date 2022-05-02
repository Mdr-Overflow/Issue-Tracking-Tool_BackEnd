package Issue.Tracking.Tool.LoginSessionPoint.Service;

import Issue.Tracking.Tool.LoginSessionPoint.Domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.Domain.Solution;
import Issue.Tracking.Tool.LoginSessionPoint.Repo.SolutionRepo;

import java.util.Collection;
import java.util.List;

public interface SolutionService {
    Solution saveSolution(Solution solution);
    Solution getSolution(String name);
    List<Solution> getSolutions();

    void  saveOwner(Solution solution, String username);
    void  addToCollabs(Solution solution, String username);

    APIUser getOwner(String solutionName);
    List<APIUser> getCollabs(String solutionName);
    java.util.Date getTimestamp(String solutionName);


}
