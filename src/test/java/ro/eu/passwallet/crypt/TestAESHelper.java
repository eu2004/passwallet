package ro.eu.passwallet.crypt;

import java.util.Arrays;

import ro.eu.passwallet.service.crypt.AESHelper;
import ro.eu.passwallet.service.crypt.CryptographyException;

public class TestAESHelper {
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
            System.out.println("testEncryptDecrypt is OK!");
        } else {
            System.err.println("testEncryptDecrypt FAILED!");
            System.err.println("decrypted string is " + new String(decryptedStringArray));
        }
    }

    private static void testEncryptDecrypt2(AESHelper aesHelper) {
        String pass = "123";
        byte[] encryptedStringArray = aesHelper.encrypt("decrypted string".getBytes(), pass);
        try {
            aesHelper.decrypt(encryptedStringArray, pass + "1");
        } catch (CryptographyException ex) {
            System.out.println("testEncryptDecrypt is OK!");
            return;
        }
        System.err.println("testEncryptDecrypt FAILED!");
    }
}
