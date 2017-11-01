package ro.eu.passwallet.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserAccount {
    private Integer id;
    private String name;
    private String password;
    private String nickName;
    private String description;
    private String siteURL;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSiteURL() {
        return siteURL;
    }

    public void setSiteURL(String applicationURL) {
        this.siteURL = applicationURL;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", siteURL='" + siteURL + '\'' +
                ", nickName='" + nickName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccount that = (UserAccount) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (!password.equals(that.password)) return false;
        if (siteURL != null ? !siteURL.equals(that.siteURL) : that.siteURL != null) return false;
        if (nickName != null ? !nickName.equals(that.nickName) : that.nickName != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (siteURL != null ? siteURL.hashCode() : 0);
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
