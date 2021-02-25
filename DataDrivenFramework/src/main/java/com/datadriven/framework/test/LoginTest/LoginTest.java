package com.datadriven.framework.test.LoginTest;

import java.util.Hashtable;
import java.util.Scanner;

import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.datadriven.framework.base.BaseUI;
import com.datadriven.framework.utils.ExtentReportManager;
import com.datadriven.framework.utils.TestDataProvider;

public class LoginTest extends BaseUI {
	
	
	
	@Test(priority=1, dataProvider="getTestOneData")
	public void testOne(Hashtable<String,String> dataTable){
	    logger = report.createTest("Test One");
		logger.log(Status.INFO,"Opening the browser and website");
		System.out.println("Enter the number to choose the browser");
		System.out.println("1.Chrome\n2.Mozilla Firefox\n3.Internet Explorer");
		@SuppressWarnings("resource")
		Scanner sc=new Scanner(System.in);
		int browser=sc.nextInt();
		invokeBrowser(browser);	
		openURL("website");
		click("propertyBtn_Xpath");
		click("RentersBtn_Xpath");
		enterText("ZipCodeTB_Xpath",dataTable.get("Zipcode"));
		verifyPageTitle(dataTable.get("PageTitle"));
		click("GetMyBtn_Xpath");
	}
	
    @Test(priority=2, dataProvider="getTestOneData")
	public void testTwo(Hashtable<String,String> dataTable){
		logger =report.createTest("Test Two");
		logger.log(Status.INFO, "Filling the form details");
		enterText("FirstNTB_Xpath",dataTable.get("FirstName"));
		enterText("LastNTB_Xpath",dataTable.get("LastName"));
		enterText("EmailTB_Xpath",dataTable.get("Email"));
		enterText("StreetAd_Xpath",dataTable.get("StreetAdd"));
		enterText("AptNo_Xpath",dataTable.get("AptNo"));
		click("HowLng_Xpath");
		scrollDown();
	}
    
    @Test(priority=3, dataProvider="getTestOneData")
    public void testThree(Hashtable<String,String> dataTable){
    	SelectElementInList("Type_Xpath","2");
    	SelectElementInList("Month_Xpath", "4");
    	enterText("Date_Xpath", dataTable.get("Date"));
    	enterText("Year_Xpath", dataTable.get("Year"));
    	enterText("Phone_Xpath", dataTable.get("Phone"));
    	click("Auto_Xpath");
    	SelectElementInList("Stuff_Xpath","5000");
    	click("QuoteBtn_Xpath");
    }
	
	@AfterTest
	public void endReport(){
		report.flush();
	}
	
	@DataProvider
	public Object[][] getTestOneData(){
	     return TestDataProvider.getTestData("TestData.xlsx", "SampleData1", "DataForTest");
	}
}
