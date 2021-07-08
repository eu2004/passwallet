package ro.eu.passwallet.client.flexui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ro.eu.passwallet.model.UserAccount;
import ro.eu.passwallet.service.PasswordGenerator;

import java.net.URL;
import java.util.ResourceBundle;

public class PasswordGeneratorUIController implements Initializable {

    private final UIControllerHelper uiControllerHelper = new UIControllerHelper();

    private final PasswordGenerator passwordGenerator = new PasswordGenerator();

    @FXML
    private AnchorPane ap;

    @FXML
    private TextField password;

    @FXML
    private CheckBox useNumbers;

    @FXML
    private CheckBox useUpperCase;

    @FXML
    private CheckBox useLowerCase;

    @FXML
    private CheckBox useSymbols;

    @FXML
    private Spinner<Integer> passLength;

    private enum CLASSES {
        ADDUSER(AddUserAccountUIController.class.getName()),
        EDITUSER(EditUserAccountUIController.class.getName()),
        UNKNOWN("");

        private final String className;

        CLASSES(String className) {
            this.className = className;
        }

        public static CLASSES valueOfClassName(String className) {
            if (className == null) {
                return UNKNOWN;
            }

            for (CLASSES value : values()) {
                if (value.className.equals(className)) {
                    return value;
                }
            }
            return UNKNOWN;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boolean isIncludeNumbers = Boolean.parseBoolean(uiControllerHelper.getPreference("pass.gen.use-number", String.valueOf(passwordGenerator.isIncludeNumbers())));
        boolean isIncludeLowerCase = Boolean.parseBoolean(uiControllerHelper.getPreference("pass.gen.use-lower", String.valueOf(passwordGenerator.isIncludeLowerCase())));
        boolean isIncludeUpperCase = Boolean.parseBoolean(uiControllerHelper.getPreference("pass.gen.use-upper", String.valueOf(passwordGenerator.isIncludeUpperCase())));
        boolean isIncludeSymbols = Boolean.parseBoolean(uiControllerHelper.getPreference("pass.gen.use-symbol", String.valueOf(passwordGenerator.isIncludeSymbols())));
        int passwordLength = Integer.parseInt(uiControllerHelper.getPreference("pass.gen.pass-length", String.valueOf(passwordGenerator.getLength())));

        passwordGenerator.setIncludeSymbols(isIncludeSymbols);
        passwordGenerator.setIncludeLowerCase(isIncludeLowerCase);
        passwordGenerator.setIncludeUpperCase(isIncludeUpperCase);
        passwordGenerator.setIncludeNumbers(isIncludeNumbers);
        passwordGenerator.setLength(passwordLength);

        useNumbers.setSelected(passwordGenerator.isIncludeNumbers());
        useUpperCase.setSelected(passwordGenerator.isIncludeUpperCase());
        useLowerCase.setSelected(passwordGenerator.isIncludeLowerCase());
        useSymbols.setSelected(passwordGenerator.isIncludeSymbols());
        if (passLength.getValueFactory() == null) {
            passLength.setValueFactory(new SpinnerValueFactory<>() {
                @Override
                public void decrement(int i) {
                    if (getValue() == 3) {
                        return;
                    }
                    setValue(getValue() - i);
                }

                @Override
                public void increment(int i) {
                    setValue(getValue() + i);
                }
            });
        }
        passLength.getValueFactory().setValue(passwordGenerator.getLength());
        passLength.valueProperty().addListener((obs, oldValue, newValue) ->
                passwordGenerator.setLength(newValue));
    }

    @FXML
    private void onGenerate(ActionEvent event) {
        password.setText(passwordGenerator.generate());
    }

    @FXML
    private void onClose(ActionEvent event) {
        UserAccount currentUserAccount = PassWalletApplicationContext.getInstance().getCurrentUserAccountAttribute();
        currentUserAccount.setPassword(password.getText());

        switch (CLASSES.valueOfClassName(PassWalletApplicationContext.getInstance().getCurrentUserAccountSourceAttribute())) {
            case ADDUSER -> uiControllerHelper.launchAddUserAccountToWalletUIController(ap);
            case EDITUSER -> uiControllerHelper.launchEditAccountToWalletUIController(ap);
            default -> throw new IllegalStateException("Unexpected value: " + PassWalletApplicationContext.getInstance().getCurrentUserAccountSourceAttribute());
        }

    }

    public void onUseNumbers() {
        passwordGenerator.setIncludeNumbers(!passwordGenerator.isIncludeNumbers());
    }

    public void onUseUpperCase() {
        passwordGenerator.setIncludeUpperCase(!passwordGenerator.isIncludeUpperCase());
    }

    public void onUseLowerCase() {
        passwordGenerator.setIncludeLowerCase(!passwordGenerator.isIncludeLowerCase());
    }

    public void onUseSymbols() {
        passwordGenerator.setIncludeSymbols(!passwordGenerator.isIncludeSymbols());
    }

}
