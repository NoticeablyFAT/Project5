import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

		// pane
		GridPane gridPane = new GridPane();
		gridPane.setHgap(20);
		gridPane.setVgap(20);

		scene = new Scene(gridPane);

		// labels and text
		Label sliderLabel = new Label("Enter Hamming Dist: 1");
		Label compareWith = new Label("Compare with:");
		TextField enterHamm2 = new TextField();

		Label distance0 = new Label("Distance 0: ");
		Label distance1 = new Label("Distance 1: ");
		Label distance2 = new Label("Distance 2: ");
		Label distance3 = new Label("Distance 3: ");
		Label distance4 = new Label("Distance 4: ");

		enterHamm2.setText("Enter Hamming Dist:");

		// slider

		Slider hammSlider = new Slider(1, 4, 1);
		hammSlider.setMinorTickCount(0);
		hammSlider.setMajorTickUnit(1);
		hammSlider.setShowTickMarks(true);
		hammSlider.setShowTickLabels(true);
		hammSlider.setSnapToTicks(true);

		hammSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, //
					Number oldValue, Number newValue) {

				sliderLabel.setText("Enter Hamming Dist: " + Integer.toString((int) hammSlider.getValue()));
			}
		});

		// text box
		TextArea box = new TextArea();
		box.setPrefColumnCount(10);

		// combobox
		ArrayList<String> allStations = new MesoFileReader().getStations();
		ComboBox<String> combo = new ComboBox<String>(FXCollections.observableArrayList(allStations));
		combo.getSelectionModel().select("NRMN");

		// show station button
		Button showStation = new Button("Show Station");

		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				ArrayList<String> temp = new MesoCal().findHammStations((int) hammSlider.getValue(), combo.getValue());
				String tempString = "";
				for (int i = 0; i < temp.size(); ++i) {
					tempString += temp.get(i) + "\n";
				}
				box.setText(tempString);
			}
		};

		showStation.setOnAction(event);

		// Calculate HD button
		Button calculateHD = new Button("Calculate HD");

		EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				distance0.setText("Distance 0: 1");

				ArrayList<String> temp = new MesoCal().findHammStations(1, combo.getValue());
				distance1.setText("Distance 1: " + Integer.toString(temp.size()));

				temp = new MesoCal().findHammStations(2, combo.getValue());
				distance2.setText("Distance 2: " + Integer.toString(temp.size()));

				temp = new MesoCal().findHammStations(3, combo.getValue());
				distance3.setText("Distance 3: " + Integer.toString(temp.size()));

				temp = new MesoCal().findHammStations(4, combo.getValue());
				distance4.setText("Distance 4: " + Integer.toString(temp.size()));

			}
		};

		calculateHD.setOnAction(event2);

		// pane setup
		// gridPane.add(enterHamm, 0, 0);
		gridPane.add(sliderLabel, 0, 0);
		gridPane.add(hammSlider, 0, 1, 2, 1);
		gridPane.add(showStation, 0, 2);
		gridPane.add(box, 0, 3, 2, 2);
		gridPane.add(compareWith, 0, 6);
		gridPane.add(combo, 1, 6);
		gridPane.add(calculateHD, 0, 7);
		gridPane.add(enterHamm2, 20, 20);
		gridPane.add(distance0, 0, 8);
		gridPane.add(distance1, 0, 9);
		gridPane.add(distance2, 0, 10);
		gridPane.add(distance3, 0, 11);
		gridPane.add(distance4, 0, 12);

		mesoStage.setScene(scene);
		mesoStage.setTitle("Hamming Distance");
		mesoStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
