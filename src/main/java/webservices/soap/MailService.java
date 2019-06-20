package webservices.soap;

import model.Email;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface MailService {

    @WebMethod
     ListWrapper getAllEmails();

    @WebMethod
    ListWrapper getEmailByEmailAddress(String emailAddress);

    @WebMethod
    ListWrapper getEmailBySubject(String subject);

    @WebMethod
    boolean addEmail(Email email);

    @WebMethod
    boolean removeEmail(String subject);
}

