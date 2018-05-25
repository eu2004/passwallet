package ro.eu.passwallet.service.xml;

import ro.eu.passwallet.model.UserAccount;
import ro.eu.passwallet.service.crypt.CryptographyService;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Collection;

public class XMLFileService implements IXMLFileService{
    private XMLFileHelper xmlFileHelper;
    private CryptographyService cryptographyService;
    private String xmlFilePath;


    public XMLFileService(String password, String xmlFilePath) {
        try {
            xmlFileHelper = new XMLFileHelper();
        } catch (JAXBException e) {
            throw new XMLFileServiceException(e);
        }
        this.xmlFilePath = xmlFilePath;
        cryptographyService = new CryptographyService(password);
    }

    public void saveToXMLFile(Collection<UserAccount> usersAccounts) {
        try {
            xmlFileHelper.saveXMLFileContent(cryptographyService.encrypt(xmlFileHelper.convertUsersAccountsToXML(usersAccounts)), xmlFilePath);
        } catch (IOException | JAXBException e) {
            throw new XMLFileServiceException(e);
        }
    }

    public void saveXMLFile(byte[] content) {
        try {
            xmlFileHelper.saveXMLFileContent(cryptographyService.encrypt(content), xmlFilePath);
        } catch (IOException e) {
            throw new XMLFileServiceException(e);
        }
    }

    private byte[] loadXMLFIle() throws IOException {
        return cryptographyService.decrypt(xmlFileHelper.getXMLFileContent(xmlFilePath));
    }

    public Collection<UserAccount> getAllUsersAccountsFromXML() {
        try {
            return xmlFileHelper.getAllUsersAccountsFromXML(loadXMLFIle());
        } catch (JAXBException | IOException e) {
            throw new XMLFileServiceException(e);
        }
    }

}
