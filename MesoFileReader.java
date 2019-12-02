import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MesoFileReader {

	private ArrayList<String> stations;
	private String fileName = "Mesonet.txt";

	public MesoFileReader() {
		stations = new ArrayList<String>();
		String line = "";

		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName)); // Initializes buffered reader to read file
																				
			line = br.readLine();
			while (line != null) { // Reads file and adds all station ID's to strings arraylist

				stations.add(line.substring(0, 4));
				line = br.readLine();

			}
			br.close();
		} catch (IOException e) { // Catches exception if file is not found
			System.out.println("File not found.");
		}

	}
	
	
	
	

}
