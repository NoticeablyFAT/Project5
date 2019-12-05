import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javafx.geometry.Point2D;

public class Centipede {
	private LinkedHashMap<Point2D, Character> map;
	
	private String direction = "right";
	private ArrayList<String> stations = new MesoFileReader().getStations();
	private ArrayList<Character> allCharacters = new ArrayList<Character>();
	private Point2D headPosition;
	private Point2D tailPosition;
	
	private Point2D nextLetterPos;
	private Character nextLetter;
	private String condition = "";
	
	public Centipede() {
		map = new LinkedHashMap<Point2D, Character>();
		
		
		for (int i = 0; i < stations.size() ; ++i) {

			for (int j = 0; j < 4; ++j) {
				allCharacters.add(stations.get(i).charAt(j));

			}
		}
		
		
		headPosition = new Point2D(0,0);
		map.put(headPosition, allCharacters.get(0));
		
		allCharacters.remove(0);

		newChar();
		
		

	}

	@Override
	public String toString() {
		
		if(condition.equals("You win!") || condition.equals("You lose!")) {
			return condition;
		}
		
		
		String complete2 = "";
		for (double i = 0; i < 15; ++i) {
			String temp = "                                                            ";
			StringBuilder temp2 = new StringBuilder(temp);

			Iterator<Map.Entry<Point2D, Character>> itr = map.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<Point2D, Character> entry = itr.next();
				if (entry.getKey().getY() == i) {
					temp2.setCharAt((int) entry.getKey().getX(), entry.getValue());
				}

			}
			
			
			if(nextLetterPos.getY() == i) {
				temp2.setCharAt((int) nextLetterPos.getX(), nextLetter);
			}
			
			
			
			if (i <= 13.0) {
				String temp3 = temp2.toString();
				complete2 += (temp3 + "\n");
			} else {
				complete2 += temp2;
			}

		}
		
		
		
		

		return complete2;

	}

	public void advance() {
		
		Point2D speculation;
		
		
		
		
		if(direction.equals("right")) {
			speculation = new Point2D(headPosition.getX() + 1, headPosition.getY());
			if(speculation.getX() == 60) {
				speculation = new Point2D(0, headPosition.getY());
			}
			else if(speculation.getY() == 15) {
				speculation = new Point2D(headPosition.getX(), 0);
			}
			else if(speculation.getX() == -1) {
				speculation = new Point2D(59, headPosition.getY());
			}
			else if(speculation.getY() == -1) {
				speculation = new Point2D(headPosition.getX(), 14);
			}
			
			if(map.containsKey(speculation)) {
				condition = "You lose!";
			}
			else if(speculation.equals(nextLetterPos)) {
				map.put(tailPosition, nextLetter);
				newChar();
				shift(speculation);
			}
			else {
				shift(speculation);
				
				
			}
			
			
			
			
			
		}
		else if(direction.equals("left")) {
			speculation = new Point2D(headPosition.getX() - 1, headPosition.getY());
			if(speculation.getX() == 60) {
				speculation = new Point2D(0, headPosition.getY());
			}
			else if(speculation.getY() == 15) {
				speculation = new Point2D(headPosition.getX(), 0);
			}
			else if(speculation.getX() == -1) {
				speculation = new Point2D(59, headPosition.getY());
			}
			else if(speculation.getY() == -1) {
				speculation = new Point2D(headPosition.getX(), 14);
			}
			
			if(map.containsKey(speculation)) {
				condition = "You lose!";
			}
			else if(speculation.equals(nextLetterPos)) {
				map.put(tailPosition, nextLetter);
				newChar();
				shift(speculation);
			}
			else {
				shift(speculation);
				
				
			}
			
			
		}
		else if(direction.equals("up")) {
			speculation = new Point2D(headPosition.getX(), headPosition.getY() - 1);
			if(speculation.getX() == 60) {
				speculation = new Point2D(0, headPosition.getY());
			}
			else if(speculation.getY() == 15) {
				speculation = new Point2D(headPosition.getX(), 0);
			}
			else if(speculation.getX() == -1) {
				speculation = new Point2D(59, headPosition.getY());
			}
			else if(speculation.getY() == -1) {
				speculation = new Point2D(headPosition.getX(), 14);
			}
			
			if(map.containsKey(speculation)) {
				condition = "You lose!";
			}
			else if(speculation.equals(nextLetterPos)) {
				map.put(tailPosition, nextLetter);
				newChar();
				shift(speculation);
			}
			else {
				shift(speculation);
				
				
			}
			
		}
		else if(direction.equals("down")) {
			speculation = new Point2D(headPosition.getX(), headPosition.getY() + 1);
			if(speculation.getX() == 60) {
				speculation = new Point2D(0, headPosition.getY());
			}
			else if(speculation.getY() == 15) {
				speculation = new Point2D(headPosition.getX(), 0);
			}
			else if(speculation.getX() == -1) {
				speculation = new Point2D(59, headPosition.getY());
			}
			else if(speculation.getY() == -1) {
				speculation = new Point2D(headPosition.getX(), 14);
			}
			
			if(map.containsKey(speculation)) {
				condition = "You lose!";
			}
			else if(speculation.equals(nextLetterPos)) {
				map.put(tailPosition, nextLetter);
				newChar();
				shift(speculation);
			}
			else {
				shift(speculation);
				
				
			}
			
		}
		
		
		
		
		
		
		
	}
	
	private void newChar() {
		
		if(allCharacters.get(0).equals(null)) {
			condition = "You win!";
		}
		
		else {
		
		nextLetter = allCharacters.get(0);
		allCharacters.remove(0);
		
		nextLetterPos = randGen();
		}
		
		
		
		
	}
	
	
	
	
	private Point2D randGen() {
		Random xRandom = new Random();
		Random yRandom = new Random();
		
		int x = xRandom.nextInt(60);
		int y = yRandom.nextInt(15);
		
		Point2D point = new Point2D(x,y);
		if(map.containsKey(point)) {
			randGen();
		}
		
		return point;
		
		
		
		
		
	}
	
	
	
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
	
	
	
	
	
	public void setDirection(String input) {
		direction = input;
	}

}
