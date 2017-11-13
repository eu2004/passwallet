package ro.eu.passwallet.service;

import ro.eu.passwallet.model.UserAccount;
import ro.eu.passwallet.model.dao.IUserAccountDAO;

import java.util.Collection;

public final class UserAccountService {
    private IUserAccountDAO userAccountDAO;

    public UserAccountService(IUserAccountDAO userAccountDAO) {
        if (userAccountDAO == null) {
            throw new IllegalArgumentException("userAccountDAO cannot be NULL!");
        }
        this.userAccountDAO = userAccountDAO;
    }

    public Collection<UserAccount> search(String text) {
        if (text == null || text.trim().length() == 0) {
            return userAccountDAO.findAllUsersAccounts();
        }
        return userAccountDAO.findUsersAccountsByName(text);
    }

    public boolean delete(Integer userId) {
        return userAccountDAO.deleteUserAccountById(userId);
    }

    public Integer createUser(UserAccount userAccount) {
        return userAccountDAO.createUserAccount(userAccount);
    }

    public boolean update(UserAccount userAccount) {
        return userAccountDAO.updateUserAccount(userAccount);
    }
}