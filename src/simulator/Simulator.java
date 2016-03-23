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

	public static final String INPUTFILE = "src/simulator/sprint1_chrono_test_data.txt";

	public static void main(String[] args) {
		try {
			ChronoTimer testChronoTimer = new ChronoTimer();
			Sensor[] sensors = new Sensor[ChronoTimer.DEFAULT_CHANNEL_COUNT];
			InputStream inStream = new FileInputStream(new File(INPUTFILE));
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(inStream));
			String singleLine;
			while ((singleLine = fileReader.readLine()) != null) {
				String[] singleLineCommand = singleLine.split("\t");
				testChronoTimer.setTime(singleLineCommand[0]);
				if (singleLineCommand[1].contains("TIME")) {
					testChronoTimer.setTime(singleLineCommand[0]);
					String[] timeArgs = singleLineCommand[1].split(" ");
					testChronoTimer.setTime(timeArgs[1]);
				} else if (singleLineCommand[1].contains("CONN")) {
					String[] connArgs = singleLineCommand[1].split(" ");
					int num = Integer.parseInt(connArgs[2]);
					if (num > 0 && num <= sensors.length && sensors[num - 1] == null) {
						// no sensor is connected on channel "num" right now so
						// we can connect a new Sensor to that channel
						if (connArgs[1].contains("GATE")) {
							sensors[num - 1] = new Sensor(SensorType.GATE);
						} else if (connArgs[1].contains("EYE")) {
							sensors[num - 1] = new Sensor(SensorType.EYE);
						} else if (connArgs[1].contains("PAD")) {
							sensors[num - 1] = new Sensor(SensorType.PAD);
						}

						if (sensors[num - 1] != null) {
							testChronoTimer.connectSensor(sensors[num - 1], num);
						}
					}
				} else if (singleLineCommand[1].contains("DISC")) {
					String[] discArgs = singleLineCommand[1].split(" ");
					int num = Integer.parseInt(discArgs[1]);
					testChronoTimer.disconnectSensor(num);
					sensors[num - 1] = null;
				} else if (singleLineCommand[1].contains("ON")) {
					testChronoTimer.powerOn();
				} else if (singleLineCommand[1].contains("OFF")) {
					testChronoTimer.powerOff();
				} else if (singleLineCommand[1].contains("EVENT")) {
					String[] eventArgs = singleLineCommand[1].split(" ");
					if (eventArgs[1] == "IND") {
						Event newEvent = new Event(EventType.IND);
						testChronoTimer.newEvent(newEvent);
					}
				} else if (singleLineCommand[1].contains("TOGGLE")) {
					String[] toggleArgs = singleLineCommand[1].split(" ");
					testChronoTimer.toggleChannel(Integer.parseInt(toggleArgs[1]));
				} else if (singleLineCommand[1].contains("NUM")) {
					String[] racerArgs = singleLineCommand[1].split(" ");
					Racer newRacer = new Racer(Integer.parseInt(racerArgs[1]));
					testChronoTimer.addRacerToCurrentRun(newRacer);
				} else if (singleLineCommand[1].contains("TRIG")) {
					String[] trigArgs = singleLineCommand[1].split(" ");
					int num = Integer.parseInt(trigArgs[1]);
					if (num > 0 && num <= sensors.length) {
						sensors[num - 1].trigger();
					}
				} else if (singleLineCommand[1].contains("PRINT")) {
					testChronoTimer.printCurrentRun();
				} else if (singleLineCommand[1].contains("ENDRUN")) {
					testChronoTimer.endRunCurrentEvent();
				} else if (singleLineCommand[1].contains("NEWRUN")) {
					testChronoTimer.newRunCurrentEvent();
				} else if (singleLineCommand[1].contains("RESET")) {
					testChronoTimer.reset();
				} else if (singleLineCommand[1].contains("EXIT")) {
					System.out.println("Exiting simulator...");
					break;
				} else {
					System.out.println("Unknown command: " + singleLineCommand[1]);
				}
			}

			fileReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + INPUTFILE + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + INPUTFILE + "'");
		}

	}
}