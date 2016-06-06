import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HandleEventExample extends Application implements EventHandler<ActionEvent> {

	Button button;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello world");
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Yelp Tool");
		button = new Button();
		button.setText("click me");
		button.setOnAction(this);
		
        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        
        primaryStage.show();
	}
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource() == button){
			System.out.println("clicked button.");
		}
	}

}
