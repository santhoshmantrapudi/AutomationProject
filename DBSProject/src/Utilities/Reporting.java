package Utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import ReusableComponents.PublicVariables;
import junit.framework.Assert;

public class Reporting {
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ExtentHtmlReporter htmlReporter;
	static String strDate = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	static Date date = new Date();
	public static String filePath = System.getProperty("user.dir") + "\\ExtentReports\\Extentreport_+" + strDate
			+ ".html";
	public static Logger log;
	public static PublicVariables pv;
	public static String strTimeStmp;

	public static ExtentReports GetExtent() {
		if (extent != null)
			return extent;
		extent = new ExtentReports();
		extent.attachReporter(getHtmlReporter());
		return extent;
	}

	private static ExtentHtmlReporter getHtmlReporter() {
		htmlReporter = new ExtentHtmlReporter(Reporting.filePath);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName(System.getProperty("user.name"));
		return htmlReporter;
	}

	public static ExtentTest createTest(String name, String description) {
		test = extent.createTest(name, description);
		return test;
	}

	public static void startreporting() {
		extent = Reporting.GetExtent();
	}

	public static void Endreporting() {
		extent.flush();
		extent = null;
		htmlReporter = null;
	}

	public Reporting(String plogname) {
		String stDate = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		PublicVariables.strExeDateTimeStamp = stDate;
		String strLogname = plogname + "_" + PublicVariables.strExeDateTimeStamp;
		System.setProperty("htmlpath",
				System.getProperty("user.dir") + "\\log\\LogFiles\\" + strLogname + "_application.html");
		System.setProperty("logpath",
				System.getProperty("user.dir") + "\\log\\LogFiles\\" + strLogname + "_Logfile.log");
		System.setProperty("logpath", System.getProperty("user.dir") + "\\log\\testlog1.log");
		log = Logger.getLogger(strLogname);
		PropertyConfigurator.configure("./log/log4j.properties");
	}

	public static void StartLogging(String plogname) {
		Reporting.createTest(plogname, "Verify functionality as defined in the: " + PublicVariables.gTestname);
		String stDate = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		strTimeStmp = stDate;
		System.setProperty("htmlpath", System.getProperty("user.dir") + "\\TestResults\\LogFiles\\"
				+ PublicVariables.gTestname + "\\" + strTimeStmp + "\\" + plogname + "_application.html");
		System.setProperty("logpath", System.getProperty("user.dir") + "\\TestResults\\LogFiles\\"
				+ PublicVariables.gTestname + "\\" + strTimeStmp + "\\" + plogname + "_Logfile.log");
		System.setProperty("log1path", System.getProperty("user.dir") + "\\log\\testlog1.log");
		log = Logger.getLogger(plogname);
		//PropertyConfigurator.configure("./log/Log4j.properties");
	}

	public static String GetScreenshots(WebDriver dr) throws IOException {
		String stDate = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) dr;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String Screenshotpath = System.getProperty("user.dir") + "\\TestResults\\Screenshots\\"
				+ PublicVariables.gTestname + "\\" + strTimeStmp + "\\" + PublicVariables.gTestname + "_" + stDate
				+ ".png";
		File Desti = new File(Screenshotpath);
		FileUtils.copyFile(source, Desti);
		return Screenshotpath;
	}

	public static void WritetoReport(String gStatus, String Desc) {
		switch (gStatus.toUpperCase()) {
		case "PASS":
			try {
				if (PublicVariables.gStepwisescreenshots) {
					test.log(Status.PASS, Desc + "; for ScreenShot path:" + "<a href="
							+ GetScreenshots(PublicVariables.dr) + ">Click Here</a>");
				} else {
					test.pass(Desc);
				}
				log.info(Desc);
				Assert.assertTrue(Desc, true);
			} catch (Exception ex) {
				test.log(Status.PASS, Desc);
			}
			break;
		case "FAIL":
			try {
				test.log(Status.FAIL, Desc + "; for ScreenShot path:" + "<a href=" + GetScreenshots(PublicVariables.dr)
						+ ">Click Here</a>");
				log.info(Desc);
				//Assert.assertTrue(Desc, true);
			} catch (Exception ex) {
				test.log(Status.FAIL, Desc);
			}
			break;
		case "Error":
			try {
				test.log(Status.ERROR, Desc + "; for ScreenShot path:" + "<a href=" + GetScreenshots(PublicVariables.dr)
						+ ">Click Here</a>");
				log.info(Desc);
				Assert.assertTrue(Desc, false);
			} catch (Exception ex) {
				test.log(Status.ERROR, Desc);
				Assert.assertTrue(Desc, false);
				log.info(Desc);
			}
			break;
		case "Log":
			log.info(Desc);
			break;
		}
	}
}
