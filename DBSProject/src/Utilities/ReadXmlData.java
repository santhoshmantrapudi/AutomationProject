package Utilities;

import java.io.StringWriter;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import ReusableComponents.GenericMethods;
import ReusableComponents.PublicVariables;

public class ReadXmlData {

	static DocumentBuilder docBuilder;
	static Document document;
	static XPath xpath;
	static Transformer transformer;
	static StringWriter stwrt;

	public static String modifyXML() {
		HashMap<String, String> ocrDataMap = new HashMap<String, String>();
		String modifiedString = null;
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = docBuilder.parse(System.getProperty("user.dir" + "//Testdata//Samplexml.xml"));
			xpath = XPathFactory.newInstance().newXPath();
			String modifyxmlTagNames = (String) PublicVariables.hmTestData.get("XmlTagNames");
			String modifyxmlTagValues = (String) PublicVariables.hmTestData.get("XmlTagValues");
			String[] modifyXmlTagNamearr = modifyxmlTagNames.split(",;");
			String[] modifyXmlTagValuearr = modifyxmlTagValues.split(",;");
			if (modifyXmlTagNamearr.length == modifyXmlTagValuearr.length) {
				for (int i = 0; i <= modifyXmlTagNamearr.length - 1; i++) {
					String xpathExpression = "//Name[contains(text(),'" + modifyXmlTagNamearr[i]
							+ "')]/following-sibling::Value";
					document = ReadXmlData.modifyXmlTag(document, xpathExpression, modifyXmlTagValuearr[i]);
					ocrDataMap.put(modifyXmlTagNamearr[i], modifyXmlTagValuearr[i]);
				}
				document = ReadXmlData.modifyXmlTag(document, "//BatchName",
						"BC154_" + PublicVariables.idealRefNum + "_Trade_Grpadmin1_06-05-201905_00_01)");
				transformer = TransformerFactory.newInstance().newTransformer();
				stwrt = new StringWriter();
				transformer.transform(new DOMSource(document), new StreamResult(stwrt));
				modifiedString = stwrt.getBuffer().toString();
				PublicVariables.hmOCRXLData = ocrDataMap;
			} else {
				Reporting.WritetoReport("FAIL", "Mismatch in Json TagNames and Tag Values");
			}
		} catch (Exception ex) {
			Reporting.WritetoReport("FAIL", "Exception occured due to " + ex.getMessage());
		}
		return modifiedString;
	}

	public static Document modifyXmlTag(Document doc, String xpathExpression, String newTagValue) {
		try {
			NodeList nodes = (NodeList) xpath.evaluate(xpathExpression, doc, XPathConstants.NODESET);
			int nodeSize = nodes.getLength();
			if (nodeSize != 1) {
				Reporting.WritetoReport("FAIL",
						"With this expression more than 1 elementis matching " + xpathExpression);
			} else {
				nodes.item(0).setTextContent(newTagValue);
				String modifyTagValue = nodes.item(0).getTextContent();
				Reporting.WritetoReport("PASS", "Existing tag values is modified with " + modifyTagValue);
			}
		} catch (Exception ex) {
			Reporting.WritetoReport("FAIL", "Exception occured due to " + ex.getMessage());
		}
		return doc;
	}

	public static void xmlPosting() {
		String xmlContent = ReadXmlData.modifyXML();
		ReadXmlData.postxmlHTTP(xmlContent);
	}

	public static void postxmlHTTP(String xmlContent) {
		try {
			String OCRPostManURL = GenericMethods.readDataProperties("SITOCRPostManURL");
			CloseableHttpClient httpclient = createAcceptSelfSignedCertificateClient();
			HttpPost httppost = new HttpPost(OCRPostManURL);
			httppost.addHeader("Content-Type", "application/xml");
			httppost.setEntity(new StringEntity(xmlContent));
			CloseableHttpResponse httpResponse = httpclient.execute(httppost);
			int responseCode = httpResponse.getStatusLine().getStatusCode();
			if (responseCode == 200) {
				String responseBody = EntityUtils.toString(httpResponse.getEntity());
				Reporting.WritetoReport("Pass", "HTTP response code is 200");
			} else {
				Reporting.WritetoReport("Pass", "HTTP response code is not 200");
			}
		} catch (Exception ex) {
			Reporting.WritetoReport("Fail", "Exception occured due to " + ex.getMessage());
		}
	}

	private static CloseableHttpClient createAcceptSelfSignedCertificateClient()
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(new TrustSelfSignedStrategy()).build();
		HostnameVerifier allowAllHosts = new NoopHostnameVerifier();
		SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, allowAllHosts);
		return HttpClients.custom().setSSLSocketFactory(connectionFactory).build();
	}

}
