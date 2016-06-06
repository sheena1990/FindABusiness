package com.pages;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class hw2 extends Application implements EventHandler<ActionEvent> {

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}*/

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Yelp Tool");
		BorderPane mainLayout = new BorderPane();
		/*		Button button = new Button();
		button.setText("click me");*/
/*		mainLayout.getChildren().add(button);*/
		mainLayout.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		mainLayout.setTop(new Text("Start"));
		mainLayout.setBottom(new Text("End"));
		
		//checkbox
		CheckBox checkBox1 = new CheckBox("Restaurants");
		CheckBox checkBox2 = new CheckBox("Grocery");
		mainLayout.setLeft(checkBox1);
		
		//list
		ListView<String> listView;
        listView = new ListView<String>();
        /*listView.getItems().addAll("Iron Man", "Titanic", "Contact", "Surrogates");*/
        //listView.getItems().addAll(checkBox1, checkBox2);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        /*ObservableList<String> movies;
        movies = listView.getSelectionModel().getSelectedItems();*/
		mainLayout.setRight(listView);
		
		//CheckBoxListCell
		//CheckBoxListCell<String> 
        /*
		Scene scene = new Scene(mainLayout, 300, 250);
        primaryStage.setScene(scene);*/
        
        primaryStage.show();
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

}
