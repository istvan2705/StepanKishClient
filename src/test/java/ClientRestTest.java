import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import org.junit.Assert;
import org.testng.annotations.Test;
import utills.URL;

import java.util.List;

public class ClientRestTest {

    private Client client = Client.create();

    @Test
    public void getEmailsBySubjectTest() {
        String param = "Test1";
        WebResource webResource = client.resource(URL.URL_GET_EMAILS_BY_SUBJECT).path(param);
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
        Assert.assertEquals("[{emailAddress=1234454n@gmail.com, subject=Test1, body=Hello my friend}]",
                response.getEntity(List.class).toString());
    }

    @Test
    public void getEmailByNotExistingSubjectTest() {
        String param = "Test35";
        WebResource webResource = client.resource(URL.URL_GET_EMAILS_BY_SUBJECT).path(param);
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void getEmailByEmptySubject() {
        String param = "";
        WebResource webResource = client.resource(URL.URL_GET_EMAILS_BY_SUBJECT).path(param);
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void getEmailByEmailAddress() {
        String param = "4323234n@gmail.com";
        WebResource webResource = client.resource(URL.URL_GET_EMAILS_BY_EMAIL).path(param);
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
        Assert.assertEquals("[{emailAddress=4323234n@gmail.com, subject=Test3, body=If you like this}]",
                response.getEntity(List.class).toString());
    }

    @Test
    public void getEmailByNotExistingEmailAddress() {
        String param = "4323253234n@gmail.com";
        WebResource webResource = client.resource(URL.URL_GET_EMAILS_BY_EMAIL).path(param);
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void getEmailByEmptyEmailAddress() {
        String param = "";
        WebResource webResource = client.resource(URL.URL_GET_EMAILS_BY_EMAIL).path(param);
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void addEmailToServerTest() {
        String input = "{\"subject\":\"Test45\",\"emailAddress\":\"23665633444n@gmail.com\",\"body\":\"Welcome to my "+
                "city\"}";
        WebResource webResource = client.resource(URL.URL_ADD_EMAIL);
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);
        Assert.assertTrue(response.getEntity(Boolean.class));
    }
    @Test
    public void removeEmailBySubject(){
        String param ="Test45";
        WebResource webResource = client.resource(URL.URL_REMOVE_EMAIL).path(param);
        ClientResponse response = webResource.accept("application/json").delete(ClientResponse.class);
        Assert.assertTrue(response.getEntity(Boolean.class));
    }

    @Test
    public void addEmailWithEmptyParameter(){
        String input = "{\"subject\":\"\",\"emailAddress\":\"23665633444n@gmail.com\",\"body\":\"Welcome to my city\"}";
        WebResource webResource = client.resource(URL.URL_ADD_EMAIL);
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);
        Assert.assertFalse(response.getEntity(Boolean.class));
         }

    @Test
    public void removeEmailByNotExistingSubject(){
        String param ="Test25";
        WebResource webResource = client.resource(URL.URL_REMOVE_EMAIL).path(param);
        ClientResponse response = webResource.accept("application/json").delete(ClientResponse.class);
        Assert.assertFalse( response.getEntity(Boolean.class));
    }
  }