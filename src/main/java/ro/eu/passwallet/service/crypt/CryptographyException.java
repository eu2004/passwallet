package ro.eu.passwallet.service.crypt;

public class CryptographyException extends RuntimeException {

    public CryptographyException(String message) {
        super(message);
    }

    public CryptographyException(Throwable cause) {
        super(cause);
    }
}
