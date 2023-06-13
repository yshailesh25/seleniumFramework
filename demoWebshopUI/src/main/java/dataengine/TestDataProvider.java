package dataengine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.DataProvider;

import keywords.CommonActionKeywords;

public class TestDataProvider extends CommonActionKeywords{

	public static Logger log = LogManager.getLogger(TestDataProvider.class);
	
	/**
	 * @author shailesh
	 */
	@DataProvider
	public Object[][] dataEngine(ITestNGMethod result, ITestContext context) {
		DataEngine generic = new DataEngine();
		int paramCount = result.getConstructorOrMethod().getMethod().getParameterCount();
		System.out.println("till paraCount> "+paramCount);
		String moduleName = context.getCurrentXmlTest().getName();
		System.out.println("till module> "+moduleName);
		String testCaseName = result.getMethodName();
		System.out.println("till testcaseName "+testCaseName);
		Object[][] data = generic.getTestData(paramCount, moduleName, testCaseName);
		
		if (data.length == 0) {
			log.info("*****************************************************************************************************");
			log.info("Test Execution Skipped. Test Data does not exist or mismatches for the test case: "
					+ result.getMethodName());
			log.info("*****************************************************************************************************");
		}
		return data;
	}
}
