package lab5;

public class Driver {
	public static void main(String[] args) {
		for (int i = 0; i < 4; i++) {
			System.out.println("Test " + (i + 1));
			ATM myATM = new ATM();
			myATM.start();
			System.out.println("End of Test " + (i + 1));
		}
	}
}
