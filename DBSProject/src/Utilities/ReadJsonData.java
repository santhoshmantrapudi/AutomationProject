package Utilities;

import java.io.FileReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

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
import org.json.simple.parser.JSONParser;

import ReusableComponents.GenericMethods;
import ReusableComponents.PublicVariables;

public class ReadJsonData {

	static FileReader fileReader;
	static JSONParser jsonParser=new JSONParser();
	static Object jsonData;
	static JSONObject jsonObj;
	
	public static void jsonPosting() {
		JSONObject modifyJson=ReadJsonData.modifyJson();
		JSONObject responseJson=ReadJsonData.postHTTP(modifyJson);
		PublicVariables.modifeJsonobj=modifyJson;
		PublicVariables.tiwRefNum=(String) responseJson.get("refId");
		Reporting.WritetoReport("Pass", "TIW RefNumberis "+PublicVariables.tiwRefNum);
	}
	public static JSONObject modifyJson() {
		try {
			FileReader fileReader=new FileReader(System.getProperty("user.dir")+"//TestData//SampleJson.txt");
			jsonData=jsonParser.parse(fileReader);
			jsonObj=(JSONObject)jsonData;
			String modifyTagNames=(String) PublicVariables.hmTestData.get("JsonTagNames");
			String modifyTagValues=(String) PublicVariables.hmTestData.get("JsonTagValues");
			String[] modifyTagNamearr=modifyTagNames.split(",;");
			String[] modifyTagValuearr=modifyTagValues.split(",;");
			if(modifyTagNamearr.length==modifyTagValuearr.length) {
				for(int i=0;i<=modifyTagNamearr.length-1;i++) {
					jsonObj.put(modifyTagNamearr[i], modifyTagValuearr[i]);
				}
			}else {
				Reporting.WritetoReport("Fail", "Mismatch in Json TagNames and Tag Values in the excel");
			}
			jsonObj.put("idealRefNo", PublicVariables.idealRefNum);
			jsonObj.put("ipeRefNo", PublicVariables.ipeRefNum);
		}catch(Exception ex) {
			Reporting.WritetoReport("FAIL", "Exception occured due to "+ex.getMessage());
		}
		return jsonObj;
	}
	
	public static JSONObject postHTTP(JSONObject jsonObj) {
		JSONObject responseObj=null;
		try {
			String IPEPostManURL=GenericMethods.readDataProperties("SITPostManURL");
			CloseableHttpClient httpclient=createAcceptSelfSignedCertificateClient();
			HttpPost httppost=new HttpPost(IPEPostManURL);
			httppost.addHeader("Content-Type","application/json");
			httppost.setEntity(new StringEntity(jsonObj.toString()));
			CloseableHttpResponse httpResponse=httpclient.execute(httppost);
			int responseCode=httpResponse.getStatusLine().getStatusCode();
			if(responseCode==200) {
				String responseBody=EntityUtils.toString(httpResponse.getEntity());
				responseObj=(JSONObject) jsonParser.parse(responseBody);
				Reporting.WritetoReport("Pass","HTTP response code is 200 and response body is "+responseBody);
			}else {
				Reporting.WritetoReport("Pass","HTTP response code is not 200"); 
			}
		}catch(Exception ex) {
			Reporting.WritetoReport("Fail", "Failed due to "+ex.getMessage());
		}
		return responseObj;
	}
	
	private static CloseableHttpClient createAcceptSelfSignedCertificateClient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		SSLContext sslContext=SSLContextBuilder.create().loadTrustMaterial(new TrustSelfSignedStrategy()).build();
	    HostnameVerifier allowAllHosts=new NoopHostnameVerifier();
	    SSLConnectionSocketFactory connectionFactory=new SSLConnectionSocketFactory(sslContext,allowAllHosts);
	    return HttpClients.custom().setSSLSocketFactory(connectionFactory).build();
	}
}
