package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ResourceCDN;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import demoWebShop.Base;
import keywords.CommonActionKeywords;

/**
 * @author shailesh
 */
public class ExtentManager extends CommonActionKeywords {
	static SimpleDateFormat sdfDate = new SimpleDateFormat("ddMMMyy_HHmm");
	static Date now = new Date();
	static String strDate = sdfDate.format(now);
	
	private static ExtentReports extent;
	private static String reportFileName;
	public static String reportFileLocation;
	
	public static ExtentReports getInstance() {
		if (extent == null)
			createInstance();
		return extent;
	}
	
	// Create an extent report instance
	public static ExtentReports createInstance() {
		Base base = new Base();
		
		reportFileName = "demoWebUI_ExecutionReport" + strDate + ".html";
		reportFileLocation = base.getProperties("TestReport_Folder") + reportFileName;
		
		Capabilities browserCap = ((RemoteWebDriver) wdriver).getCapabilities();
		String browserName = browserCap.getBrowserName();
		String browserVersion = browserCap.getVersion();
		
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportFileLocation);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName("demoWebShop Execution Report");
		htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
		htmlReporter.config().setDocumentTitle("WEBTEST- demoWebShop Test Execution Report" + strDate);
		htmlReporter.config().setResourceCDN(ResourceCDN.EXTENTREPORTS);
		
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		// set environment details
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("OS VERSION", System.getProperty("os.version"));
		extent.setSystemInfo("USER", System.getProperty("user.name"));
		extent.setSystemInfo("BROWSER NAME", browserName);
		extent.setSystemInfo("BROWSER VERSON", browserVersion);
		extent.setSystemInfo("ENVIRONMENT", base.getProperties("Environment"));
		
		return extent;
	}

}
