package com.pages;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

import com.controller.Controller;
import com.model.entity.Business;
import com.model.entity.CheckBoxItem;
import com.model.entity.DayOfWeek;
import com.model.entity.Review;
import com.model.entity.SearchCriteria;
import com.util.Util;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

/**
 * Find a business based on user preferences
 * @author Sheen
 *
 */
public class hw3 extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
/*	Controller controller = new Controller();
	final ObservableList<CheckBoxItem> categoryData = controller.getBusinessCategoryData();*/
	Controller controller = new Controller();
	Stage mainWindow;
/*	List<String> selectedCategoryList = new ArrayList<>();
	List<String> selectedSubcategoryList = new ArrayList<>();
	List<String> selectedAttributeList = new ArrayList<>();*/
	SearchCriteria searchCriteria = new SearchCriteria();
	BorderPane mainLayout = new BorderPane();
	HBox centre = new HBox();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// main window
		mainWindow = primaryStage;
		mainWindow.setTitle("Yelp Tool");
		
		mainLayout.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		centre = createCentreBox();
		HBox footer = createFooter();
		
		//controller.initialize();
		
		//category listView
/*		final ObservableList<CheckBoxItem> categoryData = controller.getBusinessCategoryData();
		final ListView<CheckBoxItem> businessCategoryListView = createList(categoryData, Constants.CATEGORY);*/
		
		mainLayout.setTop(new Text("Find A Business"));
        mainLayout.setCenter(centre);
		mainLayout.setBottom(footer);
		Scene scene = new Scene(mainLayout, Constants.WIDTH, Constants.HEIGHT);
        mainWindow.setScene(scene);
        mainWindow.show();
	}
	
	private HBox createFooter() {
		HBox hbox = new HBox();
	    hbox.setPadding(new Insets(15, 12, 15, 12));
	    hbox.setSpacing(10);
	    hbox.setStyle("-fx-background-color: #336699;");
	    
	    Util util = new Util();
	    
	    ChoiceBox<String> dayChoiceBox = new ChoiceBox<>();
//	    dayChoiceBox.getItems().addAll("Monday", "Tuesday", "Wednesdy");
	    dayChoiceBox.getItems().addAll(util.createDaysList());
	    dayChoiceBox.setOnAction(e -> searchCriteria.setSelectedDay(dayChoiceBox.getValue()));
	    
	    ChoiceBox<String> fromChoiceBox = new ChoiceBox<>();
	    fromChoiceBox.getItems().addAll(util.createTimeList());
	    fromChoiceBox.setOnAction(e -> searchCriteria.setSelectedStartTime(fromChoiceBox.getValue()));
	    
	    ChoiceBox<String> toChoiceBox = new ChoiceBox<>();
	    toChoiceBox.getItems().addAll(util.createTimeList());
	    toChoiceBox.setOnAction(e -> searchCriteria.setSelectedEndTime(toChoiceBox.getValue()));
	    
	    ChoiceBox<String> searchForChoiceBox = new ChoiceBox<>();
	    searchForChoiceBox.getItems().addAll(Constants.ALL);
	    searchForChoiceBox.getItems().addAll(Constants.ANY);
	    searchForChoiceBox.setOnAction(e -> searchCriteria.setSearchFor(searchForChoiceBox.getValue()));
	    
	    Button searchButton = new Button("Search");
	    searchButton.setOnAction(e -> displayBusiness());
	    
	    Button closeButton = new Button("Close");
	    closeButton.setOnAction(e -> mainWindow.close());
	    
	    hbox.getChildren().addAll(dayChoiceBox, fromChoiceBox, toChoiceBox, searchForChoiceBox, searchButton, closeButton);
	    return hbox;
	}

	private void displayBusiness() {
		if(centre.getChildren().size() > 3){
        	centre.getChildren().remove(3);
        }
		
		//Business column
        TableColumn<Business, String> nameColumn = new TableColumn<>("Business");
        //nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //City column
        TableColumn<Business, Double> cityColumn = new TableColumn<>("City");
        //priceColumn.setMinWidth(100);
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

        //State column
        TableColumn<Business, String> stateColumn = new TableColumn<>("State");
        //quantityColumn.setMinWidth(100);
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        
        //Stars column
        TableColumn<Business, String> starsColumn = new TableColumn<>("Stars");
        //quantityColumn.setMinWidth(100);
        starsColumn.setCellValueFactory(new PropertyValueFactory<>("stars"));

        TableView<Business> table = new TableView<>();
        table.setItems(controller.getBusiness(searchCriteria));
        table.getColumns().addAll(nameColumn, cityColumn, stateColumn, starsColumn);
        table.setMinWidth(Constants.HALF_WIDTH);
        table.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (table.getSelectionModel().getSelectedItem() != null) {
                searchCriteria.setBusiness(newValue);
                createReviewWindow();
            }
        });
		centre.getChildren().add(table);
	}

	private void createReviewWindow() {
		StackPane reviewLayout = new StackPane();

		TableColumn<Review, String> dateColumn = new TableColumn<>("Review Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Review, Double> starsColumn = new TableColumn<>("Stars");
        starsColumn.setCellValueFactory(new PropertyValueFactory<>("stars"));

        TableColumn<Review, String> textColumn = new TableColumn<>("Review Text");
        textColumn.setMaxWidth(Constants.HALF_WIDTH);
        textColumn.setCellValueFactory(new PropertyValueFactory<>("text"));
        
        TableColumn<Review, String> userColumn = new TableColumn<>("User ID");
        userColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        
        TableColumn<Review, String> votesColumn = new TableColumn<>("Useful votes");
        votesColumn.setCellValueFactory(new PropertyValueFactory<>("votes_useful"));

        TableView<Review> table = new TableView<>();
        table.setItems(controller.getReview(searchCriteria));
        table.getColumns().addAll(dateColumn, starsColumn, textColumn, userColumn, votesColumn);
        //table.setMinWidth(Constants.HALF_WIDTH);
        reviewLayout.getChildren().add(table);
		Scene reviewScene = new Scene(reviewLayout, Constants.WIDTH, Constants.HEIGHT);
        mainWindow.setTitle("Reviews");
		mainWindow.setScene(reviewScene);
        mainWindow.show();
	}

	private HBox createCentreBox() {
		//category list
		final ObservableList<CheckBoxItem> categoryData = controller.getBusinessCategoryData();
		final ListView<CheckBoxItem> businessCategoryListView = createList(categoryData, Constants.CATEGORY);
		HBox hbox = new HBox();
	    hbox.setPadding(new Insets(15, 12, 15, 12));
	    hbox.setSpacing(10);
	    hbox.setStyle("-fx-background-color: #336699;");
	    hbox.getChildren().addAll(businessCategoryListView);
	    return hbox;
	}

	private void addCategoryListener(ObservableList<CheckBoxItem> data){

		//final ObservableList<CheckBoxItem> data = controller.getBusinessCategoryData();

		ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> paramObservableValue, Boolean paramT1, Boolean selected) {
				//if(selected){
				if(!searchCriteria.getSelectedCategoryList().isEmpty()){
					searchCriteria.getSelectedCategoryList().clear();
				}
				if(centre.getChildren().size() > 3){
					centre.getChildren().remove(3);
				}
		        if(centre.getChildren().size() > 2){
		        	centre.getChildren().remove(2);
		        	centre.getChildren().remove(1);
				}
					for (CheckBoxItem item : data) {
						if(item.getSelected()){
							//System.out.println("category: " + item.getName());
							searchCriteria.getSelectedCategoryList().add(item.getName());
						}
					}
/*							final ListView<CheckBoxItem> subcategoryListView = createSubcategoryList();
					        mainLayout.setCenter(subcategoryListView);
					        final ListView<CheckBoxItem> attributeListView = createAttributeList();
					        mainLayout.setRight(attributeListView);*/
							final List<ObservableList<CheckBoxItem>> displayLists = controller.getDisplayLists(searchCriteria.getSelectedCategoryList());
							if(!displayLists.isEmpty()){
							final ObservableList<CheckBoxItem> subcategoryData = displayLists.get(0);
							final ListView<CheckBoxItem> subcategoryListView = createList(subcategoryData, Constants.SUBCATEGORY);
					        //mainLayout.setCenter(subcategoryListView);
							centre.getChildren().add(1, subcategoryListView);
					        final ObservableList<CheckBoxItem> attributeData = displayLists.get(1);
					        final ListView<CheckBoxItem> attributeListView = createList(attributeData, Constants.ATTRIBUTE);
//					        mainLayout.setRight(attributeListView);
					        centre.getChildren().add(2, attributeListView);
							}
						
					
				//}
			}
		};
		for (CheckBoxItem item : data) {
			item.selectedProperty().addListener(listener);
		}
	}
	
	private void addSubcategoryListener(ObservableList<CheckBoxItem> data) {
		ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> paramObservableValue, Boolean paramT1,
					Boolean selected) {
				if(!searchCriteria.getSelectedSubcategoryList().isEmpty()){
					searchCriteria.getSelectedSubcategoryList().clear();
				}
				for (CheckBoxItem item : data) {
					if (item.getSelected()) {
						searchCriteria.getSelectedSubcategoryList().add(item.getName());
					}
				}
			}
		};
		for (CheckBoxItem item : data) {
			item.selectedProperty().addListener(listener);
		}
	}
	
	private void addAttributeListener(ObservableList<CheckBoxItem> data){
		ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> paramObservableValue, Boolean paramT1,
					Boolean selected) {
				if(!searchCriteria.getSelectedAttributeList().isEmpty()){
					searchCriteria.getSelectedAttributeList().clear();
				}
				for (CheckBoxItem item : data) {
					if (item.getSelected()) {
						searchCriteria.getSelectedAttributeList().add(item.getName());
					}
				}
			}
		};
		for (CheckBoxItem item : data) {
			item.selectedProperty().addListener(listener);
		}
	}

	protected ListView<CheckBoxItem> createList(ObservableList<CheckBoxItem> data, String type) {
		// TODO Auto-generated method stub
		final ListView<CheckBoxItem> listView = new ListView<CheckBoxItem>();
		listView.setEditable(true);
		if(type.equalsIgnoreCase(Constants.CATEGORY)){
			addCategoryListener(data);
		}
		else if(type.equalsIgnoreCase(Constants.SUBCATEGORY)){
			addSubcategoryListener(data);
		}
		else if(type.equalsIgnoreCase(Constants.ATTRIBUTE)){
			addAttributeListener(data);
		}
		listView.setItems(data);

		Callback<CheckBoxItem, ObservableValue<Boolean>> getProperty = layer -> {
			return layer.selectedProperty();
		};

		Callback<ListView<CheckBoxItem>, ListCell<CheckBoxItem>> forListView = CheckBoxListCell
				.forListView(getProperty);
		listView.setCellFactory(forListView);
		return listView;
	}
	
}
