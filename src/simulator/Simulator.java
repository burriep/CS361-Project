package simulator;

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
					String[] connArgs = singleLineCommand[1].split(" ");
					Sensor connSens = null;
					if (connArgs[1].contains("GATE")) {
						connSens = new Sensor(SensorType.GATE);
					} else if (connArgs[1].contains("EYE")) {
						connSens = new Sensor(SensorType.EYE);
					}
					testChronoTimer.connectSensor(connSens, (Integer.parseInt(connArgs[2])));
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
					/*
					 * 
					 * ADD RACER TO QUEUE IN EVENT/CHRONOTIMER
					 */

				} else if (singleLineCommand[1].contains("TRIG")) {
					String[] trigArgs = singleLineCommand[1].split(" ");
					/*
					 * 
					 * TRIGGER CHANNEL BASED ON NUMBER
					 */

				} else if (singleLineCommand[1].contains("PRINT")) {
					testChronoTimer.printData();
				} else if (singleLineCommand[1].contains("ENDRUN")) {
					/*
					 * 
					 * HAVE RUN END
					 */

				} else if (singleLineCommand[1].contains("NEWRUN")) {
					Run newRun = new Run();
					/*
					 * 
					 * START A NEW RUN IN EVENT
					 */

				} else if (singleLineCommand[1].contains("RESET")) {
					testChronoTimer.reset();
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