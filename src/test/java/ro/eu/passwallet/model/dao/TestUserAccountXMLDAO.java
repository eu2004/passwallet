package ro.eu.passwallet.model.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import ro.eu.passwallet.model.UserAccount;
import ro.eu.passwallet.service.LoggerService;
import ro.eu.passwallet.service.xml.XMLFileService;

public class TestUserAccountXMLDAO extends CommonTest {
    private static final Logger logger = LoggerService.getInstance().getLogger();

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
            logger.info("testDeleteUserAccountById is OK");
            logger.info(userAccountXMLDAO.findAllUsersAccounts().toString());
        } else {
            logger.info("testDeleteUserAccountById FAILED");
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
            logger.info("testCreateUserAccount is OK");
            logger.info(user.toString());
        } else {
            logger.info("testCreateUserAccount FAILED");
        }
    }

    private static void testFindUsersAccountsByName(UserAccountXMLDAO userAccountXMLDAO) {
        Collection<UserAccount> usersAccounts = userAccountXMLDAO.findUsersAccountsByName("nickname_");
        if (usersAccounts != null && usersAccounts.size() > 0) {
            logger.info(usersAccounts.toString());
            logger.info("testFindUsersAccountsByName is OK");
        } else {
            logger.info("testFindUsersAccountsByName FAILED");
        }
    }

    private static void testFindAllUsers(UserAccountXMLDAO userAccountXMLDAO) {
        boolean testOK = userAccountXMLDAO.findAllUsersAccounts() != null;
        if (testOK) {
            logger.info("testFindAllUsers is OK!");
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
            logger.info("Test testFindUserAccountById is OK!");
        } else {
            logger.info("Test testFindUserAccountById FAILED!");
        }
    }
}
