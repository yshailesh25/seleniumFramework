package keywords;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import demoWebShop.Base;

public class Synchronization extends Base {
	private static Synchronization instance;
	private int waitTime = Base.getExplicitWaitTime();
	public static Synchronization getInstance() {
		if(instance == null) {
		   instance = new Synchronization();
		}
		return instance;
	}
	
	private WebDriverWait getWait(WebDriver driver, int time) {
		return new WebDriverWait(driver, time);
	}
	
	public void implicitWait(WebDriver driver, int time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}
	
	public void explicitWaitElementLocated(WebDriver driver, By locator) {
		getWait(driver, waitTime).ignoring(StaleElementReferenceException.class)
		        .until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(locator)));
	}
	
	public void explicitWaitElementClickable(WebDriver driver, WebElement element, int time) {
		getWait(driver, time).ignoring(StaleElementReferenceException.class)
		        .until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
	}
	
	public void explicitWaitElementClickable(WebDriver driver, By by, int time) {
		getWait(driver, time).ignoring(StaleElementReferenceException.class)
		        .until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(by)));
	}
	
	public void explicitWaitElementClickable(WebDriver driver, WebElement element) {
		getWait(driver, waitTime).ignoring(StaleElementReferenceException.class)
		        .until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
	}
	
	public void explicitWaitElementInvisible(WebDriver driver, int time, By by) {
		getWait(driver, time).until(ExpectedConditions.invisibilityOfElementLocated(by));
	}
	
	public void explicitWaitElementClickable(WebDriver driver, By by) {
		getWait(driver, waitTime).ignoring(StaleElementReferenceException.class)
		        .until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(by)));
	}
	
	public void explicitWaitElementToBeSelected(WebDriver driver, WebElement element) {
		getWait(driver, waitTime).until(ExpectedConditions.elementToBeSelected(element));
	}
	
	public void explicitWaitElementToBeSelected(WebDriver driver, By by) {
		getWait(driver, waitTime).until(ExpectedConditions.elementToBeSelected(by));
	}
	
	public void explicitWaitStaleElement(WebDriver driver, By by) {
		getWait(driver, waitTime).until(ExpectedConditions.stalenessOf(driver.findElement(by)));
	}
	
	public void explicitWaitStaleElement(WebDriver driver, WebElement element) {
		getWait(driver, waitTime).until(ExpectedConditions.stalenessOf(element));
	}
	
	public void explicitWaitStaleElement(WebDriver driver, By by, int time) {
		getWait(driver, waitTime).until(ExpectedConditions.stalenessOf(driver.findElement(by)));
	}
	
	public void explicitWaitStaleElement(WebDriver driver, WebElement element, int time) {
		getWait(driver, time).until(ExpectedConditions.stalenessOf(element));
	}
	
	public void explicitWaitVisibleElement(WebDriver driver, By by) {
		getWait(driver, waitTime).ignoring(StaleElementReferenceException.class)
		        .until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	public void explicitWaitVisibleElement(WebDriver driver, WebElement element) {
		getWait(driver, waitTime).ignoring(StaleElementReferenceException.class)
		        .until(ExpectedConditions.visibilityOf(element));
	}
	
	public void explicitWaitVisibleElement(WebDriver driver, By by, int time) {
		getWait(driver, time).ignoring(StaleElementReferenceException.class)
		        .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
	}
	
	public void explicitWaitVisibleElement(WebDriver driver, WebElement element, int time) {
		getWait(driver, time).ignoring(StaleElementReferenceException.class)
		        .until(ExpectedConditions.visibilityOf(element));
	}
	
	public void explicitWaitElementToBeSelected(WebDriver driver, WebElement element, int time) {
		getWait(driver, time).ignoring(StaleElementReferenceException.class)
		        .until(ExpectedConditions.elementToBeSelected(element));
	}
	
	public void explicitWaitElementToBeSelected(WebDriver driver, By by, int time) {
		getWait(driver, time).ignoring(StaleElementReferenceException.class)
		        .until(ExpectedConditions.elementToBeSelected(by));
	}
	
	public void explicitWaitInvisibleElement(WebDriver driver, List<WebElement> elements, int time) {
		getWait(driver, time).ignoring(StaleElementReferenceException.class)
		        .until(ExpectedConditions.invisibilityOfAllElements(elements));
	}

}
