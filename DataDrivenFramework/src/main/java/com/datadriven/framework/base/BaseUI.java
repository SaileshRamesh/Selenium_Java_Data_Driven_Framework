package com.datadriven.framework.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.datadriven.framework.utils.DateUtils;
import com.datadriven.framework.utils.ExtentReportManager;

public class BaseUI {

	public WebDriver driver;
	public Properties prop;
	public ExtentReports report = ExtentReportManager.getReportInstance();
	public ExtentTest logger;
	SoftAssert softAssert=new SoftAssert();

	/****************** Invoke Browser ***********************/

	public void invokeBrowser(Integer browser) {
		try {
			if (browser == 1) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver.exe");
				driver = new ChromeDriver();
			} else if (browser == 2) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "/src/test/resources/drivers/geckodriver.exe");
				driver = new FirefoxDriver();
			} else if (browser == 3) {
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "/src/test/resources/drivers/IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		if (prop == null) {
			prop = new Properties();
			try {
				FileInputStream file = new FileInputStream(System.getProperty("user.dir")
						+ "/src/test/resources/ObjectRepository/projectConfig.properties");
				prop.load(file);
			} catch (Exception e) {
				reportFail(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/****************** Open URL ***********************/

	public void openURL(String websitekey) {
		try {
			driver.get(prop.getProperty(websitekey));
			reportPass(websitekey + "Identified Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/****************** Close Browser ***********************/

	public void tearDown() {
		driver.close();
	}

	/****************** Quit Browser ***********************/

	public void quitBrowser() {
		driver.quit();
	}

	/****************** Enter Text ***********************/

	public void enterText(String xpathKey, String data) {
		try {
			getElement(xpathKey).sendKeys(data);
			reportPass(data + " - Entered successfully in locator Element : " + xpathKey);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
	
	/****************** Element Click ***********************/

	public void click(String xpathKey) {
		try {
			getElement(xpathKey).click();
			reportPass(xpathKey + "Element Clicked successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
	
	/****************** Select List Drop Down ******************/
	public void SelectElementInList(String locatorXpath, String Value){
		try{
			WebElement dp=getElement(locatorXpath);
			Select select=new Select(dp);
			Thread.sleep(2000);
			select.selectByValue(Value);
			reportPass(Value + "Selected Successfully");
		}catch(Exception e){
			reportFail(e.getMessage());
		}
	}
	

	/****************** Identify Element ***********************/

	public WebElement getElement(String locatorKey) {
		WebElement element = null;
		try {
			if (locatorKey.endsWith("_Id")) {
				element = driver.findElement(By.id(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified:" + locatorKey);
			} else if (locatorKey.endsWith("_Xpath")) {
				element = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified:" + locatorKey);
			}else if(locatorKey.endsWith("_ClassName")) {
				element = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified:" + locatorKey);
			} else if (locatorKey.endsWith("_CSS")) {
				element = driver.findElement(By.cssSelector(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified:" + locatorKey);
			} else if (locatorKey.endsWith("_LinkText")) {
				element = driver.findElement(By.linkText(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified:" + locatorKey);
			} else if (locatorKey.endsWith("_PartialLinkText")) {
				element = driver.findElement(By.partialLinkText(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified:" + locatorKey);
			} else if (locatorKey.endsWith("_Name")) {
				element = driver.findElement(By.name(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified:" + locatorKey);
			} else {
				reportFail("Failing the Testcase, Invalid Locator " + locatorKey);
				Assert.fail("Failing the Testcase, Invalid Locator " + locatorKey);
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
			e.printStackTrace();
			Assert.fail("Failing the TestCase: " + e.getMessage());
		}

		return element;

	}

	/****************** Handle Frames **********************/
	public void switchToFrame(String frameLocator){
		try {
			logger.log(Status.INFO, "Switching Frame : " + frameLocator);
			driver.switchTo().frame(frameLocator);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
	
	public void switchToFrameByIndex(int frameNumner){
		try {
			logger.log(Status.INFO, "Switching Frame : " + frameNumner);
			driver.switchTo().frame(frameNumner);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
	
	public void switchToDefaultFrame(){
		try {
			logger.log(Status.INFO, "Switching to Main Windpw");
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
	
	
	/****************** Verify Element ***********************/

	public boolean isElementPresent(String locatorKey) {
		try {
			if (getElement(locatorKey).isDisplayed()) {
				reportPass(locatorKey + " :Element is Displayed");
				return true;
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return false;
	}

	public boolean isElementSelected(String locatorKey) {
		try {
			if (getElement(locatorKey).isSelected()) {
				reportPass(locatorKey + " :Element is Selected");
				return true;
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return false;
	}
	
	public boolean isElementEnabled(String locatorKey) {
		try {
			if (getElement(locatorKey).isEnabled()) {
				reportPass(locatorKey + " :Element is Enabled");
				return true;
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return false;
	}
	
	/****************** Verify PageTitle ***********************/
	
	public void verifyPageTitle(String pageTitle){
		try{
			String actualTitle=driver.getTitle();
			logger.log(Status.INFO, "Actual Title is :" + actualTitle);
			logger.log(Status.INFO, "Expected Title is :" + pageTitle);
			Assert.assertEquals(actualTitle, pageTitle);
		}catch(Exception e){
			reportFail(e.getMessage());
		}
	}
	
	/****************** Assertion Functions ***********************/
	
	public void assertTrue(boolean flag) {
		softAssert.assertTrue(flag);
	}

	public void assertfalse(boolean flag) {
		softAssert.assertFalse(flag);
	}

	public void assertequals(String actual, String expected) {
		try{
			logger.log(Status.INFO, "Assertion : Actual is -" + actual + " And Expacted is - " + expected);
			softAssert.assertEquals(actual, expected);
		}catch(Exception e){
			reportFail(e.getMessage());
		}
		
	}
	
	

	/****************** Reporting Functions ***********************/

	public void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		takeScreenShotOnFailure();
		Assert.fail(reportString);

	}

	public void reportPass(String reportString) {
		logger.log(Status.PASS, reportString);

	}
	
	@AfterMethod
	public void afterTest(){
		softAssert.assertAll();
	}

	/****************** Take Screenshots ***********************/

	public void takeScreenShotOnFailure() {
		TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
		File sourceFile = takeScreenShot.getScreenshotAs(OutputType.FILE);

		File destFile = new File(System.getProperty("user.dir") + "/Screenshots" + DateUtils.getTimeStamp() + ".png");
		try {
			logger.addScreenCaptureFromPath(
					System.getProperty("user.dir") + "/Screenshots" + DateUtils.getTimeStamp() + ".png");
			FileUtils.copyFile(sourceFile, destFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/********************Scroll Down the page****************/
	public void scrollDown(){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,1000)");
	}
	
	/***************** Wait Functions in Framework *****************/
	public void waitForPageLoad() {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		int i = 0;
		while (i != 180) {
			String pageState = (String) js.executeScript("return document.readyState;");
			if (pageState.equals("complete")) {
				break;
			} else {
				waitLoad(1);
			}
		}

		waitLoad(2);

		i = 0;
		while (i != 180) {
			Boolean jsState = (Boolean) js.executeScript("return window.jQuery != undefined && jQuery.active == 0;");
			if (jsState) {
				break;
			} else {
				waitLoad(1);
			}
		}
	}

	public void waitLoad(int i) {
		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
