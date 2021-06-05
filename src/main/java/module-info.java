module passwallet {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.logging;
    requires java.prefs;
    requires passwalletservice;

    opens ro.eu.passwallet.client.flexui;
}