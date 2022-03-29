package Issue.Tracking.Tool.LoginSessionPoint.Repo;

import Issue.Tracking.Tool.LoginSessionPoint.Domain.APIUser;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo  extends MongoRepository<APIUser,Long> {
    APIUser findByUsername(String username);

}
