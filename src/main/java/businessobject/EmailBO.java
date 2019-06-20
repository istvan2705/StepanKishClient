package businessobject;
import dao.MailServiceManager;
import model.Email;
import java.util.List;

public class EmailBO {

    public List<Email> getListOfEmailsOnServer(){
        return MailServiceManager.getAllEmails();
    }

    public List<Email> getListOfEmailsByEmail(String senderEmailAddress) {
        return MailServiceManager.getEmailsBySenderEmailAddress(senderEmailAddress);
    }

    public List<Email> getListOfEmailByEmailSubject(String subject) {
        return MailServiceManager.getEmailsByEmailSubject(subject);
    }

    public boolean addEmailToServer(Email email) {
        return MailServiceManager.addEmailToMailBox(email);
    }

    public boolean removeEmailFromServer(String subject) {
        return MailServiceManager.removeEmailFromMailBox(subject);
    }


}
