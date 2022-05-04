package Issue.Tracking.Tool.LoginSessionPoint.Repo;

import Issue.Tracking.Tool.LoginSessionPoint.Domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.Domain.apiKeyPair;
import org.springframework.data.jpa.repository.JpaRepository;

public interface keyPairRepo extends JpaRepository<apiKeyPair,Long> {
    //apiKeyPair getapiKeyPairByUserID(Long UID);


}
