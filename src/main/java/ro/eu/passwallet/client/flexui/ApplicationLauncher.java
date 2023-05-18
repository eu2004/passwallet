package ro.eu.passwallet.client.flexui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;

public class ApplicationLauncher extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("open_passwallet_ui.fxml"));
        primaryStage.setTitle("Pass-Wallet");
        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.show();
    }

    @Override
    public void stop() {
        clearClipboardHook();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void clearClipboardHook() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(" ");
        clipboard.setContent(content);
        System.out.println("Clipboard cleared");
    }
}
