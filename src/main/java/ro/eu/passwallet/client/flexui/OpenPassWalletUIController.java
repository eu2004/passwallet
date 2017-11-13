package ro.eu.passwallet.client.flexui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ro.eu.passwallet.client.ClientUIException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OpenPassWalletUIController implements Initializable {
    @FXML
    private AnchorPane ap;

    @FXML
    private Label fileChooseLabel;

    @FXML
    private PasswordField passwordId;

    private UIControllerHelper uiControllerHelper = new UIControllerHelper();

    @FXML
    private void fileChooseButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ap.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Wallet files (encrypted xml)", "*.xml"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            fileChooseLabel.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void openWalletOnAction(ActionEvent event) {
        if (fileChooseLabel.getText() != null && fileChooseLabel.getText().length() > 0 && passwordId.getText() != null && passwordId.getText().length() > 0) {
            PassWalletApplicationContext.getInstance().setProperty("password", passwordId.getText());
            PassWalletApplicationContext.getInstance().setProperty("wallet_file", fileChooseLabel.getText());
            uiControllerHelper.setPreference("wallet_file", PassWalletApplicationContext.getInstance().getProperty("wallet_file"));
            uiControllerHelper.launchManageWalletUIController(ap);
        }
    }

    @FXML
    private void createWalletOnAction(ActionEvent event) {
        Stage primaryStage = (Stage) ap.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("create_passwallet_ui.fxml"));
        } catch (IOException e) {
            throw new ClientUIException(e);
        }
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String lastWalletFile = uiControllerHelper.getPreference("wallet_file", "");
        if (lastWalletFile.length() > 0) {
            fileChooseLabel.setText(lastWalletFile);
        }
    }
}
