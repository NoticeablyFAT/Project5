import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MesoGUI extends Application {

	@Override
	public void start(Stage mesoStage) throws Exception {
		Scene scene = null;
		Pane pane = null;
		GridPane gridPane = null;
		
		pane = new Pane();
		
		gridPane = new GridPane();
		gridPane.setHgap(20);
		gridPane.setVgap(20);
		
		scene = new Scene(gridPane);
		TextField enterHamm = new TextField();
		TextField enterHamm2 = new TextField();
		Slider hammSlider = new Slider(1,4,1);
		hammSlider.setMinorTickCount(0);
		hammSlider.setMajorTickUnit(1);
		hammSlider.setShowTickMarks(true);
		hammSlider.setShowTickLabels(true);
		hammSlider.setSnapToTicks(true);
		
		enterHamm.setText("Enter Hamming Dist:");
		enterHamm2.setText("Enter Hamming Dist:");

		enterHamm.setEditable(false);
		//enterHamm.setPrefColumnCount(2);
		gridPane.add(enterHamm, 0, 0);
		gridPane.add(enterHamm2, 20, 20);
		gridPane.add(hammSlider, 0, 1);
		
		
		
		mesoStage.setScene(scene);
		mesoStage.setTitle("Test");
		mesoStage.show();
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		launch(args);
	}

	

}
