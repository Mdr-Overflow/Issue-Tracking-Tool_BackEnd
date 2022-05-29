package Issue.Tracking.Tool.LoginSessionPoint.repo;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Privilege;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
;
import Issue.Tracking.Tool.LoginSessionPoint.domain.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RoleRepo  extends JpaRepository<Role,Long> {


    Role findByName(String name);

    Role findFirstByName(String string);
    void deleteByName(String name);
    void removeByName(String name);

   List<Role> findByNameContaining(String toSearch);

    @Query(value = " SELECT DISTINCT r FROM Role as r  " +
            "WHERE (:inputString is null or r.id = :inputInt) or (:inputString is null or r.name like :inputString)"
    )
    List<Role> findBy(String inputString, Long inputInt);

    @Query("SELECT DISTINCT r.privileges FROM  Role r where r.name = :name ")
    List<Privilege> getAllPrivs(String name);

}