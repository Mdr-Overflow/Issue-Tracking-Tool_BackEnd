package Issue.Tracking.Tool.LoginSessionPoint.Repo;

import Issue.Tracking.Tool.LoginSessionPoint.Domain.APIUser;

import Issue.Tracking.Tool.LoginSessionPoint.Domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepo  extends MongoRepository<APIUser,Long> {
    APIUser findByUsername(String username);
    List<Role> findAllByUsername(String username);

}
