package ro.eu.passwallet.client.flexui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ro.eu.passwallet.model.UserAccount;
import ro.eu.passwallet.model.dao.UserAccountXMLDAO;
import ro.eu.passwallet.service.LoggerService;
import ro.eu.passwallet.service.UserAccountService;
import ro.eu.passwallet.service.xml.XMLFileService;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class EditUserAccountUIController implements Initializable {
    private static final Logger logger = LoggerService.getInstance().getLogger();

    @FXML
    private AnchorPane ap;

    @FXML
    private PasswordField password;

    @FXML
    private TextField nickName;

    @FXML
    private TextField name;

    @FXML
    private TextField site;

    @FXML
    private TextField description;

    private final UIControllerHelper uiControllerHelper = new UIControllerHelper();

    @FXML
    private void onSave(ActionEvent event) {
        if (password.getText().trim().length() == 0 || name.getText().trim().length() == 0) {
            logger.info("invalid password " + password.getText() + "or invalid name " + name.getText());
            return;
        }

        UserAccount selectedUserAccount = (UserAccount) PassWalletApplicationContext.getInstance().getAttribute("selected_user_account");
        selectedUserAccount.setDescription(description.getText());
        selectedUserAccount.setPassword(password.getText());
        selectedUserAccount.setNickName(nickName.getText());
        selectedUserAccount.setSiteURL(site.getText());
        selectedUserAccount.setName(name.getText());

        String password = PassWalletApplicationContext.getInstance().getProperty("password");
        String walletXMLFilePath = PassWalletApplicationContext.getInstance().getProperty("wallet_file");
        UserAccountService userAccountService = new UserAccountService(new UserAccountXMLDAO(new XMLFileService<>(password, walletXMLFilePath, UserAccount.class), LoggerService.getInstance()));

        userAccountService.update(selectedUserAccount);

        uiControllerHelper.launchManageWalletUIController(ap);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserAccount selectedUserAccount = (UserAccount) PassWalletApplicationContext.getInstance().getAttribute("selected_user_account");
        password.setText(selectedUserAccount.getPassword());
        nickName.setText(selectedUserAccount.getNickName());
        name.setText(selectedUserAccount.getName());
        site.setText(selectedUserAccount.getSiteURL());
        description.setText(selectedUserAccount.getDescription());
    }
}
