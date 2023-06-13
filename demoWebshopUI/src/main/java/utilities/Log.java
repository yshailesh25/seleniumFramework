package utilities;

import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.Status;

import static org.apache.logging.log4j.LogManager.getLogger;

/**
 * @author shailesh
 */
public class Log {

	// Initialize Log4j logs
	private static Logger log = getLogger(Log.class);
	
	public static void pass(String message) {
		log.info(message);
		ExtentTestManager.getTest().log(Status.PASS, message);
	}
	
	public static void info(String message) {
		log.info(message);
		ExtentTestManager.getTest().log(Status.INFO, message);
	}
	
	public static void warn(String message) {
		log.warn(message);
		ExtentTestManager.getTest().log(Status.WARNING, message);
	}
	
	public static void error(String message) {
		log.error(message);
		ExtentTestManager.getTest().log(Status.ERROR, message);
	}
	
	public static void fatal(String message) {
		log.fatal(message);
		ExtentTestManager.getTest().log(Status.FATAL, message);
	}
	
	public static void fail(String message) {
		log.error(message);
		ExtentTestManager.getTest().log(Status.FAIL, message);
	}
}
