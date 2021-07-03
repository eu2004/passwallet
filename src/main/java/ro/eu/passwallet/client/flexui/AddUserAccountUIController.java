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

public class AddUserAccountUIController implements Initializable {
    static final String ADD_USER_ACCOUNT = "add_user_account";
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserAccount addUserAccount = (UserAccount) PassWalletApplicationContext.getInstance().getAttribute(ADD_USER_ACCOUNT);
        if (addUserAccount != null) {
            password.setText(addUserAccount.getPassword());
            nickName.setText(addUserAccount.getNickName());
            name.setText(addUserAccount.getName());
            site.setText(addUserAccount.getSiteURL());
            description.setText(addUserAccount.getDescription());
        }
    }

    @FXML
    private void onCreate(ActionEvent event) {
        if (password.getText().trim().length() == 0 || name.getText().trim().length() == 0) {
            logger.info("password " + password.getText() + "; name " + name.getText());
            return;
        }

        UserAccount userAccount = getCurrentUserAccount();

        String password = PassWalletApplicationContext.getInstance().getProperty("password");
        String walletXMLFilePath = PassWalletApplicationContext.getInstance().getProperty("wallet_file");
        UserAccountService userAccountService = new UserAccountService(new UserAccountXMLDAO(new XMLFileService<>(password,
                walletXMLFilePath, UserAccount.class), LoggerService.getInstance()));

        userAccountService.createUser(userAccount);

        uiControllerHelper.launchManageWalletUIController(ap);
    }

    public void onGenerate(ActionEvent actionEvent) {
        PassWalletApplicationContext.getInstance().setAttribute(ADD_USER_ACCOUNT,
                getCurrentUserAccount());
        uiControllerHelper.launchPasswordGeneratorUIController(ap);
    }

    private UserAccount getCurrentUserAccount() {
        UserAccount userAccount = new UserAccount();
        userAccount.setDescription(description.getText());
        userAccount.setPassword(password.getText());
        userAccount.setNickName(nickName.getText());
        userAccount.setSiteURL(site.getText());
        userAccount.setName(name.getText());
        return userAccount;
    }
}
