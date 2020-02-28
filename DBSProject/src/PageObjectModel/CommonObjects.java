package PageObjectModel;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CommonObjects {

	@FindBy(xpath = "//*[@id='md-input-0-input']")
	public static WebElement txtUserName;

	@FindBy(xpath = "//*[@id='md-input-1-input']")
	public static WebElement txtPassword;

	@FindBy(xpath = "//label[@class='selectDropdown-icon']")
	public static WebElement regionDropdown;

	@FindBy(xpath = "//li[contains(text(),'REGI")
	public static WebElement selectReg1;
	
	@FindBy(xpath="//table/thead/tr/th")
	public static List<WebElement> clientCheckTableHeader;

}
