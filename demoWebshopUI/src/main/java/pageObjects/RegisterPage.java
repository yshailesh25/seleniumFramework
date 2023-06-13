package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

	private WebDriver driver;
	private WebDriverWait wait;
	
	@FindBy(id="gender-male")
	private WebElement gender;
	
	@FindBy(name="FirstName")
	private WebElement firstName;
	
	@FindBy(name="LastName")
	private WebElement lastName;
	
	@FindBy(name="Email")
	private WebElement email;
	
	@FindBy(name="Password")
	private WebElement password;
	
	@FindBy(name="ConfirmPassword")
	private WebElement confirmPassword;
	
	@FindBy(name="register-button")
	private WebElement registerBtn;
	
	//this constructor to init driver once so you not need to pass again and again in openurl etc
	public RegisterPage(WebDriver driver)//this is to init driver&wait to this page
	{
		this.driver=driver;
		this.wait=new WebDriverWait(driver, 30);
		PageFactory.initElements(driver, this);
	}
	
	public void openUrl()//to open register page
	{
		this.driver.get("http://demowebshop.tricentis.com/register");
		this.wait.until(ExpectedConditions.visibilityOf(this.gender));
	}
	
	public void enterPersonalDetails(String firstName, String lastName, String email)
	{
		this.gender.click();
		this.firstName.sendKeys(firstName);
		this.lastName.sendKeys(lastName);
		this.email.sendKeys(email);
	}
	
	public void enterPassword(String password,String confirmPassword)
	{
		this.password.sendKeys(password);
		this.confirmPassword.sendKeys(confirmPassword);
	}
	
	public void registerBtn()
	{
		this.registerBtn.click();
	}
}
