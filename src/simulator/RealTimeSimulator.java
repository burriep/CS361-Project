package simulator;

/**
 * The Simulator reads in the text file line by line through a BufferedReader, checks the power status of the ChronoTimer,
 * parses the time, parses the command and its arguments, and then executes the appropriate action.
 *
 */

import java.util.Scanner;

import chronotimer.*;

public class RealTimeSimulator {
	public static void main(String[] args) {
		Scanner stdIn = new Scanner(System.in);
		ChronoTimer testChronoTimer;
		Sensor[] sensors = new Sensor[ChronoTimer.DEFAULT_CHANNEL_COUNT];
		String singleLine;
		testChronoTimer = new ChronoTimer();
		System.out.print("Enter command (type EXIT to quit): ");
		singleLine = stdIn.nextLine();
		while (!singleLine.contains("EXIT")) {
			System.out.print(">> ");
			singleLine = stdIn.nextLine();
			parseLine(null, singleLine, testChronoTimer, sensors);
		}
		stdIn.close();
	}

	private static void parseLine(String time, String singleCommand, ChronoTimer testChronoTimer, Sensor[] sensors) {
		if (time != null) {
			testChronoTimer.setTime(time);
		}
		if (singleCommand.contains("TIME")) {
			String[] timeArgs = singleCommand.split(" ");
			testChronoTimer.setTime(timeArgs[1]);
		} else if (singleCommand.contains("CONN")) {
			String[] connArgs = singleCommand.split(" ");
			try {
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
			} catch (NumberFormatException e) {
				System.out.println("Invalid Channel Number");
			}
		} else if (singleCommand.contains("DISC")) {
			String[] discArgs = singleCommand.split(" ");
			int num = Integer.parseInt(discArgs[1]);
			testChronoTimer.disconnectSensor(num);
			sensors[num - 1] = null;
		} else if (singleCommand.contains("ON")) {
			testChronoTimer.powerOn();
		} else if (singleCommand.contains("OFF")) {
			testChronoTimer.powerOff();
		} else if (singleCommand.contains("EVENT")) {
			String[] eventArgs = singleCommand.split(" ");
			RunType et = RunType.IND;
			if (eventArgs.length >= 2) {
				if (eventArgs[1].equals("PARGRP")) {
					et = RunType.PARGRP;
				} else if (eventArgs[1].equals("GRP")) {
					et = RunType.GRP;
				} else if (eventArgs[1].equals("PARIND")) {
					et = RunType.PARIND;
				}
			}
			testChronoTimer.setEventType(et);
		} else if (singleCommand.contains("TOGGLE")) {
			String[] toggleArgs = singleCommand.split(" ");
			testChronoTimer.toggleChannel(Integer.parseInt(toggleArgs[1]));
		} else if (singleCommand.contains("NUM")) {
			String[] racerArgs = singleCommand.split(" ");
			testChronoTimer.addRacer(Integer.parseInt(racerArgs[1]));
		} else if (singleCommand.contains("TRIG")) {
			String[] trigArgs = singleCommand.split(" ");
			int num = Integer.parseInt(trigArgs[1]);
			if (num > 0 && num <= sensors.length && sensors[num - 1] != null) {
				sensors[num - 1].trigger();
			}
		} else if (singleCommand.contains("PRINT")) {
			testChronoTimer.printCurrentRun();
		} else if (singleCommand.contains("ENDRUN")) {
			testChronoTimer.endRun();
		} else if (singleCommand.contains("NEWRUN")) {
			testChronoTimer.newRun();
		} else if (singleCommand.contains("EXPORT")) {
			String[] exportArgs = singleCommand.split(" ");
			if (exportArgs.length > 1) {
				int num = Integer.parseInt(exportArgs[1]);
				testChronoTimer.exportRun(num);
			} else {
				testChronoTimer.exportCurrentRun();
			}
		} else if (singleCommand.contains("RESET")) {
			testChronoTimer.reset();
		} else if (singleCommand.contains("EXIT")) {
			System.out.println("Exiting simulator...");
		} else {
			System.out.println("Unknown command: " + singleCommand);
		}
	}
}
