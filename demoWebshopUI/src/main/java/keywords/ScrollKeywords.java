package keywords;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 * 
 * @author shailesh
 * @Desciption: This class can be used to scroll the web page
 *
 */
public class ScrollKeywords {

	private static ScrollKeywords instance;
	
	public static ScrollKeywords getInstance() {
		if (instance == null) {
			instance = new ScrollKeywords();
		}
		return instance;
	}
	
	private JavascriptExecutor jsExecutor(WebDriver driver) {
		return (JavascriptExecutor) driver;
	}
	
	private JavascriptExecutor jsExecutor(EventFiringWebDriver driver) {
		return (JavascriptExecutor) driver;
	}
	
	public void scrollByVisibleElement(WebDriver driver, WebElement element) {
		jsExecutor(driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public void scrollByVisibleElement(EventFiringWebDriver driver, WebElement element) {
		jsExecutor(driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public void scrollToBottom(WebDriver driver) {
		jsExecutor(driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}
	
	public void scrollToBottom(EventFiringWebDriver driver) {
		jsExecutor(driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}
	
	public void scrollToTop(WebDriver driver) {
		jsExecutor(driver).executeScript("window.scrollTo(0, -document.body.scrollHeight);");
	}
	
	public void scrollToTop(EventFiringWebDriver driver) {
		jsExecutor(driver).executeScript("window.scrollTo(0, -document.body.scrollHeight);");
	}
	//
	public void scrollByPixel(WebDriver driver, int xPixel, int yPixel) {
		jsExecutor(driver).executeScript("window.scrollBy(" + xPixel + "," + yPixel + ")");
	}
	
	public void scrollByPixel(EventFiringWebDriver driver, int xPixel, int yPixel) {
		jsExecutor(driver).executeScript("window.scrollBy(" + xPixel + "," + yPixel + ")");
	}
}
