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
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class apiKeyPairServiceImpl implements apiKeyPairService {

    private final keyPairRepo keyPairRepo;


    @Override
    public boolean validate(String apiKey) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
      try {
          if (keyPairRepo.findAll() != null) {

              for (apiKeyPair key : keyPairRepo.findAll()) {
                  return apiKey.equals(key.getApiKey());
              }
          }
      }
      catch (Exception ignored){

      }

        return false;
    }



    @Override
    public String generate() {

        KeyPairGenerator generator = null;
        try {

           generator = KeyPairGenerator.getInstance("RSA");
           generator.initialize(1024);
           KeyPair pair = generator.generateKeyPair();

            PrivateKey secretKey = pair.getPrivate();
            PublicKey ApiKey = pair.getPublic();
            String secretMessage =  kPSImplem.GenerateKeyPair()[0];
            Cipher encryptCipher = Cipher.getInstance("RSA");
            encryptCipher.init(Cipher.ENCRYPT_MODE, ApiKey);


            byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);

            String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);


            keyPairRepo.save(new apiKeyPair(1,encodedMessage , secretKey.toString(), null, null)); // auto assigned by hibernate
            return encodedMessage;
        } catch (NoSuchAlgorithmException e) {
            log.error("ENCRYPTION FAILED", e);

    } catch (NoSuchPaddingException | IllegalBlockSizeException | InvalidKeyException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<apiKeyPair> get_All() {
        return keyPairRepo.findAll();
    }

}
