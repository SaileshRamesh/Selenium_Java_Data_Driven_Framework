package com.datadriven.framework.utils;

import java.util.Hashtable;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestDataProvider {

	/*@Test(dataProvider="getTestData")
	public void sampleTestOne(Hashtable<String , String> table){
		
		
	}*/
	
	
	/*****************To the data for the testcase***************/
	
	public static Object[][] getTestData(String DatafileName, String SheetName, String TestName){
		
		ReadExcelDataFile readdata=new ReadExcelDataFile(System.getProperty("user.dir") + "/src/test/TestData/" + DatafileName);
		String sheetName=SheetName;
		String testName=TestName;
		
		//Find Start Row of Testcase
		int startRowNum=0;
		while(!readdata.getCellData(sheetName, 0, startRowNum).equalsIgnoreCase(testName)){
			startRowNum++;
		}
	
		
		int startTestColumn=startRowNum+1;
		int startTestRow=startRowNum+2;
		
		//Find number of Rows in TestCase
		int rows = 0;
		while(!readdata.getCellData(sheetName, 0, startTestRow+rows).equals("")){
			rows++;
		}
		
		
		//Find the number of columns in Test
		int colmns=0;
		while(!readdata.getCellData(sheetName, colmns, startTestColumn).equals("")){
			colmns++;
		}
		
		
		//Define Two dimensional object array
		Object[][] dataSet=new Object[rows][1];
		Hashtable<String , String> dataTable = null;
		int dataRowNumber=0;
		
		for (int rowNumber=startTestRow; rowNumber<=startTestColumn+rows; rowNumber++) {
			dataTable= new Hashtable<String , String>();
			for (int colNumber=0; colNumber<colmns; colNumber++) {
				String key= readdata.getCellData(sheetName, colNumber, startTestColumn);
				String value=readdata.getCellData(sheetName, colNumber, rowNumber);
				dataTable.put(key, value);
				
				//dataSet[dataRowNumber][colNumber]=readdata.getCellData(sheetName, colNumber, rowNumber);
						
			}
			dataSet[dataRowNumber][0]=dataTable;
			dataRowNumber++;
		}
		return dataSet;
	}
		
}	
