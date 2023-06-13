package registerUser;

import java.io.IOException;

import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import keywords.CommonActionKeywords;
import pageObjects.LoginPage;

public class S100_testRegisterUser extends CommonActionKeywords {

	Actions builder;
	LoginPage loginPage;
	
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws IOException {
		initializeDriver();
	}
	
	@AfterMethod(alwaysRun = true)
	public void TearDown() throws IOException {
		closeBrowser();
	}
	
	@Test(dataProvider = "dataEngine", dataProviderClass = dataengine.TestDataProvider.class)
	public void registerPageTest(String userid, String password) throws Exception
	{
		openApplication(userid, password);
	}
	
}
