package client;

import wsimportclient.Email;
import wsimportclient.ListWrapper;
import wsimportclient.MailService;
import wsimportclient.MailServiceImplService;

import java.util.List;

public class SoapClient implements WebServiceClient {
    MailServiceImplService service = new MailServiceImplService();
    MailService soap = service.getMailServiceImplPort();

    @Override
    public List<Email> getAllEmails() {
        ListWrapper email = soap.getAllEmails();
        return email.getEmailsList();
    }

    @Override
    public List<Email> getEmailsByEmailAddress(String emailAddress) {
        ListWrapper email = soap.getEmailByEmailAddress(emailAddress);
        return email.getEmailsList();
    }

    @Override
    public List<Email> getEmailsBySubject(String subject) {
        ListWrapper email = soap.getEmailBySubject(subject);
        return email.getEmailsList();
    }

    @Override
    public boolean addEmail(Email email) {
        return soap.addEmail(email);
    }

    @Override
    public boolean removeEmail(String subject) {
        return soap.removeEmail(subject);
    }
}







