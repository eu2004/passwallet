package ro.eu.passwallet.model.dao;

import ro.eu.passwallet.model.UserAccount;

import java.util.Collection;

public interface IUserAccountDAO {
    public UserAccount findUserAccountById(Integer id);

    public Collection<UserAccount> findAllUsersAccounts();

    Collection<UserAccount> findUsersAccountsByName(String name);

    public Integer createUserAccount(UserAccount userAccount);

    boolean deleteUserAccountById(Integer id);

    boolean updateUserAccount(UserAccount userAccount);
}
