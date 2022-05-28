package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Solution;

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


    List<Solution> getSolutionByOwner(String ownerName);

    List<Solution> getSolutionByDescription(String description_);

    List<Solution> getSolutionByContent(String content_);

    List<Solution> getSolutionByType(String type_);

    void deleteSol(Solution sol);

    List<Solution> findBy(String toSearch);
}
