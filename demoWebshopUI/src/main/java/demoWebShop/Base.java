package demoWebShop;

import static org.apache.logging.log4j.LogManager.getLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.Duration;
import java.time.Instant;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;

public class Base {
	public static Logger log = getLogger(Base.class);
	private static int implicitWaitTime;
	private static int explicitWaitTime;
	private static String urlInUse;
	private static String applicationURL;
	private static String emailID;
	private static String configFileUserName;
	private static String configFilePassword;
	private static String browserType;
	
	
	public static int getImplicitWaitTime() {
		return implicitWaitTime;
	}

	private static void setImplicitWaitTime(int implicitWaitTime) {
		Base.implicitWaitTime = implicitWaitTime;
	}

	public static int getExplicitWaitTime() {
		return explicitWaitTime;
	}

	private static void setExplicitWaitTime(int explicitWaitTime) {
		Base.explicitWaitTime = explicitWaitTime;
	}

	public static String getUrlInUse() {
		return urlInUse;
	}

	private static void setUrlInUse(String urlInUse) {
		Base.urlInUse = urlInUse;
	}

	public static String getApplicationURL() {
		return applicationURL;
	}

	private static void setApplicationURL(String applicationURL) {
		Base.applicationURL = applicationURL;
	}

	public static String getEmailID() {
		return emailID;
	}

	private static void setEmailID(String emailID) {
		Base.emailID = emailID;
	}

	public static String getConfigFileUserName() {
		return configFileUserName;
	}

	private static void setConfigFileUserName(String configFileUserName) {
		Base.configFileUserName = configFileUserName;
	}

	public static String getConfigFilePassword() {
		return configFilePassword;
	}

	private static void setConfigFilePassword(String configFilePassword) {
		Base.configFilePassword = configFilePassword;
	}

	public static String getBrowserType() {
		return browserType;
	}

	private static void setBrowserType(String browserType) {
		Base.browserType = browserType;
	}

	/**
	 * This method return property from environment.properties file
	 * 
	 * @param propertyName
	 * @return
	 */
	public String getProperties(String propertyName)
	{
		String location = null;
		Properties prop = null;
		FileInputStream fis = null;
		try {
			log.info("{}-->loading property: {} from environment.properties",
					Thread.currentThread().getStackTrace()[1].getMethodName(), propertyName);
			location = "./resources/environment.properties";//System.getProperty("user.dir")+"\\resources\\environment.properties"
			prop = new Properties();
			fis = new FileInputStream(location);
			prop.load(fis);
		} catch(IOException e) {
			log.error("Unable to load Properties file", e.getMessage());
		}
		return prop.getProperty(propertyName);
	}
	
	/**
	 * This method return database connection
	 * 
	 * @param apiName
	 * @return
	 */
	public static Connection getConnection(String apiName)
	{
		Base base = new Base();
		Connection con;
		log.info("{}-->Getting DB details and creating connection object",
				Thread.currentThread().getStackTrace()[1].getMethodName());
		
		if(apiName.equalsIgnoreCase("TransactionDetailAPI")) {
			try {
				// load the driver class
				Class.forName("oracle.jdbc.driver.OracleDriver");
				//create connection object
				con = DriverManager.getConnection(base.getProperties("TJConnection"), base.getProperties("TJ_DB_ID"),
						base.getProperties("TJ_DB_PSSWD"));
				return con;
			} catch (Exception e) {
				log.error(e);
			}
		} else if (apiName.equalsIgnoreCase("TransactionDetailAPI_QA4")) {
			try {
				// load the driver class
				Class.forName("oracle.jdbc.driver.OracleDriver");
				//create connection object
				con = DriverManager.getConnection(base.getProperties("TJConnection_QA"), base.getProperties("TJ_DB_ID_QA"),
						base.getProperties("TJ_DB_PSSWD_QA"));
				return con;
			} catch (Exception e) {
				log.error(e);
			}
	} else if(apiName.equalsIgnoreCase("ClientProfileAPI")) {
		try {
			// load the driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//create connection object
			con = DriverManager.getConnection(base.getProperties("CPConnection"), base.getProperties("CP_DB_ID"),
					base.getProperties("CP_DB_PSSWD"));
			return con;
		} catch (Exception e) {
			log.error(e);
		}
	}
    return null;	
    }//getConnection
	
	/**
	 * @author shailesh this method delete Screenshot older than X days
	 * @throws IOException
	 */
	public static void clearDirectory() throws IOException 
	{
		Base base = new Base();
		Boolean deletedFiles = false;
		int numberOfDays = Integer.parseInt(base.getProperties("Screenshot_Archive_Days"));
		try {
			Instant beforeDate = Instant.now().minus(Duration.ofDays(numberOfDays));
			//File targetDir = new File(System.getProperty("user.dir")+base.getProperties("Screenshot_Location"));
			File targetDir = new File(base.getProperties("Screenshot_Location"));
			File[] file = targetDir.listFiles();
			Instant instant;
			for(File aFile : file) {
				if(beforeDate.isAfter(Instant.ofEpochMilli(aFile.lastModified()))) {
					deletedFiles = true;
					FileUtils.cleanDirectory(aFile);
					FileUtils.deleteDirectory(aFile);
				}
			}
			if (deletedFiles)
				log.info("Deleted Screenshot older than {} days", numberOfDays);
		} catch (Exception e) {
		     log.error("Unable to delete screenshot older than {} days. Please check environment.properties file.",
		    		    numberOfDays, e);	
		}
	}
	
	public static String getEnvProperties(String propertyName)
	{
		String location = null;
		Properties prop = null;
		FileInputStream fis = null;
		try {
			log.info("{}-->loading property: {} from environment.properties",
					Thread.currentThread().getStackTrace()[1].getMethodName(), propertyName);
			location = "./resources/environment.properties";//System.getProperty("user.dir")+"\\resources\\environment.properties"
			prop = new Properties();
			fis = new FileInputStream(location);
			prop.load(fis);
		} catch(IOException e) {
			log.error("Unable to load Properties file", e.getMessage());
		}
		return prop.getProperty(propertyName);		
	}
	
	public static void loadEnvironmentData()
	{
		try {
			setBrowserType(getEnvProperties("Browser"));
			setUrlInUse(getEnvProperties("URL_IN_USE"));
			setImplicitWaitTime(Integer.parseInt(getEnvProperties("Implicit_Wait_Time")));
			setExplicitWaitTime(Integer.parseInt(getEnvProperties("Explicit_Wait_Time")));
			
			setConfigFileUserName(getEnvProperties("userName"));
			setConfigFilePassword(getEnvProperties("passWord"));
			setEmailID(getEnvProperties("emailId"));
			
			String urlInUse = getEnvProperties("URL_IN_USE");
			if (urlInUse.equals("Client_Central_URL")) {
				setApplicationURL(getEnvProperties("Client_Central_URL"));
			} else if (urlInUse.equals("App_Direct_URL")) {
				setApplicationURL(getEnvProperties("App_Direct_URL"));
			} else {
				log.fatal("******************************************************************************************");
				log.fatal("Please correct the URL_IN_USE property in environment.properties file. value: {}", urlInUse);
				log.fatal("Aborting execution due to incorrect configuration");
				log.fatal("******************************************************************************************");
				System.exit(0);
			}
		} catch (Exception e) {
			log.fatal("Issue reading environment.properties file. Please check. -->");
			e.printStackTrace();
			System.exit(0);
		}
	}

}//Base class
