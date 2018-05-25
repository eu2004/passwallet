package ro.eu.passwallet.service;

import ro.eu.passwallet.service.xml.XMLFileServiceException;

public interface ILoggerService {
    void severe(String message, XMLFileServiceException e);

    void severe(String message);
}
