package Issue.Tracking.Tool.LoginSessionPoint.Repo;

import Issue.Tracking.Tool.LoginSessionPoint.Domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.Domain.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SolutionRepo  extends JpaRepository<Solution,Long>  {
    Solution findByName(String name);

    @Query("SELECT s.owner FROM Solution s where s.name = :name")
    APIUser findOwnerByName(@Param("name") String name);

    @Query("SELECT s.collaborators FROM Solution s where s.name = :name")
    List<APIUser> findAllCollaboratorsByName(String name);

    @Query("SELECT s.createdAt FROM Solution s where s.name = :name")
    java.util.Date findTimestampByName(String name) ;

    //@Query("SELECT s.collaborators FROM Solution s  where s.collaborators = :name")
    //APIUser findCollaboratorByName(@Param("name") String name);

}
