package com.pages;

import com.controller.Controller;
import com.model.entity.CheckBoxItem;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
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
import javafx.util.Callback;

public class hw1 extends Application{

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}*/
	
/*	Controller controller = new Controller();
	final ObservableList<CheckBoxItem> categoryData = controller.getBusinessCategoryData();*/
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Yelp Tool");
		BorderPane mainLayout = new BorderPane();
		mainLayout.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		mainLayout.setTop(new Text("Start"));
		mainLayout.setBottom(new Text("Filters"));
		
		//listView
		final ListView<CheckBoxItem> listView = new ListView<CheckBoxItem>();
		listView.setPrefSize(200, 250);
		listView.setEditable(true);
		
/*		for (CheckBoxItem item : data) {
			item.selectedProperty().addListener(listener);
		}*/
		Controller controller = new Controller();
		final ObservableList<CheckBoxItem> categoryData = controller.getBusinessCategoryData();
		createListWithListener(categoryData);
		listView.setItems(categoryData);
		
		Callback<CheckBoxItem, ObservableValue<Boolean>> getProperty = layer -> {return layer.selectedProperty();};
		
		Callback<ListView<CheckBoxItem>, ListCell<CheckBoxItem>> forListView = CheckBoxListCell.forListView(getProperty);
		listView.setCellFactory(forListView);
        mainLayout.setCenter(listView);
        
		Scene scene = new Scene(mainLayout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public void createListWithListener(ObservableList<CheckBoxItem> data){

		//final ObservableList<CheckBoxItem> data = controller.getBusinessCategoryData();

		ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> paramObservableValue, Boolean paramT1, Boolean selected) {
				//if(selected){
					for (CheckBoxItem item : data) {
						if(item.getSelected()){
							System.out.println("name: " + item.getName());
						}
					}
				//}
			}
		};
		for (CheckBoxItem item : data) {
			item.selectedProperty().addListener(listener);
		}
	}
	
	/*	private ListView<CheckBoxItem> createCategoryList(){
	//listView
	final ListView<CheckBoxItem> businessCategoryListView = new ListView<CheckBoxItem>();
	// businessCategoryListView.setPrefSize(200, 250);
	businessCategoryListView.setEditable(true);
	final ObservableList<CheckBoxItem> categoryData = controller.getBusinessCategoryData();
	addCategoryListener(categoryData);
	businessCategoryListView.setItems(categoryData);

	Callback<CheckBoxItem, ObservableValue<Boolean>> getProperty = layer -> {
		return layer.selectedProperty();
	};

	Callback<ListView<CheckBoxItem>, ListCell<CheckBoxItem>> forListView = CheckBoxListCell
			.forListView(getProperty);
	businessCategoryListView.setCellFactory(forListView);
	return businessCategoryListView;
}*/

/*private ListView<CheckBoxItem> createSubcategoryList(){
	final ListView<CheckBoxItem> subcategoryListView = new ListView<CheckBoxItem>();
	subcategoryListView.setEditable(true);
	final ObservableList<CheckBoxItem> subcategoryData = controller.getSubcategoryData(selectedCategoryList);
	addSubcategoryListener(subcategoryData);
	subcategoryListView.setItems(subcategoryData);

	Callback<CheckBoxItem, ObservableValue<Boolean>> getProperty = layer -> {
		return layer.selectedProperty();
	};

	Callback<ListView<CheckBoxItem>, ListCell<CheckBoxItem>> forListView = CheckBoxListCell
			.forListView(getProperty);
	subcategoryListView.setCellFactory(forListView);
	return subcategoryListView;
}*/
	
}
