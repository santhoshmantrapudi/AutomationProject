package PageObjectModel;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewObjects {

	@FindBy(xpath="//a[@class='login']")
	public static WebElement linkSignin;
	
	@FindBy(xpath="//h1[text()='Authentication']")
	public static WebElement elmAuthentication;
	
	@FindBy(xpath="//label[@for='email']/following-sibling::input")
	public static WebElement txtEmailUserName;
	
	@FindBy(xpath="//label[@for='passwd']/following-sibling::span/input")
	public static WebElement txtEmailPasswrd;
	
	@FindBy(xpath="//i[@class='icon-lock left']/parent::span/parent::button")
	public static WebElement btnSignIn;
	
	@FindBy(xpath="(//a[@title='T-shirts'])[1]")
	public static WebElement linkTshirts;
	
	@FindBy(xpath="(//a[@title='Women'])[1]/parent::li")
	public static WebElement linkWomen;
	
	@FindBy(xpath="(//a[@title='Dresses'])[1]")
	public static WebElement linkDresses;
	
	@FindBy(xpath="(//a[@title='Women'])/following-sibling::ul//a[text()='Blouses']")
	public static WebElement linkWomen_Blouses;
	
	
}
