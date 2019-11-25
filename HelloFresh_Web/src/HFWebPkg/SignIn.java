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

public class SignIn extends BaseTest {
	/*
	 public ExtentHtmlReporter htmlReporter;
	 public ExtentReports extent;
	 public ExtentTest test; 
*/	
	//public String baseUrl = "http://automationpractice.com/index.php";
   //String driverPath = "./Webdrivers/chromedriver.exe";
   public WebDriver driver ;
   WebDriverWait wait;


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
		System.out.println("Before SignIn Test execution:Launching the browser");
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
			   test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName()); // to add name in extent report
			   test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getThrowable()); // to add error/exception in extent report
			   String screenshotPath = SignIn.getScreenshot(driver, result.getName());
			   test.addScreenCaptureFromPath(screenshotPath);// adding screen shot
			   System.out.println("Login_Screenshot Taken");
			  }
              else if (result.getStatus() == ITestResult.SKIP) {
		       test.log(Status.SKIP, "Test Case SKIPPED IS " + result.getName());
		      }
		      else if (result.getStatus() == ITestResult.SUCCESS) {
		      test.log(Status.PASS, "Test Case PASSED IS " + result.getName());
			   try
	        	{
	        	
	    		// Logout if no failures
	        	
	        	boolean SignoutVisible = driver.findElement(By.className("logout")).isDisplayed();
	    		boolean SignoutEnabled = driver.findElement(By.className("logout")).isEnabled();
	            if (SignoutVisible==true && SignoutEnabled==true)
	            {
	            	   System.out.println("Logged in session identified:Logging out");
	                   driver.findElement(By.className("logout")).click();// For logging out
	                   Reporter.log("Logging out the SignIn Test Case session", true);
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
	public void signIn() throws Exception
	{
		test = extent.createTest("signIn");
		
		System.out.println("signIn method running with ThreadID:" + Thread.currentThread().getId());
		//----
		  String CSV_PATH="./DataFiles/SignUpTestData.csv";
		  CSVReader reader = new CSVReader(new FileReader(CSV_PATH));
		  reader.readNext();
		  String [] csvCell;
		  //while loop will be executed till the last line In CSV.
		  while ((csvCell = reader.readNext()) != null) {   
		   String varTitle = csvCell[0];
		   String varFirstName = csvCell[1];
		   String varLastName = csvCell[2];
		   String varPassword = csvCell[3];
		   String varDay = csvCell[4];
		   String varMonth = csvCell[5];
		   String varYear = csvCell[6];
		   String varCompany = csvCell[7];
		   String varAddress1 = csvCell[8];
		   String varAddress2 = csvCell[9];
		   String varCity = csvCell[10];
		   String varState = csvCell[11];
		   String varZip = csvCell[12];
		   String varAdditionalInfo = csvCell[13];
		   String varHomePhonoe = csvCell[14];
		   String varMobilePhone = csvCell[15];
		   String varAlias = csvCell[16];
		   Reporter.log("New row has been read from CSV data file", true);
		//----
		
		System.out.println("Signup starting");
			
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("login"))).click();
       Reporter.log("Clicked Sign In button", true);
       String timestamp = String.valueOf(new Date().getTime());
       String email = "hf_challenge_" + timestamp + "@hf" + timestamp.substring(7) + ".com";
       
       driver.findElement(By.id("email_create")).sendKeys(email);
       Reporter.log("Typed in dynamically generated email address", true);
       driver.findElement(By.id("SubmitCreate")).click();
       Reporter.log("Clicked Create an account button", true);
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(varTitle))).click();
       Reporter.log("Selected Title", true);
       driver.findElement(By.id("customer_firstname")).sendKeys(varFirstName);
       Reporter.log("Typed in First Name", true);
       driver.findElement(By.id("customer_lastname")).sendKeys(varLastName);
       Reporter.log("Typed in Last Name", true);
       driver.findElement(By.id("passwd")).sendKeys(varPassword);
       Reporter.log("Typed in Password", true);
       Select select = new Select(driver.findElement(By.id("days")));
       select.selectByValue(varDay);
       select = new Select(driver.findElement(By.id("months")));
       select.selectByValue(varMonth);
       select = new Select(driver.findElement(By.id("years")));
       select.selectByValue(varYear);
       Reporter.log("Selected Date of Birth", true);
       driver.findElement(By.id("company")).sendKeys(varCompany);
       Reporter.log("Typed in Company", true);
       driver.findElement(By.id("address1")).sendKeys(varAddress1);
       Reporter.log("Typed in Address", true);
       driver.findElement(By.id("address2")).sendKeys(varAddress2);
       Reporter.log("Typed in Address-Line2", true);
       driver.findElement(By.id("city")).sendKeys(varCity);
       Reporter.log("Typed in City", true);
       select = new Select(driver.findElement(By.id("id_state")));
       select.selectByVisibleText(varState);
       Reporter.log("Selected State", true);
       driver.findElement(By.id("postcode")).sendKeys(varZip);
       Reporter.log("Typed in PostCode", true);
       driver.findElement(By.id("other")).sendKeys(varAdditionalInfo);
       Reporter.log("Typed in Additional Information", true);
       driver.findElement(By.id("phone")).sendKeys(varHomePhonoe);
       Reporter.log("Typed in HomePhone", true);
       driver.findElement(By.id("phone_mobile")).sendKeys(varMobilePhone);
       Reporter.log("Typed in MobilePhone", true);
       driver.findElement(By.id("alias")).sendKeys(varAlias);
       Reporter.log("Typed in Alias", true);
       driver.findElement(By.id("submitAccount")).click();
       Reporter.log("Clicked on Register button", true);
       
       WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1")));

       Assert.assertEquals(heading.getText(), "MY ACCOUNT");
       Assert.assertEquals(driver.findElement(By.className("account")).getText(), varFirstName + " " + varLastName);
       Assert.assertTrue(driver.findElement(By.className("info-account")).getText().contains("Welcome to your account."));
       Assert.assertTrue(driver.findElement(By.className("logout")).isDisplayed());
       Assert.assertTrue(driver.getCurrentUrl().contains("controller=my-account"));
       Reporter.log("All assertions completed", true);
		  //---
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

		System.out.println("Signup ending");
		  }
	}

}
