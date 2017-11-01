package ro.eu.passwallet.client;

public class ClientUIException extends RuntimeException {

    public ClientUIException(String message) {
        super(message);
    }

    public ClientUIException(Throwable cause) {
        super(cause);
    }
}
