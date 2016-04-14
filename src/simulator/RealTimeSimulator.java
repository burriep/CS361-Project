package simulator;

/**
 * The Simulator reads in the text file line by line through a BufferedReader, checks the power status of the ChronoTimer,
 * parses the time, parses the command and its arguments, and then executes the appropriate action.
 *
 */

import java.util.Scanner;

import chronotimer.*;

public class RealTimeSimulator extends Simulator {
	public static void main(String[] args) {
		Scanner stdIn = new Scanner(System.in);
		ChronoTimer testChronoTimer;
		Sensor[] sensors = new Sensor[ChronoTimer.DEFAULT_CHANNEL_COUNT];
		String singleLine;
		testChronoTimer = new ChronoTimer();
		System.out.print("Enter command (type EXIT to quit): ");
		singleLine = stdIn.nextLine();
		parseLine(null, singleLine, testChronoTimer, sensors);
		while (!singleLine.contains("EXIT")) {
			System.out.print(">> ");
			singleLine = stdIn.nextLine();
			parseLine(null, singleLine, testChronoTimer, sensors);
		}
		stdIn.close();
	}
}
