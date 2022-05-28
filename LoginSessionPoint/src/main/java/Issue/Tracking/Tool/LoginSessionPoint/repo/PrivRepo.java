package Issue.Tracking.Tool.LoginSessionPoint.repo;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Privilege;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PrivRepo extends JpaRepository<Privilege,Long> {

    @Query("FROM Privilege as p WHERE p.name = :name")
    Privilege findByName(String name);
}
