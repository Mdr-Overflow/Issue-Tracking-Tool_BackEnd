package Issue.Tracking.Tool.LoginSessionPoint.repo;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface SolutionRepo  extends JpaRepository<Solution,Long>  {
    Solution findByName(String name);

    @Query("SELECT s.owner FROM Solution s where s.name = :name")
    APIUser findOwnerByName(@Param("name") String name);

    @Query("SELECT s.collaborators FROM Solution s where s.name = :name")
    List<APIUser> findAllCollaboratorsByName(String name);

    @Query("SELECT s.createdAt FROM Solution s where s.name = :name")
    java.util.Date findTimestampByName(String name) ;



    @Query(value = " SELECT DISTINCT s FROM Solution as s join s.collaborators c join s.owner o join s.type t" +
            " WHERE (:inputString is null or s.id = :inputInt) or (:inputString is null or s.name like :inputString) " +
            "or (:inputString is null or s.description like :inputString) or (:inputString is null or s.content like :inputString) " +
            "or (:inputString is null or c.username  like :inputString) or (:inputString is null or o.username like :inputString) " +
            "or (:inputString is null or t.name  like :inputString)  "
    )
    List<Solution> findBy(String inputString, Long inputInt);



    List<Solution> findByOwner_UsernameIn(List<String> names);

    List<Solution> findByDescriptionContains(String name);

    List<Solution> findByContentContains(String content_);

    List<Solution> findByType_NameIn(List<String> name);

    @Query(value = "SELECT DISTINCT s FROM  Solution  as s WHERE s.isFinal = :b  or  s.isAccepted = :b")
    List<Solution> findByFinalOrAccepted(Boolean b);


    @Query(value = "SELECT s FROM  Solution as s join s.collaborators sc  WHERE  sc.username = :username")
    List<Solution> findByNameOFColab(String username);


    //@Query("SELECT s.collaborators FROM Solution s  where s.collaborators = :name")
    //APIUser findCollaboratorByName(@Param("name") String name);

}
