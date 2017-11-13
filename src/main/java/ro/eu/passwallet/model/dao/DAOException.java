package ro.eu.passwallet.model.dao;

public class DAOException extends RuntimeException {

    public DAOException(Throwable throwable) {
        super(throwable);
    }

    public DAOException(String message) {
        super(message);
    }
}
