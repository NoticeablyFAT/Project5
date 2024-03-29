import java.util.ArrayList;

/* Class runs calculations on the arraylist of stations generated by mesofilereader. 
 * All work is done in findHammStations method. Method returns an ArrayList of 
 * stations that have the requested hamming distance for the requested hamming station.
 * This allows it to perform both functions required by MesoGUI.
 */

public class MesoCal {

	public ArrayList<String> findHammStations(int hammDistance, String station) {
		MesoFileReader fullList = new MesoFileReader();
		ArrayList<String> allStations = fullList.getStations();
		ArrayList<String> hammStations = new ArrayList<String>();

		char[] charStation = station.toCharArray(); // Creates char array to compare strings

		for (int i = 0; i < allStations.size(); ++i) {

			int hammRelative = 0;

			char[] charInput1Init = new char[4];

			charInput1Init = allStations.get(i).toCharArray();

			for (int j = 0; j < charStation.length; ++j) { // Finds hamming distance for station relative to input
				if (charInput1Init[j] != charStation[j]) {

					++hammRelative;
				}

			}
			if (hammRelative == hammDistance) {		//If hamming distance is the same as requested distance, adds the station to a new arraylist
				hammStations.add(allStations.get(i));
			}

		}

		return hammStations;
	}

}
