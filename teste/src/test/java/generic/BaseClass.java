package generic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * @author suman
 *
 */
public class BaseClass 
{
	public static WebDriver driver;

	public static ExtentHtmlReporter reporter;

	public static ExtentReports extent;

	public static ExtentTest test;

	public static Logger logger = LogManager.getLogger(BaseClass.class);

	public static ReadProperties pro = new ReadProperties();

	@BeforeSuite
	public void setUp()
	{
		reporter=new ExtentHtmlReporter(System.getProperty("user.dir")+"/Reports/ExtentReports.html");

		reporter.config().setDocumentTitle("Automation Report");
		reporter.config().setReportName("Test Report");
		reporter.config().setTheme(Theme.DARK);

		extent=new ExtentReports();
		extent.attachReporter(reporter);
		reporter.loadXMLConfig(System.getProperty("user.dir")+"/Extent-Config.xml");
		System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY,"true");
		WebDriverManager.chromedriver().setup();
		WebDriverManager.firefoxdriver().setup();
	}



	@BeforeClass
//	@Parameters("browser")
	
	public void setBrowser()
	{
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values.notifications", 2);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
//		if (browser.equalsIgnoreCase("chrome")) 
//		{
			driver=new ChromeDriver(options);
//		}
//
//		else if (browser.equalsIgnoreCase("firefox")) 
//		{
//			driver=new FirefoxDriver();
//		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(pro.getUrl());
	}

	@AfterMethod
	public void result(ITestResult result)
	{
		if (result.getStatus()==ITestResult.SUCCESS) 
		{
			test.createNode(result.getName());
			test.log(Status.PASS, "The test case  "+ result.getName()+" "+ " is PASSED");
		} 

		else if(result.getStatus()==ITestResult.FAILURE) 
		{
			try
			{
				test.log(Status.FAIL,"The test case"+" "+ result.getName()+ " is FAILED");
				test.log(Status.FAIL, "Exception:"+result.getThrowable());
				String screenshotPath = Utils.captureScreenshot(driver, result.getName());
			//	test.addScreenCaptureFromPath(screenshotPath);
				test.addScreenCaptureFromBase64String(screenshotPath);
			//	test.addScreenCaptureFromBase64String(screenshotPath);
			//	test.addScreenCaptureFromPath(screenshotPath);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}

		else if (result.getStatus()==ITestResult.SKIP) 
		{
			test.log(Status.SKIP, "The test case  "+ result.getName()+ " is SKIPPED");
			test.log(Status.SKIP, result.getThrowable());
		}
	}

	@AfterClass
	public void closeBrowser()
	{
		driver.close(); 
	}


	@AfterSuite
	public void rtef() throws EmailException
	{
		extent.flush();

		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(System.getProperty("user.dir")+"/Reports/ExtentReports.html");
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription(pro.getMailMsg());
		attachment.setName(pro.getMailMsg());

		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(pro.getMailHostName());
		email.setSmtpPort(456);
		email.setAuthenticator(new DefaultAuthenticator(pro.getMailId(), pro.getMailPassword()));
		email.setSSLOnConnect(true);
		email.addTo(pro.getAddToMail(), pro.getAddToName());
		email.setFrom(pro.getAddFromMail(), pro.getAddFromName());
		email.setSubject(pro.getMailSubject());
		email.setMsg(pro.getMailMsg());

		email.attach(attachment);

		email.send();
	}
}
