package ro.eu.passwallet.model.dao;

import ro.eu.passwallet.model.UserAccount;

public class CommonTest {

    protected static UserAccount createUserAccount(Integer id) {
        UserAccount userAccount = new UserAccount();
        String now = System.currentTimeMillis() + "";
        userAccount.setPassword("pass_" + now);
        userAccount.setName("name_" + now);
        userAccount.setNickName("nickname_" + now);
        userAccount.setDescription("description_" + now);
        userAccount.setSiteURL("siteurl_" + now);
        userAccount.setId(id);
        return userAccount;
    }
}
