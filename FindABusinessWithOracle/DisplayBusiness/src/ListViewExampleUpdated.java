import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxBuilder;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListViewExampleUpdated extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	final ObservableList<CheckBoxItem> data = FXCollections.observableArrayList();
	
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
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("CheckBoxListCell Sample");

		for (int i = 0; i < 5; i++) {
			data.add(new CheckBoxItem(false, "Sheena " + i));
		}

		final ListView<CheckBoxItem> listView = new ListView<CheckBoxItem>();
		listView.setPrefSize(200, 250);
		listView.setEditable(true);
		
		for (CheckBoxItem item : data) {
			item.selectedProperty().addListener(listener);
		}
		listView.setItems(data);
		
		Callback<CheckBoxItem, ObservableValue<Boolean>> getProperty = layer -> {return layer.selectedProperty();};
		
		Callback<ListView<CheckBoxItem>, ListCell<CheckBoxItem>> forListView = CheckBoxListCell.forListView(getProperty);
		listView.setCellFactory(forListView);

		StackPane root = new StackPane();
		VBox vb = new VBox();
		vb.getChildren().addAll(listView);
		root.getChildren().add(vb);
		primaryStage.setScene(new Scene(root, 200, 250));
		primaryStage.show();
	}
}