package com.datadriven.framework.utils;

public class tempreaddata {
	
	public static void main(String args[]){
		ReadExcelDataFile readData=new ReadExcelDataFile(System.getProperty("user.dir") + "/src/main/java/testData/TestData.xlsx");
		int totalRows=readData.getRowCount("SampleData");
		System.out.println("Total number of Rows: " + totalRows);
		System.out.println(readData.getCellData("SampleData",0,2));
		System.out.println(readData.getCellData("SampleData",1,2));
		System.out.println(readData.getCellData("SampleData",1,3));
	}

}
