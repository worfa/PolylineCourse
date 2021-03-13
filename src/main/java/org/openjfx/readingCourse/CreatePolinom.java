package org.openjfx.readingCourse;


import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;

public class CreatePolinom {
	
private Double [] courseAndDate;
private double cooficDate;
private double cooficCourse;
private double minCourse;
private String firstDate;
private String lastDate;
private double maxCourse;

		public ArrayList<Line> paintOXY() {
		
		ArrayList<Line> line = new ArrayList<Line>();
		
		Line lineOx = new Line(100, 50, 100, 200);
		Line lineOy = new Line(100, 200, 400, 200);
	
		line.add(lineOx);
		line.add(lineOy);
		
		return line;
	}
	
	public ArrayList<Line> paintLineMarking() {
		ArrayList<Line> line = new ArrayList<Line>();
		Line lineOne = new Line(90, 170, 400, 170);
		lineOne.getStrokeDashArray().addAll(2d, 10d);
		line.add(lineOne);
		
		Line lineTwo = new Line(90, 140, 400, 140);
		lineTwo.getStrokeDashArray().addAll(2d, 10d);
		line.add(lineTwo);
		Line lineThree = new Line(90, 110, 400, 110);
		lineThree.getStrokeDashArray().addAll(2d, 10d);
		line.add(lineThree);
		Line lineFour = new Line(90, 80, 400, 80);
		lineFour.getStrokeDashArray().addAll(2d, 10d);
		line.add(lineFour);
		Line lineFive = new Line(90, 50, 400, 50);
		lineFive.getStrokeDashArray().addAll(2d, 10d);
		line.add(lineFive);
		Line lineSix = new Line(160, 50, 160, 205);
		lineSix.getStrokeDashArray().addAll(2d, 10d);
		line.add(lineSix);
		Line lineSeven = new Line(220, 50, 220, 205);
		lineSeven.getStrokeDashArray().addAll(2d, 10d);
		line.add(lineSeven);
		Line lineEait = new Line(280, 50, 280, 205);
		lineEait.getStrokeDashArray().addAll(2d, 10d);
		line.add(lineEait);
		Line lineNine = new Line(340, 50, 340, 205);
		lineNine.getStrokeDashArray().addAll(2d, 10d);
		line.add(lineNine);
		Line lineTen = new Line(400, 50, 400, 205);
		lineTen.getStrokeDashArray().addAll(2d, 10d);
		line.add(lineTen);
		
		return line;
	}
	
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
	
	public String [] getArrayDate() throws ParseException {
		Date oneDate = simpleDateFormat.parse(firstDate);
		int [] roundDateArray = new int [courseAndDate.length/2 + 1];
		int j = 0;
		for(int i = 0; i <= courseAndDate.length - 1; i++) {
			
			if(i%2 == 0) {
				
				double a = (courseAndDate[i] - 100)/cooficDate; 
				roundDateArray [j] = (int) a;

				j++;
			}
			
		}
		j = 0;
		String [] arrayDate = new String [6];
		for(int i = 0; i < roundDateArray.length - 1; i = i + ((roundDateArray.length - 1) / 5)) {
			Calendar instance = Calendar.getInstance();
			instance.setTime(oneDate); //устанавливаем дату, с которой будет производить операции
			instance.add(Calendar.DAY_OF_MONTH, roundDateArray[i]);// прибавляем 3 дня к установленной дате
			Date newDate = instance.getTime();
			arrayDate[j] = simpleDateFormat.format(newDate);
			j++;
		}
		arrayDate[arrayDate.length-1] = lastDate;
		 
		
		return arrayDate;
	}
	
	public String [] getArrayCourse() {
		
		String [] arrayCourse = new String [6]; 
		arrayCourse[0] = String.format("%.3f", minCourse);
		
		for(int i = 1; i <= 5; i++) {
			arrayCourse[i] = String.format("%.3f", minCourse + (i * (maxCourse - minCourse)/6));
		}
		
		
		return arrayCourse;
	}
	
	public ArrayList<Text> getNumGraph() throws ParseException {
		
		
		
		ArrayList<Text> textList = new ArrayList<Text>();
		String [] arrayDate = getArrayDate();
		String [] arrayCourse = getArrayCourse();
		for(int i = 0; i <= arrayDate.length-1; i++)System.out.println(arrayDate[i]);
		for(int i = 0; i <= arrayCourse.length-1; i++)System.out.println(arrayCourse[i]);
		
		 Text yOne = new Text(arrayCourse[0]);
		 textList.add(yOne);
		 Text yTwo = new Text(arrayCourse[1]);
		 textList.add(yTwo);
		 Text yThree = new Text(arrayCourse[2]);
		 textList.add(yThree);
		 Text yFour = new Text(arrayCourse[3]);
		 textList.add(yFour);
		 Text yFive = new Text(arrayCourse[4]);
		 textList.add(yFive);
		 Text ySix = new Text(arrayCourse[5]);
		 textList.add(ySix);
		 Text xOne = new Text(arrayDate[0]);
		 textList.add(xOne);
		 Text xTwo = new Text(arrayDate[1]);
		 textList.add(xTwo);
		 Text xThree = new Text(arrayDate[2]);
		 textList.add(xThree);
		 Text xFour = new Text(arrayDate[3]);
		 textList.add(xFour);
		 Text xFive = new Text(arrayDate[4]);
		 textList.add(xFive);
		 Text xSix = new Text(arrayDate[5]);
		 textList.add(xSix);
		 
		 return textList;
	}
	
	public Polyline getGraph(Double [] courseAndDate, double cooficDate, double cooficCourse, double minCourse,String firstDate, String lastDate, double maxCourse) throws IOException, InvalidFormatException { 
		Polyline polyEuro = new Polyline();
		this.courseAndDate = courseAndDate;
		this.cooficDate = cooficDate;
		this.cooficCourse = cooficCourse;
		this.minCourse = minCourse;
		this.firstDate = firstDate;
		this.lastDate = lastDate;
		this.maxCourse = maxCourse;
		
		polyEuro.getPoints().addAll(courseAndDate);
		return polyEuro;
	}
	
	
	
	
	
	
}
