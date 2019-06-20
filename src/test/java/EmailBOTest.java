import businessobject.EmailBO;
import model.Email;
import org.junit.Assert;
import org.testng.annotations.Test;
import utills.DataProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EmailBOTest {
    private Email email = new Email();
    private EmailBO emailBO = new EmailBO();

    @Test
    public void getEmailBySubjectTest() {
        String subject = "Test5";
        List<Email> emailsList = emailBO.getListOfEmailByEmailSubject(subject);
        Assert.assertEquals("[Email{email address: 1401test2705@gmail.com, subject: Test5, body: "+
                "To my viber account}]", emailsList.toString());
    }

    @Test
    public void getEmailsByNotExistingSubject() {
        String subject = "Test35";
        List<Email> emailsList = emailBO.getListOfEmailByEmailSubject(subject);
        Assert.assertTrue(emailsList.isEmpty());
    }

    @Test
    public void getEmailsByEmailAddressTest() {
        EmailBO emailBO = new EmailBO();
        String emailAddress = "1401test2705@gmail.com";
        List<Email> emailsList = emailBO.getListOfEmailsByEmail(emailAddress);
        Assert.assertEquals("[Email{email address: 1401test2705@gmail.com, subject: Test5, body: "+
                "To my viber account}]", emailsList.toString());
    }

    @Test
    public void getEmailByNotExistingEmailAddressTest() {
        EmailBO emailBO = new EmailBO();
        String emailAddress = "140134test2705@gmail.com";
        List<Email> emailsList = emailBO.getListOfEmailsByEmail(emailAddress);
        Assert.assertTrue(emailsList.isEmpty());
    }

    @Test
    public void addEmailWithEmptySubjectTest() {
        email.setEmailAddress("123434566@gmail.com");
        email.setSubject("");
        email.setBody("Hello word");
        boolean isAdded = emailBO.addEmailToServer(email);
        Assert.assertFalse(isAdded);
    }

    @Test
    public void removeEmailWithEmptySubjectTest() {
        String subject = "";
        boolean isAdded = emailBO.removeEmailFromServer(subject);
        Assert.assertFalse(isAdded);
    }


    @Test
    public void removeNotExistingEmailTest() {
        String subject = "Test13";
        email.setEmailAddress("1232344566@gmail.com");
        email.setSubject("Test13");
        email.setBody("Hello word");
        emailBO.addEmailToServer(email);
        emailBO.removeEmailFromServer(subject);
        boolean isEmailRemoved = emailBO.removeEmailFromServer(subject);
        Assert.assertFalse(isEmailRemoved);
    }
}



