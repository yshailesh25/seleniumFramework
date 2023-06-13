package keywords;

import static org.apache.logging.log4j.LogManager.getLogger;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * 
 * @author shailesh
 * @Description: This class can be used to select/deselect options from dropdown.
 *
 */
public class DropDownKeywords {
	private static DropDownKeywords instance;
	private static Logger log = getLogger(DropDownKeywords.class);
	
	private DropDownKeywords() {
	}
	
	public static DropDownKeywords getInstance() {
		if (instance == null) {
			instance = new DropDownKeywords();
		}
		return instance;
	}
	
	private Select dropdown(WebElement element) {
		return new Select(element);
	}
	
	public void selectByVisibleText(WebElement element, String text) {
		dropdown(element).selectByVisibleText(text);
		log.info("text " + text + " is selected from " + element.getText() + " drop down. ");
	}
	
	public void selectByIndex(WebElement element, int index) {
		dropdown(element).selectByIndex(index);
		log.info("index " + index + " is selected from " + element.getText() + " drop down. ");
	}
	
	public void selectByValue(WebElement element, String Value) {
		dropdown(element).selectByValue(Value);
		log.info("Value " + Value + " is selected from " + element.getText() + " drop down. ");
	}
	
	public void deselectByVisibleText(WebElement element, String text) {
		dropdown(element).deselectByVisibleText(text);
		log.info("text " + text + " is deselected from " + element.getText() + " drop down. ");
	}
	
	public void deselectByIndex(WebElement element, int index) {
		dropdown(element).deselectByIndex(index);
		log.info("index " + index + " is deselected from " + element.getText() + " drop down. ");
	}
	
	public void deselectByValue(WebElement element, String Value) {
		dropdown(element).deselectByValue(Value);
		log.info("Value " + Value + " is deselected from " + element.getText() + " drop down. ");
	}
	
	public List getOptions(WebElement element) {
		List<WebElement> List = dropdown(element).getOptions();
		return List;
	}
	
}
