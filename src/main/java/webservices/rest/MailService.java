package webservices.rest;

import model.Email;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

public interface MailService {

    @GET
    @Path("/emails")
    @Produces("application/json; charset=UTF-8")
    Response getAllEmails() throws IOException;

    @GET
    @Path("/emailAddress/{emailAddress}")
    @Produces("application/json; charset=UTF-8")
    Response getEmailsBySenderEmailAddress(@PathParam("emailAddress") String emailAddress);

    @GET
    @Path("/emailSubject/{subject}")
    @Produces("application/json; charset=UTF-8")
    Response getEmailsByEmailSubject(@PathParam("subject") String subject);

    @POST
    @Path("/addEmailToServer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json; charset=UTF-8")
    Response addEmailToMailBox(Email email) throws IOException;

    @DELETE
    @Path("/removeEmail/{subject}")
    @Produces("application/json; charset=UTF-8")
    Response removeEmail(@PathParam("subject") String subject);

}

