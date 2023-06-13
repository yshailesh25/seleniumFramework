package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import keywords.Synchronization;

public class LoginPage {

	public WebDriver driver;
	Synchronization sync = Synchronization.getInstance();
	
	public LoginPage(EventFiringWebDriver driver) {
		this.driver = driver;
	}
	
	//@FindBy(name="Email")
	//private WebElement enterEmail;
	private By enterEmail = By.name("Email");
	private By enterPassword = By.name("Password");
	private By loginButton = By.xpath("//input[@value='Log in']");
	
	public WebElement getEnterEmail() {
		try {
			return driver.findElement(enterEmail);
		} catch (StaleElementReferenceException e) {
			sync.explicitWaitStaleElement(driver, enterEmail);
			return driver.findElement(enterEmail);
		} catch (ElementNotSelectableException e) {
			sync.explicitWaitElementClickable(driver, enterEmail);
			return driver.findElement(enterEmail);
		} catch (ElementNotVisibleException e) {
			sync.explicitWaitVisibleElement(driver, enterEmail);
			return driver.findElement(enterEmail);
		}
	}
	
	public WebElement getEnterPassword() {
		try {
			return driver.findElement(enterPassword);
		} catch (StaleElementReferenceException e) {
			sync.explicitWaitStaleElement(driver, enterPassword);
			return driver.findElement(enterPassword);
		} catch (ElementNotSelectableException e) {
			sync.explicitWaitElementClickable(driver, enterPassword);
			return driver.findElement(enterPassword);
		} catch (ElementNotVisibleException e) {
			sync.explicitWaitVisibleElement(driver, enterPassword);
			return driver.findElement(enterPassword);
		}
	}
	
	public WebElement getEnterLoginButton() {
		try {
			return driver.findElement(loginButton);
		} catch (StaleElementReferenceException e) {
			sync.explicitWaitStaleElement(driver, loginButton);
			return driver.findElement(loginButton);
		} catch (ElementNotSelectableException e) {
			sync.explicitWaitElementClickable(driver, loginButton);
			return driver.findElement(loginButton);
		} catch (ElementNotVisibleException e) {
			sync.explicitWaitVisibleElement(driver, loginButton);
			return driver.findElement(loginButton);
		}
	}
	
	public String getAppVersion() {
		String appVersion = "1.0.0";
		return appVersion;
	}
	
	
}
