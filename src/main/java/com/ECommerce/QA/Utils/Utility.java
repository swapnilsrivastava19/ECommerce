package com.ECommerce.QA.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class Utility {

	public static final int IMPLICIT_WAIT_TIME=20;
	public static final int PAGE_LOAD_TIME=10;

	public static String generateEmailWithTimeStamp() {
		Date date=new Date();
		String timeStamp = date.toString().replace(" ", "_").replace(":","_");
		return "damon"+timeStamp+"@gmail.com";
	}

	public static Object[][] getTestDataFromExcel(String sheetName) {

		File excelFile=new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\ECommerce\\QA\\TestData\\ECommerceTestData.xlsx");
		XSSFWorkbook workbook=null;
		try {
		FileInputStream fisExcel=new FileInputStream(excelFile);
		workbook=new XSSFWorkbook(fisExcel);
		}catch(Throwable e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = workbook.getSheet(sheetName);
		int rows = sheet.getLastRowNum();
		int cols = sheet.getRow(0).getLastCellNum();
		Object[][] data=new Object[rows][cols];
		for(int i=0;i<rows;i++) {
			XSSFRow row = sheet.getRow(i+1);
			for(int j=0;j<cols;j++) {
				XSSFCell cell = row.getCell(j);
				CellType cellType = cell.getCellType();
				switch(cellType) {
				case STRING:
					data[i][j]=cell.getStringCellValue();
					break;	
				case NUMERIC:
					data[i][j]=Integer.toString ((int)cell.getNumericCellValue());
					break;
				case BOOLEAN:
					data[i][j]=cell.getBooleanCellValue();

				}
			}
		}
		return data;
	}
	
	public static String captureScreenShot(WebDriver driver, String testName) {
		
		File srcScreenShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String destinationScreenShotPath = System.getProperty("user.dir")+"\\Screenshots\\"+testName+".png";
		try {
			FileHandler.copy(srcScreenShot, new File(destinationScreenShotPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return destinationScreenShotPath;
	}
}
