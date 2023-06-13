package dataengine;

/**
 * @author shailesh
 * @Description: To read test data from testdata.properties file
 */
import static org.apache.logging.log4j.LogManager.getLogger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.Logger;

import demoWebShop.Base;

public class TestDataProperties {
	public static Logger log = getLogger(Base.class);
	private static String userEmail;
	private static String userPassword;
	
	public static String getUserEmail() {
		return userEmail;
	}
	public static void setUserEmail(String userEmail) {
		TestDataProperties.userEmail = userEmail;
	}
	public static String getUserPassword() {
		return userPassword;
	}
	public static void setUserPassword(String userPassword) {
		TestDataProperties.userPassword = userPassword;
	}
	
	/**
	 * This method return property from testdata.properties file
	 * 
	 * @param propertyName
	 * @return
	 */
	public static String getTestDataProperties(String propertyName) {
		String location = null;
		Properties prop = null;
		FileInputStream fis = null;
		try {
			log.info("{}-->Loading property: {} from testdata.properties",
					Thread.currentThread().getStackTrace()[1].getMethodName(), propertyName);
			location = "./resources/testdata.properties";
			prop = new Properties();
			fis = new FileInputStream(location);
			prop.load(fis);
		} catch (IOException e) {
			log.error("Unable to load test data from properties file", e.getMessage());
		}
		return prop.getProperty(propertyName);
	}
	
	public static void loadTestData() {
		setUserEmail(getTestDataProperties("USER_EMAIL"));
		setUserPassword(getTestDataProperties("USER_PASSWORD"));
	}
}

