package client ;

import wsimportclient.Email;

import java.util.List;

public interface WebServiceClient {

    List<Email> getAllEmails();

    List<Email> getEmailsByEmailAddress(String emailAddress);

    List<Email> getEmailsBySubject(String subject);

    boolean addEmail(Email email);

    boolean removeEmail(String subject);
}
