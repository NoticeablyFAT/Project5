import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import javafx.geometry.Point2D;

public class Centipede {
	private static final int INNER_X_OR_Y = 0;
	private static final int OUTER_Y = 14;
	private static final int OUTER_X = 59;
	private static final int X_OR_Y_OUTOFBOUNDS = -1;
	private static final int X_OUTOFBOUNDS = 60;
	private static final int Y_OUTOFBOUNDS = 15;

	private LinkedHashMap<Point2D, Character> map;

	private String direction = "right";
	private ArrayList<String> stations = new MesoFileReader().getStations();
	private ArrayList<Character> allCharacters = new ArrayList<Character>();
	private Point2D headPosition;
	private Point2D tailPosition;
	private Point2D nextLetterPos;
	private Character nextLetter;
	private String condition = "";

	/* Default contructor for centipede. Creates a character arraylist to store all the characters of the stations,
	 * then creates a linkedhashmap to store those characters, along with a Point2D to store their location.
	 */
	
	public Centipede() {
		map = new LinkedHashMap<Point2D, Character>();

		for (int i = INNER_X_OR_Y; i < stations.size(); ++i) {

			for (int j = INNER_X_OR_Y; j < 4; ++j) {
				allCharacters.add(stations.get(i).charAt(j));

			}
		}

		headPosition = new Point2D(INNER_X_OR_Y, INNER_X_OR_Y);
		map.put(headPosition, allCharacters.get(INNER_X_OR_Y));

		allCharacters.remove(0);

		newChar();

	}
	/* This method is responsible for drawing the location of the centipede and characters to a string, so that MesoGUI can present it.
	 * It works something like an old television, creating the output one row at a time. It places the characters where they should be 
	 * based on their x, y coordinates, and then creates the output. It also outputs whether or not the player has won or lost, based on the 
	 * condition variable.
	 */
	

	@Override
	public String toString() {

		if (condition.equals("You win!") || condition.equals("You lose!")) {
			return condition;
		}

		String completeString = "";
		for (double i = INNER_X_OR_Y; i < Y_OUTOFBOUNDS; ++i) {
			String emptyLine = "                                                            ";
			StringBuilder valueLine = new StringBuilder(emptyLine);

			Iterator<Map.Entry<Point2D, Character>> itr = map.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<Point2D, Character> entry = itr.next();
				if (entry.getKey().getY() == i) {
					valueLine.setCharAt((int) entry.getKey().getX(), entry.getValue());
				}

			}

			if (nextLetterPos.getY() == i) {
				valueLine.setCharAt((int) nextLetterPos.getX(), nextLetter);
			}

			if (i <= Y_OUTOFBOUNDS - 2) {
				String temp = valueLine.toString();
				completeString += (temp + "\n");
			} else {
				completeString += valueLine;
			}

		}

		return completeString;

	}
	
	/* Method responsible for moving the centipede ahead one frame. Generates a speculated movement, decides whether or not it will 
	 * push the centipede out of bounds, hit its own tail, or pick up a new letter. If the movement would be out of bounds, it simply wraps around
	 * to the other side of the box. It then updates the location of all parts of the centipede based on the approriate condition. 
	 */
	

	public void advance() {

		Point2D speculation;

		if (direction.equals("right")) {
			speculation = new Point2D(headPosition.getX() + 1, headPosition.getY());
			if (speculation.getX() == X_OUTOFBOUNDS) {
				speculation = new Point2D(INNER_X_OR_Y, headPosition.getY());
			} else if (speculation.getY() == Y_OUTOFBOUNDS) {
				speculation = new Point2D(headPosition.getX(), INNER_X_OR_Y);
			} else if (speculation.getX() == X_OR_Y_OUTOFBOUNDS) {
				speculation = new Point2D(OUTER_X, headPosition.getY());
			} else if (speculation.getY() == X_OR_Y_OUTOFBOUNDS) {
				speculation = new Point2D(headPosition.getX(), OUTER_Y);
			}

			if (map.containsKey(speculation)) {
				condition = "You lose!";
			} else if (speculation.equals(nextLetterPos)) {
				map.put(tailPosition, nextLetter);
				newChar();
				shift(speculation);
			} else {
				shift(speculation);

			}

		} else if (direction.equals("left")) {
			speculation = new Point2D(headPosition.getX() - 1, headPosition.getY());
			if (speculation.getX() == X_OUTOFBOUNDS) {
				speculation = new Point2D(INNER_X_OR_Y, headPosition.getY());
			} else if (speculation.getY() == Y_OUTOFBOUNDS) {
				speculation = new Point2D(headPosition.getX(), INNER_X_OR_Y);
			} else if (speculation.getX() == X_OR_Y_OUTOFBOUNDS) {
				speculation = new Point2D(OUTER_X, headPosition.getY());
			} else if (speculation.getY() == X_OR_Y_OUTOFBOUNDS) {
				speculation = new Point2D(headPosition.getX(), OUTER_Y);
			}

			if (map.containsKey(speculation)) {
				condition = "You lose!";
			} else if (speculation.equals(nextLetterPos)) {
				map.put(tailPosition, nextLetter);
				newChar();
				shift(speculation);
			} else {
				shift(speculation);

			}

		} else if (direction.equals("up")) {
			speculation = new Point2D(headPosition.getX(), headPosition.getY() - 1);
			if (speculation.getX() == X_OUTOFBOUNDS) {
				speculation = new Point2D(INNER_X_OR_Y, headPosition.getY());
			} else if (speculation.getY() == Y_OUTOFBOUNDS) {
				speculation = new Point2D(headPosition.getX(), INNER_X_OR_Y);
			} else if (speculation.getX() == X_OR_Y_OUTOFBOUNDS) {
				speculation = new Point2D(OUTER_X, headPosition.getY());
			} else if (speculation.getY() == X_OR_Y_OUTOFBOUNDS) {
				speculation = new Point2D(headPosition.getX(), OUTER_Y);
			}

			if (map.containsKey(speculation)) {
				condition = "You lose!";
			} else if (speculation.equals(nextLetterPos)) {
				map.put(tailPosition, nextLetter);
				newChar();
				shift(speculation);
			} else {
				shift(speculation);

			}

		} else if (direction.equals("down")) {
			speculation = new Point2D(headPosition.getX(), headPosition.getY() + 1);
			if (speculation.getX() == X_OUTOFBOUNDS) {
				speculation = new Point2D(INNER_X_OR_Y, headPosition.getY());
			} else if (speculation.getY() == Y_OUTOFBOUNDS) {
				speculation = new Point2D(headPosition.getX(), INNER_X_OR_Y);
			} else if (speculation.getX() == X_OR_Y_OUTOFBOUNDS) {
				speculation = new Point2D(OUTER_X, headPosition.getY());
			} else if (speculation.getY() == X_OR_Y_OUTOFBOUNDS) {
				speculation = new Point2D(headPosition.getX(), OUTER_Y);
			}

			if (map.containsKey(speculation)) {
				condition = "You lose!";
			} else if (speculation.equals(nextLetterPos)) {
				map.put(tailPosition, nextLetter);
				newChar();
				shift(speculation);
			} else {
				shift(speculation);

			}

		}

	}
	/* This method generates a new character if a previous one has been "eaten" and places it at a random location on the field.
	 * It also generates the win condition, if all characters are consumed. 
	 */
	
	private void newChar() {

		if (allCharacters.get(0).equals(null)) {
			condition = "You win!";
		}

		else {

			nextLetter = allCharacters.get(0);
			allCharacters.remove(0);

			nextLetterPos = randGen();
		}

	}
	/* Recursive method that generates a random point between 0 and 59 for the x coordinate, then between 0 and 14 for the y.
	 * if the coordinate is is already in use, it calls itself again.
	*/

	private Point2D randGen() {
		Random xRandom = new Random();
		Random yRandom = new Random();

		int x = xRandom.nextInt(X_OUTOFBOUNDS);
		int y = yRandom.nextInt(Y_OUTOFBOUNDS);

		Point2D point = new Point2D(x, y);
		if (map.containsKey(point)) {
			randGen();
		}

		return point;

	}
	
	/* This method is responsible for moving the line of characters. It is told where the head needs to be, then creates a new hashmap
	 * with every value being set to the previous one's location. 
	 */
	

	private void shift(Point2D newHead) {
		LinkedHashMap<Point2D, Character> newMap = new LinkedHashMap<Point2D, Character>();
		Point2D previousPoint = newHead;
		Iterator<Map.Entry<Point2D, Character>> itr = map.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<Point2D, Character> entry = itr.next();
			newMap.put(previousPoint, entry.getValue());

			previousPoint = entry.getKey();
		}
		headPosition = newHead;
		map = newMap;
	}
	
	
	//Simple method that allows the key press event handles in MesoGUI to choose the direction of the centipede.
	
	public void setDirection(String input) {
		direction = input;
	}

}
