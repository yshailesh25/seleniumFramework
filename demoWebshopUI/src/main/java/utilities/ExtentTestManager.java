package utilities;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import keywords.CommonActionKeywords;

/**
 * @author shailesh
 * Generates test report
 */
public class ExtentTestManager {
	static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
	static ExtentReports extent = ExtentManager.getInstance();
	
	public static synchronized ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}
	
	public static synchronized void endTest() {
		extent.setSystemInfo("APPLICATION VERSION", CommonActionKeywords.appVersion);
		extent.flush();
	}
	
	public static synchronized ExtentTest startTest(String testName) {
		ExtentTest test = extent.createTest(testName);
		extentTestMap.put((int)(long)(Thread.currentThread().getId()), test);
		return test;
	}
}
