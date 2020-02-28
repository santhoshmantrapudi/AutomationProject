package ReusableComponents;

import java.util.Date;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;

public class PublicVariables {
	public static String gTestname;
	public static boolean gStepwisescreenshots= true;
	public static int gRowNum;
	public static String gTestDataSheet = null;
	
	public static HashMap hmTestData = null;
	public static WebDriver dr;
	public static Date intEndTime;
	public static Date intStartTime;
	public static String strExeDateTimeStamp;
	public static String strBrowserType="Chrome";
	
	public static String gXMLTargetFile= null;
	public static String gDashBoardURL = null;
	
	public static String gCountry="China";
	public static String gEnv= "SIT";
	public static String gUsername= null;
	public static String gPassword= null;
	public static String tiwRefNum=null;
	public static String idealRefNum= null;
	public static String ipeRefNum = null;
	public static JSONObject modifeJsonobj = null;
	
	public static String gRegion = "REGI";
	public static String gPCRWFEurl = null;
	public static HashMap hmTableColumn;
	public static HashMap hmOCRXLData=null;
	

}
