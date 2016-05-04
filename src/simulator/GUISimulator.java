package simulator;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;
import javax.swing.Timer;

import chronotimer.ChronoTimer;
import chronotimer.RunType;
import chronotimer.Sensor;
import chronotimer.SensorType;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class GUISimulator extends JFrame {

	static ChronoTimer testChronoTimer;
	Sensor[] sensors = new Sensor[ChronoTimer.DEFAULT_CHANNEL_COUNT];
	private JPanel contentPane;
	JTextArea mainDisplay;
	JTextPane txtrPrinterarea;
	JTextArea funDisplay;
	JLabel powerStatus;
	private boolean getFunction = false;
	private boolean getRunNum = false;
	private boolean getClrNum = false;
	private String numPadSelection = "";
	private String printerString = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		testChronoTimer = new ChronoTimer();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUISimulator frame = new GUISimulator();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUISimulator() {
		setTitle("ChronoTimer 1009");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 646, 585);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		/**
		 * Each button only displays in the function text area while the
		 * ChronoTimer is on and waiting for a numeric input.
		 */

		JButton btn1 = new JButton("1");
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numPadSelection += "1";
				if (getRunNum) {
					funDisplay.setText("NUM: " + numPadSelection);
				} else if (getClrNum) {
					funDisplay.setText("CLR: " + numPadSelection);
				} else if (getFunction) {
					funDisplay.setText("FUNC: " + numPadSelection);
				}
			}
		});
		btn1.setBorder(null);
		btn1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn1.setBackground(Color.GRAY);
		btn1.setBounds(446, 185, 31, 30);
		contentPane.add(btn1);

		JButton btn2 = new JButton("2");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numPadSelection += "2";
				if (getRunNum) {
					funDisplay.setText("NUM: " + numPadSelection);
				} else if (getClrNum) {
					funDisplay.setText("CLR: " + numPadSelection);
				} else if (getFunction) {
					funDisplay.setText("FUNC: " + numPadSelection);
				}
			}
		});
		btn2.setBorder(null);
		btn2.setBackground(Color.GRAY);
		btn2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn2.setBounds(481, 185, 31, 30);
		contentPane.add(btn2);

		JButton btn3 = new JButton("3");
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numPadSelection += "3";
				if (getRunNum) {
					funDisplay.setText("NUM: " + numPadSelection);
				} else if (getClrNum) {
					funDisplay.setText("CLR: " + numPadSelection);
				} else if (getFunction) {
					funDisplay.setText("FUNC: " + numPadSelection);
				}
			}
		});
		btn3.setBorder(null);
		btn3.setBackground(Color.GRAY);
		btn3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn3.setBounds(514, 185, 31, 30);
		contentPane.add(btn3);

		JButton btn4 = new JButton("4");
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numPadSelection += "4";
				if (getRunNum) {
					funDisplay.setText("NUM: " + numPadSelection);
				} else if (getClrNum) {
					funDisplay.setText("CLR: " + numPadSelection);
				} else if (getFunction) {
					funDisplay.setText("FUNC: " + numPadSelection);
				}
			}
		});
		btn4.setBorder(null);
		btn4.setBackground(Color.GRAY);
		btn4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn4.setBounds(446, 217, 31, 30);
		contentPane.add(btn4);

		JButton btn5 = new JButton("5");
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numPadSelection += "5";
				if (getRunNum) {
					funDisplay.setText("NUM: " + numPadSelection);
				} else if (getClrNum) {
					funDisplay.setText("CLR: " + numPadSelection);
				} else if (getFunction) {
					funDisplay.setText("FUNC: " + numPadSelection);
				}
			}
		});
		btn5.setBorder(null);
		btn5.setBackground(Color.GRAY);
		btn5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn5.setBounds(481, 217, 31, 30);
		contentPane.add(btn5);

		JButton btn6 = new JButton("6");
		btn6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numPadSelection += "6";
				if (getRunNum) {
					funDisplay.setText("NUM: " + numPadSelection);
				} else if (getClrNum) {
					funDisplay.setText("CLR: " + numPadSelection);
				} else if (getFunction) {
					funDisplay.setText("FUNC: " + numPadSelection);
				}
			}
		});
		btn6.setBorder(null);
		btn6.setBackground(Color.GRAY);
		btn6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn6.setBounds(514, 217, 31, 30);
		contentPane.add(btn6);

		JButton btn7 = new JButton("7");
		btn7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numPadSelection += "7";
				if (getRunNum) {
					funDisplay.setText("NUM: " + numPadSelection);
				} else if (getClrNum) {
					funDisplay.setText("CLR: " + numPadSelection);
				} else if (getFunction) {
					funDisplay.setText("FUNC: " + numPadSelection);
				}
			}
		});
		btn7.setBorder(null);
		btn7.setBackground(Color.GRAY);
		btn7.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn7.setBounds(446, 249, 31, 30);
		contentPane.add(btn7);

		JButton btn8 = new JButton("8");
		btn8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numPadSelection += "8";
				if (getRunNum) {
					funDisplay.setText("NUM: " + numPadSelection);
				} else if (getClrNum) {
					funDisplay.setText("CLR: " + numPadSelection);
				} else if (getFunction) {
					funDisplay.setText("FUNC: " + numPadSelection);
				}
			}
		});
		btn8.setBorder(null);
		btn8.setBackground(Color.GRAY);
		btn8.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn8.setBounds(481, 249, 31, 30);
		contentPane.add(btn8);

		JButton btn9 = new JButton("9");
		btn9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numPadSelection += "9";
				if (getRunNum) {
					funDisplay.setText("NUM: " + numPadSelection);
				} else if (getClrNum) {
					funDisplay.setText("CLR: " + numPadSelection);
				} else if (getFunction) {
					funDisplay.setText("FUNC: " + numPadSelection);
				}
			}
		});
		btn9.setBorder(null);
		btn9.setBackground(Color.GRAY);
		btn9.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn9.setBounds(514, 249, 31, 30);
		contentPane.add(btn9);

		/**
		 * The '*' acts as a clear button for the current string that has been
		 * entered
		 */
		JButton btn10 = new JButton("*");
		btn10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numPadSelection = "";
			}
		});
		btn10.setBorder(null);
		btn10.setBackground(Color.GRAY);
		btn10.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn10.setBounds(446, 282, 31, 30);
		contentPane.add(btn10);

		JButton btn11 = new JButton("0");
		btn11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numPadSelection += "0";
				if (getRunNum) {
					funDisplay.setText("NUM: " + numPadSelection);
				} else if (getClrNum) {
					funDisplay.setText("CLR: " + numPadSelection);
				} else if (getFunction) {
					funDisplay.setText("FUNC: " + numPadSelection);
				}
			}
		});
		btn11.setBorder(null);
		btn11.setBackground(Color.GRAY);
		btn11.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn11.setBounds(481, 282, 31, 30);
		contentPane.add(btn11);

		/**
		 * The '#' button functions as an enter key for numeric input. Data is
		 * only entered while the ChronoTimer is waiting for a function
		 * selection, a racer number to add, or a racer number to delete.
		 */
		JButton btn12 = new JButton("#");
		btn12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (testChronoTimer.isOn()) {
					if (getFunction) {
						getFunction = false;
						setFunction(numPadSelection);
						numPadSelection = "";
					} else if (getRunNum) {
						getRunNum = false;
						testChronoTimer.addRacer(Integer.parseInt(numPadSelection));
						funDisplay.setText("Racer " + numPadSelection + " added");
					} else if (getClrNum) {
						getClrNum = false;
						testChronoTimer.clearRacer(Integer.parseInt(numPadSelection));
						funDisplay.setText("Racer " + numPadSelection + " cleared");
					}
				}
			}
		});
		btn12.setBorder(null);
		btn12.setBackground(Color.GRAY);
		btn12.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn12.setBounds(514, 282, 31, 30);
		contentPane.add(btn12);

		/**
		 * Section of buttons that allows channels to be enabled and disabled
		 */
		JRadioButton enable2 = new JRadioButton("");
		enable2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					testChronoTimer.toggleChannel(2);
				} catch (NullPointerException n) {

				}
			}
		});
		enable2.setBackground(new Color(255, 255, 255));
		enable2.setBounds(273, 144, 28, 25);
		contentPane.add(enable2);

		JRadioButton enable4 = new JRadioButton("");
		enable4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					testChronoTimer.toggleChannel(4);
				} catch (NullPointerException n) {

				}
			}
		});
		enable4.setBackground(new Color(255, 255, 255));
		enable4.setBounds(303, 144, 30, 25);
		contentPane.add(enable4);

		JRadioButton enable6 = new JRadioButton("");
		enable6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					testChronoTimer.toggleChannel(6);
				} catch (NullPointerException n) {

				}
			}
		});
		enable6.setBackground(new Color(255, 255, 255));
		enable6.setBounds(330, 144, 30, 25);
		contentPane.add(enable6);

		JRadioButton enable8 = new JRadioButton("");
		enable8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					testChronoTimer.toggleChannel(8);
				} catch (NullPointerException n) {

				}
			}
		});
		enable8.setBackground(new Color(255, 255, 255));
		enable8.setBounds(362, 144, 30, 25);
		contentPane.add(enable8);

		JRadioButton enable1 = new JRadioButton("");
		enable1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					testChronoTimer.toggleChannel(1);
				} catch (NullPointerException n) {

				}
			}
		});
		enable1.setBackground(Color.WHITE);
		enable1.setBounds(273, 80, 28, 25);
		contentPane.add(enable1);

		JRadioButton enable3 = new JRadioButton("");
		enable3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					testChronoTimer.toggleChannel(3);
				} catch (NullPointerException n) {

				}
			}
		});
		enable3.setBackground(Color.WHITE);
		enable3.setBounds(303, 80, 21, 25);
		contentPane.add(enable3);

		JRadioButton enable5 = new JRadioButton("");
		enable5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					testChronoTimer.toggleChannel(5);
				} catch (NullPointerException n) {

				}
			}
		});
		enable5.setBackground(Color.WHITE);
		enable5.setBounds(330, 80, 30, 25);
		contentPane.add(enable5);

		JRadioButton enable7 = new JRadioButton("");
		enable7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					testChronoTimer.toggleChannel(7);
				} catch (NullPointerException n) {

				}
			}
		});
		enable7.setBackground(Color.WHITE);
		enable7.setBounds(362, 80, 30, 25);
		contentPane.add(enable7);

		/**
		 * Button to turn the ChronoTimer on and off
		 */
		JButton btnPower = new JButton("Power");
		btnPower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (testChronoTimer.isOn()) {
					testChronoTimer.powerOff();
					powerStatus.setForeground(Color.BLACK);
				} else {
					testChronoTimer.powerOn();
					powerStatus.setForeground(Color.GREEN);
				}
			}
		});
		btnPower.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnPower.setBounds(60, 29, 91, 30);
		contentPane.add(btnPower);

		/**
		 * Button that allows racers to be swapped
		 */
		JButton btnSwap = new JButton("SWAP");
		btnSwap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				testChronoTimer.swapRacer();
			}
		});
		btnSwap.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSwap.setBounds(62, 276, 89, 27);
		contentPane.add(btnSwap);

		/**
		 * Button to toggle printer power
		 */
		JButton btnPrinterPwr = new JButton("Printer Pwr");
		btnPrinterPwr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (testChronoTimer.isOn()) {
					if (testChronoTimer.printerIsOn()) {
						testChronoTimer.getPrinter().powerOff();
						funDisplay.setText("PRINTER OFF");
					} else {
						testChronoTimer.getPrinter().powerOn();
						funDisplay.setText("PRINTER ON");
					}
				}
			}
		});
		btnPrinterPwr.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnPrinterPwr.setBounds(444, 21, 108, 25);
		contentPane.add(btnPrinterPwr);

		/**
		 * The main real time display for the ChronoTimer
		 */
		mainDisplay = new JTextArea();
		mainDisplay.setBorder(new MatteBorder(1, 1, 0, 1, (Color) new Color(0, 0, 0)));
		mainDisplay.setBounds(243, 183, 150, 115);
		mainDisplay.setEditable(false);
		contentPane.add(mainDisplay);
		Timer timer = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				mainDisplay.setText(testChronoTimer.getRunningDisplay());
			}
		});
		timer.start();

		/**
		 * This allows for function information to be displayed below the real
		 * time display.
		 */
		funDisplay = new JTextArea();
		funDisplay.setBorder(new MatteBorder(0, 1, 1, 1, (Color) new Color(0, 0, 0)));
		funDisplay.setBounds(243, 298, 150, 25);
		funDisplay.setEditable(false);
		contentPane.add(funDisplay);

		/**
		 * Printer display area, only display results of each run
		 */
		txtrPrinterarea = new JTextPane();
		txtrPrinterarea.setText("");
		txtrPrinterarea.setEditable(false);
		JScrollPane scrollPrinter = new JScrollPane(txtrPrinterarea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPrinter.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		scrollPrinter.setBounds(454, 60, 84, 80);
		contentPane.add(scrollPrinter);
		Timer timer2 = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (testChronoTimer.printerIsOn()) {
					txtrPrinterarea.setText(printerString);
				}
			}
		});
		timer2.start();

		/**
		 * Series of boxes that represent the sensors and type being connected
		 */
		JComboBox comboBox1 = new JComboBox();
		comboBox1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox1.getSelectedItem().toString().equals("(Disconnect)")) {
					testChronoTimer.disconnectSensor(1);
				} else if (comboBox1.getSelectedItem().toString().equals("GATE")) {
					sensors[0] = new Sensor(SensorType.GATE);
				} else if (comboBox1.getSelectedItem().toString().equals("EYE")) {
					sensors[0] = new Sensor(SensorType.EYE);
				} else if (comboBox1.getSelectedItem().toString().equals("PAD")) {
					sensors[0] = new Sensor(SensorType.PAD);
				}
				testChronoTimer.connectSensor(sensors[0], 1);
			}
		});
		comboBox1.setModel(new DefaultComboBoxModel(new String[] { "(Disconnect)", "GATE", "EYE", "PAD" }));
		comboBox1.setBounds(90, 411, 75, 22);
		contentPane.add(comboBox1);

		JComboBox comboBox3 = new JComboBox();
		comboBox3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox3.getSelectedItem().toString().equals("(Disconnect)")) {
					testChronoTimer.disconnectSensor(3);
				} else if (comboBox3.getSelectedItem().toString().equals("GATE")) {
					sensors[2] = new Sensor(SensorType.GATE);
				} else if (comboBox3.getSelectedItem().toString().equals("EYE")) {
					sensors[2] = new Sensor(SensorType.EYE);
				} else if (comboBox3.getSelectedItem().toString().equals("PAD")) {
					sensors[2] = new Sensor(SensorType.PAD);
				}
				testChronoTimer.connectSensor(sensors[2], 3);
			}
		});
		comboBox3.setModel(new DefaultComboBoxModel(new String[] { "(Disconnect)", "GATE", "EYE", "PAD" }));
		comboBox3.setBounds(165, 411, 75, 22);
		contentPane.add(comboBox3);

		JComboBox comboBox5 = new JComboBox();
		comboBox5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox5.getSelectedItem().toString().equals("(Disconnect)")) {
					testChronoTimer.disconnectSensor(5);
				} else if (comboBox5.getSelectedItem().toString().equals("GATE")) {
					sensors[4] = new Sensor(SensorType.GATE);
				} else if (comboBox5.getSelectedItem().toString().equals("EYE")) {
					sensors[4] = new Sensor(SensorType.EYE);
				} else if (comboBox5.getSelectedItem().toString().equals("PAD")) {
					sensors[4] = new Sensor(SensorType.PAD);
				}
				testChronoTimer.connectSensor(sensors[4], 5);
			}
		});
		comboBox5.setModel(new DefaultComboBoxModel(new String[] { "(Disconnect)", "GATE", "EYE", "PAD" }));
		comboBox5.setBounds(240, 411, 75, 22);
		contentPane.add(comboBox5);

		JComboBox comboBox7 = new JComboBox();
		comboBox7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox7.getSelectedItem().toString().equals("(Disconnect)")) {
					testChronoTimer.disconnectSensor(6);
				} else if (comboBox7.getSelectedItem().toString().equals("GATE")) {
					sensors[6] = new Sensor(SensorType.GATE);
				} else if (comboBox7.getSelectedItem().toString().equals("EYE")) {
					sensors[6] = new Sensor(SensorType.EYE);
				} else if (comboBox7.getSelectedItem().toString().equals("PAD")) {
					sensors[6] = new Sensor(SensorType.PAD);
				}
				testChronoTimer.connectSensor(sensors[6], 7);
			}
		});
		comboBox7.setModel(new DefaultComboBoxModel(new String[] { "(Disconnect)", "GATE", "EYE", "PAD" }));
		comboBox7.setBounds(315, 411, 75, 22);
		contentPane.add(comboBox7);

		JComboBox comboBox2 = new JComboBox();
		comboBox2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox2.getSelectedItem().toString().equals("(Disconnect)")) {
					testChronoTimer.disconnectSensor(2);
				} else if (comboBox2.getSelectedItem().toString().equals("GATE")) {
					sensors[1] = new Sensor(SensorType.GATE);
				} else if (comboBox2.getSelectedItem().toString().equals("EYE")) {
					sensors[1] = new Sensor(SensorType.EYE);
				} else if (comboBox2.getSelectedItem().toString().equals("PAD")) {
					sensors[1] = new Sensor(SensorType.PAD);
				}
				testChronoTimer.connectSensor(sensors[1], 2);
			}
		});
		comboBox2.setModel(new DefaultComboBoxModel(new String[] { "(Disconnect)", "GATE", "EYE", "PAD" }));
		comboBox2.setBounds(90, 447, 75, 22);
		contentPane.add(comboBox2);

		JComboBox comboBox4 = new JComboBox();
		comboBox4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox4.getSelectedItem().toString().equals("(Disconnect)")) {
					testChronoTimer.disconnectSensor(4);
				} else if (comboBox4.getSelectedItem().toString().equals("GATE")) {
					sensors[3] = new Sensor(SensorType.GATE);
				} else if (comboBox7.getSelectedItem().toString().equals("EYE")) {
					sensors[3] = new Sensor(SensorType.EYE);
				} else if (comboBox7.getSelectedItem().toString().equals("PAD")) {
					sensors[3] = new Sensor(SensorType.PAD);
				}
				testChronoTimer.connectSensor(sensors[3], 4);
			}
		});
		comboBox4.setModel(new DefaultComboBoxModel(new String[] { "(Disconnect)", "GATE", "EYE", "PAD" }));
		comboBox4.setBounds(165, 447, 75, 22);
		contentPane.add(comboBox4);

		JComboBox comboBox6 = new JComboBox();
		comboBox6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox6.getSelectedItem().toString().equals("(Disconnect)")) {
					testChronoTimer.disconnectSensor(6);
				} else if (comboBox6.getSelectedItem().toString().equals("GATE")) {
					sensors[5] = new Sensor(SensorType.GATE);
				} else if (comboBox6.getSelectedItem().toString().equals("EYE")) {
					sensors[5] = new Sensor(SensorType.EYE);
				} else if (comboBox6.getSelectedItem().toString().equals("PAD")) {
					sensors[5] = new Sensor(SensorType.PAD);
				}
				testChronoTimer.connectSensor(sensors[5], 6);
			}
		});
		comboBox6.setModel(new DefaultComboBoxModel(new String[] { "(Disconnect)", "GATE", "EYE", "PAD" }));
		comboBox6.setBounds(240, 447, 75, 22);
		contentPane.add(comboBox6);

		JComboBox comboBox8 = new JComboBox();
		comboBox8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox8.getSelectedItem().toString().equals("(Disconnect)")) {
					testChronoTimer.disconnectSensor(8);
				} else if (comboBox8.getSelectedItem().toString().equals("GATE")) {
					sensors[7] = new Sensor(SensorType.GATE);
				} else if (comboBox8.getSelectedItem().toString().equals("EYE")) {
					sensors[7] = new Sensor(SensorType.EYE);
				} else if (comboBox8.getSelectedItem().toString().equals("PAD")) {
					sensors[7] = new Sensor(SensorType.PAD);
				}
				testChronoTimer.connectSensor(sensors[7], 8);
			}
		});
		comboBox8.setModel(new DefaultComboBoxModel(new String[] { "(Disconnect)", "GATE", "EYE", "PAD" }));
		comboBox8.setBounds(315, 447, 75, 22);
		contentPane.add(comboBox8);

		/**
		 * Series of buttons that allow manual trigger of channels
		 */
		JButton start1 = new JButton("");
		start1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sensors[0].trigger();
				} catch (NullPointerException n) {

				}
			}
		});
		start1.setBounds(270, 62, 23, 18);
		contentPane.add(start1);

		JButton start3 = new JButton("");
		start3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sensors[2].trigger();
				} catch (NullPointerException n) {

				}
			}
		});
		start3.setBounds(301, 62, 23, 18);
		contentPane.add(start3);

		JButton start5 = new JButton("");
		start5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sensors[4].trigger();
				} catch (NullPointerException n) {

				}
			}
		});
		start5.setBounds(330, 62, 23, 18);
		contentPane.add(start5);

		JButton start7 = new JButton("");
		start7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sensors[6].trigger();
				} catch (NullPointerException n) {

				}
			}
		});
		start7.setBounds(360, 62, 23, 18);
		contentPane.add(start7);

		JButton finish2 = new JButton("");
		finish2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sensors[1].trigger();
					printerString += "\n" + testChronoTimer.printCurrentRun();
				} catch (NullPointerException n) {

				}
			}
		});
		finish2.setBounds(270, 125, 23, 18);
		contentPane.add(finish2);

		JButton finish4 = new JButton("");
		finish4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sensors[3].trigger();
					printerString += "\n" + testChronoTimer.printCurrentRun();
				} catch (NullPointerException n) {

				}
			}
		});
		finish4.setBounds(301, 125, 23, 18);
		contentPane.add(finish4);

		JButton finish6 = new JButton("");
		finish6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sensors[5].trigger();
					printerString += "\n" + testChronoTimer.printCurrentRun();
				} catch (NullPointerException n) {

				}
			}
		});
		finish6.setBounds(330, 125, 23, 18);
		contentPane.add(finish6);

		JButton finish8 = new JButton("");
		finish8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sensors[7].trigger();
					printerString += "\n" + testChronoTimer.printCurrentRun();
				} catch (NullPointerException n) {

				}
			}
		});
		finish8.setBounds(362, 125, 23, 18);
		contentPane.add(finish8);

		JButton leftArrow = new JButton("<");
		// STUB FOR LEFT ARROW
		leftArrow.setBounds(35, 214, 42, 23);
		contentPane.add(leftArrow);

		JButton rightArrow = new JButton(">");
		// STUB FOR RIGHT ARROW
		rightArrow.setBounds(80, 214, 42, 23);
		contentPane.add(rightArrow);

		JButton downArrow = new JButton("V");
		// STUB FOR DOWN ARROW
		downArrow.setBounds(126, 214, 42, 23);
		contentPane.add(downArrow);

		JButton upArrow = new JButton("^");
		// STUB FOR UP ARROW
		upArrow.setBounds(172, 214, 42, 23);
		contentPane.add(upArrow);

		/**
		 * Series of labels to display information for each button
		 */
		JLabel lblChan = new JLabel("CHAN");
		lblChan.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblChan.setBounds(31, 394, 46, 14);
		contentPane.add(lblChan);

		JLabel lblUsbPort = new JLabel("USB PORT");
		lblUsbPort.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUsbPort.setBounds(425, 423, 70, 14);
		contentPane.add(lblUsbPort);

		JLabel lblStart = new JLabel("Start");
		lblStart.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStart.setBounds(232, 64, 32, 14);
		contentPane.add(lblStart);

		JLabel lblEnabledisable = new JLabel("Enable/Disable");
		lblEnabledisable.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEnabledisable.setBounds(178, 84, 91, 14);
		contentPane.add(lblEnabledisable);

		JLabel lblFinish = new JLabel("Finish");
		lblFinish.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFinish.setBounds(230, 126, 38, 14);
		contentPane.add(lblFinish);

		JLabel label = new JLabel("Enable/Disable");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setBounds(178, 149, 91, 14);
		contentPane.add(label);

		JLabel lblQueueRunning = new JLabel("Queue / Running / Final Time");
		lblQueueRunning.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblQueueRunning.setBounds(250, 325, 135, 14);
		contentPane.add(lblQueueRunning);

		JLabel lblChronotimer = new JLabel("CHRONOTIMER 1009");
		lblChronotimer.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblChronotimer.setBounds(223, 27, 160, 14);
		contentPane.add(lblChronotimer);

		JButton usbPort = new JButton("");
		usbPort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				testChronoTimer.exportCurrentRun();
				// String inputValue =
				// JOptionPane.showInputDialog("Please input directory: ");

			}
		});
		usbPort.setBounds(432, 435, 40, 10);
		contentPane.add(usbPort);

		JLabel CHlabel_1 = new JLabel("1                  3                 5                 7");
		CHlabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		CHlabel_1.setBounds(96, 394, 300, 14);
		contentPane.add(CHlabel_1);

		JLabel CHlabel_2 = new JLabel("2                  4                 6                 8");
		CHlabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		CHlabel_2.setBounds(96, 434, 300, 14);
		contentPane.add(CHlabel_2);

		JLabel label_1 = new JLabel("1      3      5      7");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_1.setBounds(273, 45, 129, 14);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("2      4      6      8");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_2.setBounds(273, 110, 129, 14);
		contentPane.add(label_2);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lblNewLabel.setBounds(10, 380, 610, 143);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lblNewLabel_1.setBounds(10, 11, 610, 348);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewRun = new JLabel("1. NEW RUN");
		lblNewRun.setBounds(15, 82, 75, 16);
		contentPane.add(lblNewRun);

		JLabel lblEndRun = new JLabel("2. END RUN");
		lblEndRun.setBounds(15, 102, 67, 16);
		contentPane.add(lblEndRun);

		JLabel lblAddNum = new JLabel("3. NUM");
		lblAddNum.setBounds(15, 122, 75, 16);
		contentPane.add(lblAddNum);

		JLabel lblDnf = new JLabel("4. DNF");
		lblDnf.setBounds(15, 142, 56, 16);
		contentPane.add(lblDnf);

		JLabel lblClr = new JLabel("5. CLR");
		lblClr.setBounds(15, 162, 56, 16);
		contentPane.add(lblClr);

		JLabel lblEvent = new JLabel("EVENTS");
		lblEvent.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEvent.setBounds(100, 62, 56, 16);
		contentPane.add(lblEvent);

		JLabel lblInd = new JLabel("6. IND");
		lblInd.setBounds(100, 82, 56, 16);
		contentPane.add(lblInd);

		JLabel lblParind = new JLabel("7. PARIND");
		lblParind.setBounds(100, 102, 65, 16);
		contentPane.add(lblParind);

		JLabel lblGrp = new JLabel("8. GRP");
		lblGrp.setBounds(100, 122, 56, 16);
		contentPane.add(lblGrp);

		JLabel lblPargrp = new JLabel("9. PARGRP");
		lblPargrp.setBounds(100, 142, 65, 16);
		contentPane.add(lblPargrp);

		/**
		 * Function button that allows user to select a function for the
		 * ChronoTimer
		 */
		JButton btnFunction = new JButton("FUNCTION");
		btnFunction.setBounds(60, 181, 97, 25);
		btnFunction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numPadSelection = "";
				getFunction = true;
				funDisplay.setText("FUNC: " + numPadSelection);
			}
		});
		contentPane.add(btnFunction);

		JLabel lblFunctions = new JLabel("FUNCTIONS");
		lblFunctions.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFunctions.setBounds(15, 62, 89, 16);
		contentPane.add(lblFunctions);
		
		powerStatus = new JLabel("�");
		powerStatus.setFont(new Font("Tahoma", Font.BOLD, 16));
		powerStatus.setBounds(40, 35, 30, 16);
		powerStatus.setForeground(Color.BLACK);
		contentPane.add(powerStatus);

	}

	/**
	 * Function that updates the function display on the ChronoTimer and
	 * accesses the appropriate function information
	 * 
	 * @param functionSelection
	 */
	private void setFunction(String functionSelection) {
		if (functionSelection.equals("1")) {
			testChronoTimer.newRun();
			funDisplay.setText("New Run Started.");
		} else if (functionSelection.equals("2")) {
			testChronoTimer.endRun();
			funDisplay.setText("Current Run Ended.");
		} else if (functionSelection.equals("3")) {
			getRunNum = true;
			funDisplay.setText("NUM: ");
		} else if (functionSelection.equals("4")) {
			testChronoTimer.dnfRacer();
			funDisplay.setText("Current runner DNF.");
		} else if (functionSelection.equals("5")) {
			getClrNum = true;
			funDisplay.setText("CLR: ");
		} else if (functionSelection.equals("6")) {
			testChronoTimer.setEventType(RunType.IND);
			funDisplay.setText("Race type set: IND");
		} else if (functionSelection.equals("7")) {
			testChronoTimer.setEventType(RunType.PARIND);
			funDisplay.setText("Race type set: PARIND");
		} else if (functionSelection.equals("8")) {
			testChronoTimer.setEventType(RunType.GRP);
			funDisplay.setText("Race type set: GRP");
		} else if (functionSelection.equals("9")) {
			testChronoTimer.setEventType(RunType.PARGRP);
			funDisplay.setText("Race type set: PARGRP");
		} else {
			funDisplay.setText("ERROR: Invalid selection");
		}
		funDisplay.update(funDisplay.getGraphics());
	}
}
