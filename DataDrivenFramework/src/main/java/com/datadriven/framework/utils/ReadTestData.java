package com.datadriven.framework.utils;

public class ReadTestData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadExcelDataFile readdata=new ReadExcelDataFile(System.getProperty("user.dir") + "/src/main/java/testData/TestData.xlsx");
		String sheetName="SampleData";
		String testName="Test";
		
		//Find Start Row of Testcase
		int startRowNum=0;
		while(!readdata.getCellData(sheetName, 0, startRowNum).equalsIgnoreCase(testName)){
			startRowNum++;
		}
		System.out.println("Test Starts from Row Number:" + startRowNum);
		
		int startTestColumn=startRowNum+1;
		int startTestRow=startRowNum+2;
		
		//Find number of Rows in TestCase
		int rows = 0;
		while(!readdata.getCellData(sheetName, 0, startTestRow+rows).equals("")){
			rows++;
		}
		System.out.println("Total Number of Rows in Test: " + testName + "is-" + rows);
		
		//Find the number of columns in Test
		int colmns=0;
		while(!readdata.getCellData(sheetName, colmns, startTestColumn).equals("")){
			colmns++;
		}
		System.out.println("The total number of columns in Test: " + testName + "is-" +colmns);
		
		for (int rowNumber=startTestRow; rowNumber<=startTestColumn+rows; rowNumber++) {
			for (int colNumber=0; colNumber<colmns; colNumber++) {
				System.out.println(readdata.getCellData(sheetName, colNumber, rowNumber));
			}
		}
	}

}
