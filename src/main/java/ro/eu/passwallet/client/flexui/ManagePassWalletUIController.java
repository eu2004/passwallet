package ro.eu.passwallet.client.flexui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import ro.eu.passwallet.model.UserAccount;
import ro.eu.passwallet.model.dao.UserAccountXMLDAO;
import ro.eu.passwallet.service.UserAccountService;
import ro.eu.passwallet.service.LoggerService;
import ro.eu.passwallet.service.xml.XMLFileService;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ManagePassWalletUIController implements Initializable {
    private static final Logger logger = LoggerService.getInstance().getLogger();

    @FXML
    private GridPane ap;

    @FXML
    private TableView<UserAccount> usersAccounts;

    @FXML
    private TextField filter;

    @FXML
    private Label passWalletFile;

    private UIControllerHelper uiControllerHelper = new UIControllerHelper();
    private UserAccountService userAccountService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String password = PassWalletApplicationContext.getInstance().getProperty("password");
        String walletXMLFilePath = PassWalletApplicationContext.getInstance().getProperty("wallet_file");
        logger.info("walletXMLFilePath " + walletXMLFilePath);
        passWalletFile.setText(passWalletFile.getText() + " " + walletXMLFilePath);

        userAccountService = new UserAccountService(new UserAccountXMLDAO(new XMLFileService<>(password, walletXMLFilePath, UserAccount.class), LoggerService.getInstance()));
        final ObservableList<UserAccount> data =
                FXCollections.observableArrayList(userAccountService.search(""));
        ObservableList<TableColumn<UserAccount, ?>> columns = usersAccounts.getColumns();
        usersAccounts.setEditable(false);
        columns.forEach(c -> {
            switch (c.getId()) {
                case "id" -> c.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("id"));
                case "nickName" -> c.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("nickName"));
                case "name" -> c.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("name"));
                case "site" -> c.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("siteURL"));
                case "description" -> c.setCellValueFactory(new PropertyValueFactory<UserAccount, String>("description"));
            }
        });

        usersAccounts.setItems(data);
    }

    @FXML
    public void onCopy() {
        UserAccount selectedRow = (UserAccount) usersAccounts.getSelectionModel().getSelectedItem();
        if (selectedRow == null) {
            return;
        }

        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(selectedRow.toString());
        clipboard.setContent(content);
    }

    @FXML
    public void onDelete() {
        UserAccount selectedRow = (UserAccount) usersAccounts.getSelectionModel().getSelectedItem();
        if (selectedRow == null) {
            return;
        }

        usersAccounts.getItems().remove(selectedRow);
        userAccountService.delete(selectedRow.getId());
    }

    @FXML
    public void onAdd() {
        uiControllerHelper.launchAddUserAccountToWalletUIController(ap);
    }

    @FXML
    public void onFilterKeyReleased() {
        String filterText = filter.getText();
        final ObservableList<UserAccount> filteredData =
                FXCollections.observableArrayList(userAccountService.search(filterText));
        usersAccounts.setItems(filteredData);
    }

    @FXML
    public void onEdit() {
        UserAccount selectedRow = (UserAccount) usersAccounts.getSelectionModel().getSelectedItem();
        if (selectedRow == null) {
            return;
        }
        PassWalletApplicationContext.getInstance().setAttribute("selected_user_account",
                selectedRow);
        uiControllerHelper.launchEditAccountToWalletUIController(ap);
    }
}
