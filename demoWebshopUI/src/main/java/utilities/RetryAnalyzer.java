package utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import demoWebShop.Base;

/*
 * @author shailesh To retry failed test cases
 */
public class RetryAnalyzer implements IRetryAnalyzer {
	Base base = new Base();
	int retryCount = 0;
	static boolean isRerun;
	
	// this method retries a failed test cases
	public boolean retry(ITestResult result) {
		int maxRetryCount = Integer.parseInt(base.getProperties("TestCase_Retry_Count"));
		if(!result.isSuccess()) {
			if(retryCount < maxRetryCount) {
				retryCount++;
				isRerun = true;
				return true;
			} else {
				retryCount = 0;
				isRerun = false;
				return false;
			}
		}
		return false;
	}
	
	public boolean isRerun() {
		return isRerun;
	}
}
