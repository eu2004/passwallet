package ro.eu.passwallet.client.flexui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ro.eu.passwallet.client.ClientUIException;
import ro.eu.passwallet.service.xml.XMLFileService;

public class CreatePassWalletUIController {
    @FXML
    private AnchorPane ap;

    @FXML
    private PasswordField passwordId;

    private UIControllerHelper uiControllerHelper = new UIControllerHelper();

    @FXML
    private void fileSaveButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ap.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Resource File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Wallet files (encrypted xml)", "*.xml"));
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            XMLFileService xmlFileService = new XMLFileService(passwordId.getText(), file.getAbsolutePath());
            System.out.println("file " + file);
            byte[] content = null;
            try {
                content = getWalletXMLTemplateContent("passwallet_xml_file_template.xml");
            } catch (IOException e) {
                throw new ClientUIException(e);
            }

            xmlFileService.saveXMLFile(content);

            PassWalletApplicationContext.getInstance().setProperty("password", passwordId.getText());
            PassWalletApplicationContext.getInstance().setProperty("wallet_file", file.getAbsolutePath());
            uiControllerHelper.setPreference("wallet_file", file.getAbsolutePath());

            uiControllerHelper.launchManageWalletUIController(ap);
        }
    }

    private byte[] getWalletXMLTemplateContent(String xmlFilePath) throws IOException {
        File xmlFile = new File(xmlFilePath);
        System.out.println(xmlFile.getAbsolutePath());
        byte[] content = new byte[(int) xmlFile.length()];
        try (FileInputStream xmlFileInputStream = new FileInputStream(xmlFile)) {
            xmlFileInputStream.read(content);
            return content;
        }
    }
}
