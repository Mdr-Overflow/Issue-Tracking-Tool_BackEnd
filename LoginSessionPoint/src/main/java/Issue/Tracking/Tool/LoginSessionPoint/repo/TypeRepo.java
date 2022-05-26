package Issue.Tracking.Tool.LoginSessionPoint.repo;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Status;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeRepo extends JpaRepository<Type,Long> {

    List<Type> findAllByNameContains(String name);
}
