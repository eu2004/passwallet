package ro.eu.passwallet.service.xml;

import ro.eu.passwallet.model.UserAccount;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class XMLFileHelper {

    public byte[] getXMLFileContent(String xmlFilePath) throws IOException {
        ByteArrayOutputStream xmlFileOutput = new ByteArrayOutputStream();
        try(FileInputStream xmlFileInputStream = new FileInputStream(xmlFilePath)){
            byte[] buffer = new byte[1024];
            int readBytes;
            while((readBytes = xmlFileInputStream.read(buffer)) != -1) {
                xmlFileOutput.write(buffer, 0, readBytes);
            }
        }
        return xmlFileOutput.toByteArray();
    }

    public void saveXMLFileContent(byte[]content, String xmlFilePath) throws IOException {
        try(FileOutputStream xmlFileOut = new FileOutputStream(xmlFilePath)) {
            xmlFileOut.write(content);
            xmlFileOut.flush();
        }
    }

    protected static class Wrapper<T> {

        private Collection<T> items;

        public Wrapper() {
            items = new ArrayList<T>();
        }

        public Wrapper(Collection<T> items) {
            this.items = items;
        }

        @XmlAnyElement(lax = true)
        public Collection<T> getItems() {
            return items;
        }

    }

    protected JAXBContext jaxbContext;

    public XMLFileHelper() throws JAXBException {
        jaxbContext = JAXBContext.newInstance(Wrapper.class, UserAccount.class);
    }

    public Collection<UserAccount> getAllUsersAccountsFromXML(byte[] xmlFileContent) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return unmarshal(unmarshaller, UserAccount.class, xmlFileContent);
    }

    public byte[] convertUsersAccountsToXML(Collection<UserAccount> usersAccounts) throws JAXBException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        ByteArrayOutputStream xmlOut = new ByteArrayOutputStream();
        marshal(marshaller, usersAccounts, "USERSACCOUNTS", xmlOut);
        return xmlOut.toByteArray();
    }

    /**
     * Unmarshal XML to Wrapper and return List value.
     */
    protected <T> Collection<T> unmarshal(Unmarshaller unmarshaller,
                                          Class<T> clazz, byte[] xmlFileContent) throws JAXBException {
        StreamSource xml = new StreamSource(new ByteArrayInputStream(xmlFileContent));
        Wrapper<T> wrapper = (Wrapper<T>) unmarshaller.unmarshal(xml,
                Wrapper.class).getValue();
        return wrapper.getItems();
    }

    /**
     * Wrap List in Wrapper, then leverage JAXBElement to supply root element
     * information.
     */
    protected void marshal(Marshaller marshaller, Collection<?> collection, String name, ByteArrayOutputStream xmlOut)
            throws JAXBException {
        QName qName = new QName(name);
        Wrapper wrapper = new Wrapper(collection);
        JAXBElement<Wrapper> jaxbElement = new JAXBElement<Wrapper>(qName,
                Wrapper.class, wrapper);
        StreamResult out = new StreamResult(xmlOut);
        marshaller.marshal(jaxbElement, out);
    }
}
