package HFWebPkg;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import freemarker.template.Configuration;

public class BaseTest {

	 public static ExtentHtmlReporter htmlReporter;
	 public static ExtentReports extent;
	 public static ExtentTest test;
	 
	 @BeforeSuite
	    public void setUp()
	    {
		    System.out.println("Basetest setup starting");
		    htmlReporter = new ExtentHtmlReporter("./test-output/HF_WebAutiomation_Report.html");
	        extent = new ExtentReports();
	        extent.attachReporter(htmlReporter);
	         
	        extent.setSystemInfo("OS", "Windows 10");
	        extent.setSystemInfo("Host Name", "AG");
	        extent.setSystemInfo("Environment", "Practice");
	        extent.setSystemInfo("User Name", "Ankit Gupta");
	         
	        htmlReporter.config().setDocumentTitle("HF_AutomationTesting_Demo_Report");
	        htmlReporter.config().setReportName("Task1_Web_Report");
	        htmlReporter.config().setTheme(Theme.DARK);
	    }
	 
	    @AfterSuite
	    public void tearDown()
	    {
	    	System.out.println("Basetest teardown starting");
	    	extent.flush();
	    }

}
