package Issue.Tracking.Tool.LoginSessionPoint.service;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

public interface apiKeyPairService {
    public boolean validate(String apiKey) throws NoSuchPaddingException, NoSuchAlgorithmException;
    public void generate();

}
