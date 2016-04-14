package simulator;

/**
 * The Simulator is the class that reads in data from a text file whose name is specified
 * in the "inputFile" string and whose commands are in the format:
 * <TIMESTAMP>	<CMD> <ARGUMENT LIST> <EOL>
 * Where <TIMESTAMP> is in the format HH:MM:SS.Hundredth
 * 
 */

import java.io.*;
import java.util.Scanner;

import chronotimer.*;

public class FileInputSimulator extends Simulator {
	public static void main(String[] args) {
		Scanner stdIn = new Scanner(System.in);
		String INPUTFILE = "";
		ChronoTimer testChronoTimer;
		Sensor[] sensors = new Sensor[ChronoTimer.DEFAULT_CHANNEL_COUNT];
		String singleLine;
		testChronoTimer = new ChronoTimer(true);
		System.out.print("Enter file name: ");
		INPUTFILE = stdIn.next();
		try {
			InputStream inStream = new FileInputStream(new File(INPUTFILE));
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(inStream));
			while ((singleLine = fileReader.readLine()) != null) {
				String[] singleLineCommand = singleLine.split("\t");
				parseLine(singleLineCommand[0], singleLineCommand[1], testChronoTimer, sensors);
				if (singleLineCommand[1].contains("EXIT")) {
					break;
				}
			}
			fileReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + INPUTFILE + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + INPUTFILE + "'");
		}
		stdIn.close();
	}
}