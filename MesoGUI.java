import java.util.ArrayList;
import java.util.Comparator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MesoGUI extends Application {
	//Creates the centipede object and text area for it to run
	TextArea centi = new TextArea();
	Centipede pede = new Centipede();
	
	/* This method is called to update the game's frame. It moves things forward one frame when called.
	 * It calls the centipede methods toString to draw the game, and advance to update it again.
	 */
	
	private void incrementFrame() {
		centi.setText(pede.toString());
		pede.advance();

	}

	@Override
	public void start(Stage mesoStage) throws Exception {
		Scene scene = null;
		//Makes the textArea for centipede non editable.
		centi.setEditable(false);

		// Gridpane for set up and orientation of GUI
		GridPane gridPane = new GridPane();
		gridPane.setHgap(20);
		gridPane.setVgap(20);

		scene = new Scene(gridPane);

		// Labels and text. Fairly self explanatory.
		Label sliderLabel = new Label("Enter Hamming Dist: 1");
		Label compareWith = new Label("Compare with:");
		TextField enterStation = new TextField();

		Label distance0 = new Label("Distance 0: ");
		Label distance1 = new Label("Distance 1: ");
		Label distance2 = new Label("Distance 2: ");
		Label distance3 = new Label("Distance 3: ");
		Label distance4 = new Label("Distance 4: ");

		// The slider that allows hamming distance to be selected, as well as its change listener.

		Slider hammSlider = new Slider(1, 4, 1);
		hammSlider.setMinorTickCount(0);
		hammSlider.setMajorTickUnit(1);
		hammSlider.setShowTickMarks(true);
		hammSlider.setShowTickLabels(true);
		hammSlider.setSnapToTicks(true);

		hammSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				sliderLabel.setText("Enter Hamming Dist: " + Integer.toString((int) hammSlider.getValue()));
			}
		});

		// Box that displays the list of stations with the hamming distance selected by the slider.
		TextArea box = new TextArea();
		box.setPrefColumnCount(10);

		/* This is the box in which the game is played. The monospace font makes all characters the same size, preventing
		 * visual errors. The setPrefWidth and height configure the box to match the play space of 15 vertical and 60 horizontal spaces.
		 * Obviously this could be enlarged if desired, but I did not want to make it too big.
		 */

		centi.setStyle("-fx-font-family: 'monospaced';");
		centi.setPrefWidth(561);
		centi.setPrefHeight(315);

		/* This is the start button for the game. It begins the game, and also makes it visible. The button also disables itself
		 * after being pressed, because the game speeds up if it is pressed again, making it unplayable very quickly.
		 * The game updates .1 times per second, giving it a 10 frames per second framerate. Interestingly, the game itself is tied to the framerate.
		 * This is not uncommon in games, though it is generally undesireable, as it forces the framerate to be restricted.
		 * If the updates per second were increases, the centipede would be too fast to control easily.
		 * I believe this behavior is possibly due to the thread in which the game is running being left open when the game is lost.
		 * Shamelessly stolen from https://riptutorial.com/javafx/example/7291/updating-the-ui-using-platform-runlater
		 * This allows the GUI to be updated whilst the loop is running, so that the game can be played.
		 */
		Button start = new Button("Start");

		EventHandler<ActionEvent> startEvent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				start.setDisable(true);

				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						Runnable updater = new Runnable() {

							@Override
							public void run() {
								incrementFrame();

							}
						};

						while (true) {
							try {
								Thread.sleep(100);
							} catch (InterruptedException ex) {
							}

							// UI update is run on the Application thread
							Platform.runLater(updater);
						}
					}

				});
				// don't let thread prevent JVM shutdown
				thread.setDaemon(true);
				thread.start();

				

			}
		};

		start.setOnAction(startEvent);

		// This is the reset button. It resets the game to its initial state, so that it can be played again if lost.

		Button reset = new Button("Reset");

		EventHandler<ActionEvent> resetEvent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				pede = new Centipede();
			}
		};

		reset.setOnAction(resetEvent);

		// This is a combo box that displays all of the stations. It's initial text is NRMN.
		ArrayList<String> allStations = new MesoFileReader().getStations();
		ComboBox<String> combo = new ComboBox<String>(FXCollections.observableArrayList(allStations));
		combo.getSelectionModel().select("NRMN");

		/* This handles the Show Station button. When pressed, it simply finds all of the stations that match the hamming distance
		 * chosen on the slider, then shows shows that information on the textArea. 
		 */
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

		/* This handles the calculate HD button. It will call the MesoCal findHammStations method in order to calculate the results.
		 * It then updates the appropriate labels, and clears the textArea.
		 */
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

				box.clear();

			}
		};

		calculateHD.setOnAction(event2);

		/* The add station button. In addition to adding the station that is entered in the enterStation field,
		 * this event handler also makes sure the input is only four characters, has not been added before, and 
		 * then sorts and updates the combo box containing the list.
		 */
		Button addStation = new Button("Add Station");

		EventHandler<ActionEvent> event3 = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (enterStation.getText().length() == 4) {
					if (!allStations.contains(enterStation.getText())) {
						allStations.add(enterStation.getText());
						Comparator<? super String> c = null;
						allStations.sort(c);
						combo.setItems(FXCollections.observableArrayList(allStations));
					} else {
						Alert alert1 = new Alert(AlertType.INFORMATION, "Station list already contains this value.");
						alert1.showAndWait();
					}

				} else {
					Alert alert2 = new Alert(AlertType.ERROR, "Please enter a four character string.");
					alert2.showAndWait();
				}

			}
		};
		addStation.setOnAction(event3);

		// This adds all the various GUI components to the gridpane in the necessary layout
		gridPane.add(sliderLabel, 0, 0);
		gridPane.add(hammSlider, 0, 1, 2, 1);
		gridPane.add(showStation, 0, 2);
		gridPane.add(box, 0, 3, 2, 2);
		gridPane.add(compareWith, 0, 6);
		gridPane.add(combo, 1, 6);
		gridPane.add(calculateHD, 0, 7);
		gridPane.add(distance0, 0, 8);
		gridPane.add(distance1, 0, 9);
		gridPane.add(distance2, 0, 10);
		gridPane.add(distance3, 0, 11);
		gridPane.add(distance4, 0, 12);
		gridPane.add(addStation, 0, 13);
		gridPane.add(enterStation, 1, 13);
		gridPane.add(centi, 4, 2);
		gridPane.add(start, 4, 3);
		gridPane.add(reset, 4, 4);
		
		
		
		//This code handles input to control the game. The centipede is controlled using the WASD keys.

		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			if (key.getCode() == KeyCode.A) {
				pede.setDirection("left");
			}
		});

		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key1) -> {
			if (key1.getCode() == KeyCode.D) {
				pede.setDirection("right");
			}
		});
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key2) -> {
			if (key2.getCode() == KeyCode.W) {
				pede.setDirection("up");
			}
		});
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key3) -> {
			if (key3.getCode() == KeyCode.S) {
				pede.setDirection("down");
			}
		});
		
		
		//Creates GUI window, titles it, and makes it visible.
		mesoStage.setScene(scene);
		mesoStage.setTitle("Hamming Distance");
		mesoStage.show();

	}
	//Launches GUI
	public static void main(String[] args) {
		launch(args);
	}

}
