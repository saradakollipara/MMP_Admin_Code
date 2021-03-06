package org.totalqa.util;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.IHookCallBack;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import io.qameta.allure.Attachment;
 
public class BaseClass {
	protected static WebDriver driver;
	@Parameters({"url","browserType"})
	@BeforeClass
	public void loadConfiguration(String url,String browserType) throws IOException
	{
		if(browserType.equals("CH"))
		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/binaries/chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if(browserType.equals("FF"))
		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/binaries/geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else
		{
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"/src/test/resources/binaries/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
 
 
	}
	/**
	 * screenShot method is invoked whenever the Testcase is Failed.
	 * @param name
	 * @param driver
	 * @return
	 */
	@Attachment(value = "Screenshot of {0}", type = "image/png")
	public byte[] saveScreenshot(String name, WebDriver driver) {
		return (byte[]) ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}
 
	public void run(IHookCallBack iHookCallBack, ITestResult iTestResult) {
		iHookCallBack.runTestMethod(iTestResult);
		if (iTestResult.getThrowable() != null) {
			this.saveScreenshot(iTestResult.getName(), driver);
		}
	}
}