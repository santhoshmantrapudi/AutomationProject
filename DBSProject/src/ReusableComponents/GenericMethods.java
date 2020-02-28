package ReusableComponents;

import java.awt.AWTException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import PageObjectModel.CommonObjects;
import PageObjectModel.CreateAccountObj;
import PageObjectModel.NewObjects;
import Utilities.ExcelConnections;
import Utilities.Reporting;

public class GenericMethods {

	public static WebDriver dr;

	public static String readDataProperties(String propertyName) throws IOException {
		Properties prop = new Properties();
		File file = new File(System.getProperty("user.dir") + "\\src\\ReusableComponents\\properties.properties");
		FileInputStream f1InputStream = new FileInputStream(file);
		prop.load(f1InputStream);
		String propertyValue = prop.getProperty(propertyName);
		return propertyValue;
	}

	public static void loginApplication(String userType, String testEnv) throws IOException {
		try {
			initialiseRegionVariabes();
			PublicVariables.dr.manage().window().maximize();
			// Navigateto(PublicVariables.gDashBoardURL);
			// setUserName(userType,testEnv);
			CommonObjects.txtUserName.sendKeys(PublicVariables.gUsername);
			CommonObjects.txtPassword.sendKeys(PublicVariables.gPassword);
			CommonObjects.selectReg1.click();
			WebDriverWait wait = new WebDriverWait(PublicVariables.dr, 30);
			wait.until(ExpectedConditions.elementToBeClickable(CommonObjects.selectReg1));
		} catch (Exception ex) {
			Reporting.WritetoReport("Fail", "Exception in log in " + ex.getMessage());
		}
	}

	public static void BrowserSetUp() {
		switch (PublicVariables.strBrowserType.toUpperCase()) {
		case "CHROME":
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\Drivers\\ChromeDriver\\chromedriver.exe");
			PublicVariables.dr = new ChromeDriver();
			PublicVariables.dr.manage().window().maximize();
			PageFactory.initElements(PublicVariables.dr, NewObjects.class);
			PageFactory.initElements(PublicVariables.dr, CreateAccountObj.class);
			break;
		
		}
	}

	public static void goToHomePage() {
		PublicVariables.dr.get("http://automationpractice.com/index.php");
		PublicVariables.dr.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public static void InitiateExecution(int intRow) throws IOException {
		PublicVariables.gRowNum = intRow;
		PublicVariables.idealRefNum = new SimpleDateFormat("yyMMddhhmmss").format(new Date());
		PublicVariables.ipeRefNum = "IPE" + new SimpleDateFormat("yyMMddhhmmss").format(new Date());
		PublicVariables.hmTestData = ExcelConnections.GetTestData(intRow);
	}

	public static boolean verifyObject(WebElement elm) {
		boolean flag = false;
		try {
			if (elm.isDisplayed()) {
				flag = true;
				Reporting.WritetoReport("Pass", "WebElement exist");
			} else {
				Reporting.WritetoReport("Fail", "WebElement not exist");
			}
		} catch (Exception ex) {
			Reporting.WritetoReport("Error", "Verification unsuccessfull due to " + ex.getMessage());
		}
		return flag;
	}

	public static void moveToElement(WebElement elm) {
		try {
			((JavascriptExecutor) PublicVariables.dr).executeScript("arguments[0].scrollIntoView(true);", elm);
		} catch (Exception ex) {
			Reporting.WritetoReport("Error", "Verification unsuccessfull due to " + ex.getMessage());
		}
	}

	public static void setText(WebElement elm, String elmValue) {
		try {
			elm.sendKeys(elmValue);
			Reporting.WritetoReport("Pass", "Text entered suceesfully " + elmValue);
		} catch (Exception ex) {
			Reporting.WritetoReport("Error", "exception occured due to " + ex.getMessage());
		}
	}

	public static String getText(WebElement elm) {
		String elmValue = null;
		try {
			elmValue = elm.getText();
			Reporting.WritetoReport("Pass", "the value suceesfully fetched " + elmValue);
		} catch (Exception ex) {
			Reporting.WritetoReport("Error", "exception occured due to " + ex.getMessage());
		}
		return elmValue;
	}

	public static void elmClick(WebElement elm) {
		try {
			elm.click();
			Reporting.WritetoReport("Pass", "element suceesfully clicked ");
		} catch (Exception ex) {
			Reporting.WritetoReport("Error", "exception occured due to " + ex.getMessage());
		}
	}
	
	public static void pickDropDownValue(WebElement elm,String elmValue) {
		try {
			Select sel = new Select(elm);
			sel.selectByValue(elmValue);
			Reporting.WritetoReport("Pass", "element suceesfully selected");			
		}catch(Exception ex) {
			Reporting.WritetoReport("Error", "exception occured due to " + ex.getMessage());
		}
	}

	private static void initialiseRegionVariabes() {
		// TODO Auto-generated method stub

	}
}
