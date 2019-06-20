package client;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utills.URL;
import wsimportclient.Email;

import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
       WebServiceClient clientForServices = FactoryMethod.getServiceClient(URL.SOAP_HOME);
        List<Email> emailList = clientForServices.getAllEmails();
        logger.info("getAllEmailsFromServer result:");
        logger.info(emailList);

        List<Email> emailsByEmailAddress = clientForServices.getEmailsByEmailAddress("1234454n@gmail.com");
        logger.info("getEmailsByEmailAddress method result:");
        logger.info(emailsByEmailAddress);

        List<Email> emailListBySubject = clientForServices.getEmailsBySubject("Test1");
        logger.info("getEmailsBySubject method result:");
        logger.info(emailListBySubject);

        Email email = new Email();
        email.setSubject("Test29");
        email.setEmailAddress("134411111n@gmail.com");
        email.setBody("Hello my friends");
        boolean isEmailAdded = clientForServices.addEmail(email);
        logger.info("addEmail method result:");
        logger.info(isEmailAdded);

        String subject = "Test29";
        boolean isRemovedEmail = clientForServices.removeEmail(subject);
        logger.info("removeEmail method result:");
        logger.info(isRemovedEmail);
     }
}
