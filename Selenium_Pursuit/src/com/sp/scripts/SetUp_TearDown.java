package com.sp.scripts;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.sp.util.COMMON_METHODS;
import com.sp.util.INITIALIZE;
import com.sp.util.REPORTER;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;


public class SetUp_TearDown extends INITIALIZE {
	
	public static WebDriver driver;
	public Selenium selenium;
	public long vScriptStartTime = Calendar.getInstance().getTimeInMillis();
	
	String exString;
	
	COMMON_METHODS CM = null;
	
	
	public static String scriptName = null;
	String browserName = null;
	String Env = "QA";
	
	@BeforeSuite
	public void AtBeforeSuite(ITestContext ic) throws Exception {
		//String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		
		scriptName = ic.getCurrentXmlTest().getName().toString();
	
		
		//Close any Open IEDriverServer instances
		//Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
		
		//Close any Open iexplore instances
		//Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
		
		//Close any Open firefox instances
		//Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
		
		//Close any Open Chrome instances
		Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
		
		//Loading test Objects and Data for BFX Portal
		readTestData_POI(getDataTablePath("SP"),0);
		readTestObject_POI(getDataTablePath("SP"),1);
		
		
		/*ProfilesIni profile = new ProfilesIni();
		FirefoxProfile ffprofile = profile.getProfile("default");
		
		driver = new FirefoxDriver(ffprofile);*/
		
		File file = new File(System.getProperty("user.dir") + "\\IE_Chrome_Driver\\chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		driver = new ChromeDriver();
		
	
	}

	@BeforeTest
	public void AtBeforeTest(ITestContext ic) throws Exception {
		//String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		//System.out.println("Inside - " + methodName);

		//scriptName = getClass().getSimpleName();
		scriptName = ic.getCurrentXmlTest().getName().toString();
		new REPORTER(scriptName,REPORTER.getDateFormat(REPORTER.vDatetype8));
		
		// CREATE AN INSTANCE OF INTERNETEXPLORERDRIVER
		/*File file = new File(System.getProperty("user.dir") + "\\IEDriver\\IEDriverServer.exe");
		System.setProperty("webdriver.ie.driver", file.getAbsolutePath());

		DesiredCapabilities Dsrdcaps = DesiredCapabilities.internetExplorer();
		Dsrdcaps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
		BFX_SetUp_TearDown.driver = new InternetExplorerDriver(Dsrdcaps);*/

		//this.driver = new InternetExplorerDriver();

		// CREATE AN INSTANCE OF WebDriverBackedSelenium
		selenium = new WebDriverBackedSelenium(driver, COMMON_METHODS.BFXURL);
		//System.out.println("Exiting Method - " + methodName.toUpperCase());

		// CREATE AN INSTANCE OF Common_Methods
		CM = new COMMON_METHODS(driver, "on");

		//Code to get the Browser Name and Version
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
			
		// UPDATE EXCEL, NOTEPAD AND HTML REPORT WITH THE BROWSER NAME
		REPORTER.updateReports(updateValue.bName, caps.getBrowserName().toString().toUpperCase()+" / "+caps.getVersion().toString(), "");
		browserName = caps.getBrowserName().toString().toUpperCase()+" / "+caps.getVersion().toString();
		//System.out.println(caps.getBrowserName().toString().toUpperCase()+" / "+caps.getVersion().toString());
		REPORTER.updateReports(updateValue.Env, Env, "");

	}
	@Test(priority = 1)
	public void BrokenLinksTest() throws IOException, Exception {	
		
		//For Reporting Purpose
		REPORTER.LogEvent(TestStatus.MSG, "Some useful Message".toUpperCase(), "","");
				
		String BaseURL = "http://www.google.com";
		
		CM.openBrowser(BaseURL);
		
	}

	@AfterTest
	public void AtAfterTest(ITestContext ic) throws IOException, Exception {
		//String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		//System.out.println("Inside - " + methodName);
		String finalScriptStatus;
		
		System.out.println("---------------------"+ic.getFailedTests().toString());
		String sTemp = ic.getFailedTests().toString();
		
		REPORTER.updateReports(updateValue.tEndTime, "", "");
		REPORTER.updateReports(updateValue.execTime,formatIntoHHMMSS(Calendar.getInstance().getTimeInMillis() - vScriptStartTime).toString(), "");
		REPORTER.updateReports(updateValue.totalSteps, "", "");
		
		if(!sTemp.contains("FAILURE")){
			finalScriptStatus = "PASS";
			REPORTER.updateReports(updateValue.execStatus, "", finalScriptStatus);
			REPORTER.updateReports(updateValue.failedStepNo, "", finalScriptStatus);
			
					
		}else{
			finalScriptStatus = "FAIL";
			REPORTER.updateReports(updateValue.execStatus, "", finalScriptStatus);
			REPORTER.updateReports(updateValue.failedStepNo, "", finalScriptStatus);
		}
		REPORTER.LogEvent(TestStatus.INFO, "", "", "-------->[ End Of Script Execution ]<--------");
		
		//Rename the result log
		REPORTER.RenameResultLog(finalScriptStatus);
		//REPORTER.takeScreenShot(finalScriptStatus);
		
		
		//String mailFormat = EMAIL.HTMLMailFormat(scriptName, getTestData_POI("TD_ENV").toString(), browserName, REPORTER.ScriptStartTime, REPORTER.ScriptEndTime, REPORTER.ScriptexecTime, REPORTER.ScripttotalSteps, finalScriptStatus);
		//String mailFormat = SendEmailUsingGMailSMTP.HTMLMailFormat(scriptName, Env, browserName, REPORTER.ScriptStartTime, REPORTER.ScriptEndTime, REPORTER.ScriptexecTime, REPORTER.ScripttotalSteps, finalScriptStatus);
		
		//EMAIL.SendEmail(mailFormat);
		//SendEmailUsingGMailSMTP.SendEmail(mailFormat);
	
	}
	
	@AfterSuite
	public void AtAfterSuite(ITestContext ic) throws Exception {
		//String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		
		//Close Driver
		driver.quit();
		
		scriptName = ic.getCurrentXmlTest().getName().toString();
		
		// Reset The Hash Map
		clearHashMap();				
		
	}
	

}
