package ro.eu.passwallet.client.flexui;

import ro.eu.passwallet.model.UserAccount;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class PassWalletApplicationContext {
    private static final PassWalletApplicationContext passWalletApplicationContext = new PassWalletApplicationContext();
    private final Map<String, String> properties = new ConcurrentHashMap<>();
    private final Map<String, Object> attributes = new ConcurrentHashMap<>();

    private PassWalletApplicationContext() {
    }

    public static PassWalletApplicationContext getInstance() {
        return passWalletApplicationContext;
    }

    public String getProperty(String property) {
        return properties.get(property);
    }

    public void setProperty(String property, String value) {
        if (value == null) {
            properties.remove(property);
        }else {
            properties.put(property, value);
        }
    }

    public Object getAttribute(String attributeName) {
        return attributes.get(attributeName);
    }

    public void setAttribute(String attributeName, Object attributeValue) {
        if (attributeValue == null) {
            attributes.remove(attributeName);
        }else {
            attributes.put(attributeName, attributeValue);
        }
    }

    public void setCurrentUserAccountAttribute(String source, UserAccount currentUserAccount) {
        Map<String, Object> currentUserAccountValue = new HashMap<>();
        currentUserAccountValue.put("source", source);
        currentUserAccountValue.put("userAccount", currentUserAccount);

        setAttribute(UIControllerHelper.CURRENT_USER_ACCOUNT, currentUserAccountValue);

    }

    public UserAccount getCurrentUserAccountAttribute() {
        Map<String, Object> currentUserAccountValue = (Map<String, Object>) getAttribute(UIControllerHelper.CURRENT_USER_ACCOUNT);
        if (currentUserAccountValue == null) {
            return null;
        }
        return (UserAccount) currentUserAccountValue.get("userAccount");
    }

    public String getCurrentUserAccountSourceAttribute() {
        Map<String, Object> currentUserAccountValue = (Map<String, Object>) getAttribute(UIControllerHelper.CURRENT_USER_ACCOUNT);
        return (String) currentUserAccountValue.get("source");
    }
}
