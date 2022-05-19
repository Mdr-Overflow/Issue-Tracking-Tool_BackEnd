package Issue.Tracking.Tool.LoginSessionPoint.securityConfig;

import Issue.Tracking.Tool.LoginSessionPoint.repo.UserRepo;
import Issue.Tracking.Tool.LoginSessionPoint.repo.keyPairRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {

    private String apiKey;


    public ApiKeyAuthenticationToken(String apiKey, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.apiKey = apiKey;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return apiKey;
    }
}