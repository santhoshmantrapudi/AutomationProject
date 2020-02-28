package TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;



import PageObjectModel.NewObjects;
import ReusableComponents.PublicVariables;

public class sample {
    static WebDriver dr;
	public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException, SQLException, ParseException {
		
		String response="  [\r\n" + 
				"         {\r\n" + 
				"               \"long_name\" : \"Chicago\",\r\n" + 
				"               \"short_name\" : \"Chicago\",\r\n" + 
				"               \"types\" : [ \"locality\", \"political\" ]\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"               \"long_name\" : \"Cook County\",\r\n" + 
				"               \"short_name\" : \"Cook County\",\r\n" + 
				"               \"types\" : [ \"administrative_area_level_2\", \"political\" ]\r\n" + 
				"            }\r\n" + 
				"]\r\n" + 
				"\r\n" + 
				"";		
	    JSONParser jsonParser=new JSONParser();
	    JSONArray myArray=(JSONArray) jsonParser.parse(response);
	    //JSONObject obj=(JSONObject) jsonParser.parse(response);
		//JSONObject jsonObg=new JSONObject();
	    //JSONArray myArray = (JSONArray)obj.get("results");
	    List<String> ls=new ArrayList<String>();
	    for(int i=0; i<myArray.size(); i++) {
	        JSONObject object = (JSONObject) myArray.get(i);
	        String nid = (String) object.get("long_name");
	        //String changed = (String) object.get("short_name");
	        ls.add(nid);
	        //System.out.println(nid);
	        //System.out.println(changed);
	        
	    }
	    //ascending order
	    //Collections.sort(ls);
	    //descending order
	    Collections.sort(ls,Collections.reverseOrder());
	    System.out.print(ls);
	    System.out.print("remotechange");
	}
	
	
	public static void readJsonwithKey() throws IOException, ParseException {
		String response="{\r\n" + 
				"   \"results\" : [\r\n" + 
				"         {\r\n" + 
				"               \"long_name\" : \"Chicago\",\r\n" + 
				"               \"short_name\" : \"Chicago\",\r\n" + 
				"               \"types\" : [ \"locality\", \"political\" ]\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"               \"long_name\" : \"Cook County\",\r\n" + 
				"               \"short_name\" : \"Cook County\",\r\n" + 
				"               \"types\" : [ \"administrative_area_level_2\", \"political\" ]\r\n" + 
				"            }\r\n" + 
				"]\r\n" + 
				"}\r\n" + 
				"";		
	    JSONParser jsonParser=new JSONParser();
	    //JSONArray obj=(JSONArray) jsonParser.parse(response);
	    JSONObject obj=(JSONObject) jsonParser.parse(response);
		//JSONObject jsonObg=new JSONObject();
	    JSONArray myArray = (JSONArray)obj.get("results");
	    List<String> ls=new ArrayList<String>();
	    for(int i=0; i<myArray.size(); i++) {
	        JSONObject object = (JSONObject) myArray.get(i);
	        String nid = (String) object.get("long_name");
	        //String changed = (String) object.get("short_name");
	        ls.add(nid);
	        //System.out.println(nid);
	        //System.out.println(changed);
	        
	    }
	    //ascending order
	    Collections.sort(ls);
	    //descending order
	    Collections.sort(ls,Collections.reverseOrder());
	    System.out.print(ls);
	}
	
	

}
