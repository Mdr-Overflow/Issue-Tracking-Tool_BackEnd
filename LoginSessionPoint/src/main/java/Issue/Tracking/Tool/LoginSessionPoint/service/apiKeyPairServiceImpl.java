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
    public boolean validate(String apiKey) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
      try {
          if (keyPairRepo.findAll() != null) {
              log.info("GOOOOD");
              for (apiKeyPair key : keyPairRepo.findAll()) {
                  return apiKey.equals(key.getApiKey());
              }
          }
      }
      catch (Exception e){
          log.info(e.getMessage() + "  AFFFFFFFFFFFF");
      }
        log.info("BAADDD");
        return false;
    }








    @Override
    public void generate() {

        KeyPairGenerator generator = null;
        try {
           // KeyPairGenerator dsaKeyGen = KeyPairGenerator.getInstance("SHA256withDSA");
           // dsaKeyGen.initialize(1024);
           // KeyPair pair = dsaKeyGen.generateKeyPair();

           generator = KeyPairGenerator.getInstance("RSA");
           generator.initialize(1024);
           KeyPair pair = generator.generateKeyPair();

            PrivateKey secretKey = pair.getPrivate();
            PublicKey ApiKey = pair.getPublic();
            //String[] secret = kPSImplem.GenerateKeyPair();

            // Cipher encryptCipher = Cipher.getInstance("RSA");
            // encryptCipher.init(Cipher.ENCRYPT_MODE, ApiKey);


           //   byte[] secretMessageBytes = secret[0].getBytes(StandardCharsets.UTF_8);
            //  byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);

          //  String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);

            keyPairRepo.save(new apiKeyPair(1, ApiKey.toString(), secretKey.toString(), null, null)); // auto assigned by hibernate

        } catch (NoSuchAlgorithmException e) {
            log.error("ENCRYPTION FAILED", e);

    }}}
