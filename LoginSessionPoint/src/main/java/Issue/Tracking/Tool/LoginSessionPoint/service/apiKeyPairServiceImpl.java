package Issue.Tracking.Tool.LoginSessionPoint.service;


import Issue.Tracking.Tool.LoginSessionPoint.domain.apiKeyPair;
import Issue.Tracking.Tool.LoginSessionPoint.repo.keyPairRepo;
import Issue.Tracking.Tool.LoginSessionPoint.util.kPSImplem;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class apiKeyPairServiceImpl implements apiKeyPairService {

    private final keyPairRepo keyPairRepo;


    @Override
    public boolean validate(String apiKey) throws NoSuchPaddingException, NoSuchAlgorithmException {


        for (apiKeyPair  key : keyPairRepo.findAll()){

            String[] secret = kPSImplem.GenerateKeyPair();

            String secretMessage = secret[0];  // API key

            Cipher encryptCipher = Cipher.getInstance("RSA");
            encryptCipher.init(Cipher.ENCRYPT_MODE, apiKey);

            byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);

            String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);
            key.getSecretKey();


        }

    }

    @Override
    public void generate() {

        KeyPairGenerator generator = null;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();

            PrivateKey secretKey = pair.getPrivate();
            PublicKey ApiKey = pair.getPublic();



            keyPairRepo.save(new apiKeyPair(1, ApiKey.toString(), secretKey.toString(), null, null)); // auto assigned by hibernate

        } catch (NoSuchAlgorithmException e) {
            log.error("ENCRYPTION FAILED", e);
        }

}

}
