package com.sp.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class EMAIL extends REPORTER {

	public EMAIL(String FileName, String vDateForReports) {
		super(FileName, vDateForReports);
		// TODO Auto-generated constructor stub
	}

	public static String ScriptStatus = null;
	public static String env = null; 
	public static String sName = null; 
	public static String sStatus = null; 
	
	
	public static void SendEmail(String MailFormat) throws Exception {
	
		
		String attachpath;
		String attachpath1;
		
		String host = "";
				
		String from = "";
				

		// Get system properties
		Properties properties = System.getProperties();
		

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		// Create a default MimeMessage object.
		MimeMessage message = new MimeMessage(session);

		
		// Set the RFC 822 "From" header field using the
		// value of the InternetAddress.getLocalAddress method.
		message.setFrom(new InternetAddress(from));

		// Add the given addresses to the specified recipient type.
		//message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setRecipients(Message.RecipientType.TO, "");
				
		

		// Set the "Subject" header field.
		message.setSubject("AUTOMATION TEST MAIL!!");
		
		Multipart multipart = new MimeMultipart();
		//MimeBodyPart textPart = new MimeBodyPart();
	
	    //textPart.setText(text, "utf-8");

	   			
	   	//String html = HTMLMailFormat();
		MimeBodyPart htmlPart = new MimeBodyPart();
	    htmlPart.setContent(MailFormat, "text/html; charset=utf-8");

	    multipart.addBodyPart(htmlPart);


		// Part two is attachment
		//messageBodyPart = new MimeBodyPart();
	    attachpath = REPORTER.printscreen;
		attachpath1 = REPORTER.FinalHTMLName;
		
		
		// attachments
		ArrayList<String> attch = new ArrayList<String>();
		System.out.println("attachpath is - " + attachpath);
		attch.add(attachpath);
		attch.add(attachpath1);
		
	
		addAtachments(attch, multipart);
	

		// Put parts in message
		message.setContent(multipart);

		// Send the message
		try {
			Transport.send(message);
			System.out.println("Sent Message Successfully....");
		} catch (Exception e) {
			System.out.println("Sending Mail Failed : " + e.getMessage().toString());
			e.printStackTrace();
		}
		
		
	}
	
	public static void addAtachments(ArrayList<String> attachments, Multipart multipart) throws MessagingException, AddressException {
		int i;
		// if no attachments, exit;
		if (attachments == null)
			return;

		for (String fileName : attachments) {
			MimeBodyPart attachmentBodyPart = new MimeBodyPart();

			// use a JAF FileDataSource as it does MIME type detection
			DataSource source = new FileDataSource(fileName);
			attachmentBodyPart.setDataHandler(new DataHandler(source));

			// assume that the filename you want to send is same as the
			// actual file name - could alter this to remove the file path
			i = fileName.lastIndexOf("\\");
			// String mailAttachFileName=fileName.substring(i+1,
			// fileName.length());
			attachmentBodyPart.setFileName(fileName.substring(i + 1,
					fileName.length()));

			// add the attachment
			multipart.addBodyPart(attachmentBodyPart);
		}
	}
	
	public static String HTMLMailFormat(String ScriptName, String ENV, String bName, String StartTime, String ScriptEndTime, String ScriptexecTime, int ScripttotalSteps, String finalScriptStatus) throws UnknownHostException {
		ScriptStatus = finalScriptStatus;env = ENV;sName = ScriptName;
		String New_finalScriptStatus = null;
		String osName = (System.getProperty("os.name").toUpperCase()) + " / " + (System.getProperty("os.version").toUpperCase());
		
		
		if(finalScriptStatus.equalsIgnoreCase("pass")){
			sStatus = "PASSED";
			New_finalScriptStatus = "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\" bgcolor=\"#33CC00\"><b>" + finalScriptStatus + "</b></td>";
			
		}else if (finalScriptStatus.equalsIgnoreCase("fail")){
			sStatus = "FAILED";
			New_finalScriptStatus = "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\" bgcolor=\"#FF0000\"><b>" + finalScriptStatus + "</b></td>";
			
		}else if (finalScriptStatus.equalsIgnoreCase("warning")){
			sStatus = "WARNING";
			New_finalScriptStatus = "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\" bgcolor=\"#FFFF00\"><b>" + finalScriptStatus + "</b></td>";
			
		}
		
		
		
		String html = "<!DOCTYPE html><!--\"Multipart/Alternative MIME\" format-->"
				+ "<body>"
				+ "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"750\"><tr><td valign=\"top\">"

				+ "<table border=\"3\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr>"
				+ "<td align=\"center\" bgcolor=\"#E0E0E0\">"
				+ "<h2 style=\"color:#0000FF;font-family:Century Gothic, sans-serif;font-weight:bold;text-decoration:none;text-underline:none;\">Automation Report</h2>"
				+ "</td></tr></table>"

				+ "<table border=\"3\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"font-family:Century Gothic, sans-serif;font-size:16px;\">"
				+ "<tr>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ "Test Script Name"
				+ "</td>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ ScriptName
				+ "</td>"
				+ "<tr>"
				+ "<tr>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ "Test Environment"
				+ "</td>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ ENV
				+ "</td>"
				+ "<tr>"
				+ "<tr>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ "Script Executed By"
				+ "</td>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ (System.getProperty("user.name")).toUpperCase()
				+ "</td>"
				+ "<tr>"
				+ "<tr>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ "Java Version"
				+ "</td>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ System.getProperty("java.version").toUpperCase()
				+ "</td>"
				+ "<tr>"
				+ "<tr>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ "OS Name / Version"
				+ "</td>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ osName
				+ "</td>"
				+ "<tr>"
				+ "<tr>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ "Computer Name"
				+ "</td>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ InetAddress.getLocalHost().getHostName().toString()
				+ "</td>"
				+ "<tr>"
				+ "<tr>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ "Browser Name / Version"
				+ "</td>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ bName
				+ "</td>"
				+ "<tr>"
				+ "<tr>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ "Test Start Time"
				+ "</td>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ StartTime
				+ "</td>"
				+ "<tr>"
				+ "<tr>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ "Test End Time"
				+ "</td>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ ScriptEndTime
				+ "</td>"
				+ "<tr>"
				+ "<tr>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ "Script Execution Time"
				+ "</td>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ ScriptexecTime
				+ "</td>"
				+ "<tr>"
				+ "<tr>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ "Total Number Of Steps"
				+ "</td>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ ScripttotalSteps
				+ "</td>"
				+ "<tr>"
				+ "<tr>"
				+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">"
				+ "Script Execution Status"
				+ "</td>"
				//+ "<td width=\"50%\" align=\"left\" height=\"25\" valign=\"middle\">" + finalScriptStatus + "</td>"
				+ New_finalScriptStatus
				+ "<tr>"
				+ "</table>"

				+ "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr>"
				+ "<td align=\"left\" style=\"font-family:Century Gothic, sans-serif;font-size:14px;\" width=\"70%\">"

				+ "</td>"
				+ "</tr></table>"

				+ "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><td height=\"30\"></td></tr></table>"

				+ "<table border=\"3\" cellpadding=\"5\" cellspacing=\"0\" width=\"100%\">"
				+ "<tr>"
				+ "<td align=\"left\" bgcolor=\"#E0E0E0\" style=\"color:#0000FF;font-family:Century Gothic, sans-serif;font-size:18px;font-weight:bold;\">"
				+ "Additional Information"
				+ "</td></tr>"
				+ "<tr>"
					+ "<td align=\"left\" style=\"font-family:Century Gothic, sans-serif;font-size:15px;font-weight:bold;\">"
						+ "Last Step"
					+ "</td>"
				+ "</tr>"
				+ "<tr>"
					+ "<td align=\"left\" style=\"font-family:Century Gothic, sans-serif;font-size:15px;font-weight:bold;\">"
						+ "Attached is the Result Log and Screen Print"
					+ "</td>"
				+ "</tr>"
				+ "</table>"

				

				+ "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
				+ "<tr>"
				+ "<td height=\"30\" width=\"100%\"></td>"
				+ "<tr>"
				+ "<tr>"
				+ "<td bgcolor=\"#000000\" height=\"2\" width=\"100%\"></td>"
				+ "<tr>"
				+ "</table>"
				+ "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
				+ "<tr>"
				+ "<td align=\"left\" style=\"color:#787878;font-family:Century Gothic, sans-serif;font-size:15px;\" width=\"80%\"><b>Scripted and Executed Using SELENIUM WEBDRIVER and TestNG</b></td>"
				+ "<tr>"
				+ "</table>"

				+ "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
				+ "<tr>"
			
				+ "<td>&nbsp</td>"
			
				+ "<tr>"
				+ "</table>"

				+ "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
				+ "<tr>"
				+ "<td align=\"left\" style=\"color:#787878;font-family:arial;font-size:14px;\" width=\"80%\"><b>Regards,</b></td>"
				+ "<tr>"

				+ "<tr>"
				+ "<td align=\"left\" style=\"color:#787878;font-family:arial;font-size:14px;\" width=\"80%\"><b>Ravi Naidu</b></td>"
				+ "<tr>"
				+ "<tr>"
				+ "<td>&nbsp</td>"
				+ "<tr>"
				+ "<tr>"
				+ "<td align=\"left\" style=\"color:#787878;font-family:arial;font-size:14px;\" width=\"80%\"><b>For any queries, Please contact: rnaidu3@csc.com</b></td>"
				+ "<tr>" + "</table>"

				+ "</body>" + "</html>";

				return html;
	}
	
}