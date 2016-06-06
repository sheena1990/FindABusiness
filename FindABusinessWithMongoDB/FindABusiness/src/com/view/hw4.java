package com.view;

import java.util.List;

import com.controller.Controller;
import com.model.dao.GenericDao;
import com.model.entity.Business;
import com.model.entity.CheckBoxItem;
import com.model.entity.Location;
import com.model.entity.Review;
import com.model.entity.SearchCriteria;
import com.util.Constants;
/*import com.model.entity.Business;

import com.model.entity.Review;
import com.model.entity.SearchCriteria;
import com.pages.Constants;*/
import com.util.Util;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class hw4 extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	
	Controller controller = new Controller();
	Stage mainWindow;

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
	    Label locationLabel = new Label("Point of Interest");
	    ChoiceBox<Location> locationChoiceBox = new ChoiceBox<>();
	    locationChoiceBox.getItems().addAll(util.createLocationList());
	    locationChoiceBox.setOnAction(e -> searchCriteria.setLocation(locationChoiceBox.getValue()));
	    VBox locationVbox = new VBox();
	    locationVbox.getChildren().addAll(locationLabel, locationChoiceBox);
	    
	    Label distanceLabel = new Label("proximity (in miles)");
	    ChoiceBox<Integer> distanceChoiceBox = new ChoiceBox<>();
	    distanceChoiceBox.getItems().addAll(5);
	    distanceChoiceBox.getItems().addAll(10);
	    distanceChoiceBox.getItems().addAll(20);
	    distanceChoiceBox.getItems().addAll(30);
	    distanceChoiceBox.getItems().addAll(50);
	    distanceChoiceBox.setOnAction(e -> searchCriteria.setMaxDistance(distanceChoiceBox.getValue()));
	    VBox distanceVbox = new VBox();
	    distanceVbox.getChildren().addAll(distanceLabel, distanceChoiceBox);

	    Label searchForLabel = new Label("Search for");
	    ChoiceBox<String> searchForChoiceBox = new ChoiceBox<>();
	    searchForChoiceBox.getItems().addAll(Constants.ALL);
	    searchForChoiceBox.getItems().addAll(Constants.ANY);
	    searchForChoiceBox.setOnAction(e -> searchCriteria.setSearchFor(searchForChoiceBox.getValue()));
	    VBox searchForVbox = new VBox();
	    searchForVbox.getChildren().addAll(searchForLabel, searchForChoiceBox);
	    
	    Label blankLabel = new Label("");
	    Button searchButton = new Button("Search");
	    searchButton.setOnAction(e -> displayBusiness());
	    VBox searchVbox = new VBox();
	    searchVbox.getChildren().addAll(blankLabel, searchButton);
	    
	    Button closeButton = new Button("Close");
	    closeButton.setOnAction(e -> mainWindow.close());
	    VBox closeVbox = new VBox();
	    closeVbox.getChildren().addAll(blankLabel, closeButton);
	    
	    hbox.getChildren().addAll(locationVbox, distanceVbox, searchForVbox, searchVbox, closeVbox);
	   return hbox;
	}

	private void displayBusiness() {
		if(centre.getChildren().size() > 2){
        	centre.getChildren().remove(2);
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

        TableColumn<Review, String> starsColumn = new TableColumn<>("Stars");
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
		ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> paramObservableValue, Boolean paramT1, Boolean selected) {
				//if(selected){
				if(!searchCriteria.getSelectedCategoryList().isEmpty()){
					searchCriteria.getSelectedCategoryList().clear();
				}
				if(centre.getChildren().size() > 2){
					centre.getChildren().remove(2);
				}
		        if(centre.getChildren().size() > 1){
		        	centre.getChildren().remove(1);
				}
					for (CheckBoxItem item : data) { 
						if(item.getSelected()){
							//System.out.println("category: " + item.getName());
							searchCriteria.getSelectedCategoryList().add(item.getName());
						}
					}
				final ObservableList<CheckBoxItem> attributeData = controller.getAttributes(searchCriteria.getSelectedCategoryList());
				final ListView<CheckBoxItem> attributeListView = createList(attributeData, Constants.ATTRIBUTE);
				centre.getChildren().add(1, attributeListView);
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
