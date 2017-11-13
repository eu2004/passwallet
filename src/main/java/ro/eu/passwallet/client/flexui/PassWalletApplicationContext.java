package ro.eu.passwallet.client.flexui;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class PassWalletApplicationContext {
    private static PassWalletApplicationContext passWalletApplicationContext = new PassWalletApplicationContext();
    private Map<String, String> properties = new ConcurrentHashMap<>();
    private Map<String, Object> attributes = new ConcurrentHashMap<>();

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
}
