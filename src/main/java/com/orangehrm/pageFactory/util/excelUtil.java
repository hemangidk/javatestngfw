package com.orangehrm.pageFactory.util;

import java.io.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class excelUtil {
//	public static XSSFWorkbook workbook;
//	public static XSSFSheet worksheet;


	public static DataFormatter formatter = new DataFormatter();

	public static void readExcel(String filePath, String fileName, String sheetName) throws IOException {

		File file = new File(filePath + "\\" + fileName);
		FileInputStream inputStream = new FileInputStream(file);

		Workbook wb = null;
		String fileExtensionName = fileName.substring(fileName.indexOf("."));

		if (fileExtensionName.equals(".xlsx")) {
			wb = new XSSFWorkbook(inputStream);
		} else if (fileExtensionName.equals(".xls")) {
			wb = new HSSFWorkbook(inputStream);
		}

		assert wb != null;
		Sheet sht = wb.getSheet(sheetName);
		int rowCount = sht.getLastRowNum() - sht.getFirstRowNum();

		for (int i = 0; i < rowCount + 1; i++) {
			Row row = sht.getRow(i);
			for (int j = 0; j < row.getLastCellNum(); j++) {
				System.out.print(row.getCell(j).getStringCellValue() + "|| ");
			}
			System.out.println();
		}
	}

	public static Object[][] ReadDataFromExcelFile(String FileName, String sheetname) throws IOException
	{
		Object[][] Data = new Object[0][];
		try{
		FileInputStream fileInputStream = new FileInputStream(FileName); // mentioned here
		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream); // get my workbook
		XSSFSheet worksheet = workbook.getSheet(sheetname);// get my sheet from workbook
		XSSFRow Row = worksheet.getRow(0); // get my Row which start from 0

		int RowNum = worksheet.getPhysicalNumberOfRows();// count my number of Rows
		int ColNum = Row.getLastCellNum(); // get last ColNum

		Data = new Object[RowNum - 1][ColNum]; // pass my count data in array

		for (int i = 0; i < RowNum - 1; i++) // Loop work for Rows
		{
			XSSFRow row = worksheet.getRow(i + 1);

			for (int j = 0; j < ColNum; j++) // Loop work for colNum
			{
				if (row == null)
					Data[i][j] = "";
				else {
					XSSFCell cell = row.getCell(j);
					if (cell == null)
						Data[i][j] = ""; // if it get Null value it pass no data
					else {
						String value = formatter.formatCellValue(cell);
						Data[i][j] = value; // This formatter get my all values as string i.e integer, float all type
											// data value
					}
				}
			}
		}
		workbook.close();
		}
		catch (FileNotFoundException e)
		{
		e.printStackTrace();
		}
		return Data;

	}

	
	public static void writeExcel(String filePath, String fileName, String sheetName, String[] dataToWrite)
			throws IOException {
		File file = new File(filePath + "\\" + fileName);
		FileInputStream inputStream = new FileInputStream(file);
		Workbook workbook = null;

		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		if (fileExtensionName.equals(".xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (fileExtensionName.equals(".xls")) {
			workbook = new HSSFWorkbook(inputStream);
		}

		assert workbook != null;
		Sheet sheet = workbook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		Row row = sheet.getRow(0);
		Row newRow = sheet.createRow(rowCount + 1);

		// Create a loop over the cell of newly created Row
		for (int j = 0; j < row.getLastCellNum(); j++) {
			Cell cell = newRow.createCell(j);
			cell.setCellValue(dataToWrite[j]);
		}

		// Close input stream
		inputStream.close();

		// Create an object of FileOutputStream class to create write data in excel file
		FileOutputStream outputStream = new FileOutputStream(file);

		// write data in the excel file
		workbook.write(outputStream);

		// close output stream
		outputStream.close();
	}

	// Main function is calling readExcel function to read data from excel file
	public static void main(String... strings) throws IOException {
		String filePath = System.getProperty("user.dir");

		// Call read file method of the class to read data

		readExcel(filePath, "ExportExcel.xlsx", "ExcelDemo");

		/////////////////////
		// Create an array with the data in the same order in which you expect to be
		///////////////////// filled in excel file

		String[] valueToWrite = { "Mr. E", "Noida" };

		// Create an object of current class

		// Write the file using file name, sheet name and the data to be filled

		writeExcel(System.getProperty("user.dir"), "ExportExcel.xlsx", "ExcelDemo", valueToWrite);

	}

}
