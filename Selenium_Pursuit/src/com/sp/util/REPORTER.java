package com.sp.util;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;

import com.sp.scripts.SetUp_TearDown;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

/**
 * @author aa24358
 *
 */
public class REPORTER extends INITIALIZE{

	// Date Variables                                 
	//public static String vDatetype1 = "MM_dd_yyyy_HH_mm";
	public static String vDatetype2 = "MM/dd/yyyy";
	public static String vDatetype3 = "dd";
	public static String vDatetype4 = "HH:mm";
	public static String vDatetype5 = "HH:mm:ss";
	public static String vDatetype6 = "MM_dd_yyyy_HH_mm_a";
	public static String vDatetype7 = "E_dd_MMM_yyyy_HH_mm_a_z";
	public static String vDatetype8 = "E_MMM_dd_yyyy_HH_mm_ss_a_z";
	public static String vDatetype9 = "MM/dd/yyyy" + " " + vDatetype5;
	public static String vDatetype10 = "hh:mm:ss a";
	
	// Notepad, HTML and Screen Shot Variables     
	public static String NotePadResultFile;
	public static String NotePadResultFile1;
	public static String NotepadStepsFile;
	public static String NotepadLogFile;
	public static String outputExcelFile;
	public static String HTMLResultFile;
	public static String ScreenShotFile;
	public static String printscreen;
	public static String td_new;
	
	//Counter For Reporting Step Count
	public static int stepCount = 1;
	protected static int LogCount = 1;
	public static int totalstepsgrand;

	//Time Reporting Variables
	public static long startTime;
	public static long endTime;
	public static long totalTime;
	public static String ScriptStartTime = Calendar.getInstance().getTime().toString();
	
	
	public static String ScriptEndTime;
	public static String ScriptexecTime;
	public static int ScripttotalSteps;
	public static String FinalNotePadName;
	public static String FinalHTMLName;
	
	public static String computername;
	
	//Get the class name and store it in a variable
	public String className = this.getClass().getSimpleName().toString().toUpperCase();
	public static String GoogleURL = "https://www.google.com/";

	/**
	 * @param FileName
	 * @param vDateForReports
	 */
	public REPORTER(String FileName, String vDateForReports) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		//System.out.println("Inside - " + methodName);

		// Create NotePad and HTML Report Template
		try {
			createHTMLResultTemplate(FileName, vDateForReports);
			createNotepadResultTemplate(FileName, vDateForReports);
		} catch (Exception e) {

			System.out.println("ERROR OCCURED IN CLASS - " + className);
			System.out.println("ERROR OCCURED IN METHOD - " + methodName);
			System.out.println(methodName + " CONSTRUTOR Error Caused By - "
					+ e.getMessage());

		}

		//System.out.println("EXITING - " + methodName);
	}

	/**
	 * @param fileName
	 * @param vGetDateForHTML
	 * @throws IOException
	 */
	public void createHTMLResultTemplate(String fileName, String vGetDateForHTML)
			throws IOException {
		@SuppressWarnings("unused")
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		//System.out.println("Inside - " + methodName);
		
		computername=InetAddress.getLocalHost().getHostName();
		

		File folder = new File(System.getProperty("user.dir") + "\\Reports\\");
		if (!folder.exists()) {
			folder.mkdir();
		}

		HTMLResultFile = System.getProperty("user.dir") + "\\Reports\\"
				+ fileName.toUpperCase() + "_" + vGetDateForHTML + ".html";

		File fHTML = new File(HTMLResultFile);
		
		BufferedWriter wHTML = new BufferedWriter(new FileWriter(fHTML));
		if (fHTML.exists()) {
			fHTML.delete();
		}
		wHTML.write("<!DOCTYPE html><html><head><style>"
			+ "div{font-family:\"Trebuchet MS\", Arial, Helvetica, sans-serif;width:750px;padding:10px;background-color:#EAF2D3;border:5px solid #98bf21;margin: auto;font-size:20px;text-align:justify;line-height:40%;}"
			+ "#Log {font-family:\"Trebuchet MS\", Arial, Helvetica, sans-serif;width:100%;border-collapse:collapse;}"
			+ "#Log th {font-size:1.1em;border:1px solid #98bf21;font-weight:bold;text-align:center;padding-top:5px;padding-bottom:4px;background-color:#A7C942;color:#ffffff;}"
			+ "</style></head>"
			+ "<body><div align=\"center\">"
			+ "<pre>Test Script Name 	:	" + fileName.toUpperCase() + "</pre>"
			+ "<pre>Test Environment	: 	</pre>"
			+ "<pre>Test Executed By	:	" + (System.getProperty("user.name")).toUpperCase() + "</pre>"
			+ "<pre>Java Version		: 	" + (System.getProperty("java.version")).toUpperCase() + "</pre>"
			+ "<pre>OS Name / Version	: 	" + (System.getProperty("os.name")).toUpperCase() + " / " + (System.getProperty("os.version")).toUpperCase()+ "</pre>"
			+ "<pre>Computer Name		: 	" + computername.toUpperCase() + "</pre>"
			+ "<pre>Browser Name / Version	: 	</pre>"
			+ "<pre>Test Start Time		: 	" + ScriptStartTime + "</pre>"
			+ "<pre>Test End Time		: 	</pre>"
			+ "<pre>Script Execution Time	: 	</pre>"
			+ "<pre>Total Number Of Steps	: 	</pre>"
			+ "<pre>Script Execution Status	: 	</pre>"
			+ "<pre>						: 	</pre>"

			+ "</div><br>"
			
			+ "<table id=\"Log\">"
			+ "<tr>"
			+ "<th width=\"4%\">Step</th>"
			+ "<th width=\"16%\">Execution Date-Time</th>"
			+ "<th width=\"35%\">Step Description</th>"
			+ "<th width=\"38%\">Actual Result</th>"
			+ "<th width=\"7%\">Status</th>"
			+ "</tr>"
			+ "</table>"
			
			+ "</body></html>");
			
		wHTML.close();
		
		//System.out.println("EXITING - " + methodName);
		
	}

	/**
	 * @param fileName
	 * @param vGetDateForNotepad
	 * @throws IOException
	 */
	public void createNotepadResultTemplate(String fileName,
			String vGetDateForNotepad) throws IOException {
		@SuppressWarnings("unused")
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		//System.out.println("Inside - " + methodName);
		fileName = fileName.toUpperCase();
		vGetDateForNotepad = vGetDateForNotepad.toUpperCase();
		
		File folder = new File(System.getProperty("user.dir") + "\\Reports\\");
		if (!folder.exists()) {
			folder.mkdir();
		}

		// Result Log
		NotePadResultFile = System.getProperty("user.dir") + "\\Reports\\"
				+ fileName + "_" + vGetDateForNotepad + ".txt";
		NotePadResultFile1 = fileName + "_" + vGetDateForNotepad;
		//System.out.println("vGetDateForNotepad value is " + vGetDateForNotepad);
		// Steps To Reproduce File
		NotepadStepsFile = System.getProperty("user.dir") + "\\TestCases\\"
				+ fileName + ".txt";

		// Log File
		NotepadLogFile = System.getProperty("user.dir") + "\\Reports\\"
				+ fileName + "_" + "ExecutionLOG".toUpperCase()
				+ "_" + vGetDateForNotepad + ".txt";

		// Result File, Steps File, Log File
		File RF = new File(NotePadResultFile);
		File SF = new File(NotepadStepsFile);
		File LF = new File(NotepadLogFile);

		BufferedWriter wRF = new BufferedWriter(new FileWriter(RF));
		BufferedWriter wSF = new BufferedWriter(new FileWriter(SF));
		BufferedWriter wLF = new BufferedWriter(new FileWriter(LF));

		if (RF.exists()) {
			RF.delete();
		}

		if (SF.exists()) {
			SF.delete();
		}
		if (LF.exists()) {
			LF.delete();
		}

		RF.createNewFile();
		SF.createNewFile();
		LF.createNewFile();

		// Write Into Steps File
		wSF.write("************************** - " + fileName + " - "
				+ "STEPS TO REPRODUCE DEFECT - **************************");
		wSF.newLine();
		wSF.newLine();

		// Write Into Log File
		wLF.write("************************** - " + fileName + " - "
				+ "EXECUTION LOG - **************************");
		wLF.newLine();
		wLF.newLine();

		//computername=InetAddress.getLocalHost().getHostName();
		//ScriptStartTime = Calendar.getInstance().getTime().toString();
		
		// Write Into ResulS File
		wRF.write("\t\t\t"
				+ "*******************************************************");
		wRF.newLine();
		wRF.write("\t\t\t" + "Test Script Name" + "\t" + ": " + fileName.toUpperCase());
		wRF.newLine();
		wRF.write("\t\t\t" + "Test Environment" + "\t" + ": ");
		wRF.newLine();
		wRF.write("\t\t\t" + "Test Executed By" + "\t" + ": "
				+ (System.getProperty("user.name")).toUpperCase());
		wRF.newLine();
		wRF.write("\t\t\t" + "Java Version" + "\t\t" + ": "
				+ (System.getProperty("java.version")).toUpperCase());
		wRF.newLine();
		wRF.write("\t\t\t" + "OS Name / Version" + "\t" + ": "
				+ (System.getProperty("os.name")).toUpperCase() + " / " +(System.getProperty("os.version")).toUpperCase());
		wRF.newLine();
		wRF.write("\t\t\t" + "Computer Name" + "\t\t" + ": "
				+ computername.toUpperCase());
		wRF.newLine();
		wRF.write("\t\t\t" + "Browser Name / Version" + "\t" + ": ");
		wRF.newLine();
		wRF.write("\t\t\t" + "Test Start Time" + "\t\t" + ": "
				+ ScriptStartTime);
		wRF.newLine();
		wRF.write("\t\t\t" + "Test End Time: ");
		wRF.newLine();
		wRF.write("\t\t\t" + "Script Execution Time: ");
		wRF.newLine();
		wRF.write("\t\t\t" + "Total Number Of Steps: ");
		wRF.newLine();
		wRF.write("\t\t\t" + "Script Execution Status: ");
		wRF.newLine();
		wRF.write("\t\t\t" + "  						: ");
		wRF.newLine();
		wRF.write("\t\t\t"
				+ "*******************************************************");
		wRF.newLine();
		wRF.newLine();

		// Close All The Three NotePad Files
		wRF.close();
		wSF.close();
		wLF.close();	

		//System.out.println("EXITING - " + methodName);
	}

		/**
	 * @param value
	 * @param value1
	 * @param Result
	 * @throws IOException
	 */
	public static void updateReports(updateValue value, String value1, String Result) throws IOException {
		@SuppressWarnings("unused")
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		//System.out.println("Inside - " + methodName);

		String newNotepadText = null;
		String newHTMLtext = null;
		String old_NotepadValue = null;
		String new_NotepadValue = null;
		String old_HTMLValue = null;
		String new_HTMLValue = null;

		File NotePadFile = new File(NotePadResultFile);
		File HTMLFile = new File(HTMLResultFile);

		BufferedReader NotePadFilereader = new BufferedReader(new FileReader(NotePadFile));
		BufferedReader HTMLFilereader = new BufferedReader(new FileReader(HTMLFile));

		String line = "", oldtext = "";
		while ((line = NotePadFilereader.readLine()) != null) {
			oldtext += line + "\r\n";
			// System.out.println("Older HTML text is - " + oldtext);
		}
		NotePadFilereader.close();

		String line1 = "", oldtext1 = "";
		while ((line1 = HTMLFilereader.readLine()) != null) {
			oldtext1 += line1 + "\r\n";
			// System.out.println("Older HTML text is - " + oldtext1);
			
		}
		HTMLFilereader.close();

		FileWriter NotePadFilewriter = new FileWriter(NotePadResultFile);
		FileWriter HTMLFilewriter = new FileWriter(HTMLResultFile);

		//Switch Case Starts Here
		switch (value) {
		case bName: {

			old_NotepadValue = "\t\t\t" + "Browser Name / Version" + "\t"
					+ ": ";
			new_NotepadValue = "\t\t\t" + "Browser Name / Version" + "\t"
					+ ": " + value1;

			old_HTMLValue = "<pre>Browser Name / Version	: 	</pre>";
			new_HTMLValue = "<pre>Browser Name / Version	: 	" + value1 + "</pre>";
			
			break;
		} 
		case Env: {


			old_NotepadValue = "\t\t\t" + "Test Environment" + "\t" + ": ";
			new_NotepadValue = "\t\t\t" + "Test Environment" + "\t" + ": " + value1;


			old_HTMLValue = "<pre>Test Environment	: 	</pre>";
			new_HTMLValue = "<pre>Test Environment	: 	" + value1 + "</pre>";

			break;
		}
		case tEndTime: {
			ScriptEndTime = Calendar.getInstance().getTime().toString();
			old_NotepadValue = "\t\t\t" + "Test End Time: ";
			new_NotepadValue = "\t\t\t" + "Test End Time" + "\t\t" + ": "
					+ ScriptEndTime;

			old_HTMLValue = "<pre>Test End Time		: 	</pre>";
			new_HTMLValue = "<pre>Test End Time		: 	" + ScriptEndTime +"</pre>";

			break;
		}

		case execTime: {
			ScriptexecTime = value1;
			old_NotepadValue = "\t\t\t" + "Script Execution Time: ";
			new_NotepadValue = "\t\t\t" + "Script Execution Time" + "\t" + ": "
					+ value1;

			old_HTMLValue = "<pre>Script Execution Time	: 	</pre>";
			new_HTMLValue = "<pre>Script Execution Time	: 	" + value1 + "</pre>";

			break;
		}
		case totalSteps: {
			ScripttotalSteps = stepCount;
			old_NotepadValue = "\t\t\t" + "Total Number Of Steps: ";
			new_NotepadValue = "\t\t\t" + "Total Number Of Steps	: "
					+ stepCount;

			old_HTMLValue = "<pre>Total Number Of Steps	: 	</pre>";
			new_HTMLValue = "<pre>Total Number Of Steps	: 	" + stepCount + "</pre>";

			break;

		}
		case execStatus: {
			old_NotepadValue = "\t\t\t" + "Script Execution Status: ";
			new_NotepadValue = "\t\t\t" + "Script Execution Status	: " + Result;

			old_HTMLValue = "<pre>Script Execution Status	: 	</pre>";
			new_HTMLValue = null;
			if (Result.equalsIgnoreCase("pass")) {
				new_HTMLValue = "<pre>Script Execution Status	: 	"
						+ "<FONT COLOR=\"#006400\"><b>" + Result.toUpperCase()
						+ "</b></FONT>" + "</pre>";
			} else {
				new_HTMLValue = "<pre>Script Execution Status : 	"
						+ "<FONT COLOR=\"#B22222\"><b>" + Result.toUpperCase()
						+ "</b></FONT>" + "</pre>";

			}

			break;

		}

		case failedStepNo: {

			old_NotepadValue = "\t\t\t" + "  						: ";
			new_NotepadValue = null;

			old_HTMLValue = "<pre>						: 	</pre>";
			new_HTMLValue = null;

			if (Result.equalsIgnoreCase("pass")) {

				new_HTMLValue = "";
				new_NotepadValue = "";

			} else {
				new_HTMLValue = "<pre>Failed at Step Number	:  	"
						+ "<FONT COLOR=\"#B22222\"><b>" + stepCount
						+ "</b></FONT>" + "</pre>";
				new_NotepadValue = "\t\t\t" + "Failed at Step Number	: "
						+ stepCount;

			}

			break;

		}
		case ScreenPrint: {

			boolean append = true;

			FileWriter fout1 = new FileWriter(HTMLResultFile, append);
			BufferedWriter fbw1 = new BufferedWriter(fout1);
			fbw1.write("<!DOCTYPE html><html><head><style>"
					+ "img.one{display: block;border-style:solid;border-color:#FF0000;max-width:90%;margin-left: auto;margin-right: auto}"
					+ "</style></head><body>"
					+ "<font color=\"#FF0000\">"
					+ "<h2>Screen Print</h2>"
					+ "</font>"
					//+ "<img class=\"one\" border=\"10\" align=\"middle\" src=\"C:/Selenium_Pursuit_WD_232/Sanity_Suite/Screen_Prints/ITV1_SMB_Sanity_WED_JUN_26_2013_15_26_13_PM_CDT_PASS.PNG\">"
					+ "<img class=\"one\" border=\"10\" align=\"middle\" src=\"C:/Selenium_Pursuit_WD_232/Sanity_Suite/Screen_Prints/ITV1_SMB_Sanity_WED_JUN_26_2013_15_26_13_PM_CDT_PASS.PNG\">"
					+ "</body></html>");

			fbw1.close();
			
			

			break;
		}

		}

		// To replace a line in a file
		newNotepadText = oldtext.replaceAll(old_NotepadValue,new_NotepadValue);
		newHTMLtext = oldtext1.replaceAll(old_HTMLValue, new_HTMLValue);

		NotePadFilewriter.write(newNotepadText);
		HTMLFilewriter.write(newHTMLtext);

		NotePadFilewriter.close();
		HTMLFilewriter.close();
		newNotepadText = null;
		newHTMLtext = null;
		old_NotepadValue = null;
		new_NotepadValue = null;
		old_HTMLValue = null;
		new_HTMLValue = null;

		//System.out.println("EXITING - " + methodName);

	}

	/**
	 * @param status
	 * @param expected
	 * @param actual
	 * @throws IOException 
	 */
	public static void writeToHTML(String status, String expected, String actual, String addInfo) throws IOException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		//System.out.println("Inside - " + methodName);
		@SuppressWarnings("unused")
		String BorderColor = null;
		boolean append = true;

		FileWriter fout1 = new FileWriter(HTMLResultFile, append);
		BufferedWriter fbw1 = new BufferedWriter(fout1);
		
		//Color Scheme For Reporting
		String gstatus = null;
		String gactual = null;

		try {

			if (status.equalsIgnoreCase("pass")){
				
				gstatus = "<FONT COLOR=\"#006400\"><b>" + status.toUpperCase() + "</b></FONT>";
				gactual = "<FONT COLOR=\"#006400\">"+ actual +"</FONT>";
								
				fbw1.write("<!DOCTYPE html><html><head><style>"

				+ "#Log {font-family:\"Trebuchet MS\", Arial, Helvetica, sans-serif;width:100%;table-layout:fixed;border-collapse:collapse;}"
				+ "#Log td {font-size:1em;border:1px solid #98bf21;padding:3px 7px 2px 7px;word-wrap:break-word;}"
				+ "#Log tr.alt1 td {border:3px solid #B22222;background-color:#EA9A9A;}"
				+ "#Log tr.alt2 td {border:3px solid #FF9900}"
				+ "#Log tr.alt3 td {background-color:#EAF2D3;}"
				+ "</style></head><body>"
				+ "<table id=\"Log\">"
				+ "<tr>"
				+ "<td align=\"center\" width=\"4%\"><b>" + stepCount + "</b></td>"
				+ "<td align=\"center\" width=\"16%\">"+ getDateFormat(vDatetype9) + "</td>"
				+ "<td align=\"left\" width=\"35%\">" + expected + "</td>"
				+ "<td align=\"left\" width=\"38%\">" + gactual + "</td>"
				+ "<td align=\"center\" width=\"7%\">" + gstatus + "</td>"
				+ "</tr>"
				+ "</table></body></html>");
				
			}else if (status.equalsIgnoreCase("fail")){
				
				gstatus = "<FONT COLOR=\"#B22222\"><b>" + status.toUpperCase()	+ "</b></FONT>";
				gactual = "<FONT COLOR=\"#B22222\"><b>"+ actual +"</b></FONT>";
				
				fbw1.write("<!DOCTYPE html><html><body>"
						
				+ "<table id=\"Log\">"
				+ "<tr class=\"alt1\">"
				
				+ "<td align=\"center\" width=\"4%\"><b>" + stepCount + "</b></td>"
				+ "<td align=\"center\" width=\"16%\">"+ getDateFormat(vDatetype9) + "</td>"
				+ "<td align=\"left\" width=\"35%\">" + expected + "</td>"
				+ "<td align=\"left\" width=\"38%\">" + gactual + "</td>"
				+ "<td align=\"center\" width=\"7%\">" + gstatus + "</td>"
				+ "</tr>"
				+ "</table></body></html>");
				
			}else if (status.equalsIgnoreCase("warning")){
				
				gstatus = "<FONT COLOR=\"#FF9900\"><b>" + status.toUpperCase()	+ "</b></FONT>";
				gactual = "<FONT COLOR=\"#FF9900\"><b>"+ actual +"</b></FONT>";

				fbw1.write("<!DOCTYPE html><html><body>"
						
				+ "<table id=\"Log\">"
				+ "<tr class=\"alt2\">"
				
				+ "<td align=\"center\" width=\"4%\"><b>" + stepCount + "</b></td>"
				+ "<td align=\"center\" width=\"16%\">"+ getDateFormat(vDatetype9) + "</td>"
				+ "<td align=\"left\" width=\"35%\">" + expected + "</td>"
				+ "<td align=\"left\" width=\"38%\">" + gactual + "</td>"
				+ "<td align=\"center\" width=\"7%\">" + gstatus + "</td>"
				+ "</tr>"
				+ "</table></body></html>");

			} else if (status.equalsIgnoreCase("msg")) {
				fbw1.write("<!DOCTYPE html><html><body>"
				
				+ "<table id=\"Log\">"
				+ "<tr class=\"alt3\">"
				+ "<td align=\"center\" width=\"4%\"><b></b></td>"
				+ "<td align=\"center\" width=\"16%\">"+ getDateFormat(vDatetype9) + "</td>"
				/*+ "<td width=\"35%\"><b></b></td>"*/
				+ "<td width=\"73%\"><b>" + expected.toUpperCase() + "</b></td>"
				+ "<td align=\"center\" width=\"7%\"></td>"
				+ "</tr>"

				+ "</table></body></html>");

			} else if (status.equalsIgnoreCase("info")) {

				fbw1.write("<!DOCTYPE html><html><body>"
				
				+ "<table id=\"Log\">"
				+ "<tr class=\"alt3\">"
				+ "<td align=\"center\" width=\"4%\"><b></b></td>"
				+ "<td align=\"center\" width=\"16%\">"+ getDateFormat(vDatetype9) + "</td>"
				+ "<td align=\"center\" width=\"73%\"><b>" + addInfo.toUpperCase() + "</b></td>"
				+ "<td align=\"center\" width=\"7%\"></td>"
				+ "</tr>"
				+ "</table></body></html>");
			} 	
			
			fbw1.close();
		}catch (Exception e) {

			System.out.println("ERROR OCCURED IN METHOD - " + methodName);
			System.out.println(methodName + " Method Error Caused By - " + e.getMessage());
		}
	
		//System.out.println("EXITING - " + methodName);
	}

	/**
	 * @param status - Status can me Pass, Fail, Msg
	 * @param expected
	 * @param actual
	 */
	public static void writeToNotepad(String status, String expected, String actual, String addInfo/*Additional Information*/) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		//System.out.println("Inside - " + methodName);
		
		try {

			boolean append = true;
			FileWriter fout = new FileWriter(NotePadResultFile, append);
			FileWriter fout2 = new FileWriter(NotepadStepsFile, append);
			BufferedWriter fbw = new BufferedWriter(fout);
			BufferedWriter fbw2 = new BufferedWriter(fout2);
			
			//WRITE LOG
			fbw2.write(expected);
			fbw2.newLine();
			
			if (status.equalsIgnoreCase("pass") | status.equalsIgnoreCase("fail") | status.equalsIgnoreCase("warning")) {
				
				// CONDITION WHEN THE ACTUAL AND EXPECTED VALUE ARE PASSED
				fbw.write("Step ");
				fbw.write(stepCount + "");
				fbw.write("\t\t");
				// fbw.write("(" + getDateTimeForNotepad() + ")");
				fbw.write(getDateFormat(vDatetype9));
				fbw.write("\t\t");
				fbw.write(status.toUpperCase());
				fbw.newLine();
				fbw.write("------------------------------------------------------------");
				fbw.newLine();
				fbw.write("Description	: " + expected);
				fbw.newLine();
				fbw.write("Actual Result	: " + actual);
				fbw.newLine();
				fbw.newLine();
				fbw.newLine();
				
			} else if (status.equalsIgnoreCase("msg")) {
				
				fbw.write("************************************************************");
				fbw.newLine();
				// fbw.write("Step Description : " + expected);
				fbw.write("Description : " + expected);
				fbw.newLine();
				fbw.newLine();
				fbw.newLine();
				
			} else if (status.equalsIgnoreCase("info")) {
				
				fbw.newLine();
				fbw.write("\t\t" + addInfo);
				fbw.newLine();
				fbw.newLine();
				fbw.newLine();
			}
			
			fbw.close();
			fbw2.close();
	

		} catch (Exception e) {
			
		
			System.out.println("ERROR OCCURED IN METHOD - " + methodName);
			System.out.println(methodName + " Method Error Caused By - " + e.getMessage());
		}
		
		//System.out.println("EXITING - " + methodName);
	}
	/**
	 * @param Status
	 * @param Expected
	 * @param Actual
	 */
	public static void LogEvent(TestStatus Status, String Expected,
			String Actual, String addInfo) {
		@SuppressWarnings("unused")
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		//System.out.println("Inside - " + methodName);

		switch (Status) {
		case PASS: {
			try {
				writeToNotepad("PASS", Expected, Actual, addInfo);
				writeToHTML("PASS", Expected, Actual,"");
								
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			stepCount++;
			break;
		}
		case FAIL: {
			try {
				writeToNotepad("FAIL", Expected, Actual.toUpperCase(), addInfo);
				writeToHTML("FAIL", Expected, Actual.toUpperCase(),"");
				failurepopUp(Expected, Actual);
				
								
				writeToNotepad("INFO", "", "",
						"-------->[ Exiting Script Execution".toUpperCase()
								+ " ]<--------");
				writeToHTML("INFO", "", "",
						"-------->[ Exiting Script Execution".toUpperCase()
								+ " ]<--------");
				
				
				Assert.fail();

			} catch (Exception e) {
				
				e.printStackTrace();
			}
			stepCount++;
			break;
		}
		case WARNING: {
			try {
				writeToNotepad("WARNING", Expected, Actual, addInfo);
				writeToHTML("WARNING", Expected, Actual,"");
				
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			stepCount++;
			break;
		}
		case MSG: {
			try {
				writeToNotepad("MSG", Expected, Actual, addInfo);
				writeToHTML("MSG", Expected, Actual,"");
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			//stepCount++;
			break;
		}
		case INFO: {
			try {
				if (addInfo.equalsIgnoreCase("-------->[ End Of Script Execution ]<--------")) {
					stepCount--;
				}
				writeToNotepad("INFO", Expected, Actual, addInfo);
				writeToHTML("INFO", Expected, Actual, addInfo);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			//stepCount++;
			break;
		}
		
		
		}
		
		//System.out.println("EXITING - " + methodName);
	}

	/**
	 * @param Expected
	 * @param Actual
	 */
	protected static void failurepopUp(String Expected, String Actual) {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("Inside - " + methodName);
		
		JFrame jframe = new JFrame();
		String sTitle = "Automation Pursuit";
		jframe.setTitle(sTitle.toUpperCase());
		
		jframe.setSize(600, 150);

		java.awt.Toolkit kit = jframe.getToolkit();
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		Insets in = kit.getScreenInsets(gs[0].getDefaultConfiguration());

		Dimension d = kit.getScreenSize();
		int max_width = (d.width - in.left - in.right);
		int max_height = (d.height - in.top - in.bottom);

		jframe.setLocation((int) (max_width - jframe.getWidth()) / 2,
				(int) (max_height - jframe.getHeight()) / 2);

		Container pane = jframe.getContentPane();
		pane.setLayout(new GridLayout(4, 1));

		JLabel L0 = new JLabel((" Test Execution Failure Notification").toString().toUpperCase());
		L0.setBackground(Color.YELLOW);

		JLabel L1 = new JLabel(" STEP NO: " + stepCount);
		L1.setForeground(Color.RED);

		JLabel L2 = new JLabel(" Description: " + Expected);
		JLabel L3 = new JLabel(" Actual Result: " + Actual.toUpperCase());
		L3.setForeground(Color.RED);

		pane.add(L0);
		pane.add(L1);
		pane.add(L2);
		pane.add(L3);
		jframe.setVisible(true);
		jframe.setAlwaysOnTop(true);

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}

		jframe.dispose();
		System.out.println("EXITING - " + methodName);
	}

	/**
	 * @param methodName
	 * @throws IOException
	 */
	public static void writeLog(String methodName) throws IOException {
		boolean append = true;
		FileWriter fout = new FileWriter(NotepadLogFile, append);
		BufferedWriter fbw = new BufferedWriter(fout);
		fbw.write("Step ");
		fbw.write(LogCount + "" + "\t");
		fbw.write(getDateFormat(vDatetype9) + "\t");
		fbw.write(methodName);
		fbw.newLine();
		fbw.close();
		LogCount++;
		

	}

	/**
	 * @param Result
	 * @return
	 */
	public String getFinalStatus(int Result) {
		
		String TestStatus = null;
		if (Result == 1) {
			TestStatus = "PASS";
		} else if (Result == 2) {
			TestStatus = "FAIL";
		}

		return TestStatus;
	}

	public static String getDateFormat(String vDateFormat) {
		
		Date vDate = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(vDateFormat);
		return sdf.format(vDate);

	}


	protected static String formatIntoHHMMSS(long milliSeconds) {
		 
 		DateFormat f1 = new SimpleDateFormat("hh:mm:ss a");
		return(f1.format(milliSeconds));
 	}
	protected static String textExecutionTime(long diffSeconds) {
		 
 	    diffSeconds = diffSeconds / 1000;

        long hours = diffSeconds / 3600;
        long remainder = diffSeconds % 3600;
        long minutes = remainder / 60;
        long seconds = remainder % 60;

        return ((hours < 10 ? "0" : "") + hours + ":"
                    + (minutes < 10 ? "0" : "") + minutes + ":"
                    + (seconds < 10 ? "0" : "") + seconds);
 	}	
	

	public static void takeScreenShot(String status) throws Exception {
		
		ScreenShotFile = SetUp_TearDown.scriptName.toUpperCase();
				
		System.out.println("ScreenShotFile name is " + ScreenShotFile);
			
		File folder1 = new File(System.getProperty("user.dir")
				+ "\\Screen_Prints\\");
		if (!folder1.exists()) {
			folder1.mkdir();
		}
		printscreen = System.getProperty("user.dir") + "\\Screen_Prints\\"
				+ ScreenShotFile + "_" + getDateFormat(vDatetype8).toUpperCase() + "_" + status + ".PNG";
		System.out.println("printscreen complete path is - " + printscreen );
		try {
			File TakeScreenPrint = ((TakesScreenshot) COMMON_METHODS.driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(TakeScreenPrint, new File(printscreen));
			//LogEvent(TestStatus.MSG, "", "Screen Print Taken and Saved under *'Screen_Prints'* Folder");
			
		} catch (Exception e) {
			
			LogEvent(TestStatus.FAIL, "Take Screen Print : ", e.getMessage().toString(), "");
		}
	
		ScreenShotFile=null;//printscreen=null;
		
		Runtime.getRuntime().gc();
	}

	
	public static void catchException(Exception e, String Expected) {
		Selenium selenium = new WebDriverBackedSelenium(COMMON_METHODS.driver, GoogleURL);

		// Check If The Page Contains HTTP Status
		if (COMMON_METHODS.driver.getPageSource().contains("HTTP Status")) {
			String[] sTemp = selenium.getBodyText().split("type");
			System.out.println(sTemp[0]);
			try {
				REPORTER.takeScreenShot("FAIL");
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			LogEvent(TestStatus.FAIL, Expected, sTemp[0].trim(), "");
			sTemp = null;
		}
		// Check If The Page Title Contains Internet Explorer cannot display the webpage / Problem loading page
		//FIXED THIS IN LOVE-FIELD AIRPORT, DALLAS, TEXAS - DEC 19 2012 - 5:53 PM LOCAL TIME
		else if (COMMON_METHODS.driver.getTitle().equalsIgnoreCase(
				"Internet Explorer cannot display the webpage")
				| COMMON_METHODS.driver.getTitle().equalsIgnoreCase(
						"Problem loading page")) {
			try {
				REPORTER.takeScreenShot("FAIL");
				LogEvent(TestStatus.FAIL, Expected, "CHECK THE INTERNET CONNECTION", "");
			} catch (Exception e1) {
			
				e1.printStackTrace();
			}
			
	        
	      }

		else {
			String sTemp1 = e.getMessage();
			String[] sTemp2 = sTemp1.split("WARN");
			sTemp2[0] = new String(sTemp2[0].substring(0,
					(sTemp2[0].length()) - 1));
			
			try {
				takeScreenShot("FAIL");
				LogEvent(TestStatus.FAIL, Expected, sTemp2[0], "");
				
			} catch (Exception e1) {

				e1.printStackTrace();
			}

			sTemp1 = null;
		}
		Runtime.getRuntime().gc();
	}

	
	 public static void RenameResultLog(String finalScriptStatus) {
	    	//String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			//System.out.println("Inside - " + methodName);
			
			
			File oldNotepadfile =new File(NotePadResultFile);
			File newNotepadfile =new File(System.getProperty("user.dir") + "\\Reports\\" + NotePadResultFile1.toUpperCase() + "_" + finalScriptStatus +".txt".toUpperCase());
			FinalNotePadName = newNotepadfile.toString();
			
			File oldHTMLfile =new File(HTMLResultFile);
			File newHTMLfile =new File(System.getProperty("user.dir") + "\\Reports\\" + NotePadResultFile1.toUpperCase() + "_"+ finalScriptStatus +".html".toUpperCase());
			FinalHTMLName = newHTMLfile.toString();
	    	try {
				oldNotepadfile.renameTo(newNotepadfile);
				oldHTMLfile.renameTo(newHTMLfile);
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		
			
	    }
	
	
	//*****************************************************End Of File*****************************************************
}
