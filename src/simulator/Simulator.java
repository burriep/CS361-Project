package simulator;

/**
 * The Simulator is the class that reads in data from a text file whose name is specified
 * in the "inputFile" string and whose commands are in the format:
 * <TIMESTAMP>	<CMD> <ARGUMENT LIST> <EOL>
 * Where <TIMESTAMP> is in the format HH:MM:SS.Hundredth
 * 
 * The Simulator reads in the text file line by line through a BufferedReader, checks the power status of the ChronoTimer,
 * parses the time, parses the command and its arguments, and then executes the appropriate action.
 *
 */

import java.io.*;
import chronotimer.*;

public class Simulator {

	public static String inputFile = "src/simulator/sprint1_chrono_test_data.txt";
	public static ChronoTimer testChronoTimer = new ChronoTimer();

	public static void main(String[] args) {
		try {
			String inputFile = "src/simulator/sprint1_chrono_test_data.txt";
			InputStream inStream = new FileInputStream(new File(inputFile));
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(inStream));
			StringBuilder out = new StringBuilder();
			Timer systemTime = new Timer();
			String singleLine;
			while ((singleLine = fileReader.readLine()) != null) {
				String[] singleLineCommand = singleLine.split("\t");
				if (singleLineCommand[1].contains("TIME")) {
					systemTime.setTime(singleLineCommand[0]);
				} else if (singleLineCommand[1].contains("CONN")) {
					if (testChronoTimer.getPowerState()) {
						String[] connArgs = singleLineCommand[1].split(" ");
						Sensor connSens = null;
						if (connArgs[1].contains("GATE")) {
							connSens = new Sensor(SensorType.GATE);
						} else if (connArgs[1].contains("EYE")) {
							connSens = new Sensor(SensorType.EYE);
						}
						testChronoTimer.connectSensor(connSens, (Integer.parseInt(connArgs[2])));
					}
				} else if (singleLineCommand[1].contains("ON")) {
					testChronoTimer.powerOn();
				} else if (singleLineCommand[1].contains("OFF")) {
					testChronoTimer.powerOff();
				} else if (singleLineCommand[1].contains("EVENT")) {
					if (testChronoTimer.getPowerState()) {
						String[] eventArgs = singleLineCommand[1].split(" ");
						if (eventArgs[1] == "IND") {
							Event newEvent = new Event(EventType.IND);
							testChronoTimer.newEvent(newEvent);
						}
					}
				} else if (singleLineCommand[1].contains("TOGGLE")) {
					if (testChronoTimer.getPowerState()) {
						String[] toggleArgs = singleLineCommand[1].split(" ");
						testChronoTimer.toggleChannel(Integer.parseInt(toggleArgs[1]));
					}
				} else if (singleLineCommand[1].contains("NUM")) {
					if (testChronoTimer.getPowerState()) {
						String[] racerArgs = singleLineCommand[1].split(" ");
						Racer newRacer = new Racer(Integer.parseInt(racerArgs[1]));
						testChronoTimer.addRacerToCurrentRun(newRacer);
					}
				} else if (singleLineCommand[1].contains("TRIG")) {
					if (testChronoTimer.getPowerState()) {
						String[] trigArgs = singleLineCommand[1].split(" ");
						testChronoTimer.triggerChannel(Integer.parseInt(trigArgs[1]));
					}
				} else if (singleLineCommand[1].contains("PRINT")) {
					if (testChronoTimer.getPowerState() && testChronoTimer.getPrinterState()) {
						testChronoTimer.printData();
					}
				} else if (singleLineCommand[1].contains("ENDRUN")) {
					if (testChronoTimer.getPowerState()) {
						/*
						 * 
						 * HAVE RUN END
						 */
					}

				} else if (singleLineCommand[1].contains("NEWRUN")) {
					if (testChronoTimer.getPowerState()) {
						Run newRun = new Run();
						/*
						 * 
						 * START A NEW RUN IN EVENT
						 */
					}

				} else if (singleLineCommand[1].contains("RESET")) {
					if (testChronoTimer.getPowerState()) {
						testChronoTimer.reset();
					}
				} else if (singleLineCommand[1].contains("EXIT")) {
					System.out.println("Exiting simulator...");

				} else {
					System.out.println("Unknown command: " + singleLineCommand[1]);
				}
			}

			fileReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + inputFile + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + inputFile + "'");
		}

	}
}