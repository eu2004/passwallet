package ro.eu.passwallet.client.flexui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ro.eu.passwallet.client.ClientUIException;
import ro.eu.passwallet.model.UserAccount;
import ro.eu.passwallet.service.LoggerService;
import ro.eu.passwallet.service.xml.XMLFileService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CreatePassWalletUIController {
    private static final Logger logger = LoggerService.getInstance().getLogger();

    @FXML
    private AnchorPane ap;

    @FXML
    private PasswordField passwordId;

    private final UIControllerHelper uiControllerHelper = new UIControllerHelper();

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
            XMLFileService<UserAccount> xmlFileService = new XMLFileService<>(passwordId.getText(), file.getAbsolutePath(), UserAccount.class);
            logger.info("file " + file);
            byte[] content;
            try {
                content = getWalletXMLTemplateContent();
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

    private byte[] getWalletXMLTemplateContent() throws IOException {
        try (InputStream xmlFileInputStream = CreatePassWalletUIController.class.getClassLoader()
                .getResourceAsStream("passwallet_xml_file_template.xml")) {
            if (xmlFileInputStream == null) {
                return null;
            }

            List<Byte> content = new ArrayList<>();
            byte[] readBuffer = new byte[1024];
            int bytesReadCount;
            while ((bytesReadCount = xmlFileInputStream.read(readBuffer)) != -1) {
                for (int i = 0; i < bytesReadCount; i++) {
                    content.add(readBuffer[i]);
                }
            }
            if (content.isEmpty()) {
                return null;
            } else {
                byte[] contentInBytes = new byte[content.size()];
                for (int i = 0; i < contentInBytes.length; i++) {
                    contentInBytes[i] = content.get(i);
                }
                return contentInBytes;
            }
        }
    }
}
