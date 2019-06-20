package client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utills.URL;
import wsimportclient.Email;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestClient implements WebServiceClient {
    private static final Logger logger = LogManager.getLogger(RestClient.class);
    Client client = Client.create();

    @Override
    public List<Email> getAllEmails() {
        List<Email> emails = Collections.emptyList();
        WebResource webResource = client.resource(URL.URL_ALL_EMAILS);
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            logger.info(response.toString());
        } else {
            emails = response.getEntity(List.class);
        }
        return emails;
    }

    @Override
    public List<Email> getEmailsByEmailAddress(String emailAddress) {
        List<Email> emails = Collections.emptyList();
        WebResource webResource = client.resource(URL.URL_GET_EMAILS_BY_EMAIL).path(emailAddress);
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            logger.info(response.toString());
        } else {
            emails = response.getEntity(List.class);
        }
        return emails;
    }

    @Override
    public List<Email> getEmailsBySubject(String param) {
        List<Email> emails = Collections.emptyList();
        WebResource webResource = client.resource(URL.URL_GET_EMAILS_BY_SUBJECT).path(param);
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            logger.info(response.toString());
        } else {
            emails = response.getEntity(List.class);
        }
        return emails;
    }

    @Override
    public boolean addEmail(Email email) {
        Map<String, Object> input = new HashMap<>();
        input.put("subject", email.getSubject());
        input.put("emailAddress", email.getEmailAddress());
        input.put("body", email.getBody());
        WebResource webResource = client.resource(URL.URL_ADD_EMAIL);
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);
         return response.getEntity(Boolean.class);
    }

    @Override
    public boolean removeEmail(String subject) {
        WebResource webResource = client.resource(URL.URL_REMOVE_EMAIL).path(subject);
        ClientResponse response = webResource.accept("application/json").delete(ClientResponse.class);
        return response.getEntity(Boolean.class);
    }
}

