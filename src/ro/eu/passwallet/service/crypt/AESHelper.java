package ro.eu.passwallet.service.crypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public final class AESHelper {

    private SecretKeySpec generateKey(String password) {
        try {
            byte[] key = password.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            return new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new CryptographyException(e);
        }
    }

    public byte[] encrypt(byte[] strToEncrypt, String secret) {
        try {
            SecretKeySpec secretKey = generateKey(secret);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(strToEncrypt);
        } catch (Exception e) {
            throw new CryptographyException(e);
        }
    }

    public byte[] decrypt(byte[] strToDecrypt, String secret) {
        try {
            SecretKeySpec secretKey = generateKey(secret);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(strToDecrypt);
        } catch (Exception e) {
            throw new CryptographyException(e);
        }
    }

}