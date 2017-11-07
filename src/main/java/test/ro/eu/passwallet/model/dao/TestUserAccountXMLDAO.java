package test.ro.eu.passwallet.model.dao;

import ro.eu.passwallet.model.UserAccount;
import ro.eu.passwallet.model.dao.UserAccountXMLDAO;
import ro.eu.passwallet.service.xml.XMLFileService;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class TestUserAccountXMLDAO extends CommonTest {

    public static void main(String[] args) {
        try {
            System.setProperty("users_accounts_xml_file_path", "test_users_accounts.xml");
            File xmlFile = new File("test_users_accounts.xml");
            xmlFile.deleteOnExit();
            XMLFileService xmlFileService = new XMLFileService("testPassword", xmlFile.getAbsolutePath());
            createXMLFile(xmlFileService);
            UserAccountXMLDAO userAccountXMLDAO = new UserAccountXMLDAO(xmlFileService);
            testFindUserAccountById(userAccountXMLDAO);
            testFindAllUsers(userAccountXMLDAO);
            testFindUsersAccountsByName(userAccountXMLDAO);
            testCreateUserAccount(userAccountXMLDAO);
            testDeleteUserAccountById(userAccountXMLDAO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testDeleteUserAccountById(UserAccountXMLDAO userAccountXMLDAO) {
        boolean removed = userAccountXMLDAO.deleteUserAccountById(3);
        if (removed) {
            System.out.println("testDeleteUserAccountById is OK");
            System.out.println(userAccountXMLDAO.findAllUsersAccounts());
        } else {
            System.out.println("testDeleteUserAccountById FAILED");
        }
    }

    private static void testCreateUserAccount(UserAccountXMLDAO userAccountXMLDAO) {
        UserAccount newUserAccount = new UserAccount();
        newUserAccount.setName("newName");
        newUserAccount.setSiteURL("newSite");
        newUserAccount.setDescription("newDescription");
        newUserAccount.setNickName("newNickName");
        newUserAccount.setPassword("newPass");
        Integer id = userAccountXMLDAO.createUserAccount(newUserAccount);
        UserAccount user = userAccountXMLDAO.findUserAccountById(id);
        if (user != null) {
            System.out.println("testCreateUserAccount is OK");
            System.out.println(user);
        } else {
            System.out.println("testCreateUserAccount FAILED");
        }
    }

    private static void testFindUsersAccountsByName(UserAccountXMLDAO userAccountXMLDAO) {
        Collection<UserAccount> usersAccounts = userAccountXMLDAO.findUsersAccountsByName("nickname_");
        if (usersAccounts != null && usersAccounts.size() > 0) {
            System.out.println(usersAccounts);
            System.out.println("testFindUsersAccountsByName is OK");
        } else {
            System.out.println("testFindUsersAccountsByName FAILED");
        }
    }

    private static void testFindAllUsers(UserAccountXMLDAO userAccountXMLDAO) {
        boolean testOK = userAccountXMLDAO.findAllUsersAccounts() != null;
        if (testOK) {
            System.out.println("testFindAllUsers is OK!");
        } else {
            System.err.println("testFindAllUsers FAILED!");
        }
    }

    private static void createXMLFile(XMLFileService xmlFileService) throws JAXBException, IOException {
        Collection<UserAccount> usersAccounts = new ArrayList<>();
        usersAccounts.add(createUserAccount(1));
        usersAccounts.add(createUserAccount(2));
        usersAccounts.add(createUserAccount(3));

        xmlFileService.saveToXMLFile(usersAccounts);
    }

    private static void testFindUserAccountById(UserAccountXMLDAO userAccountXMLDAO) {
        UserAccount userAccount = userAccountXMLDAO.findUserAccountById(1);
        if (userAccount != null) {
            System.out.println("Test testFindUserAccountById is OK!");
        } else {
            System.err.println("Test testFindUserAccountById FAILED!");
        }
    }
}
