package ro.eu.passwallet.service.xml;

import ro.eu.passwallet.model.UserAccount;

import java.util.Collection;

public interface IXMLFileService {
    public void saveToXMLFile(Collection<UserAccount> usersAccounts);
    public void saveXMLFile(byte[] content);
    public Collection<UserAccount> getAllUsersAccountsFromXML();
}
