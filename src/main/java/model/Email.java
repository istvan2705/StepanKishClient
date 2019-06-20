package model;
import java.io.Serializable;

public class Email implements Serializable {

    private String emailAddress;
    private String subject;
    private String body;

    public Email(){}

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;
        Email email = (Email) o;
        if (getSubject() != null ? !getSubject().equals(email.getSubject()) : email.getSubject() != null) return false;
        if (getEmailAddress() != null ? !getEmailAddress().equals(email.getEmailAddress()) : email.getEmailAddress() != null)
            return false;
        return getBody() != null ? getBody().equals(email.getBody()) : email.getBody() == null;
    }

    @Override
    public int hashCode() {
        int result = getSubject() != null ? getSubject().hashCode() : 0;
        result = 31 * result + (getEmailAddress() != null ? getEmailAddress().hashCode() : 0);
        result = 31 * result + (getBody() != null ? getBody().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("Email{email address: %s, subject: %s, body: %s}", getEmailAddress(), getSubject(),  getBody());
    }
}
