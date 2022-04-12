package Issue.Tracking.Tool.LoginSessionPoint.Service;

public interface SecurityService {
    boolean isAuthenticated();
    void autoLogin(String username, String password);
}