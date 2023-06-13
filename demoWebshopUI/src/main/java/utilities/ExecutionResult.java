package utilities;

import static org.apache.logging.log4j.LogManager.getLogger;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.apache.logging.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import keywords.CommonActionKeywords;

/**
 * @author shailesh
 */
public class ExecutionResult implements ITestListener, IAnnotationTransformer {

	public static Logger log = getLogger(ExecutionResult.class);
	CommonActionKeywords keywords = new CommonActionKeywords();
	RetryAnalyzer retryAnalyzer = new RetryAnalyzer();
	
	public void onStart(ITestContext context) {
		log.info("*******************************************************************************************************");
		log.info("************************Test Module: {} Execution Started************************",
				context.getCurrentXmlTest().getName());
		log.info(context.getStartDate());
		log.info("*******************************************************************************************************");
	}
	
	public void onFinish(ITestContext context) {
		log.info("*******************************************************************************************************");
		log.info("************************Test Module: {} Execution Completed************************",
				context.getCurrentXmlTest().getName());
		log.info(context.getEndDate());
		log.info("*******************************************************************************************************");
	}
	
	public void onTestStart(ITestResult result) {
		log.info("*******************************************************************************************************");
		log.info("**************************Starting Test Case:{} Execution*************************", result.getName());
		log.info("*******************************************************************************************************");
		
		ExtentTestManager.startTest(result.getMethod().getMethodName());
	}
	
	public void onTestSuccess(ITestResult result) {
		log.info("*******************************************************************************************************");
		log.info("**************************Test Case:{} PASSED*******************************", result.getName());
		log.info("*******************************************************************************************************");
	}
	
	public void onTestFailure(ITestResult result) {
		log.error(result.getThrowable());
		
		// Capture Screenshot and attach to Extent test
		String filePath = keywords.takeScreenshot(result.getName());
		try {
			ExtentTestManager.getTest().fail(result.getThrowable().getMessage(),
					MediaEntityBuilder.createScreenCaptureFromPath(filePath).build());
		} catch (IOException e) {
			log.warn("An execution occured while attaching scrrenshot with test step." + e.getCause());
		}
		
		// set test case status to FAILED
		ExtentTestManager.getTest().log(Status.FAIL, "Test Failed");
		
		// If Rerun= true, remove test from Extent Report
		if (retryAnalyzer.isRerun()) {
			ExtentTestManager.extent.removeTest(ExtentTestManager.getTest());
			log.info("Test case will be re-executed due to failure. Retry enabled");
		}
		log.error("*******************************************************************************************************");
		log.error("***************************Test Case:{} FAILED*********************", result.getName());
		log.error("*******************************************************************************************************");
	}
	
	public void onTestSkipped(ITestResult result) {
		log.error(result.getThrowable());
		
		// set test case status to FAILED
		ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
				
		// If Rerun= true, remove test from Extent Report
		if (retryAnalyzer.isRerun()) {
			ExtentTestManager.extent.removeTest(ExtentTestManager.getTest());
			log.info("Test case will be re-executed due to failure. Retry enabled");
		}
		
		log.error("*******************************************************************************************************");
		log.error("***************************Test Case:{} SKIPPED*********************", result.getName());
		log.error("*******************************************************************************************************");
	}
	
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("*** Test failed but within percentage % " + result.getMethod().getMethodName());
	}
	
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		annotation.setRetryAnalyzer(RetryAnalyzer.class);
	}
}
