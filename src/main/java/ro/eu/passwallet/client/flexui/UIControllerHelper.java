package ro.eu.passwallet.client.flexui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ro.eu.passwallet.client.ClientUIException;

import java.io.IOException;
import java.util.prefs.Preferences;

public class UIControllerHelper {

    private final Preferences preferences = Preferences.userRoot().node("passwallet");

    public void launchManageWalletUIController(Pane anchorPane) {
        Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("manage_passwallet_ui.fxml"));
        } catch (IOException e) {
            throw new ClientUIException(e);
        }
        primaryStage.setScene(new Scene(root, 1260, 768));
        primaryStage.show();
    }

    public void launchAddUserAccountToWalletUIController(Pane anchorPane) {
        Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("add_user_account_ui.fxml"));
        } catch (IOException e) {
            throw new ClientUIException(e);
        }
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public void launchEditAccountToWalletUIController(Pane anchorPane) {
        Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("edit_user_account_ui.fxml"));
        } catch (IOException e) {
            throw new ClientUIException(e);
        }
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public void setPreference(String key, String value) {
        preferences.put(key, value);
    }

    public String getPreference(String key, String defaultValue) {
        return preferences.get(key, defaultValue);
    }
}
