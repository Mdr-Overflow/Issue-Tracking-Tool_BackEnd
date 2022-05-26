package Issue.Tracking.Tool.LoginSessionPoint.repo;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriorityRepo extends JpaRepository<Priority,Long> {


    Priority findByName(String name);



    void deleteByName(String name);

}
