package webservices.rest;

import businessobject.EmailBO;
import model.Email;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import webservices.fault.FaultMessage;
import webservices.fault.ServiceFaultInfo;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/rest")
public class MailServiceImpl implements MailService {
    private static final Logger logger = LogManager.getLogger(MailServiceImpl.class);
    private EmailBO emailBO = new EmailBO();
    private Response response;

    @Override
    public Response getAllEmails(){
        logger.info("getAllEmails method");
        List<Email> allEmails = emailBO.getListOfEmailsOnServer();
        if (allEmails.isEmpty()) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NO_EMAIL_FOUND_ON_SERVER);
            logger.warn(faultInfo.getMessage());
            response = Response.status(Response.Status.NOT_FOUND).entity(faultInfo).build();
        } else {
            logger.info("method result");
            response = Response.ok().entity(allEmails).build();
        }
        return response;
    }

    @Override
    public Response getEmailsBySenderEmailAddress(String emailAddress)  {
        logger.info("getEmails method by Email address");
        List<Email> emailList = emailBO.getListOfEmailsByEmail(emailAddress);
        if (emailList.isEmpty()) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NO_EMAIL_FOUND_BY_EMAIL_ADDRESS, emailAddress);
            logger.warn(faultInfo.getMessage());
            response = Response.status(Response.Status.NOT_FOUND).entity(faultInfo).build();
        } else {
            logger.info("method result");
            response = Response.ok().entity(emailList).build();
        }
        return response;
    }

    @Override
    public Response getEmailsByEmailSubject(String subject) {
        logger.info("getEmails method by Email subject");
        Response response;
        List<Email> emailList = emailBO.getListOfEmailByEmailSubject(subject);
        if (emailList.isEmpty()) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NO_EMAIL_FOUND_BY_SUBJECT, subject);
            logger.warn(faultInfo.getMessage());
            response = Response.status(Response.Status.NOT_FOUND).entity(faultInfo).build();
        } else {
            logger.info("get Emails by subject method result ");
            response = Response.ok().entity(emailList).build();
        }
        return response;
    }

    @Override
    public Response addEmailToMailBox(Email email) {
        logger.info("add email method");
        Response response;
          if (emailBO.addEmailToServer(email)) {
            response = Response.ok().entity(true).build();
            logger.info("email was added");
        } else {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.SUCH_EMAIL_EXISTS_OR_NOT_FILLED_ALL_FIELDS,
                    email.getSubject());
            logger.warn(faultInfo.getMessage());
            response = Response.status(Response.Status.NOT_ACCEPTABLE).entity(false).build();
        }
        return response;
    }
    @Override
    public Response removeEmail(String subject)  {
        Response response;
        logger.info("method remove email from server");
        if (!emailBO.removeEmailFromServer(subject)) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NO_EMAIL_FOUND_BY_SUBJECT, subject);
            logger.warn(faultInfo.getMessage());
            response = Response.status(Response.Status.NOT_FOUND).entity(false).build();
        } else {
            response = Response.ok().entity(true).build();
        }
        return response;
    }
}
