package Issue.Tracking.Tool.LoginSessionPoint.service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

public interface apiKeyPairService {
    public boolean validate(String apiKey) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException;
    public void generate();

}
