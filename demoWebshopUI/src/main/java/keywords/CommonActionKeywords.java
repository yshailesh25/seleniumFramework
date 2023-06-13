package keywords;

import java.beans.EventHandler;
import java.io.File;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


import static org.apache.logging.log4j.LogManager.getLogger;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import dataengine.TestDataProperties;
import demoWebShop.Base;
import pageObjects.LoginPage;
import utilities.EventHandlers;
import utilities.ExtentManager;
import utilities.ExtentTestManager;
import utilities.Log;

public class CommonActionKeywords {
	private static Logger log = getLogger(CommonActionKeywords.class);
	public static WebDriver wdriver;
	public static EventFiringWebDriver driver;
	public static EventHandlers handler;
	public static String appVersion = null;	

	
	/**
	 * @author shailesh Initialize the WebDriver based on the browser
	 */
	public static void initializeDriver() throws IOException {
		try {
			String browserName = Base.getBrowserType();
			
			if(browserName.equalsIgnoreCase("IE")) {
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				capabilities.setCapability("nativeEvents", false);
				capabilities.setCapability("unexpectedAlertBehaviour", "accept");
				capabilities.setCapability("ignoreProtectedModeSettings", true);
				capabilities.setCapability("disable-popup-blocking", true);
				capabilities.setCapability("enablePersistentHover", true);
				
				InternetExplorerOptions ieOptions = new InternetExplorerOptions();
				ieOptions.merge(capabilities);
				
				String driverPath = System.getProperty("./drivers/IEDriverServer.exe");
				System.setProperty("webdriver.ie.driver", driverPath);
				wdriver = new InternetExplorerDriver(ieOptions);
			} 
			else if(browserName.equalsIgnoreCase("chrome")) {
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				String driverPath = "./drivers/chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", driverPath);
				System.setProperty("webdriver.chrome.silentOutput", "true");
				wdriver = new ChromeDriver(chromeOptions);
			}
			else if(browserName.equalsIgnoreCase("firefox")) {
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				firefoxOptions.merge(capabilities);
				String driverPath = System.getProperty("./drivers/geckodriver.exe");
				System.setProperty("webdriver.gecko.driver", driverPath);
				wdriver = new FirefoxDriver(firefoxOptions);
			} else {
				log.error("Browser type not supported. Please check environment.properties file");
			}
			
			driver = new EventFiringWebDriver(wdriver);
			handler = new EventHandlers();
			driver.register(handler);
			
			driver.manage().deleteAllCookies();
			log.info("Driver initiaized successfully");
			
			//Get configured time from properties file and apply Implicit wait
			int implicitWaitTime = Base.getImplicitWaitTime();
			Synchronization.getInstance().implicitWait(driver,implicitWaitTime);
			
		} catch (Exception e) {
			Log.fail("Unable to initialize the driver" + e);
		}
	}
	
	/**
	 * @author shailesh Opens DemoWebShop Application
	 * @param userName
	 * @param password
	 * @throws Exception
	 */
	public void openApplication(String userid, String password) throws Exception
	{
		LoginPage loginPageObjects = new LoginPage(driver);
		try {
			String urlInUse = Base.getUrlInUse();
			String url = Base.getApplicationURL();
			if(urlInUse.equalsIgnoreCase("Client_Central_URL")) {
				driver.get(url);
				driver.manage().window().maximize();
				
				log.info("Logging with user name: {}", userid);
				loginPageObjects.getEnterEmail().sendKeys(userid);
				loginPageObjects.getEnterPassword().sendKeys(password);
				loginPageObjects.getEnterLoginButton().click();
				
				//you can enter switch between tab to open
			} else if(urlInUse.equalsIgnoreCase("App_Direct_URL")) {
				driver.get(url);
				driver.manage().window().maximize();
				
				log.info("Logging with user name: {}", userid);
				loginPageObjects.getEnterEmail().sendKeys(userid);
				loginPageObjects.getEnterPassword().sendKeys(password);
				loginPageObjects.getEnterLoginButton().click();
				
				//you can enter switch between tab to open
			} else {
				log.fatal("******************************************************************************************");
				log.fatal("Please correct the URL_IN_USE property in environment.properties file. value: {}", urlInUse);
				log.fatal("Aborting execution due to incorrect configuration");
				log.fatal("******************************************************************************************");
				closeBrowser();
				System.exit(0);
			}
			
			// Get application verson form UI
			if (appVersion == null) {
				appVersion = loginPageObjects.getAppVersion(); 
			}
			Log.pass("Application launched successfully");

		} catch (Exception e) {
			throw new Exception("Error launching the demo web app-->" + e);
		}
	}
	
	/**
	 * @author shailesh closes all the browser window
	 * 
	 */
	public void closeBrowser()
	{
		try {
			if(driver == null) {
				return;
			}
			log.info("Closing all the browsers");
			driver.unregister(handler);
			driver.quit();
			driver = null;
			Log.pass("Application closed successfully");
		} catch (Exception e) {
			Log.fail(Thread.currentThread().getStackTrace()[1].getMethodName() + "Error closing the browser.--> " + e);
		}
	}
	
	/**
	 * @author shailesh this method verifies the page title
	 * @param driver
	 * @param pageTitle
  	 */
	public void verifyPageTitle(WebDriver driver, String pageTitle) {
		try {
			log.info("Page Title is - " + driver.getTitle());
			Assert.assertEquals(driver.getTitle(), pageTitle);
		} catch (Exception e) {
			Log.fail(Thread.currentThread().getStackTrace()[1].getMethodName()
					+ "Not able to figure out the page title.--> " + e);
		}
	}
	
	/**
	 * @author shailesh
	 * @throws Exception
	 * @Description Clicks the web element
	 */
	public void clickElement(WebElement element) throws Exception {
		try {
			element.click();
			Log.pass(" Clicked Successfully: " + element);
		} catch (Exception e) {
			throw new Exception("Unable to Click : " + element + "-------" + e);
		}
	}
	
	/**
	 * @author shailesh
	 * @throws Exception
	 * @Description This method can be used to enter value in a text field
	 */
	public void inputData(WebElement element, String inputData) throws Exception {
		try {
			element.sendKeys(inputData);
			Log.pass("Entered value: " + inputData + " in: " + element);
		} catch (Exception e) {
			log.error("Not able to Enter value " + inputData + e);
			throw new Exception("Unable to Enter data in :" + element + "------" + e);
		}
	}
	
	/**
	 * @author shailesh
	 * @Description this method can be used to clear value in a text field
	 */
	public void clear(WebElement element) {
		try {
			element.clear();
			log.info(element + " cleared successfully.");
		} catch (Exception e) {
			log.error("Not able to clear " + element + " " + e.getMessage());
			Assert.fail("Unable to clear element: " + element.getText());
		}
	}
	
	/**
	 * @author shailesh
	 * @Description this method can be used to select from drop down
	 */
	public void selectFromDropDown(WebElement element, String inputData) {
		try {
			Select SelectObject = new Select(element);
			SelectObject.selectByVisibleText(inputData);
			log.info("Value " + inputData + " is selected from " + element.getText() + " drop down.");
		} catch (Exception e) {
			log.fatal("Not able to select" + element.getText() + "from drop down.", e);
			Assert.fail("Unable to select" + element.getText() + "from drop down.");
		}
	}
	
	/**
	 * @author shailesh
	 * @Description: This method can be used to verify visible text on the UI
	 */
	public void verifyField_GUIValue(WebElement element, String expectedValue) {
		try {
			Assert.assertEquals(element.getText(), expectedValue);
			log.info("Expected value: {} matches with the element value.", expectedValue);
		} catch (Exception e) {
			log.fatal("Unable to fetch value of the element: {}.",element.getText(), e);
			Assert.fail("Unable to fetch value of the element: " + element.getText());
		}
	}
	
	/**
	 * @author shailesh
	 * @Description: This method moves the mouse to the specified element
	 */
	public void moveMouseOver(WebElement element) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();
			log.info("Mouse is successfully placed over: {}", element.getText());
		} catch (Exception e) {
			log.fatal("Not able to hover mouse to ", e);
			Assert.fail("Unable to place mouse over: " + element.getText());
		}
	}
	
	/**
	 * @author shailesh
	 * @Description: This method takes screenshot and saves at configured location
	 */
	public String takeScreenshot(String testCaseName) {
		Base base = new Base();
		try {
			File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(Calendar.getInstance().getTime());
			String targetFolder = base.getProperties("Screenshot_Location")
			                      + new SimpleDateFormat("ddMMyyyy").format(Calendar.getInstance().getTime()) 
			                      + "\\" + testCaseName + "\\";
			
			String destination = targetFolder + fileName + ".png";
			FileUtils.copyFile(source, new File(destination));
			log.info("Screenshot captured successfully. {}", destination);
			return destination;
		} catch (Exception e) {
			log.info("Unable to take Sceenshot taken for failed Step", e);
			return null;
		}
	}
	
	/**
	 * @author shailesh
	 * @Description This method can be used to get webelement visible text
	 */
	public String getTextElement(WebElement element) {
		try {
			String text = element.getText();
			log.info("Inside GetText");
			return text;
		} catch (Exception e) {
			Assert.fail("Unable to get text elemnt: " + element.getText());
			log.fatal("{} Unable to get text element: {} --> {}",
					Thread.currentThread().getStackTrace()[1].getMethodName(), element.getText(), e);
		}
		return null;
	}
	
	/**
	 * @author shailesh
	 * @Description: This method can be used to select options from drop down
	 */
	public void customDropdownHandle(WebDriver driver, WebElement dropdownElement, WebElement menuItem) throws InterruptedException
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdownElement);
		Thread.sleep(2000);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", menuItem);
	}
	
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite(ITestContext context) {
		log.info("*******************************************************************************************************");
		log.info("*********************** {} Execution Started **********************",
				context.getCurrentXmlTest().getSuite().getName());
		log.info(context.getStartDate());
		log.info("*******************************************************************************************************");
		TestDataProperties.loadTestData();
		Base.loadEnvironmentData();
		//if any holiday or other data want to load
		try {
			Base.clearDirectory();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@AfterSuite(alwaysRun = true)
	public void afterSuite(ITestContext context) {
		log.info("Test Cases Passed: {}", context.getPassedTests());
		log.info("Test Cases Failed: {}", context.getFailedTests());
		log.info("*****************************************************************************************************");
		log.info("Test Case execution report available at: {}", ExtentManager.reportFileLocation);
		log.info("Execution completed: {}", context.getEndDate());
		log.info("*****************************************************************************************************");
		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();
		
	}
	
	
	
}
