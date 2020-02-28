package TestCases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import PageObjectModel.CreateAccountObj;
import PageObjectModel.NewObjects;
import ReusableComponents.GenericMethods;
import ReusableComponents.PublicVariables;
import Utilities.ExcelConnections;
import Utilities.Reporting;

public class DigiDocTest {

	@BeforeTest
	public void beforeTest() {
		GenericMethods.BrowserSetUp();
	}

	@AfterTest
	public void afterTest() {

	}

	@BeforeClass
	public void beforeClass() throws IOException {
		Reporting.startreporting();
		// GenericMethods.loginApplication("PreProcessingMaker", PublicVariables.gEnv);
	}

	@AfterClass
	public void afterClass() {
		Reporting.Endreporting();
	}

	@BeforeMethod
	public void beforeMethod() {
			GenericMethods.goToHomePage();
	}

	@DataProvider(name = "TestIterations")
	public Object[][] TestIterations(Method result) throws IOException {
		PublicVariables.gTestname = result.getName();
		List<Integer> TestData = null;
		ExcelConnections exl = new ExcelConnections();
		TestData = exl.FindRows(PublicVariables.gTestname);
		Object[][] DatatobeReturned = new Object[TestData.size()][2];
		int i = 0;
		for (int j = 0; j < TestData.size(); j++) {
			DatatobeReturned[j][0] = i + 1;
			DatatobeReturned[j][1] = TestData.get(j);
			i = i + 1;
		}
		return DatatobeReturned;
	}

	@Test(dataProvider = "TestIterations")
	public void verifyLogin(int iteration, int intRow) throws IOException {
		Reporting.StartLogging(PublicVariables.gTestname + "_" + iteration);
		GenericMethods.InitiateExecution(intRow);
		GenericMethods.elmClick(NewObjects.linkSignin);
		if (GenericMethods.getText(NewObjects.elmAuthentication).equals("AUTHENTICATION")) {
			Reporting.WritetoReport("Pass", "Authenticatio displayed successfully");
		} else {
			Reporting.WritetoReport("Fail", "Authenticatio not displayed successfully");
		}
		GenericMethods.setText(NewObjects.txtEmailUserName, (String) PublicVariables.hmTestData.get("UserName"));
		GenericMethods.setText(NewObjects.txtEmailPasswrd, (String) PublicVariables.hmTestData.get("Password"));
		GenericMethods.elmClick(NewObjects.btnSignIn);
	}

	@Test(dataProvider = "TestIterations")
	public void createAccount(int iteration, int intRow) throws IOException {
		Reporting.StartLogging(PublicVariables.gTestname + "_" + iteration);
		GenericMethods.InitiateExecution(intRow);
		GenericMethods.elmClick(NewObjects.linkSignin);
		WebDriverWait wait=new WebDriverWait(PublicVariables.dr,30);
		wait.until(ExpectedConditions.textToBePresentInElementValue(CreateAccountObj.elmCreateAccount, "CREATE AN ACCOUNT"));
		if (GenericMethods.getText(CreateAccountObj.elmCreateAccount).equals("CREATE AN ACCOUNT")) {
			Reporting.WritetoReport("Pass", "User is in Authorization page");
		} else {
			Reporting.WritetoReport("Fail", "User is not in Authorization page");
		}
        GenericMethods.setText(CreateAccountObj.txtEmailCreate, (String) PublicVariables.hmTestData.get("UserName"));
        GenericMethods.elmClick(CreateAccountObj.buttonCreateAccount);
		GenericMethods.verifyObject(CreateAccountObj.elmPersonalInfo);
		GenericMethods.elmClick(CreateAccountObj.radioMrs);
		GenericMethods.setText(CreateAccountObj.textfirstNamePersonal, (String) PublicVariables.hmTestData.get("firstName"));
		GenericMethods.setText(CreateAccountObj.textLastNamePersonal, (String) PublicVariables.hmTestData.get("LastName"));
		GenericMethods.setText(CreateAccountObj.txtPasswrdPersonal, (String) PublicVariables.hmTestData.get("Password"));
		GenericMethods.pickDropDownValue(CreateAccountObj.drpdownDays, "20");
		GenericMethods.pickDropDownValue(CreateAccountObj.drpdownMonths, "3");
		GenericMethods.pickDropDownValue(CreateAccountObj.drpdownYears, "1994");
		GenericMethods.elmClick(CreateAccountObj.checkBoxNewsleter);
		GenericMethods.elmClick(CreateAccountObj.checkBoxOffers);
	}
	
	@Test(dataProvider = "TestIterations")
	public void selectDepartment(int iteration, int intRow) throws IOException, InterruptedException {
		Reporting.StartLogging(PublicVariables.gTestname + "_" + iteration);
		GenericMethods.InitiateExecution(intRow);
		Actions act= new Actions(PublicVariables.dr);		
		act.moveToElement(NewObjects.linkWomen).click(NewObjects.linkWomen_Blouses).build().perform();
		//act.moveToElement(NewObjects.linkDresses);
		System.out.println("executed");
		
	}
}
