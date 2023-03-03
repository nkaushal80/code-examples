import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SoapRequest {
    public static void main(String[] args) {
        try {
            // Create SOAP message
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();

            // Add SOAP envelope
            SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
            soapEnvelope.addNamespaceDeclaration("ns", "http://www.example.com/soap-api");
            SOAPHeader soapHeader = soapEnvelope.getHeader();
            SOAPBody soapBody = soapEnvelope.getBody();

            // Add SOAP body content
            String requestBody = "<ns:getUserInfo><username>user123</username></ns:getUserInfo>";
            StringEntity requestEntity = new StringEntity(requestBody, ContentType.APPLICATION_XML);
            HttpPost httpPost = new HttpPost("http://www.example.com/soap-api");
            httpPost.setEntity(requestEntity);

            // Send SOAP request
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity responseEntity = httpResponse.getEntity();

            // Parse SOAP response
            InputStream responseStream = responseEntity.getContent();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document responseDocument = documentBuilder.parse(responseStream);
            String responseXml = responseDocument.getDocumentElement().getTextContent();

            // Print SOAP response
            System.out.println(responseXml);

            // Clean up resources
            httpResponse.close();
            httpClient.close();
        } catch (SOAPException | IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }
}
