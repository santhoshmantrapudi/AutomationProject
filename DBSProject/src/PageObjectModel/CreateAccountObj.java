package PageObjectModel;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateAccountObj {

	@FindBy(xpath="//h3[contains(text(),'Create an account')]")
	public static WebElement elmCreateAccount;
	
	@FindBy(xpath="//input[@name='email_create']")
	public static WebElement txtEmailCreate;
	
	@FindBy(xpath="//button[@id='SubmitCreate']")
	public static WebElement buttonCreateAccount;
	
	@FindBy(xpath="//h3[contains(text(),'Your personal information')]")
	public static WebElement elmPersonalInfo;
	
	
	@FindBy(xpath="//label[@for='id_gender2']/div/span/input")
	public static WebElement radioMrs;
	
	@FindBy(xpath="//label[@for='id_gender1']/div/span/input")
	public static WebElement radioMr;
	
	@FindBy(xpath="(//label[contains(text(),'First name')])[1]/following-sibling::input")
	public static WebElement textfirstNamePersonal;
	
	@FindBy(xpath="(//label[contains(text(),'Last name')])[1]/following-sibling::input")
	public static WebElement textLastNamePersonal;
	
	@FindBy(xpath="(//label[contains(text(),'Email')])[1]/following-sibling::input")
	public static WebElement txtEmailPersonal;
	
	@FindBy(xpath="(//label[contains(text(),'Password')])[1]/following-sibling::input")
	public static WebElement txtPasswrdPersonal;
	
	@FindBy(xpath="//select[@name='days']")
	public static WebElement drpdownDays;

	@FindBy(xpath="//select[@name='months']")
	public static WebElement drpdownMonths;

	@FindBy(xpath="//select[@name='years']")
	public static WebElement drpdownYears;
	
	@FindBy(xpath="//input[@name='newsletter']")
	public static WebElement checkBoxNewsleter;
	
	@FindBy(xpath="//input[@name='optin']")
	public static WebElement checkBoxOffers;
	
	
}
