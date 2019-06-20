package endpoint;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import webservices.soap.MailServiceImpl;

import javax.xml.ws.Endpoint;

import static utills.URL.SOAP_HOME;

public class EndpointPublisher {
    private static final Logger logger = LogManager.getLogger(EndpointPublisher.class);

    public static void main(String[] args) {

        Endpoint.publish(SOAP_HOME, new MailServiceImpl());
        logger.info("Soap service is ready to use");
    }
}





