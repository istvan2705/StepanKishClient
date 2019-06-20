package webservices.soap;

import model.Email;

import java.util.List;

public class ListWrapper {

    private List<Email> emailsList;

    public List<Email> getEmailsList() {
        return emailsList;
    }

    public void setEmailsList(List<Email> emailsList) {
        this.emailsList = emailsList;
    }
}
