package ro.eu.passwallet.crypt;

import java.util.Arrays;
import java.util.logging.Logger;

import ro.eu.passwallet.service.crypt.AESHelper;
import ro.eu.passwallet.service.crypt.CryptographyException;
import ro.eu.passwallet.service.LoggerService;

public class TestAESHelper {
    private static final Logger logger = LoggerService.getInstance().getLogger();

    public static void main(String[] args) {
        try {
            AESHelper aesHelper = new AESHelper();
            testEncryptDecrypt(aesHelper);
            testEncryptDecrypt2(aesHelper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testEncryptDecrypt(AESHelper aesHelper) {
        String pass = "123";
        byte[] encryptedStringArray = aesHelper.encrypt("decrypted string".getBytes(), pass);
        byte[] decryptedStringArray = aesHelper.decrypt(encryptedStringArray, pass);
        boolean testOK = Arrays.equals("decrypted string".getBytes(), decryptedStringArray);
        if (testOK) {
            logger.info("testEncryptDecrypt is OK!");
        } else {
            logger.info("testEncryptDecrypt FAILED!");
            logger.info("decrypted string is " + new String(decryptedStringArray));
        }
    }

    private static void testEncryptDecrypt2(AESHelper aesHelper) {
        String pass = "123";
        byte[] encryptedStringArray = aesHelper.encrypt("decrypted string".getBytes(), pass);
        try {
            aesHelper.decrypt(encryptedStringArray, pass + "1");
        } catch (CryptographyException ex) {
            logger.info("testEncryptDecrypt is OK!");
            return;
        }
        logger.info("testEncryptDecrypt FAILED!");
    }
}
