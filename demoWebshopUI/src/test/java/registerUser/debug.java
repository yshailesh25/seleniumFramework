package registerUser;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import dataengine.DataEngine;

public class debug {
	@Test
	public void dataEngine(ITestNGMethod result, ITestContext context) {
		int paramCount = result.getConstructorOrMethod().getMethod().getParameterCount();
		String moduleName = context.getCurrentXmlTest().getName();
		String testCaseName = result.getMethodName();
	}


}
