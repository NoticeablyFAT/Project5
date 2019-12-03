import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MesoGUI extends Application {

	@Override
	public void start(Stage mesoStage) throws Exception {
		Scene scene = null;
		
		
		//pane
		GridPane gridPane = new GridPane();
		gridPane.setHgap(20);
		gridPane.setVgap(20);
		
		scene = new Scene(gridPane);
		
		
		
		//labels and text
		Label sliderLabel = new Label("Enter Hamming Dist: 1");
		TextField enterHamm2 = new TextField();
		
		Label enterHamm = new Label();

		enterHamm.setText("Enter Hamming Dist:");
		enterHamm.setPrefWidth(200);
		
		
		
		
		
		enterHamm2.setText("Enter Hamming Dist:");
		
		
		
		
		
		
		//slider
		
		Slider hammSlider = new Slider(1,4,1);
		hammSlider.setMinorTickCount(0);
		hammSlider.setMajorTickUnit(1);
		hammSlider.setShowTickMarks(true);
		hammSlider.setShowTickLabels(true);
		hammSlider.setSnapToTicks(true);
		
		
		hammSlider.valueProperty().addListener(new ChangeListener<Number>() {
			 
	         @Override
	         public void changed(ObservableValue<? extends Number> observable, //
	               Number oldValue, Number newValue) {
	 
	        	 sliderLabel.setText("Enter Hamming Dist: " + Integer.toString((int)hammSlider.getValue()));
	         }
	      });
		

		//text box
		TextArea box =  new TextArea();
		box.setPrefColumnCount(10);
		
		
		
		
		
		
		//show station button
		Button showStation = new Button("Show Station");
		
		
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
            	ArrayList<String> temp = new MesoCal().findHammStations((int)hammSlider.getValue(), "NRMN");
            	String tempString = "";
            	for(int i = 0; i < temp.size(); ++i) {
            		tempString += temp.get(i) + "\n";
            	}
                box.setText(tempString); 
            } 
        }; 
        
        showStation.setOnAction(event);
		
		
		
		
		
		
		
		
		
		
		
		
		//spinner
		
		
		
		
		
		
		
		
		
		

		
		//pane setup
		//gridPane.add(enterHamm, 0, 0);
		gridPane.add(sliderLabel, 0, 0);
		gridPane.add(hammSlider, 0, 1);
		gridPane.add(showStation, 0, 2);
		gridPane.add(box, 0, 3);
		gridPane.add(enterHamm2, 20, 20);
		
		
		
		
		mesoStage.setScene(scene);
		mesoStage.setTitle("Test");
		mesoStage.show();
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		launch(args);
	}

	

}
