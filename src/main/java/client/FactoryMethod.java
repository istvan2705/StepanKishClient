package client;

import utills.URL;

public class FactoryMethod {

    public static WebServiceClient getServiceClient (String serviceHome) {
        WebServiceClient client = null;
        if (serviceHome.equals(URL.REST_HOME)) {
            client = new RestClient();
        }
        if (serviceHome.equals(URL.SOAP_HOME)){
            client = new SoapClient();
        }
        return client;
    }
}
