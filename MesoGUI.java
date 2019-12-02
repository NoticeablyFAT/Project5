import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MesoGUI extends Application {

	@Override
	public void start(Stage mesoStage) throws Exception {
		Scene scene = null;
		Pane pane = null;
		
		pane = new Pane();
		scene = new Scene(pane);
		TextField enterHamm = new TextField();
		
		enterHamm.setText("Enter Hamming Dist:");
		enterHamm.setEditable(false);
		//enterHamm.setPrefColumnCount(2);
		pane.getChildren().add(enterHamm);
		
		
		
		mesoStage.setScene(scene);
		mesoStage.setTitle("Test");
		mesoStage.show();
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		launch(args);
	}

	

}
