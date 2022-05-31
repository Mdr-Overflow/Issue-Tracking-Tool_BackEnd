package Issue.Tracking.Tool.LoginSessionPoint.repo;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Priority;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PriorityRepo extends JpaRepository<Priority,Long> {

    Priority findByName(String name);

    @Query(value = " SELECT DISTINCT p FROM Priority as p  " +
            "WHERE (:inputString is null or p.id = :inputInt) or (:inputString is null or p.name like :inputString)"
    )
    List<Priority> findBy(String inputString, Long inputInt);

    void deleteByName(String name);

}
