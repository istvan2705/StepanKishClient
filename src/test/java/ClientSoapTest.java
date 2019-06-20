import org.junit.Assert;
import org.testng.annotations.Test;
import wsimportclient.Email;
import wsimportclient.ListWrapper;
import wsimportclient.MailService;
import wsimportclient.MailServiceImplService;

import java.util.List;

public class ClientSoapTest {
    private MailServiceImplService service = new MailServiceImplService();
    private MailService soap = service.getMailServiceImplPort();

    @Test
    public void getEmailsBySubjectTest() {
        String subject = "Test1";
        ListWrapper email = soap.getEmailBySubject(subject);
        List<Email> emails = email.getEmailsList();
        Assert.assertEquals("[Email{body: Hello my friend, subject: Test1, email address: 1234454n@gmail.com}]",
                emails.toString());
    }

    @Test
    public void getEmailsByEmailAddressTest() {
        String emailAddress = "1234454n@gmail.com";
        ListWrapper email = soap.getEmailByEmailAddress(emailAddress);
        List<Email> emails = email.getEmailsList();
        Assert.assertEquals("[Email{body: Hello my friend, subject: Test1, email address: 1234454n@gmail.com}]",
                emails.toString());
    }

    @Test
    public void addEmailToServerTest() {
        Email email = new Email();
        email.setEmailAddress("43454566@gmail.com");
        email.setSubject("Test48");
        email.setBody("Hello Peter");
        boolean isEmailAdded = soap.addEmail(email);
        Assert.assertTrue(isEmailAdded);
    }

    @Test
    public void removeEmailFromServerTest() {
        String subject = "Test48";
        boolean isEmailRemoved = soap.removeEmail(subject);
        Assert.assertTrue(isEmailRemoved);
    }

    @Test
    public void getEmailByNotExistingSubjectTest() {
        String subject = "Test54";
        ListWrapper email = soap.getEmailBySubject(subject);
        List<Email> emails = email.getEmailsList();
        Assert.assertEquals("[]", emails.toString());
    }

    @Test
    public void getEmailByNotExistingEmailAddressTest() {
        String subject = "weerr1234@gmail.com";
        ListWrapper email = soap.getEmailBySubject(subject);
        List<Email> emails = email.getEmailsList();
        Assert.assertEquals("[]", emails.toString());
    }


    @Test
    public void addEmptyEmailToServerTest() {
        Email email = new Email();
        email.setEmailAddress("");
        email.setSubject("");
        email.setBody("");
        boolean isEmailAdded = soap.addEmail(email);
        Assert.assertFalse(isEmailAdded);
    }

    @Test
    public void removeEmptyEmailFromServerTest() {
        String subject = "";
        boolean isEmailRemoved = soap.removeEmail(subject);
        Assert.assertFalse(isEmailRemoved);
    }
}
