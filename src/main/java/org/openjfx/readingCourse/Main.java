package org.openjfx.readingCourse;
	
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javafx.application.Application;
import javafx.event.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;


public class Main extends Application {

	File workFile;
	@Override
	public void start(Stage primaryStage) {
		try {
			
			primaryStage.setTitle("Построение графика курса валюты");
			
			Group root = new Group();			//основная группировка эллементов
			Group dateDiagramm = new Group();	//группировка эллементов диаграммы, текстовые элементы, выборы дат
			
			  DatePicker datePickerLastDate = new DatePicker(); //выбор последней даты
			  datePickerLastDate.setLayoutX(250);  //проставновка положения
			  datePickerLastDate.setLayoutY(250);
			  datePickerLastDate.setMaxWidth(100); //простановка размера поля

				  DatePicker datePickerFirstDate = new DatePicker(); 	//выбор первой даты
				  datePickerFirstDate.setLayoutX(50);					//постановка положений
				  datePickerFirstDate.setLayoutY(250);
				  datePickerFirstDate.setMaxWidth(100);					//постановка размера поля
		        
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy"); //форматер для перевода данных дат, в корректный формат
		        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");  //простой форматер, также нужен для перевода даты в нужный формат 
			
		        Group chartGroup = new Group(); //группировка данных полинома, для более простой отчистки
		        
		        CreatePolinom oXY = new CreatePolinom();
		        
		    Button buttonChooseFile = new Button("Выбрать файл");
		    buttonChooseFile.setLayoutX(datePickerFirstDate.getLayoutX());
		    buttonChooseFile.setLayoutY(datePickerFirstDate.getLayoutY() + 40);
		    buttonChooseFile.setOnAction(new EventHandler<ActionEvent>() {public void handle(ActionEvent event) {
		    	FileChooser fileChooser = new FileChooser();
		    	
		    	workFile = fileChooser.showOpenDialog(primaryStage);
		    }
		    });
		        
			Button buttonCreateChart = new Button();		//создание кнопки
		    buttonCreateChart.setLayoutX(buttonChooseFile.getLayoutX());				//выставление положения кнопки по X
		    buttonCreateChart.setLayoutY(buttonChooseFile.getLayoutY() + 30); 				//выставление положения кнопки по y 
		    buttonCreateChart.setText("Создание графика");		//надпись внутри кнопки
	//-------------------задания действия кнопки--------------------		
		    buttonCreateChart.setOnAction(new EventHandler<ActionEvent>() {public void handle(ActionEvent event) { 
				
				try {
					ReadCourse rc;
					if(workFile != null) { rc = new ReadCourse(workFile.getPath());}
					else {
					rc = new ReadCourse();
					}
					chartGroup.getChildren().clear(); 	//отчистка уже построенного графика
					if(datePickerFirstDate.getValue() == null && datePickerLastDate.getValue() != null || datePickerFirstDate.getValue() != null && datePickerLastDate.getValue() == null) 
					{alertErrorDiapazonOneNum();}
					else {
						if(datePickerFirstDate.getValue() == null && datePickerLastDate.getValue() == null) {
							CreatePolinom chart = new CreatePolinom();											//создание полинома
							chartGroup.getChildren().add(chart.getGraph(rc.getDoubleArrayEuroAndDate(),rc.dateCoofic,rc.cooficCourse,rc.minCourse,rc.firstDate,rc.lastDate,rc.maxCourse));	//группировка для вывода на график
																		
							root.getChildren().add(chartGroup);
						}
						else if(rc.getDayCount(	simpleDateFormat.parse( 									//вызов метода подсчета разницы между двумя датами для проверки
														datePickerFirstDate.getValue().format(formatter)),	//если первая дата больше второй, то выводится окно ошибки,  
												simpleDateFormat.parse(										//иначе выводится график
														datePickerLastDate.getValue().format(formatter))
										 	 ) > 0) {
								CreatePolinom chart = new CreatePolinom();											//создание полинома
								chartGroup.getChildren().add(chart.getGraph(rc.getDoubleArrayEuroAndDate(	//группировка для вывода на график
										datePickerFirstDate.getValue().format(formatter),
										datePickerLastDate.getValue().format(formatter)),rc.dateCoofic,rc.cooficCourse,rc.minCourse,rc.firstDate,rc.lastDate,rc.maxCourse));
								int coordinat [] = new int [] {40,200, 40,170, 40,140, 40,110, 40,80, 40,50,       70,220, 130,220, 190,220, 250,220, 310,220, 370,220};
								int j = 0;
								for(int i = 0; i <= chart.getNumGraph().size() - 1; i++){
									Text text = chart.getNumGraph().get(i);
									text.setX(coordinat[j]); j++;
									text.setY(coordinat[j]); j++;
									chartGroup.getChildren().add(text);
								}
								Text courseName = new Text (rc.nameCourse);
								courseName.setLayoutX(150);
								courseName.setLayoutY(40);
								chartGroup.getChildren().add(courseName);
								root.getChildren().add(chartGroup);
							} else {alertErrorDiapazon();}
					}//вывод ошибки
				} catch (IOException | InvalidFormatException | ParseException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException d) {
				}
				
			} });
	//---------------------конец задания действия кнопки-----------------------		
			
			Text firstDateText 	= new Text ("Начальная дата");
			firstDateText.setLayoutX(datePickerFirstDate.getLayoutX());
			firstDateText.setLayoutY(datePickerFirstDate.getLayoutY() - 5);
			dateDiagramm.getChildren().add(firstDateText);
			
			Text lastDateText 	= new Text ("Конечная дата");
			lastDateText.setLayoutX(datePickerLastDate.getLayoutX());
			lastDateText.setLayoutY(datePickerLastDate.getLayoutY() - 5);
			dateDiagramm.getChildren().add(lastDateText);
			
			
			Text dateText = new Text ("Дата");
			dateText.setLayoutX(415);
			dateText.setLayoutY(200);
			
			Text courseText = new Text ("Курс");
			courseText.setLayoutX(100);
			courseText.setLayoutY(40);
			
			root.getChildren().add(buttonChooseFile);
			root.getChildren().add(buttonCreateChart);
			root.getChildren().add(datePickerLastDate);
			root.getChildren().add(datePickerFirstDate);
			root.getChildren().add(dateDiagramm);
			root.getChildren().add(dateText);
			root.getChildren().add(courseText);
			
			for(int i = 0; i <= oXY.paintOXY().size() - 1; i++){
				root.getChildren().add(oXY.paintOXY().get(i));
			}//создание оси oXY
			
			for(int i = 0; i <= oXY.paintLineMarking().size() - 1; i++){
				root.getChildren().add(oXY.paintLineMarking().get(i));
			}
			
			
			Scene scene = new Scene(root,450,400); // создание сцены с размерами 400х400
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void alertErrorDiapazon() {
		Alert alert = new Alert(AlertType.ERROR);
		
		alert.setTitle("Ошибка ввода данных");
		alert.setHeaderText("Неправильно введён диапазон дат");
		alert.setContentText("Первая дата должна быть меньше последней");
		 
		alert.showAndWait();
	} 
	
	public void alertErrorDiapazonOneNum() {
		Alert alert = new Alert(AlertType.ERROR);
		
		alert.setTitle("Ошибка ввода данных");
		alert.setHeaderText("Неправильно введён диапазон дат");
		alert.setContentText("Для правильного ввода необходимо ввести начальную дату и конечную");
		 
		alert.showAndWait();
	} 
	
}

