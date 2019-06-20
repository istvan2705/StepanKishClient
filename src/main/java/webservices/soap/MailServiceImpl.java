package webservices.soap;

import businessobject.EmailBO;
import model.Email;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import webservices.fault.FaultMessage;
import webservices.fault.ServiceFaultInfo;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType
@WebService(endpointInterface = "webservices.soap.MailService")
public class MailServiceImpl implements MailService {
    private static final Logger logger = LogManager.getLogger(MailServiceImpl.class);

    @XmlElement
    @Override
    public ListWrapper getAllEmails() {
        logger.info("get All emails from server");
        EmailBO emailBO = new EmailBO();
        List<Email> emails = emailBO.getListOfEmailsOnServer();
        if (emails.isEmpty()) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NO_EMAIL_FOUND_ON_SERVER);
            logger.warn(faultInfo);
        }
        ListWrapper wrapper = new ListWrapper();
        wrapper.setEmailsList(emails);
        return wrapper;
    }

    @XmlElement
    @Override
    public ListWrapper getEmailByEmailAddress(String emailAddress) {
        logger.info("get emails by emailAddress");
        EmailBO emailBO = new EmailBO();
        List<Email> emails = emailBO.getListOfEmailsByEmail(emailAddress);
        if (emails.isEmpty()) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NO_EMAIL_FOUND_BY_EMAIL_ADDRESS, emailAddress);
            logger.warn(faultInfo);
        }
        ListWrapper wrapper = new ListWrapper();
        wrapper.setEmailsList(emails);
        logger.info("getEmailByEmailAddress result:" + emails);
        return wrapper;
    }

    @XmlElement
    @Override
    public ListWrapper getEmailBySubject(String subject) {
        EmailBO emailBO = new EmailBO();
        logger.info("get emails by subject");
        List<Email> emails = emailBO.getListOfEmailByEmailSubject(subject);
        if (emails.isEmpty()) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NO_EMAIL_FOUND_BY_SUBJECT, subject);
            logger.warn(faultInfo);
        }
        logger.info("getEmailBySubject result: " + emails);
        ListWrapper wrapper = new ListWrapper();
        wrapper.setEmailsList(emails);
        return wrapper;
    }

    @XmlElement
    @Override
    public boolean addEmail(Email email) {
        EmailBO emailBO = new EmailBO();
        logger.info("add email to soap server ");
        if (emailBO.addEmailToServer(email)) {
            logger.info("addEmailToServer result: " + true);
            return true;
        }
        ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.SUCH_EMAIL_EXISTS_OR_NOT_FILLED_ALL_FIELDS,
                email);
        logger.warn(faultInfo);
        return false;
    }

    @XmlElement
    @Override
    public boolean removeEmail(String subject) {
        EmailBO emailBO = new EmailBO();
        logger.info("remove email from server");
        if (!emailBO.removeEmailFromServer(subject)) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NO_EMAIL_FOUND_BY_SUBJECT,
                    subject);
            logger.warn(faultInfo.getMessage());
            return false;
        }
        logger.info("removeEmail result" + true);
        return true;
    }
}
