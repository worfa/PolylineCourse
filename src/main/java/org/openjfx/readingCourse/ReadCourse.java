package org.openjfx.readingCourse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadCourse {

	public  double cooficCourse;
	public  double dateCoofic;
	public  double minCourse;
	public  Double [] arrayCoursAndDate;
	public  double maxCourse;
	public  String firstDate;
	public  String lastDate;
	public	String nameCourse;
	
	private static String path = "Euro_1992_2020.xlsx"; 

	ReadCourse(String path){
		this.path = path;
	}
	ReadCourse(){
		}
	
	/*формирование массива данных по формату курс, дата*/
	
	public Double [] getDoubleArrayEuroAndDate() throws IOException, InvalidFormatException
	{
		
		String path = "Euro_1992_2020.xlsx"; //<i> путь до файла
		
		XSSFWorkbook workBook = new XSSFWorkbook(new File(path)); //подключение к указанному файлу
		XSSFSheet sheet = workBook.getSheetAt(0);
		
		Double [] course = new Double [sheet.getLastRowNum()]; 
		
		//получаем мин и макс валюты за заданный период
				
				double maxCourse = -2;
				double minCourse = 1000000000;
				
				for(int i = 1; i <= sheet.getLastRowNum(); i++)
				{
					XSSFRow row = sheet.getRow(i);
					double courseValue = row.getCell(2).getNumericCellValue();
					if(maxCourse < courseValue)maxCourse = courseValue;
					if(minCourse > courseValue)minCourse = courseValue;
				}
		
				this.cooficCourse = cooficCourse;
				this.minCourse = minCourse;
				this.maxCourse = maxCourse;
				
				double cooficCourse = (150 / (maxCourse - minCourse));
				for(int i = 1; i <= sheet.getLastRowNum(); i++) { 
					XSSFRow row = sheet.getRow(i);
				  
					double courseValue = row.getCell(2).getNumericCellValue(); 
					
					course [i-1] = ((courseValue - minCourse) * cooficCourse);
				}
				
				Double [] normalCourse = new Double [course.length];
				int j = 0;
				for(int i = 0; i <= normalCourse.length - 1; i++) {
					normalCourse[j] = 200 - course[i];
					j++;
				}
		
		Double [] date = new Double [sheet.getLastRowNum()];
		XSSFRow rowFirstDate = sheet.getRow(1);
		Date strFirstDate = rowFirstDate.getCell(1).getDateCellValue();
		 
		
		for(int i = 2; i <= sheet.getLastRowNum(); i++) {
			
			XSSFRow row = sheet.getRow(i);
			
			Date doubleEndDate = row.getCell(1).getDateCellValue();
						
			Double point = getDayCount(strFirstDate, doubleEndDate);
			date[i-1] = point;
		}
		date[0] = 100.0;
			Double [] normalDate = new Double[date.length];
			Double dateCoofic = (300 / date[date.length-1]);
			
			this.dateCoofic = dateCoofic;
			
		for(int i = 0; i <= date.length-1; i++) {
			normalDate [i] = (date[i] * dateCoofic) + 100;
		}
		normalDate [0] = 100.0;
	
		
		Double [] arrayCoursAndDate = new Double [(sheet.getLastRowNum() - 1)*2];
		int c = 0;
		int d = 0;
		for(int i = 0; i < (sheet.getLastRowNum() - 1)*2; i++ ) {
			
			if(0 == i%2) {
				arrayCoursAndDate [i] = normalDate[d];
				d++;
			}
			if(1 == i%2) {
				arrayCoursAndDate [i] = normalCourse[c];
				c++;
			}
		}
		
		workBook.close();
		this.arrayCoursAndDate = arrayCoursAndDate;
		return arrayCoursAndDate;
	}
	
	public Double [] getDoubleArrayEuroAndDate(String firstDate, String lastDate) throws IOException, InvalidFormatException, ParseException
	{ 
		
		XSSFWorkbook workBook = new XSSFWorkbook(new File(path)); //подключение к указанному файлу
		XSSFSheet sheet = workBook.getSheetAt(0);
		int firstLastArray [] = new int [2];
		
		firstLastArray = parseNumRow(firstDate, lastDate);
		
		
		
		Double [] course = new Double [firstLastArray[1] - firstLastArray[0]]; 
	
	//получаем мин и макс валюты за заданный период
		int rowNum = firstLastArray[0];
		double maxCourse = -2;
		double minCourse = 1000000000;
		
		XSSFRow firstRow = sheet.getRow(2);
		nameCourse = firstRow.getCell(3).getStringCellValue();
		
		for(int i = 1; i <= firstLastArray[1] - firstLastArray[0]; i++)
		{
			XSSFRow row = sheet.getRow(rowNum);
			double courseValue = row.getCell(2).getNumericCellValue();
			if(maxCourse < courseValue)maxCourse = courseValue;
			if(minCourse > courseValue)minCourse = courseValue;
			rowNum++;
		}
		
		
	
	//получаем и формируем необходимые данные 		
		rowNum = firstLastArray[0];
		double cooficCourse = (150 / (maxCourse - minCourse));
		for(int i = 1; i <= firstLastArray[1] - firstLastArray[0]; i++) { 
			XSSFRow row = sheet.getRow(rowNum);
		  
			double courseValue = row.getCell(2).getNumericCellValue(); 
			
			course [i-1] = ((courseValue - minCourse) * cooficCourse);
			rowNum++;
		}
		this.cooficCourse = cooficCourse;
		this.minCourse = minCourse;
		this.maxCourse = maxCourse;
		this.firstDate = firstDate;
		this.lastDate = lastDate;
		
		Double [] normalCourse = new Double [course.length];
		int j = 0;
		for(int i = 0; i <= normalCourse.length - 1; i++) {
			normalCourse[j] = 200 - course[i];
			j++;
		}
		

//--------------- работа с датой ------------------
		Double [] date = new Double [firstLastArray[1] - firstLastArray[0]];
		XSSFRow rowFirstDate = sheet.getRow(firstLastArray [0]);
		Date strFirstDate = rowFirstDate.getCell(1).getDateCellValue();
		 
		
		rowNum = firstLastArray[0];
		for(int i = 1; i <= firstLastArray[1] - firstLastArray[0]; i++) {
			
			XSSFRow row = sheet.getRow(rowNum);
			
			Date doubleEndDate = row.getCell(1).getDateCellValue();
			String strEndDate = doubleEndDate.toString();
						
			Double point = getDayCount(strFirstDate, doubleEndDate);
			date[i-1] = point;
			rowNum++;
		}
		date[0] = 100.0;
			Double [] normalDate = new Double[date.length];
			Double coofic = (300 / date[date.length-1]);
		for(int i = 0; i <= date.length-1; i++) {
			normalDate [i] = (date[i] * coofic) + 100;
		}
		normalDate [0] = 100.0;
		
		this.dateCoofic = coofic;
		
		Double [] arrayCoursAndDate = new Double [(firstLastArray[1] - firstLastArray[0] - 1)*2];
		int c = 0;
		int d = 0;
		for(int i = 0; i < (firstLastArray[1] - firstLastArray[0] - 1)*2; i++ ) {
			
			if(0 == i%2) {
				arrayCoursAndDate [i] = normalDate[d];
				d++;
			}
			if(1 == i%2) {
				arrayCoursAndDate [i] = normalCourse[c];
				c++;
			}
		}
		
		workBook.close();
		return arrayCoursAndDate;
	}
	
	private static int [] parseNumRow(String firstDate, String lastDate) throws InvalidFormatException, IOException, ParseException {
		
		int hashFirstDate = firstDate.hashCode();
		int hashLastDate = lastDate.hashCode();
		int [] rowFirstLast = new int [2];
		
		XSSFWorkbook workBook = new XSSFWorkbook(new File(path)); //подключение к указанному файлу
		XSSFSheet sheet = workBook.getSheetAt(0);
		
	while(rowFirstLast[0] == 0 || rowFirstLast[1] == 0) {	
		for(int i = 1; i <= sheet.getLastRowNum(); i++) {
			XSSFRow row = sheet.getRow(i);
			Date date = row.getCell(1).getDateCellValue();
			String dateString = simpleDateFormat.format(date);
			
			int hashdate = dateString.hashCode();
				
			if(hashdate == hashFirstDate)
				rowFirstLast [0] = i;
			
			if(hashdate == hashLastDate) 
				rowFirstLast [1] = i;
		}	
		System.out.println(rowFirstLast[0] + " " + rowFirstLast[1]);
		if(rowFirstLast [0] == 0) {
			Date firstDateDate = simpleDateFormat.parse(firstDate);
			Calendar instance = Calendar.getInstance();
			instance.setTime(firstDateDate); //устанавливаем дату, с которой будет производить операции
			instance.add(Calendar.DAY_OF_MONTH, 1);// прибавляем 1 день к установленной дате
			firstDateDate = instance.getTime(); // получаем измененную дату
			firstDate = simpleDateFormat.format(firstDateDate);
			hashFirstDate = firstDate.hashCode();
		}
		if(rowFirstLast [1] == 0) {
			Date lastDateDate = simpleDateFormat.parse(lastDate);
			Calendar instance = Calendar.getInstance();
			instance.setTime(lastDateDate); //устанавливаем дату, с которой будет производить операции
			instance.add(Calendar.DAY_OF_MONTH, -1);// прибавляем 1 день к установленной дате
			lastDateDate = instance.getTime(); // получаем измененную дату
			lastDate = simpleDateFormat.format(lastDateDate);
			hashLastDate = lastDate.hashCode();
		}
	}
		return rowFirstLast;
	}
	
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
	
	public static double getDayCount(Date start, Date end) //получение кол-ва дней от начала до конечного
	{
		double diff = -1;
		try {
			String strDateStart = simpleDateFormat.format(start);
			Date dateStart = simpleDateFormat.parse(strDateStart);
			String strDateEnd = simpleDateFormat.format(end);
			Date dateEnd = simpleDateFormat.parse(strDateEnd);
			
			diff = Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000);
		} catch (Exception e) {
		}
		return diff;
	}


}
