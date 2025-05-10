package ro.eu.passwallet.client.flexui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import ro.eu.passwallet.client.utils.FileUtils;
import ro.eu.passwallet.model.UserAccount;
import ro.eu.passwallet.model.dao.UserAccountXMLDAO;
import ro.eu.passwallet.service.LoggerService;
import ro.eu.passwallet.service.UserAccountService;
import ro.eu.passwallet.service.xml.XMLFileService;

import java.net.URL;
import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ManagePassWalletUIController implements Initializable {
    private static final Logger logger = LoggerService.getInstance().getLogger();

    @FXML
    private GridPane ap;

    @FXML
    private TableView usersAccounts;

    @FXML
    private TextField filter;

    @FXML
    private Label passWalletFile;

    private final UIControllerHelper uiControllerHelper = new UIControllerHelper();
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
        ObservableList<TableColumn> columns = usersAccounts.getColumns();
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

        ContextMenu cm = createContextMenu();
        usersAccounts.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
            if(t.getButton() == MouseButton.SECONDARY) {
                cm.show(usersAccounts, t.getScreenX(), t.getScreenY());
            }
        });
    }

    private ContextMenu createContextMenu() {
        ContextMenu cm = new ContextMenu();
        MenuItem mi1 = new MenuItem("Copy");
        mi1.setOnAction(t -> onCopy());
        cm.getItems().add(mi1);
        MenuItem mi3 = new MenuItem("Copy user");
        mi3.setOnAction(t -> onCopyUser());
        cm.getItems().add(mi3);
        MenuItem mi2 = new MenuItem("Copy key");
        mi2.setOnAction(t -> onCopyKey());
        cm.getItems().add(mi2);
        return cm;
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
    public void onCopyAll() {
        Collection<UserAccount> userAccounts = userAccountService.search("");
        StringBuilder all = new StringBuilder();
        userAccounts.forEach(userAccount -> all.append(userAccount).append(System.lineSeparator()));
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(all.toString());
        clipboard.setContent(content);
    }

    @FXML
    public void onClearClipBoard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(" ");
        clipboard.setContent(content);
    }

    public void onCopyKey() {
        UserAccount selectedRow = (UserAccount) usersAccounts.getSelectionModel().getSelectedItem();
        if (selectedRow == null) {
            return;
        }

        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(selectedRow.getPassword());
        clipboard.setContent(content);
    }

    public void onCopyUser() {
        UserAccount selectedRow = (UserAccount) usersAccounts.getSelectionModel().getSelectedItem();
        if (selectedRow == null) {
            return;
        }

        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(selectedRow.getName());
        clipboard.setContent(content);
    }

    @FXML
    public void onDelete() {
        UserAccount selectedRow = (UserAccount) usersAccounts.getSelectionModel().getSelectedItem();
        if (selectedRow == null) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete user");
        alert.setContentText("Are you sure you want to delete selected user?");

        Optional<ButtonType> option = alert.showAndWait();
        if (ButtonType.OK.equals(option.get())) {
            usersAccounts.getItems().remove(selectedRow);
            FileUtils.backup(PassWalletApplicationContext.getInstance().getProperty("wallet_file"));
            userAccountService.delete(selectedRow.getId());
        }
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
        PassWalletApplicationContext.getInstance().setCurrentUserAccountAttribute(ManagePassWalletUIController.class.getName(), selectedRow);
        uiControllerHelper.launchEditAccountToWalletUIController(ap);
    }
}
