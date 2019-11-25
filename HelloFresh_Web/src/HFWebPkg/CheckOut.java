package HFWebPkg;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Date;

//-------
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import au.com.bytecode.opencsv.CSVReader;
import org.testng.IReporter;

public class CheckOut extends BaseTest{
	/*
	 public ExtentHtmlReporter htmlReporter;
	 public ExtentReports extent;
	 public ExtentTest test;
*/

   String existingUserEmail = "hf_challenge_123456@hf123456.com";
   String existingUserPassword = "12345678";

   //String CSV_PATH="./DataFiles/SignUpTestData.csv";
	//public String baseUrl = "http://automationpractice.com/index.php";
   //String driverPath = "./Webdrivers/chromedriver.exe";
   public WebDriver driver ;
   WebDriverWait wait;

   /*
   @BeforeTest
   public void setExtent() {
    // specify location of the report
    htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/HF_WebAutomationReport.html");
    htmlReporter.config().setDocumentTitle("WebAuto_Task1_Report"); // Title of report
    htmlReporter.config().setReportName("Functional Automated Testing_Web"); // Name of the report
    htmlReporter.config().setTheme(Theme.DARK);
    
    extent = new ExtentReports();
    extent.attachReporter(htmlReporter);
    
    // Passing General information
    extent.setSystemInfo("Host name", "AG_localhost");
    extent.setSystemInfo("Environemnt", "Practice_Env");
    extent.setSystemInfo("user", "AG");
   }
   
   @AfterTest
   public void endReport() {
    extent.flush();
   }
   
*/    
	@BeforeMethod
	@Parameters({"browser","url"})
	public void Setup(String browser, String url)  throws Exception{
		//--
		String baseUrl = url;
		//Check if parameter passed from TestNG is 'firefox'
				if(browser.equalsIgnoreCase("firefox")){
				//create firefox instance
					//String driverPath = "./Webdrivers/geckodriver.exe";
					System.setProperty("webdriver.firefox.marionette", "./Webdrivers/geckodriver.exe");
					driver = new FirefoxDriver();
					Reporter.log("FireFox is launched now", true);
				}
				//Check if parameter passed as 'chrome'
				else if(browser.equalsIgnoreCase("chrome")){
					//set path to chromedriver.exe
					String driverPath = "./Webdrivers/chromedriver.exe";
					//create chrome instance
					driver = new ChromeDriver();
					Reporter.log("Chrome is launched now", true);
				}

				else{
					//If no browser passed throw exception
					throw new Exception("Browser is not correct");
				}
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				
		//--
		System.out.println("Before CheckOut Test execution:Launching the browser");
		//driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 10, 50);
		driver.manage().window().maximize();
		Reporter.log("Browser is maximized", true);
		driver.get(baseUrl);
		Reporter.log("Navigate to application url", true);
	}
	
	
	@AfterMethod
	public void CaptureErrors(ITestResult result) throws IOException {
			  if (result.getStatus() == ITestResult.FAILURE) {
			   test.log(Status.FAIL, "FAILED TEST CASE IS " + result.getName()); // to add name in extent report
			   test.log(Status.FAIL, "FAILED TEST CASE IS " + result.getThrowable()); // to add error/exception in extent report
			   String screenshotPath = CheckOut.getScreenshot(driver, result.getName());
			   test.addScreenCaptureFromPath(screenshotPath);// adding screen shot
			   System.out.println("Login_Screenshot Taken");
			  }
              else if (result.getStatus() == ITestResult.SKIP) {
		       test.log(Status.SKIP, "SKIPPED TEST CASE IS " + result.getName());
		      }
		      else if (result.getStatus() == ITestResult.SUCCESS) {
		      test.log(Status.PASS, "PASSED TEST CASE IS " + result.getName());
			   try
	        	{
	        	
	    		// Logout if no failures
	        	
	        	boolean SignoutVisible = driver.findElement(By.className("logout")).isDisplayed();
	    		boolean SignoutEnabled = driver.findElement(By.className("logout")).isEnabled();
	            if (SignoutVisible==true && SignoutEnabled==true)
	            {
	            	   System.out.println("Logged in session identified:Logging out");
	                   driver.findElement(By.className("logout")).click();// For logging out
	                   Reporter.log("Logging out the CheckOut Test Case session", true);
	            }
	            
	        	}
	            catch (Exception m)
	            {
	            	System.out.println("Session already logged out");
	            }
			   
			  }
			  driver.quit();//For closing the browser
			  System.out.println("After Test execution:Closing the browser");
			 }
			 
			 public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
			  String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			  TakesScreenshot ts = (TakesScreenshot) driver;
			  File source = ts.getScreenshotAs(OutputType.FILE);
			  
			  // after execution, you could see a folder "FailedTestsScreenshots" under src folder
			  String destination = "./Screenshots/" + screenshotName + dateName + ".png";
			  File finalDestination = new File(destination);
			  FileUtils.copyFile(source, finalDestination);
			  return destination;
			 }
		//------


	
	@Test(enabled=true)
	public void checkOut() throws Exception
	{
		test = extent.createTest("checkOut");
		
		System.out.println("checkOut method running with ThreadID:" + Thread.currentThread().getId());
			//----
		  String CSV_PATH3="./DataFiles/DressDetailsData.csv";
		  CSVReader reader = new CSVReader(new FileReader(CSV_PATH3));
		  reader.readNext();
		  String [] csv3Cell;
		  //while loop will be executed till the last line In CSV.
		  while ((csv3Cell = reader.readNext()) != null) {   
		   String varDressNameElement = csv3Cell[0];
		   String varDressSize = csv3Cell[1];
		   String varDressColor = csv3Cell[2];
		   Reporter.log("New row has been read from CSV data file", true);
		//----
		
	
		System.out.println("checkOut starting");

		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("login"))).click();
		Reporter.log("Clicked Sign In button", true);
       driver.findElement(By.id("email")).sendKeys(existingUserEmail);
       driver.findElement(By.id("passwd")).sendKeys(existingUserPassword);
       driver.findElement(By.id("SubmitLogin")).click();
       Reporter.log("Clicked Sign In button after entering credentials", true);
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Women"))).click();
       Reporter.log("Clicked Women Tab", true);
       //driver.findElement(By.xpath("//a[@title='Faded Short Sleeve T-shirts']/ancestor::li")).click();
       //driver.findElement(By.xpath("//a[@title='Faded Short Sleeve T-shirts']/ancestor::li")).click();
       driver.findElement(By.xpath(varDressNameElement)).click();
       driver.findElement(By.xpath(varDressNameElement)).click();
       Reporter.log("Selected Dress by Name", true);
       Select DressSize = new Select(driver.findElement(By.id("group_1")));
       DressSize.selectByVisibleText(varDressSize);
       Reporter.log("Selected Dress Size", true);
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(varDressColor))).click();
       Reporter.log("Selected Dress Color", true);
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Submit"))).click();
       Reporter.log("Clicked Add to Cart button", true);
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='layer_cart']//a[@class and @title='Proceed to checkout']"))).click();
       Reporter.log("Clicked Proceed to checkout button in pop-up", true);
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class,'cart_navigation')]/a[@title='Proceed to checkout']"))).click();
       Reporter.log("Clicked Proceed to checkout button", true);
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("processAddress"))).click();
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uniform-cgv"))).click();
       Reporter.log("Accept Terms & conditions checkbox", true);
       driver.findElement(By.name("processCarrier")).click();
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("bankwire"))).click();
       Reporter.log("Select Bankwire as payment option", true);
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='cart_navigation']/button"))).click();
       Reporter.log("Confirmed the order", true);
       WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1")));

       Assert.assertEquals("ORDER CONFIRMATION", heading.getText());
       Assert.assertTrue(driver.findElement(By.xpath("//li[@class='step_done step_done_last four']")).isDisplayed());
       Assert.assertTrue(driver.findElement(By.xpath("//li[@id='step_end' and @class='step_current last']")).isDisplayed());
       Assert.assertTrue(driver.findElement(By.xpath("//*[@class='cheque-indent']/strong")).getText().contains("Your order on My Store is complete."));
       Assert.assertTrue(driver.getCurrentUrl().contains("controller=order-confirmation"));
       Reporter.log("All assertions completed", true);
       
       //System.out.println("DressNameElement Value is: " + varDressNameElement);
    // Logout before next iteration, if required
   	boolean SignoutVisible = driver.findElement(By.className("logout")).isDisplayed();
		boolean SignoutEnabled = driver.findElement(By.className("logout")).isEnabled();
       if (SignoutVisible==true && SignoutEnabled==true)
       {
       	   System.out.println("Logged in session identified:Logging out");
              driver.findElement(By.className("logout")).click();// For logging out
              Reporter.log("Clicked Sign Out button", true);
       }
		//Selenium code

       
       System.out.println("checkOut ending");
	}
	
}

}
