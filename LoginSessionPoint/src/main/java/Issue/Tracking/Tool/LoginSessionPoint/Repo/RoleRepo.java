package Issue.Tracking.Tool.LoginSessionPoint.Repo;

import Issue.Tracking.Tool.LoginSessionPoint.Domain.Role;
;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepo  extends MongoRepository<Role,Long> {
    Role findByName(String name);

}