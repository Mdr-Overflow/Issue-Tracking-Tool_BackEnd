package Issue.Tracking.Tool.LoginSessionPoint.repo;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Priority;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatusRepo extends JpaRepository<Status,Long> {

    Status getStatusByName(String name);

    @Query(value = " SELECT DISTINCT s FROM Status as s  " +
            "WHERE (:inputString is null or s.id = :inputInt) or (:inputString is null or s.name like :inputString)"
    )
    List<Status> findBy(String inputString, Long inputInt);

    void deleteByName(String name);

}
