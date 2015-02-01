/*--------------------------------------------------------------------------------
COMMON FUNCTIONS  LIBRARY - (USER DEFINED METHODS)
DESCRIPTION: CONSISTS OF ALL THE COMMOLY USED USERDEFINED METHODS
OWNERS - RAVI NAIDU (rnaidu) / Dixit Adoor (dadoor)
VERSION - 1.0 (Nov 24 2011)
AUTHORS - RAVI NAIDU (rnaidu) / DIXIT ADOOR (dadoor)
CONTRIBUTORS - Vijeth Thamme gowda (vthamme) / Tarun K (txkx) / NAGARAJAN BABULAL(nbabuha)

--------------------------------------------------------------------------------*/
package com.sp.util;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.support.ui.Select;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

/* ***************************************************************************************
 PURPOSE: Common Actions performed on Web Application
 RETURN VALUE: None
 INPUT(s): Defined in individual Subclass Methods
 AUTHOR: Ravi Naidu (rnaidu)
 CREATION DATE: Jan 01 2012
 *************************************************************************************** */
public class COMMON_METHODS extends INITIALIZE implements CommonActions {

	private static int Index = 0;
	public static WebDriver driver;
	public static Selenium selenium;
	@SuppressWarnings("unused")
	private String mainpage;
	public WebDriver popup;

	public enum Sel {
		id, name
	};

	private static Actions DriverActions;
	@SuppressWarnings("unused")
	private Actions KeyboardActions;
	@SuppressWarnings("unused")
	private Actions KeyboardMouseActions;
	private Keyboard keyboard;
	private Mouse mouse;

	// Get the class name and store it in a variable
	@SuppressWarnings("unused")
	private String className = this.getClass().getSimpleName().toString()
			.toUpperCase();

	@SuppressWarnings("unused")
	private long startTransactionTime;
	@SuppressWarnings("unused")
	private long endTransactionTime;

	@SuppressWarnings("unused")
	private int startTransactionStep;
	@SuppressWarnings("unused")
	private int endTransactionStep;

	public static String BFXURL = "https://qa.buysidefx.com";

	public static String sHandleBefore = "";

	public static String highlightObj;

	public enum SeleniumIdentifiers

	{
		className, cssSelector, id, linkText, name, partialLinkText, tagName, xpath, byIndex, byValue, byVisibleText
	}

	public static String CurrentWindowHandle;

	@SuppressWarnings("static-access")
	public COMMON_METHODS(WebDriver driver, String onoff) {

		highlightObj = onoff;

		this.driver = driver;
		this.selenium = new WebDriverBackedSelenium(driver, BFXURL);
		this.DriverActions = new Actions(driver);
		this.KeyboardActions = new Actions(keyboard);
		this.KeyboardMouseActions = new Actions(keyboard, mouse);
		// selenium.windowMaximize();
		this.driver.manage().window().maximize();

		mainpage = this.driver.getWindowHandle();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		
	}

	/**
	 * @author Ravi Naidu
	 * @param TestObject
	 *            The Test Object Value Read From The Data Table (XLS)
	 * @return Is an Array returned as sTemp
	 * @throws IOException
	 * 
	 */
	public static String[] splitTestObject(String TestObject)
			throws IOException {
		// String methodName =
		// Thread.currentThread().getStackTrace()[1].getMethodName();
		// Reporter.writeLog(methodName.toString());

		System.out.println("TestObject is " + TestObject);
		String[] sTemp = (TestObject.split(",", 4));
		/*
		 * if (sTemp[0].contains("_")) { String[] sTemp1 = sTemp[0].split("_",
		 * 2); sTemp[0] = new String(sTemp1[1].trim());
		 * //System.out.println("sTemp[0] is " + sTemp[0]); }
		 */
		sTemp[1] = new String(sTemp[1].trim());
		// System.out.println("sTemp[1] is " + sTemp[1]);
		sTemp[2] = new String(sTemp[2].trim());
		// System.out.println("sTemp[2] is " + sTemp[2]);
		sTemp[3] = new String(sTemp[3].trim());
		// System.out.println("sTemp[3] is " + sTemp[3]);

		return sTemp;

	}

	/**
	 * @param TestObject
	 * @return
	 * @throws IOException
	 */
	public static WebElement checkIdentifier(String objName, String objIdentifier,
			String LogEvent, String Locator, String mName) throws IOException {
		// System.out.println("Inside Method checkIdentifier");
		// Reporter.writeLog(new
		// Exception().getStackTrace()[0].getMethodName().toString());
		SeleniumIdentifiers Identifier = SeleniumIdentifiers
				.valueOf(objIdentifier);
		WebElement Element = null;

		switch (Identifier) {
		case className: {
	
			Element = driver.findElement(By.className(Locator));
			break;
		}
		case cssSelector: {
			
			Element = driver.findElement(By.cssSelector(Locator));
			break;
		}
		case id: {
			
			Element = driver.findElement(By.id(Locator));
			break;
		}
		case linkText: {
			
			Element = driver.findElement(By.linkText(Locator));
			break;
		}
		case name: {
			
			Element = driver.findElement(By.name(Locator));
			break;
		}
		case partialLinkText: {
			
			Element = driver.findElement(By.partialLinkText(Locator));
			break;
		}
		case tagName: {
			
			Element = driver.findElement(By.tagName(Locator));
			break;
		}
		case xpath: {
			
			Element = driver.findElement(By.xpath(Locator));
			break;
		}
		default:
			break;

		}
		// System.out.println("Exiting Method checkIdentifier");
		return Element;

	}

	/* ***************************************************************************************
	 * PURPOSE: Launch Browser and Open application RETURN VALUE: None INPUT(s):
	 * Driver and Application URL AUTHOR: Ravi Naidu (rnaidu) CREATION DATE: Mar
	 * 14 2012
	 * ******************************************************************
	 * ********************
	 */
	/**
	 * @author Ravi Naidu
	 * @param URL to be Launched
	 * @return
	
	 */
	@Override
	public void openBrowser(String URL) {
		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		try {
			
			REPORTER.writeLog(methodName);
			CurrentWindowHandle = driver.getWindowHandle();
			driver.navigate().to(URL);// launch Browser with the URL

			// Report to Notepad, HTMl and Excel
			REPORTER
					.LogEvent(TestStatus.PASS, "Navigate to URL - " + URL,
							"Navigate to URL - " + URL
									+ " - Successfull".toUpperCase(), "");

		} catch (Exception e) {

			REPORTER.catchException(e, "Navigate to the URL '" + URL + "'");

		}

		URL = null;
		methodName = null;
		
	}

	/* ***************************************************************************************
	 * PURPOSE: Enter Values in Edit Field RETURN VALUE: None INPUT(s): Driver,
	 * Xpath of object and data to enter in the edit box AUTHOR: Ravi Naidu
	 * (rnaidu) CREATION DATE: Mar 14 2012
	 * **************************************
	 * ************************************************
	 */
	@Override
	public void editAField(String TestObject, String Value)
			 {
		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		String[] sTemp = null;
		WebElement we = null;

		try {
			REPORTER.writeLog(methodName);

			sTemp = splitTestObject(TestObject);
			we = checkIdentifier(sTemp[0], sTemp[1], sTemp[2],
					sTemp[3], methodName);
			if (highlightObj.equalsIgnoreCase("on")) {
				highlightElement(we);
				
			}

			we.clear();
			we.sendKeys(Value);
			REPORTER.LogEvent(TestStatus.PASS, "Enter " + sTemp[2] + " '"
					+ Value + "'", "Enter " + sTemp[2] + " '" + Value + "'"
					+ " - Successfull".toUpperCase(), "");
		}/*catch (org.openqa.selenium.InvalidElementStateException e) {

			we.sendKeys(Value);
			REPORTER.LogEvent(TestStatus.PASS, "Enter " + sTemp[2] + " '"
					+ Value + "'", "Enter " + sTemp[2] + " '" + Value + "'"
					+ " - Successfull".toUpperCase(), "");

		}catch (java.lang.NullPointerException e) {
			we.sendKeys(Value);
			REPORTER.LogEvent(TestStatus.PASS, "Enter " + sTemp[2] + " '"
					+ Value + "'", "Enter " + sTemp[2] + " '" + Value + "'"
					+ " - Successfull".toUpperCase(), "");
			

		}*/catch(Exception e){
			REPORTER
			.catchException(e, "Enter " + sTemp[2] + " '" + Value + "'");
			
		}

		sTemp = null;
		Value = null;

	}
	@Override
	public void editAField_1(String TestObject, String Value) {
		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		
		String[] sTemp = null;
	
		try {
			REPORTER.writeLog(methodName);
			sTemp = splitTestObject(TestObject);
			WebElement we = checkIdentifier(sTemp[0], sTemp[1], sTemp[2],
					sTemp[3], methodName);
			if (highlightObj.equalsIgnoreCase("on")) {
				highlightElement(we);
			}

			
			we.sendKeys(Value);
			REPORTER.LogEvent(TestStatus.PASS, "Enter " + sTemp[2] + " '"
					+ Value + "'", "Enter " + sTemp[2] + " '" + Value + "'"
					+ " - Successfull".toUpperCase(), "");
		} catch (Exception e) {

			REPORTER
					.catchException(e, "Enter " + sTemp[2] + " '" + Value + "'");

		}

		sTemp = null;
		Value = null;

	}
	@Override
	public void editPasswordField(String TestObject, String Value) {

		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		

		String[] sTemp = null;

		try {
			REPORTER.writeLog(methodName);
			sTemp = splitTestObject(TestObject);
			WebElement we = checkIdentifier(sTemp[0], sTemp[1], sTemp[2],
					sTemp[3], methodName);
			if (highlightObj.equalsIgnoreCase("on")) {
				highlightElement(we);

			}
			we.clear();
			we.sendKeys(Value);
			REPORTER.LogEvent(TestStatus.PASS, "Enter " + sTemp[2]
					+ " '*******'", "Enter " + sTemp[2] + " '*******'"
					+ " - Successfull".toUpperCase(), "");
		} catch (Exception e) {
			REPORTER.catchException(e, "Edit " + sTemp[2]
					+ " with the value ******");
		}

		sTemp = null;
		Runtime.getRuntime().gc();
	}

	/* ***************************************************************************************
	 * PURPOSE: To perform click action RETURN VALUE: None INPUT(s): Driver and
	 * Xpath of object AUTHOR: Ravi Naidu (rnaidu) CREATION DATE: Feb 01 2012
	 * MODIFIED BY : Sandeep Satheesh - Feb 17 2012
	 * *****************************
	 * *********************************************************
	 */
	
	public void clickElement(String TestObject) {
		String[] sTemp = null;

		try {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			System.out.println(methodName + " - TestObject - " + TestObject);
			
			REPORTER.writeLog(methodName);
			sTemp = splitTestObject(TestObject);
			WebElement we = checkIdentifier(sTemp[0], sTemp[1], sTemp[2],
					sTemp[3], methodName);
			if (highlightObj.equalsIgnoreCase("on")) {
				highlightElement(we);

			}
			we.click();
			REPORTER.LogEvent(TestStatus.PASS, "Click '"/* + objType + */
					+ sTemp[2] + "'", "Click '" + sTemp[2] + "'"
					+ " - Successfull".toUpperCase(), "");

		} catch (Exception e) {

			REPORTER.catchException(e, "Click '" + sTemp[2] + "'");
		}

		sTemp = null;/* objType = null; */

	}

	/* ***************************************************************************************
	 * PURPOSE: To perform Check action RETURN VALUE: None INPUT(s): Driver and
	 * Xpath of object AUTHOR: Ravi Naidu (rnaidu) CREATION DATE: Mar 14 2012
	 * ***
	 * ***********************************************************************
	 * ************
	 */
	public static void checkBox(String TestObject, String status)
			throws IOException {
		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);

		String[] sTemp = splitTestObject(TestObject);
		WebElement we = checkIdentifier(sTemp[0], sTemp[1], sTemp[2], sTemp[3],
				methodName);
		if (status.equalsIgnoreCase("check")) {

			try {

				if (!we.isSelected()) {
					if (highlightObj.equalsIgnoreCase("on")) {
						/*
						 * String we1 = we.toString();
						 * 
						 * we1 = (we1.substring(we1.indexOf(">") + 1,
						 * we1.lastIndexOf("]"))).trim(); we1 =
						 * we1.replace(": ", "="); String we3 = "\"" + we2 +
						 * "\"";
						 * 
						 * selenium.highlight(we1);
						 */

						highlightElement(we);

					}

					we.click();
					REPORTER.LogEvent(TestStatus.PASS, "Check Checkbox '"
							+ sTemp[2] + "'", "Check checkbox '" + sTemp[2]
							+ "'" + "' - Successfull".toUpperCase(), "");
				} else
					REPORTER.LogEvent(TestStatus.WARNING, "Check Checkbox '"
							+ sTemp[2] + "'", "Checkbox '" + sTemp[2]
							+ "' has already been checked", "");
			} catch (Exception e) {
				REPORTER.catchException(e, "Check Checkbox '" + sTemp[2] + "'");
			}

		} else if (status.equalsIgnoreCase("uncheck")) {

			try {

				if (we.isSelected()) {
					if (highlightObj.equalsIgnoreCase("on")) {
						/*
						 * String we1 = we.toString();
						 * 
						 * we1 = (we1.substring(we1.indexOf(">") + 1,
						 * we1.lastIndexOf("]"))).trim(); we1 =
						 * we1.replace(": ", "="); String we3 = "\"" + we2 +
						 * "\"";
						 * 
						 * selenium.highlight(we1);
						 */

						highlightElement(we);

					}
					we.click();
					REPORTER.LogEvent(TestStatus.PASS, "Uncheck Checkbox '"
							+ sTemp[2] + "'", "Uncheck Checkbox '" + sTemp[2]
							+ "'" + "' - Successfull".toUpperCase(), "");
				} else
					REPORTER.LogEvent(TestStatus.WARNING, "Uncheck Checkbox '"
							+ sTemp[2] + "'", "Checkbox '" + sTemp[2]
							+ "' has already been unchecked", "");
			} catch (Exception e) {
				REPORTER.catchException(e, "Uncheck the checkbox '" + sTemp[2]
						+ "'");
			}

		} else {
			REPORTER.LogEvent(TestStatus.FAIL, "Check / Uncheck - Check Box",
					"Wrong Value Passed As Parameter", "");

		}

		sTemp = null;
		status = null;
		Runtime.getRuntime().gc();

	}

	/* ***************************************************************************************
	 * PURPOSE: To perform radio button select RETURN VALUE: None INPUT(s):
	 * Driver and Xpath of object AUTHOR: Ravi Naidu (rnaidu) CREATION DATE: Mar
	 * 14 2012
	 * ******************************************************************
	 * ********************
	 */
	public static void radioButton(String TestObject) throws IOException {

		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);

		String[] sTemp = splitTestObject(TestObject);

		try {
			WebElement we = checkIdentifier(sTemp[0], sTemp[1], sTemp[2],
					sTemp[3], methodName);
			if (!we.isSelected()) {
				if (highlightObj.equalsIgnoreCase("on")) {
					/*
					 * String we1 = we.toString();
					 * 
					 * we1 = (we1.substring(we1.indexOf(">") + 1,
					 * we1.lastIndexOf("]"))).trim(); we1 = we1.replace(": ",
					 * "="); String we3 = "\"" + we2 + "\"";
					 * 
					 * selenium.highlight(we1);
					 */
					highlightElement(we);

				}
				we.click();
				REPORTER.LogEvent(TestStatus.PASS, "Select Radio Button '"
						+ sTemp[2] + "'", "Select Radio Button '" + sTemp[2]
						+ "'" + " - Successfull".toUpperCase(), "");
			} else {
				REPORTER.LogEvent(TestStatus.WARNING,
						"Select the Radio Button '" + sTemp[2] + "'",
						"Radio Button '" + sTemp[2]
								+ "' has already been selected", "");
			}
		} catch (Exception e) {

			REPORTER
					.catchException(e, "Select Radio Button '" + sTemp[2] + "'");
		}

		sTemp = null;
		Runtime.getRuntime().gc();
	}

	/* ***************************************************************************************
	 * PURPOSE: To perform List select RETURN VALUE: None INPUT(s): Driver and
	 * Xpath of object AUTHOR: Ravi Naidu (rnaidu) CREATION DATE: Mar 14 2012
	 * ***
	 * ***********************************************************************
	 * ************
	 */
	public static void listBoxSelect(String TestObject, String Value,
			String Identifiers) throws IOException {
		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);
		if (Value.startsWith("#")) {

			String[] nValue = Value.split("#");
			// System.out.println("nValue[1]is - " + nValue[1]);
			Index = Integer.parseInt(nValue[1]);
			// System.out.println("Index is - " + Index);
		}

		SeleniumIdentifiers Identifier = SeleniumIdentifiers
				.valueOf(Identifiers);
		String[] sTemp = splitTestObject(TestObject);
		Select select = new Select(checkIdentifier(sTemp[0], sTemp[1], sTemp[2],
				sTemp[3], methodName));

		// This is to highlight the element when highlight is set to "on"
		WebElement we = checkIdentifier(sTemp[0], sTemp[1], sTemp[2], sTemp[3],
				methodName);

		switch (Identifier) {
		case byIndex: {
			String sTemp1 = null;
			try {

				select.selectByIndex(Index);
				if (highlightObj.equalsIgnoreCase("on")) {

					highlightElement(we);
					// selenium.highlight(sTemp[1]+"="+sTemp[3]);

				}
				sTemp1 = select.getOptions().get(Index).getText();
				REPORTER.LogEvent(TestStatus.PASS, "Select '" + sTemp1
						+ "' from dropdown '" + sTemp[2] + "'", "Select '"
						+ sTemp1 + "' from dropdown '" + sTemp[2] + "'"
						+ "' - Successfull".toUpperCase(), "");
			} catch (Exception e) {
				REPORTER.catchException(e, "Select '" + sTemp1
						+ "' from dropdown '" + sTemp[2] + "'");
			}
			break;
		}
		case byValue: {
			try {

				select.selectByValue(Value);
				if (highlightObj.equalsIgnoreCase("on")) {
					highlightElement(we);
					// selenium.highlight(sTemp[1] + "=" + sTemp[3]);

				}
				REPORTER.LogEvent(TestStatus.PASS, "Select '" + Value
						+ "' from dropdown '" + sTemp[2] + "'", "Select '"
						+ Value + "' from dropdown '" + sTemp[2] + "'"
						+ "' - Successfull".toUpperCase(), "");
			} catch (Exception e) {
				REPORTER.catchException(e, "Select '" + Value
						+ "' from dropdown '" + sTemp[2] + "'");
			}
			break;
		}
		case byVisibleText: {
			try {

				select.selectByVisibleText(Value);
				if (highlightObj.equalsIgnoreCase("on")) {

					highlightElement(we);
					// selenium.highlight(sTemp[1] + "=" + sTemp[3]);

				}
				REPORTER.LogEvent(TestStatus.PASS, "Select '"
						+ Value.toUpperCase() + "' from dropdown List '"
						+ sTemp[2] + "'", "Select '" + Value.toUpperCase()
						+ "' from dropdown List '" + sTemp[2]
						+ "' - Successfull".toUpperCase(), "");
			} catch (Exception e) {
				REPORTER.catchException(e, "Select '" + Value
						+ "' from dropdown '" + sTemp[2] + "'");
			}
			break;
		}
		default:
			break;

		}
		// sTemp1 = null;
		sTemp = null;
		Value = null;
		Runtime.getRuntime().gc();
	}

	public static void Alert(String Action) throws IOException,
			InterruptedException {
		REPORTER.writeLog(new Exception().getStackTrace()[0].getMethodName()
				.toString());

		int x;
		if (Action.equalsIgnoreCase("accept")) {
			x = 1;
		} else {
			x = 0;
		}

		switch (x) {
		case 1: {

			try {
				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
						REPORTER.LogEvent(TestStatus.FAIL,
								"Check Alert Present",
								"No Alert Message Displayed After 60 seconds",
								"");
					}

					try {
						if (isAlertPresent())
							break;
					} catch (Exception e) {
					}
					Thread.sleep(1000);
				}

				// String alertText = closeAlertAndGetItsText();
				String alertText = driver.switchTo().alert().getText();
				driver.switchTo().alert().accept();
				REPORTER.LogEvent(TestStatus.PASS, "Accept Alert",
						"Accept Alert with Message '" + alertText
								+ "' - Successfull".toUpperCase(), "");
			} catch (Exception e) {
				REPORTER.LogEvent(TestStatus.FAIL, "Accept Alert",
						"No Alert message present", "");
			}
			break;
		}
		case 0: {
			try {
				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
						REPORTER.LogEvent(TestStatus.FAIL,
								"Check Alert Present",
								"No Alert Message Displayed After 60 seconds",
								"");
					}

					try {
						if (isAlertPresent())
							break;
					} catch (Exception e) {
					}
					Thread.sleep(1000);
				}

				String sTemp = driver.switchTo().alert().getText();
				driver.switchTo().alert().dismiss();
				REPORTER.LogEvent(TestStatus.PASS, "Dismiss Alert",
						"Dismiss Alert with Message '" + sTemp
								+ "' - Successfull".toUpperCase(), "");
			} catch (Exception e) {
				REPORTER.LogEvent(TestStatus.FAIL, "Dismiss Alert",
						"No Alert message present", "");
			}
			break;
		}

		}

		Action = null;/* x=(Integer) null; */
	}

	public static void mouseOver(String TestObject) throws IOException {
		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);
		String[] sTemp = splitTestObject(TestObject);

		try {
			WebElement we = checkIdentifier(sTemp[0], sTemp[1], sTemp[2],
					sTemp[3], methodName);
			if (highlightObj.equalsIgnoreCase("on")) {
				/*
				 * String we1 = we.toString();
				 * 
				 * we1 = (we1.substring(we1.indexOf(">") + 1,
				 * we1.lastIndexOf("]"))).trim(); we1 = we1.replace(": ", "=");
				 * String we3 = "\"" + we2 + "\"";
				 * 
				 * selenium.highlight(we1);
				 */
				highlightElement(we);

			}

			DriverActions.moveToElement(
					checkIdentifier(sTemp[0], sTemp[1], sTemp[2], sTemp[3],
							methodName)).build().perform();
			REPORTER
					.LogEvent(TestStatus.PASS, "Mouse Over '" + sTemp[2] + "'",
							"Mouse Over '" + sTemp[2]
									+ "' - Successfull".toUpperCase(), "");

		} catch (Exception e) {

			REPORTER.catchException(e, "Move Over '" + sTemp[2] + "'");
		}
		sTemp = null;
	}

	/*
	 * public void startTransaction() { startTransactionTime =
	 * Calendar.getInstance().getTimeInMillis();
	 * Reporter.LogEvent(TestStatus.PASS, "Transaction Start Time",
	 * "Transaction Start Time Initiated",""); startTransactionStep = stepCount;
	 * System.out.println(startTransactionStep); }
	 * 
	 * public void endTransaction() { endTransactionStep = stepCount;
	 * 
	 * endTransactionTime = Calendar.getInstance().getTimeInMillis(); long
	 * transactionTime = endTransactionTime-startTransactionTime;
	 * 
	 * if (transactionTime>8000) { if
	 * ((endTransactionStep-startTransactionStep==1)) {
	 * Reporter.LogEvent(TestStatus.WARNING, "Transaction End Time",
	 * "Transaction End Time is Initiated. Total transaction time for the step "
	 * + (stepCount)+ " is "+ Reporter.textExecutionTime(transactionTime),"");}
	 * else{Reporter.LogEvent(TestStatus.PASS, "Transaction End Time",
	 * "Transaction End Time is Initiated. Total transaction time from the Step "
	 * +startTransactionStep+ " till the Step "+endTransactionStep+" is "+
	 * Reporter.textExecutionTime(transactionTime),"");}} else { if
	 * ((endTransactionStep-startTransactionStep==1)) {
	 * Reporter.LogEvent(TestStatus.PASS, "Transaction End Time",
	 * "Transaction End Time is Initiated. Total transaction time for the step "
	 * + (stepCount)+ " is "+ Reporter.textExecutionTime(transactionTime),"");}
	 * else{Reporter.LogEvent(TestStatus.PASS, "Transaction End Time",
	 * "Transaction End Time is Initiated. Total transaction time from the Step "
	 * +startTransactionStep+ " till the Step "+endTransactionStep+" is "+
	 * Reporter.textExecutionTime(transactionTime),"");} }
	 * 
	 * 
	 * startTransactionTime = 0; endTransactionTime = 0; transactionTime=0;
	 * startTransactionStep=0; endTransactionStep=0;
	 * 
	 * }
	 */

	public static void highlightElement_1(String TestObject) throws IOException {
		String[] sTemp = splitTestObject(TestObject);
		selenium.highlight(sTemp[1] + "=" + sTemp[2]);
		selenium.highlight(sTemp[1] + "=" + sTemp[2]);
		sTemp = null;
	}

	/*public static void highlightElement(WebElement element) {
		for (int i = 0; i < 2; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String bgcolor = "rgb(255,255,0)";
			js.executeScript("arguments[0].style.backgroundColor = '" + bgcolor
					+ "'", element);
			// js.executeScript("arguments[0].setAttribute(‘style’, arguments[1]);",
			// element, "color: yellow; border: 2px solid yellow;");
			// js.executeScript("arguments[0].setAttribute(‘style’, arguments[1]);",
			// element, "");
		}
	}*/

/*	public static void highlightElement(WebElement element) { 
		for (int i = 0; i < 2; i++) { 
			JavascriptExecutor js = (JavascriptExecutor) driver; 
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", 
					element, "color: yellow; border: 2px solid yellow;"); 
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", 
					element, ""); 
		} 
	}*/
		
/*	public static void highlightElement(WebElement element) {
		for (int i = 0; i < 2; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);",
					element, "color: yellow; border: 2px solid yellow;");
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);",
					element, "");
		}
	}*/
	public static void highlightElement(WebElement element) {
		for (int i = 0; i < 2; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String bgcolor  = "rgb(255,255,0)";
			js.executeScript("arguments[0].style.backgroundColor = '"+bgcolor+"'",  element);
			
		}
	}

	@SuppressWarnings("unused")
	private static void highlight(WebElement element) {
		final int wait = 75;
		String originalStyle = element.getAttribute("style");
		try {
			setAttribute(element, "style",
					"color: yellow; border: 5px solid yellow; background-color: black;");
			Thread.sleep(wait);
			setAttribute(element, "style",
					"color: black; border: 5px solid black; background-color: yellow;");
			Thread.sleep(wait);
			setAttribute(element, "style",
					"color: yellow; border: 5px solid yellow; background-color: black;");
			Thread.sleep(wait);
			setAttribute(element, "style",
					"color: black; border: 5px solid black; background-color: yellow;");
			Thread.sleep(wait);
			setAttribute(element, "style", originalStyle);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String verifyText(String TestObject, String attribute)
			throws IOException {
		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);

		String[] sTemp = splitTestObject(TestObject);
		WebElement we = checkIdentifier(sTemp[0], sTemp[1], sTemp[2], sTemp[3],
				methodName);
		String attValue = null;
		try {

			attValue = we.getAttribute(attribute);
			System.out.println("Text Value is" + attValue);
			REPORTER.LogEvent(TestStatus.PASS, "", attValue, "");

		} catch (Exception e) {

			REPORTER.LogEvent(TestStatus.FAIL, "", "", "");
		}
		return attValue;

	}

	/**
	 * @param TestObject
	 * @param iTimeout
	 * @param action
	 * @throws IOException
	 * @throws InterruptedException
	 * @Example :
	 *          Common_Methods.waitForObject(getTestObject("OL_OrderContactSummary"
	 *          ), 180, "disappear");
	 */
	public static void waitForObject(String TestObject, int iTimeout,
			String action) throws IOException, InterruptedException {
		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);

		String[] sTemp = splitTestObject(TestObject);
		WebElement we = checkIdentifier(sTemp[0], sTemp[1], sTemp[2], sTemp[3],
				methodName);

		if (action.equalsIgnoreCase("appear")) {

			for (int second = 0;; second++) {
				if (second >= iTimeout)
					fail("Timeout - Progress Bar Still Exists After "
							+ iTimeout + " seconds");
				REPORTER.LogEvent(TestStatus.FAIL, "",
						"Timeout Error - Progress Bar Still Exists After "
								+ iTimeout, "");

				try {
					if (we.isDisplayed())
						break;
				} catch (Exception e) {
				}
				Thread.sleep(1000);

			}

		} else if (action.equalsIgnoreCase("disappear")) {

			for (int second = 0;; second++) {
				if (second >= iTimeout)
					fail("Timeout - Progress Bar Still Exists After "
							+ iTimeout + " seconds");
				try {
					if (!we.isDisplayed())
						break;
				} catch (Exception e) {

				}
				Thread.sleep(1000);
			}

		} else {
			REPORTER.LogEvent(TestStatus.FAIL,
					"Wait For Object To - " + action,
					"Check The *Action* Value Passed As Parameter In The Method - "
							+ methodName, "");
		}

	}

	public static void SwitchWindow(String windowTitle) {

		driver.getWindowHandle();
		driver.getWindowHandles().iterator();
		int Count = driver.getWindowHandles().size();
		System.out.println("Number Of Windows - " + Count);
		String[] WindowNames = selenium.getAllWindowTitles();// showFundingAccountPageDialog
		String[] WindowNames1 = selenium.getAllWindowNames();
		System.out.println(WindowNames1[0]);
		for (String winNames : WindowNames) {
			System.out.println("Window names - " + winNames);
		}
		// selenium.waitForPopUp("showFundingAccountPageDialog","10000");
		selenium.selectWindow(windowTitle);
		/*
		 * if (WindowNames[0].equals(windowTitle)) { selenium.selectWindow("");
		 * REPORTER.LogEvent(TestStatus.PASS, "Switch To Window '" +
		 * windowTitle.toUpperCase(), "Switch To Window '" +
		 * windowTitle.toUpperCase() + "' - Successfull".toUpperCase(),"");
		 * }else {
		 * 
		 * REPORTER.LogEvent(TestStatus.FAIL, "Switch To Window '" +
		 * windowTitle.toUpperCase(), "Window '" + windowTitle.toUpperCase() +
		 * "' - does not exist".toUpperCase(),""); }
		 */

	}

	public static void switchToPopup() {

		try {
			sHandleBefore = driver.getWindowHandle();
			for (String Handle : driver.getWindowHandles()) {
				driver.switchTo().window(Handle);
			}
		} catch (Exception e) {
			REPORTER.LogEvent(TestStatus.FAIL, "No PopUp Window Exits '",
					"No PopUp Window Exits '", "");
		}
		REPORTER.LogEvent(TestStatus.PASS,
				"Switch To PopUp Window is Passed '",
				"Switch To PopUp Window is passed'", "");

	}

	public static void switchToNormal() {
		// APP_LOGS.info("Switch to normal window");
		try {
			driver.switchTo().window(sHandleBefore);
		} catch (Exception e) {
			REPORTER.LogEvent(TestStatus.FAIL,
					"Switch to Normal window is Failed '",
					"Switch to Normal window is Failed '", "");
		}
		REPORTER.LogEvent(TestStatus.PASS,
				"Switch to Normal window is Passed '",
				"Switch to Normal window is Passed '", "");

	}

	public static void nListBoxSelect(String listBoxSearchString,
			String ListBoxName) {

		// List all the HTML Tag Names with Select
		List<WebElement> myListboxes = driver
				.findElements(By.tagName("select"));
		// Get The Count Of List Boxes
		int Listboxes = myListboxes.size();
		// System.out.println("Number Of Listboxes is - " + myListboxes.size());

		try {
			for (int i = 0; i < Listboxes; i++) {
				String ListBoxText = myListboxes.get(i).getText().trim();

				if ((!ListBoxText.equals(null) && ListBoxText.length() > 0)) {

					// System.out.println("List Box number - "+ i +
					// " Has has Text - " + ListBoxText);
					String sClassValue = myListboxes.get(i).getAttribute(
							"class");
					if (sClassValue.equalsIgnoreCase("inputFieldMandatory")) {

						// System.out.println("List Box number - "+ i +
						// " is Mandatory");
					}

					int index = ListBoxText.indexOf(listBoxSearchString);
					if (index != -1) {

						// System.out.println("ID Value Is - " +
						// myListboxes.get(i).getAttribute("id"));
						String Obj_ID = myListboxes.get(i).getAttribute("id");
						// System.out.println(listBoxSearchString +
						// " is present in list box number "+i);
						try {
							new Select(driver.findElement(By.id(Obj_ID)))
									.selectByVisibleText(listBoxSearchString);

							// driver.findElement(By.id(Obj_ID)).sendKeys(Keys.TAB);
							REPORTER.LogEvent(TestStatus.PASS, "Select '"
									+ listBoxSearchString + "' from dropdown '"
									+ ListBoxName + "'", "Select '"
									+ listBoxSearchString + "' from dropdown '"
									+ ListBoxName + "'"
									+ "' - Successfull".toUpperCase(), "");
						} catch (Exception e) {
							REPORTER.catchException(e, "Select '"
									+ listBoxSearchString + "' from dropdown '"
									+ Obj_ID + "'");
						}

					}

				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// System.out.println("******************************************************************\n");

	}

	// @SuppressWarnings("unused")
	public static boolean isElementPresent(String objIdentifier, String locator) {
		boolean isPresent = false;
		try {

			if (locator.equalsIgnoreCase("xpath")) {
				if (selenium.isElementPresent("xpath=" + objIdentifier)
						&& selenium.isVisible("xpath=" + objIdentifier))
					isPresent = true;
			}

			if (locator.equalsIgnoreCase("id")) {

				if (selenium.isElementPresent("id=" + objIdentifier)
						&& selenium.isVisible("id=" + objIdentifier))
					isPresent = true;
			}

			if (locator.equalsIgnoreCase("linkText")) {
				if (selenium.isElementPresent("link=" + objIdentifier)
						&& selenium.isVisible("link=" + objIdentifier))
					isPresent = true;

			}

			if (locator.equalsIgnoreCase("name")) {

				if (selenium.isElementPresent("name=" + objIdentifier)
						&& selenium.isVisible("name=" + objIdentifier))
					isPresent = true;
			}
			return isPresent;

		} catch (NoSuchElementException e) {
			return isPresent;
		}
	}

	private static boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			String alertText = driver.switchTo().alert().getText();
			REPORTER.LogEvent(TestStatus.MSG, "Alert - '" + alertText
					+ "' is Displayed", "", "");
			return true;
		} catch (NoAlertPresentException e) {
			REPORTER.LogEvent(TestStatus.MSG, "Alert is NOT Displayed", "", "");
			return false;
		}
	}

	@SuppressWarnings("unused")
	private static String closeAlertAndGetItsText() {
		boolean acceptNextAlert = false;
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}

	private static void setAttribute(WebElement element, String attributeName,
			String value) {
		JavascriptExecutor js = (JavascriptExecutor) COMMON_METHODS.driver;
		js.executeScript(
				"arguments[0].setAttribute(arguments[1], arguments[2])",
				element, attributeName, value);
	}

	public static void VerifyTextPresent(String TestObject, String Text)
			throws IOException {

		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);
		String stemp = null;

		String[] sTemp = splitTestObject(TestObject);

		try {
			WebElement we = checkIdentifier(sTemp[0], sTemp[1], sTemp[2],
					sTemp[3], methodName);
			if (we.isEnabled())
				stemp = we.getText().toString().trim();

			else
				stemp = we.getAttribute("value").trim().toString();

			if (!(stemp == null) && sTemp.length > 0) {
				// System.out.println(Text.trim()+"  "+stemp.trim());
				if (Text.trim().equalsIgnoreCase(stemp.trim())) {
					REPORTER.LogEvent(TestStatus.PASS, "Verify Text '" + Text
							+ "' Present", "Text '" + Text + "' is Displayed",
							"");
				} else
					REPORTER.LogEvent(TestStatus.FAIL, "Expected Value='"
							+ Text, "Actual Value=" + stemp, "");
			}

		} catch (Exception e) {

			REPORTER.catchException(e, "Verify Text'" + Text + "' Present");
		}

		sTemp = null;
		Runtime.getRuntime().gc();
	}

	public static void VerifyTextPresent(String TestObject, String Text,
			String att) throws IOException {

		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);
		String stemp = null;

		String[] sTemp = splitTestObject(TestObject);

		try {
			WebElement we = checkIdentifier(sTemp[0], sTemp[1], sTemp[2],
					sTemp[3], methodName);
			stemp = we.getAttribute(att).trim().toString();
			if (!(stemp == null) && sTemp.length > 0) {
				if (Text.equalsIgnoreCase(stemp)) {
					REPORTER.LogEvent(TestStatus.PASS, "Verify Text '" + Text
							+ "' Present", "Text '" + Text + "' is Displayed",
							"");
				} else
					REPORTER.LogEvent(TestStatus.FAIL, "Verify Text '" + Text
							+ "' Not Present", "Text '" + Text
							+ "' is Not Displayed", "");

			}

		} catch (Exception e) {

			REPORTER.catchException(e, "Verify Text'" + Text + "' Present");
		}

		sTemp = null;
		Runtime.getRuntime().gc();
	}

	/* ***************************************************************************************
	 * PURPOSE: To perform radio button selected or not RETURN VALUE: None
	 * INPUT(s): Driver and Xpath of object AUTHOR: Satya narayana (snunna4)
	 * CREATION DATE: Feb 26 2014
	 * ***********************************************
	 * ***************************************
	 */
	public static boolean VerifyRadioButtonSelected(String TestObject)
			throws IOException {

		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);

		String[] sTemp = splitTestObject(TestObject);

		try {
			WebElement we = checkIdentifier(sTemp[0], sTemp[1], sTemp[2],
					sTemp[3], methodName);

			if (we.isSelected()) {
				REPORTER.LogEvent(TestStatus.PASS, "Select the Radio Button '"
						+ sTemp[2] + "'", "Radio Button '" + sTemp[2]
						+ "' has been selected", "");
				return true;
			} else {
				REPORTER.LogEvent(TestStatus.WARNING,
						"Select the Radio Button '" + sTemp[2] + "'",
						"Radio Button '" + sTemp[2] + "' has not selected", "");
				return false;
			}
		} catch (Exception e) {

			REPORTER
					.catchException(e, "Select Radio Button '" + sTemp[2] + "'");
		}

		sTemp = null;
		Runtime.getRuntime().gc();
		return true;
	}

	/* ***************************************************************************************
	 * PURPOSE: To perform element enable or disable
	 * 
	 * @Param action RETURN VALUE: None INPUT(s): Driver and Xpath of object
	 * AUTHOR: Satya narayana CREATION DATE: Feb 27 2013
	 * ************************
	 * **************************************************************
	 */
	public static boolean isElementEnabledDisabled(String TestObject,
			boolean action) throws IOException {
		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);
		String[] sTemp = COMMON_METHODS.splitTestObject(TestObject);
		boolean bStatus = false;

		try {
			WebElement we = COMMON_METHODS.checkIdentifier(sTemp[0], sTemp[1],
					sTemp[2], sTemp[3], methodName);
			if (we.isEnabled() && action) {

				REPORTER.LogEvent(TestStatus.PASS,
						"Element '" + sTemp[2] + "'", "Element '" + sTemp[2]
								+ "'" + "Verified Enabled".toUpperCase(), "");
				bStatus = true;
			} else if (!we.isEnabled() && !action) {

				REPORTER.LogEvent(TestStatus.PASS,
						"Element '" + sTemp[2] + "'", "Element '" + sTemp[2]
								+ "'" + " Verified Disabled".toUpperCase(), "");
				bStatus = true;
			}

		} catch (Exception e) {

			REPORTER.catchException(e, "Element '" + sTemp[2] + "'");
		}

		sTemp = null;
		// objType = null;
		return bStatus;

	}

	/* ***************************************************************************************
	 * PURPOSE: To drag a slider RETURN VALUE: None INPUT(s): Driver, Xpath,x,y
	 * co-ordinates of object AUTHOR: Deepa Katewad (dkatewad) CREATION DATE:
	 * Feb 27 2014
	 * **************************************************************
	 * ************************
	 */
	public static void DragandDrop(String fromTestObject, int x, int y)
			throws IOException {
		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);

		String[] sTempfrom = splitTestObject(fromTestObject);

		Actions action = new Actions(driver);
		try {
			WebElement fromwe = checkIdentifier(sTempfrom[0], sTempfrom[1],
					sTempfrom[2], sTempfrom[3], methodName);
			action.dragAndDropBy(fromwe, x, y).build().perform();
			System.out.println(fromwe.getText());
			REPORTER.LogEvent(TestStatus.PASS, "Drag Slider", "Drag Slider -"
					+ " Successfull".toUpperCase(), "");

		} catch (Exception e) {
			REPORTER.catchException(e, "Select Slider '" + sTempfrom[2] + "'");
		}

		sTempfrom = null;
		Runtime.getRuntime().gc();
	}

	/* ***************************************************************************************
	 * PURPOSE: To Navigate to a menu RETURN VALUE: None INPUT(s): Driver,
	 * Testobject AUTHOR: Deepa Katewad (dkatewad) CREATION DATE: Mar 19 2014
	 * ***
	 * ***********************************************************************
	 * ************
	 */
	public static void navigateToMenu(String fromTestObject) throws IOException {
		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);

		String[] sTemp = splitTestObject(fromTestObject);

		Actions action = new Actions(driver);
		try {
			WebElement we = checkIdentifier(sTemp[0], sTemp[1], sTemp[2],
					sTemp[3], methodName);
			action.moveToElement(we).clickAndHold().build().perform();
			Thread.sleep(10000);
			REPORTER.LogEvent(TestStatus.PASS, "Mouse Hover on menu "
					+ sTemp[2], " Mouse Hover on menu " + sTemp[2]
					+ " Successfull".toUpperCase(), "");

		} catch (Exception e) {
			REPORTER.catchException(e, "Mouse Hover on menu " + sTemp[2]
					+ "UnSuccessfull");
		}

		sTemp = null;
		Runtime.getRuntime().gc();
	}

	/* ***************************************************************************************
	 * PURPOSE: To drag a slider RETURN VALUE: None INPUT(s): Driver, Xpath,x,y
	 * co-ordinates of object AUTHOR: Deepa Katewad (dkatewad) CREATION DATE:
	 * Feb 27 2014
	 * **************************************************************
	 * ************************ public static void isVisible(String
	 * fromTestObject,int x,int y) throws IOException { String
	 * methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
	 * REPORTER.writeLog(methodName);
	 * 
	 * String[] sTempfrom=splitTestObject(fromTestObject);
	 * 
	 * 
	 * Actions action=new Actions(driver); try { WebElement
	 * fromwe=checkIdentifier(sTempfrom
	 * [0],sTempfrom[1],sTempfrom[2],sTempfrom[3],methodName);
	 * action.dragAndDropBy(fromwe,x,y).build().perform();
	 * System.out.println(fromwe.getText()); REPORTER.LogEvent(TestStatus.PASS,
	 * "Drag Provider radius" ,
	 * "Drag Provider radius -"+" Successfull".toUpperCase(),"");
	 * 
	 * } catch(Exception e) { REPORTER.catchException(e,
	 * "Select Provider radius '" + sTempfrom[2] + "'"); }
	 * 
	 * sTempfrom=null; Runtime.getRuntime().gc(); }
	 */

	/* ***************************************************************************************
	 * PURPOSE: To return a text RETURN VALUE: String AUTHOR: vverimadugu
	 * CREATION DATE: Mar 04 2014
	 * ***********************************************
	 * ***************************************
	 */
	public static String getText(String testObject) throws IOException {

		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);
		String returnText = null;

		String[] sTemp = splitTestObject(testObject);

		try {
			WebElement we = checkIdentifier(sTemp[0], sTemp[1], sTemp[2],
					sTemp[3], methodName);
			returnText = we.getText().toString().trim();

		} catch (Exception e) {

			REPORTER.catchException(e, "Unable to get text with objectlocator "
					+ testObject);
		}

		sTemp = null;
		Runtime.getRuntime().gc();

		return returnText;
	}

	/* ***************************************************************************************
	 * PURPOSE: To return a text RETURN VALUE: String AUTHOR: vverimadugu
	 * CREATION DATE: Mar 04 2014
	 * ***********************************************
	 * ***************************************
	 */
	public static String getText(String testObject, String att)
			throws IOException {

		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);
		String returnText = null;

		String[] sTemp = splitTestObject(testObject);

		try {
			WebElement we = checkIdentifier(sTemp[0], sTemp[1], sTemp[2],
					sTemp[3], methodName);
			returnText = we.getAttribute(att).trim().toString();

		} catch (Exception e) {

			REPORTER.catchException(e, "Unable to get text with objectlocator "
					+ testObject);
		}

		sTemp = null;
		Runtime.getRuntime().gc();

		return returnText;
	}

	/* ***************************************************************************************
	 * PURPOSE: To return a text that is selected in the list box RETURN VALUE:
	 * String AUTHOR: vverimadugu CREATION DATE: Mar 05 2014
	 * ********************
	 * ******************************************************************
	 */
	public static String getSelectedValueFromListBox(String testObject)
			throws IOException {

		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);
		// String returnText = null;
		// sTemp = null;
		String actualVal = null;
		String[] sTemp = splitTestObject(testObject);
		// Select select = new
		// Select(checkIdentifier(sTemp[0],sTemp[1],sTemp[2],sTemp[3],methodName));

		try {

			WebElement droplist = checkIdentifier(sTemp[0], sTemp[1], sTemp[2],
					sTemp[3], methodName);
			List<WebElement> droplist_contents = droplist.findElements(By
					.tagName("option"));

			for (int i = 0; i < droplist_contents.size(); i++) {
				String selected_status = droplist_contents.get(i).getAttribute(
						"selected");
				if (selected_status != null) {
					actualVal = droplist_contents.get(i).getText();
					System.out.println("List box selected " + actualVal);
				}

			}

		} catch (Exception e) {

			REPORTER.catchException(e, "Unable to get text with objectlocator "
					+ testObject);
		}
		sTemp = null;
		Runtime.getRuntime().gc();

		return actualVal;

	}

	/* ***************************************************************************************
	 * PURPOSE: To check whether an option is available in the given list box
	 * RETURN VALUE: True if the option is available, False otherwise AUTHOR:
	 * Krishna Chaitanya Maringanti CREATION DATE: Mar 06 2014
	 * ******************
	 * ********************************************************************
	 */
	public static Boolean isOptionPresentInListBox(String testObject,
			String strOption) throws IOException {

		// Get the method name
		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);

		// Split and get the test object
		String[] sTemp = splitTestObject(testObject);

		Boolean bOptionPresent = false;

		try {

			// Get all options present in the drop-down
			WebElement droplist = checkIdentifier(sTemp[0], sTemp[1], sTemp[2],
					sTemp[3], methodName);
			List<WebElement> droplist_contents = droplist.findElements(By
					.tagName("option"));

			// Loop through all the options and compare it with the option
			// expected
			for (int i = 0; i < droplist_contents.size(); i++) {
				String sListBoxItemText = droplist_contents.get(i).getText();

				if (sListBoxItemText.contains(strOption)) {
					bOptionPresent = true;
					break;
				}
			}

			// Report whether the option found or not
			if (bOptionPresent) {
				REPORTER.LogEvent(TestStatus.PASS,
						"Drop down box has the option: " + strOption,
						"Drop down box has the option: " + strOption, "");
			} else {
				REPORTER.LogEvent(TestStatus.FAIL,
						"Drop down box does not have the option: " + strOption,
						"Drop down box does not have the option: " + strOption,
						"");
			}

		} catch (Exception e) {

			REPORTER.catchException(e, "Unable to get text with objectlocator "
					+ testObject);
		}

		// Cleanup
		sTemp = null;
		Runtime.getRuntime().gc();

		return bOptionPresent;

	}

	/*private boolean ClickElement(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}*/

	/* ***************************************************************************************
	 * PURPOSE: Switch to Iframe Window RETURN VALUE: void AUTHOR: vverimadugu
	 * 
	 * @param String testObject CREATION DATE: Mar 07 2014
	 * **********************
	 * ****************************************************************
	 */
	public static void switchToIframe(String testObject) throws IOException {

		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);
		String[] sTemp = splitTestObject(testObject);

		try {
			WebElement iframeLocator = checkIdentifier(sTemp[0], sTemp[1],
					sTemp[2], sTemp[3], methodName);
			driver.switchTo().frame(iframeLocator);
		} catch (Exception e) {

			REPORTER.catchException(e, "Unable to locate IFrame window "
					+ testObject);
		}
		sTemp = null;
		Runtime.getRuntime().gc();

	}

	/* ***************************************************************************************
	 * PURPOSE: Switch to Default Window from iFrame Window RETURN VALUE: void
	 * AUTHOR: vverimadugu CREATION DATE: Mar 07 2014
	 * ***************************
	 * ***********************************************************
	 */
	public static void switchToDefaultFromIframe() throws IOException {

		try {

			driver.switchTo().defaultContent();
		} catch (Exception e) {

			REPORTER
					.catchException(e,
							"Unable to come back to default window from iFrame window ");
		}

	}

	/* ***************************************************************************************
	 * PURPOSE: Writing log to Report after each test method is succefully
	 * executed RETURN VALUE: void AUTHOR: vverimadugu CREATION DATE: Mar 11
	 * 2014
	 * *********************************************************************
	 * *****************
	 */
	public static void logToReportAfterPass(String methodName)
			throws IOException {

		try {

			REPORTER.writeToNotepad("INFO", "", "",
					"-------->[ Exiting Script Execution as " + methodName
							+ " test passed".toUpperCase() + " ]<--------");
			REPORTER.writeToHTML("INFO", "", "",
					"-------->[ Exiting Script Execution as " + methodName
							+ " test passed".toUpperCase() + " ]<--------");
		} catch (Exception e) {

			REPORTER.catchException(e, "Unable to write log reports ");
		}

	}

	/**
	 * distance slider moving
	 * 
	 * @param startTime
	 */
	public static void distanceSlider() throws IOException {

		try {

			WebElement sliderdistance = COMMON_METHODS.driver.findElement(By
					.xpath("//div[@id='radiusslider']"));

			Actions movedistance = new Actions(COMMON_METHODS.driver);

			movedistance.moveToElement(sliderdistance).click(sliderdistance)
					.sendKeys(Keys.ARROW_LEFT).perform();

		} catch (Exception e) {

			REPORTER.catchException(e, "Distance slider moving");
		}

	}

	/* ***************************************************************************************
	 * PURPOSE: To get all options in given list box RETURN VALUE: True if the
	 * option is available, False otherwise AUTHOR: Deepa Katewad CREATION DATE:
	 * APR 03 2014
	 * **************************************************************
	 * ************************
	 */
	public static void getAllOptions(String testObject) throws IOException {

		// Get the method name
		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);

		// Split and get the test object
		String[] sTemp = splitTestObject(testObject);

		try {

			// Get all options present in the drop-down
			WebElement droplist = checkIdentifier(sTemp[0], sTemp[1], sTemp[2],
					sTemp[3], methodName);
			List<WebElement> droplist_contents = droplist.findElements(By
					.tagName("option"));
			// Loop through all the options
			for (int i = 0; i < droplist_contents.size(); i++) {
				String sListBoxItemText = droplist_contents.get(i).getText();
				REPORTER.LogEvent(TestStatus.PASS, "Get All options", "Option "
						+ sListBoxItemText + " is available", "");
			}
		} catch (Exception e) {

			REPORTER.catchException(e, "Unable to get text with objectlocator "
					+ testObject);
		}
	}

	/* ***************************************************************************************
	 * PURPOSE: To perform element is selected or not
	 * 
	 * @Param RETURN VALUE: True or False AUTHOR: Lava Kumar CREATION DATE: Apr
	 * 12 2014
	 * ******************************************************************
	 * ********************
	 */
	public static boolean isSelected(String TestObject) throws IOException {
		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);
		String[] sTemp = COMMON_METHODS.splitTestObject(TestObject);
		boolean bStatus = false;

		try {
			WebElement we = COMMON_METHODS.checkIdentifier(sTemp[0], sTemp[1],
					sTemp[2], sTemp[3], methodName);
			if (we.isSelected()) {

				REPORTER.LogEvent(TestStatus.PASS,
						"Element '" + sTemp[2] + "'", "Element '" + sTemp[2]
								+ "'" + " is Enabled".toUpperCase(), "");
				bStatus = true;
			} else if (!we.isSelected()) {

				REPORTER.LogEvent(TestStatus.FAIL,
						"Element '" + sTemp[2] + "'", "Element '" + sTemp[2]
								+ "'" + " is Disabled".toUpperCase(), "");
				bStatus = false;
			}

		} catch (Exception e) {

			REPORTER.catchException(e, "Element '" + sTemp[2] + "'");
		}

		sTemp = null;
		// objType = null;
		return bStatus;

	}

	/* ***************************************************************************************
	 * PURPOSE: To perform element present or not RETURN VALUE: None INPUT(s):
	 * Driver and Xpath of object AUTHOR: Satya narayana CREATION DATE: Feb 27
	 * 2013
	 * *********************************************************************
	 * *****************
	 */
	public static boolean verifyElementDisplayed(String TestObject) throws IOException {
		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);

		String[] sTemp = COMMON_METHODS.splitTestObject(TestObject);
		WebElement we = COMMON_METHODS.checkIdentifier(sTemp[0], sTemp[1],
				sTemp[2], sTemp[3], methodName);

		try {

			if (we.isDisplayed()) {

				REPORTER.LogEvent(TestStatus.PASS, "Element '"/* + objType + */
						+ sTemp[2] + "'", "Element '" + sTemp[2] + "'"
						+ " displayed Successfull".toUpperCase(), "");
				return true;
			} else {
				REPORTER.LogEvent(TestStatus.FAIL, "Element '"/* + objType + */
						+ sTemp[2] + "'", "Element '" + sTemp[2] + "'"
						+ " not displayed".toUpperCase(), "");
				return false;
			}

		} catch (Exception e) {

			REPORTER.catchException(e, "Element '" + sTemp[2] + "'");
		}

		sTemp = null;/* objType = null; */

		return false;
	}

	/* ***************************************************************************************
	 * PURPOSE: To perform element enable or disable RETURN VALUE: None
	 * INPUT(s): Driver and Xpath of object AUTHOR: Satya narayana CREATION
	 * DATE: Feb 27 2013
	 * ********************************************************
	 * ******************************
	 */
	public static void verifyElementEnabled(String TestObject, boolean action)
			throws IOException {
		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);

		String[] sTemp = COMMON_METHODS.splitTestObject(TestObject);
		WebElement we = COMMON_METHODS.checkIdentifier(sTemp[0], sTemp[1],
				sTemp[2], sTemp[3], methodName);

		try {

			if (we.isEnabled() && action) {

				REPORTER.LogEvent(TestStatus.PASS,
						"Element '" + sTemp[2] + "'", "Element '" + sTemp[2]
								+ "'" + " Enabled Successfull".toUpperCase(),
						"");
			} else if (!we.isEnabled() && !action) {

				REPORTER.LogEvent(TestStatus.PASS,
						"Element '" + sTemp[2] + "'", "Element '" + sTemp[2]
								+ "'" + " disabled Successfull".toUpperCase(),
						"");
			}

		} catch (Exception e) {

			REPORTER.catchException(e, "Element '" + sTemp[2] + "'");
		}

		sTemp = null;/* objType = null; */

	}

	public static void isTextPresent(String text) throws IOException {

		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);

		try {

			boolean b = COMMON_METHODS.driver.getPageSource().contains(text);

			if (b) {

				REPORTER.LogEvent(TestStatus.PASS, "Verify Text '" + text
						+ "' present", "Text '" + text
						+ "' is displayed".toUpperCase(), "");

			}

		} catch (Exception e) {
			REPORTER.catchException(e, "Text '" + text + "'");
		}
	}

	/* ***************************************************************************************
	 * PURPOSE: Verify element is selected or not RETURN VALUE: None INPUT(s):
	 * Driver and Xpath of object AUTHOR: Satya narayana CREATION DATE: Feb 27
	 * 2013
	 * *********************************************************************
	 * *****************
	 */
	public static void verifyElementIsSelected(String TestObject, boolean action)
			throws IOException {
		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		REPORTER.writeLog(methodName);

		String[] sTemp = COMMON_METHODS.splitTestObject(TestObject);
		WebElement we = COMMON_METHODS.checkIdentifier(sTemp[0], sTemp[1],
				sTemp[2], sTemp[3], methodName);

		try {

			if (we.isSelected() && action) {

				REPORTER.LogEvent(TestStatus.PASS, "Element '"/* + objType + */
						+ sTemp[2] + "'", "Element '" + sTemp[2] + "'"
						+ " able to Selectable ".toUpperCase(), "");
			} else if (!we.isSelected() && !action) {

				REPORTER.LogEvent(TestStatus.PASS, "Element '"/* + objType + */
						+ sTemp[2] + "'", "Element '" + sTemp[2] + "'"
						+ " not able to Select".toUpperCase(), "");
			}

		} catch (Exception e) {

			REPORTER.catchException(e, "Element '" + sTemp[2] + "'");
		}

		sTemp = null;/* objType = null; */

	}

	public void BrokenLinks() {

		//List all the HTML Tag Names with A
		List<WebElement> Links = driver.findElements(By.tagName("a"));
		

		//Get The Count Of List Boxes 
		int LinksCount = Links.size();
		//System.out.println("WEBDRIVER - Number Of Links is - " + Links.size());
		REPORTER.LogEvent(TestStatus.MSG, ("Current Page Title is - " + driver.getTitle()).toUpperCase(), "","");
		REPORTER.LogEvent(TestStatus.MSG, ("Number Of Links on Current Page is - " + Links.size()).toUpperCase(), "","");
		
		

		for (int i = 0; i < LinksCount; i++) {


			try {

				//WebElement element = driver.findElement(By.tagName("a"));
				//String contents = (String)((JavascriptExecutor)driver).executeScript("return      arguments[0].innerHTML;", element);

				//String currentLink = "this.browserbot.getUserWindow().document.links[" + i + "]";
				//System.out.println("currentLink is " + currentLink);
				//temp = selenium.getEval(currentLink + ".href");
				//System.out.println("temp is " + temp);

				if (Links.get(i).isDisplayed()) {

					if (Links.get(i).getAttribute("href") == null) {

						
						//System.out.println("******************************");
						//System.out.println("Link - " + i + " is displayed ".toUpperCase());
						System.out.println("Link Text is - " + Links.get(i).getText());
						String sTemp = Links.get(i).getText().trim();
						//System.out.println("Link HREF Value is - " + Links.get(i).getAttribute("href"));
						highlightElement(Links.get(i));
						//REPORTER.LogEvent(TestStatus.FAIL, "Link # " +i+" HREF Value is - " + Links.get(i).getAttribute("href")+ ". Link Text value is - " + sTemp, "Link # " + i + " - is a Broken Link", "");
						REPORTER.LogEvent(TestStatus.FAIL, "Link # " +i+" HREF Value is - " + Links.get(i).getAttribute("href")+ ". Link Text value is - " + sTemp, "Link # " + i + " - is a Broken Link", "");
						//System.out.println("Link Number - " + i + " - is a Broken Link");



					} else {

						String linkHref = Links.get(i).getAttribute("href");
						String URL = Links.get(i).getAttribute("href");

						if (linkHref.contains("javascript")) {

						/*	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
							System.out.println("Link - " + i + " is displayed ".toUpperCase());
							System.out.println("Link contains javascript");
							System.out.println("Link Text is - " + Links.get(i).getText());
							System.out.println("Link HREF Value is - " + Links.get(i).getAttribute("href"));*/
							
							REPORTER.LogEvent(TestStatus.WARNING, "Link # "+i+" - HREF Value is - " + Links.get(i).getAttribute("href") , "Link # " + i + "contains javascript", "");
							//highlightElement(Links.get(i));
							/*System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");*/

						} else {


							/*System.out.println("----------------");
							System.out.println("Link - " + i + " is displayed ".toUpperCase());
							System.out.println("Link Text is - " + Links.get(i).getText());
							System.out.println("Href Value of " + i + " is - " + linkHref);*/

							//highlightElement(Links.get(i));
							int serverReturnCode1 = getResponseCode(URL);
							if (serverReturnCode1 == 200) {
								REPORTER.LogEvent(TestStatus.PASS, "Link # "+i+" - HREF Value is - " + Links.get(i).getAttribute("href"), "Server response code for Link # " + i +  " is - " +serverReturnCode1, "");
							} else {

								REPORTER.LogEvent(TestStatus.WARNING, "Link # "+i+" - HREF Value is - " + Links.get(i).getAttribute("href"), "Server response code for Link # " + i +  " is - " +serverReturnCode1, "");
							}


							//System.out.println("Server response code for - " + serverReturnCode1);


						}
						//String URL = Links.get(i).getAttribute("href");
						//System.out.println("----------------");
						//flash(Links.get(i));
						//highlightElement(Links.get(i));
						//int serverReturnCode = getStatusCode(URL);
						//System.out.println("Server response code for - " + Links.get(i).getAttribute("href") + " is - " + serverReturnCode);
						//System.out.println("----------------");
						//int serverReturnCode1 = getResponseCode(URL);
						//System.out.println("Server response code for - " + Links.get(i).getAttribute("href") + " is - " + serverReturnCode1);
						//System.out.println("----------------");

					}

				} else {
					
					//System.out.println("Link - " + i + " is not displayed/Hidden ".toUpperCase());
					//System.out.println("Link Text is - " + Links.get(i).getText());

					REPORTER.LogEvent(TestStatus.WARNING, "Link # " + i + " - HREF Value is - " + Links.get(i).getAttribute("href"), "is not displayed / Hidden ".toUpperCase(), "");
					
				}



			} catch (Exception e) {
				
				String sTemp1 = e.getMessage();
				String[] sTemp2 = sTemp1.split("WARN");
				sTemp2[0] = new String(sTemp2[0].substring(0,
						(sTemp2[0].length()) - 1));
				System.out.println("Unable to get Link text and Href for link Number - " + i + " - due to - " + sTemp2[0].toUpperCase());
			}

		}


	}
	public void BrokenImages() throws MalformedURLException, IOException {
		//List all Images
		List<WebElement> Images = COMMON_METHODS.driver.findElements(By.tagName("img"));

		//Get The Count Of List Boxes 
		int ImagesCount = Images.size();
		System.out.println("WEBDRIVER - Number Of Images is - " + ImagesCount);
		REPORTER.LogEvent(TestStatus.MSG, ("Current Page Title is - " + driver.getTitle()).toUpperCase(), "","");
		REPORTER.LogEvent(TestStatus.MSG, ("Number Of Images on CURRENT PAGE IS - " + ImagesCount).toUpperCase(), "","");

		for (int i = 0; i < ImagesCount; i++) {

			System.out.println("++++++++++++++++++++++++++++++++++++++++++");
			String currentImage = "this.browserbot.getUserWindow().document.images[" + i + "]";
			System.out.println("currentImage is " + currentImage);
			System.out.println("Source of the Image is - " +Images.get(i).getAttribute("src"));

			if (Images.get(i).getAttribute("src") == null | Images.get(i).getAttribute("src").equals("")) {

				REPORTER.LogEvent(TestStatus.FAIL, "Image # "+i+" SRC is - " + Images.get(i).getAttribute("src"), "Image # " + i + " - is a Broken Image", "");

			} else {
				String imageSRC = Images.get(i).getAttribute("src");
				System.out.println("imageSRC - " + imageSRC);

				try {
					int serverReturnCode1 = getResponseCode(imageSRC);
					if (serverReturnCode1 == 200) {
						REPORTER.LogEvent(TestStatus.PASS, "Image # " + i
								+ " SRC is - "
								+ Images.get(i).getAttribute("src") + "<br>"
								+ "Image Height is - "
								+ Images.get(i).getAttribute("height") + "<br>"
								+ "Image Width is  - "
								+ Images.get(i).getAttribute("width"),
								"Server response code for Image # " + i
										+ " is - " + serverReturnCode1, "");
					} else {
						highlightElement(Images.get(i));
						REPORTER.LogEvent(TestStatus.WARNING, "Image # "+i+" SRC is - " + Images.get(i).getAttribute("src"), "Server response code for Image # " + i +  " is - " +serverReturnCode1, "");
					}

				} catch (Exception e) {
					//e.printStackTrace();
					REPORTER.LogEvent(TestStatus.WARNING, "Image # "+i+" SRC is - " + Images.get(i).getAttribute("src"), "NOT A VALID URL", "");
				}
			}


			System.out.println("Image Height is - " +Images.get(i).getAttribute("height"));
			System.out.println("Image Width is  - " +Images.get(i).getAttribute("width"));
			System.out.println("Image alt is - " +Images.get(i).getAttribute("alt"));
			//highlightElement(Images.get(i));
			System.out.println("++++++++++++++++++++++++++++++++++++++++++");
		}


	}
	
	public int getResponseCode(String urlString) throws MalformedURLException, IOException {
		URL u = new URL(urlString); 
		HttpURLConnection huc =  (HttpURLConnection)  u.openConnection(); 
		huc.setRequestMethod("GET"); 
		huc.connect(); 
				
		return huc.getResponseCode();
		
	}

}