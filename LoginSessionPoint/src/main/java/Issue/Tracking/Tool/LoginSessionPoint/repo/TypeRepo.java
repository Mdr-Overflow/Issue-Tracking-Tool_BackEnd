package Issue.Tracking.Tool.LoginSessionPoint.repo;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Status;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepo extends JpaRepository<Type,Long> {

    List<Type> findAllByNameContains(String name);

    @Query(value = " SELECT DISTINCT t FROM Type as t  " +
            "WHERE (:inputString is null or t.id = :inputInt) or (:inputString is null or t.name like :inputString)"
    )
    List<Type> findBy(String inputString, Long inputInt);
}
