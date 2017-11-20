package ro.eu.passwallet.model.dao;

import ro.eu.passwallet.model.UserAccount;
import ro.eu.passwallet.service.xml.XMLFileService;
import ro.eu.passwallet.service.xml.XMLFileServiceException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public final class UserAccountXMLDAO implements IUserAccountDAO {
    private XMLFileService xmlFileService;

    private Collection<UserAccount> allUserAccounts;

    public UserAccountXMLDAO(XMLFileService xmlFileService) {
        this.xmlFileService = xmlFileService;
        try {
            synchronized (UserAccountXMLDAO.class) {
                allUserAccounts = xmlFileService.getAllUsersAccountsFromXML();
            }
        } catch (XMLFileServiceException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public UserAccount findUserAccountById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be NULL!");
        }

        List<UserAccount> userAccounts = allUserAccounts.stream().filter(t -> t.getId().equals(id)).collect(toList());
        if (userAccounts != null && userAccounts.size() > 0) {
            return userAccounts.get(0);
        }
        return null;
    }

    @Override
    public Collection<UserAccount> findAllUsersAccounts() {
        return Collections.unmodifiableCollection(allUserAccounts);
    }

    @Override
    public Collection<UserAccount> findUsersAccountsByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be NULL!");
        }
        final String ignoreCaseName = name.toUpperCase();
        List<UserAccount> userAccounts = allUserAccounts.stream().filter(t -> t.getName().toUpperCase().contains(ignoreCaseName)
                || t.getNickName().toUpperCase().contains(ignoreCaseName)
                || t.getSiteURL().toUpperCase().contains(ignoreCaseName)).collect(toList());
        if (userAccounts != null && userAccounts.size() > 0) {
            return userAccounts;
        }
        return Collections.emptyList();
    }

    @Override
    public Integer createUserAccount(UserAccount userAccount) {
        if (userAccount == null) {
            throw new IllegalArgumentException("userAccount cannot be NULL!");
        }

        synchronized (UserAccountXMLDAO.class) {
            Integer maxId = -1;
            if (!allUserAccounts.isEmpty()) {
                UserAccount maxUserAccount = allUserAccounts.stream().max((t1, t2) -> t1.getId().compareTo(t2.getId())).get();
                maxId = maxUserAccount.getId();
            }
            userAccount.setId(maxId + 1);
            allUserAccounts.add(userAccount);
            try {
                xmlFileService.saveToXMLFile(allUserAccounts);
            } catch (XMLFileServiceException e) {
                throw new DAOException(e);
            }
            return userAccount.getId();
        }
    }

    @Override
    public boolean deleteUserAccountById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be NULL!");
        }

        synchronized (UserAccountXMLDAO.class) {
            UserAccount userAccountToDelete = findUserAccountById(id);
            if (userAccountToDelete != null) {
                boolean removed = allUserAccounts.remove(userAccountToDelete);
                if (removed) {
                    try {
                        xmlFileService.saveToXMLFile(allUserAccounts);
                    } catch (XMLFileServiceException e) {
                        throw new DAOException(e);
                    }
                }
                return removed;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean updateUserAccount(UserAccount userAccount) {
        if (userAccount == null || userAccount.getId() == null) {
            throw new IllegalArgumentException("userAccount[id] cannot be NULL!");
        }

        synchronized (UserAccountXMLDAO.class) {
            UserAccount userAccountToEdit = findUserAccountById(userAccount.getId());
            if (userAccountToEdit != null) {
                userAccountToEdit.setName(userAccount.getName());
                userAccountToEdit.setSiteURL(userAccount.getSiteURL());
                userAccountToEdit.setNickName(userAccount.getNickName());
                userAccountToEdit.setPassword(userAccount.getPassword());
                userAccountToEdit.setDescription(userAccount.getDescription());
                try {
                    xmlFileService.saveToXMLFile(allUserAccounts);
                } catch (XMLFileServiceException e) {
                    throw new DAOException(e);
                }
                return true;
            } else {
                return false;
            }
        }
    }
}
