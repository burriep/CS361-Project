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
	private Sensor[] sensors;
	private Sensor[] sensorButtons;
	private JButton[] manualTriggers;
	private JRadioButton[] channelToggles;
	private JComboBox[] channelDropdowns;
	private JButton[] numBtns;
	private JPanel contentPane;
	private JButton leftArrow;
	private JButton rightArrow;
	private JButton downArrow;
	private JButton upArrow;
	private JButton btnFunction;
	private JButton btnSwap;
	private JButton usbPort;
	private JButton numBtnClr;
	private JButton numBtnHash;
	private JTextArea mainDisplay;
	private JTextPane txtrPrinterarea;
	private JTextArea funDisplay;
	private JLabel powerStatus;
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
		testChronoTimer.powerOn();
		setTitle("ChronoTimer 1009");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 646, 585);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		sensors = new Sensor[8];
		sensorButtons = new Sensor[8];
		manualTriggers = new JButton[8];
		channelToggles = new JRadioButton[8];
		channelDropdowns = new JComboBox[8];
		numBtns = new JButton[10];

		setupNumbers();
		setupChannelToggles();
		setupPowerSwap();
		setupDisplay();
		setupChannelDropdowns();
		setupManualTriggers();
		setupFunctions();
		setupLabels();
		setupExport();

		testChronoTimer.powerOff();
	}

	public void setupNumbers() {
		/**
		 * Each button only displays in the function text area while the
		 * ChronoTimer is on and waiting for a numeric input.
		 */

		for (int i = 0; i < numBtns.length; i++) {
			numBtns[i] = new JButton(Integer.toString(i));
			numBtns[i].setEnabled(false);
			numBtns[i].addActionListener(new NumPadBtnListener(i));
			numBtns[i].setBorder(null);
			numBtns[i].setFont(new Font("Tahoma", Font.PLAIN, 14));
			numBtns[i].setBackground(Color.GRAY);
			contentPane.add(numBtns[i]);
		}
		numBtns[0].setBounds(481, 282, 31, 30);
		numBtns[1].setBounds(446, 185, 31, 30);
		numBtns[2].setBounds(481, 185, 31, 30);
		numBtns[3].setBounds(514, 185, 31, 30);
		numBtns[4].setBounds(446, 217, 31, 30);
		numBtns[5].setBounds(481, 217, 31, 30);
		numBtns[6].setBounds(514, 217, 31, 30);
		numBtns[7].setBounds(446, 249, 31, 30);
		numBtns[8].setBounds(481, 249, 31, 30);
		numBtns[9].setBounds(514, 249, 31, 30);

		/**
		 * The '*' acts as a clear button for the current string that has been
		 * entered
		 */
		numBtnClr = new JButton("*");
		numBtnClr.setEnabled(false);
		numBtnClr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numPadSelection = "";
			}
		});
		numBtnClr.setBorder(null);
		numBtnClr.setBackground(Color.GRAY);
		numBtnClr.setFont(new Font("Tahoma", Font.PLAIN, 14));
		numBtnClr.setBounds(446, 282, 31, 30);
		contentPane.add(numBtnClr);

		/**
		 * The '#' button functions as an enter key for numeric input. Data is
		 * only entered while the ChronoTimer is waiting for a function
		 * selection, a racer number to add, or a racer number to delete.
		 */
		numBtnHash = new JButton("#");
		numBtnHash.setEnabled(false);
		numBtnHash.addActionListener(new ActionListener() {
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
		numBtnHash.setBorder(null);
		numBtnHash.setBackground(Color.GRAY);
		numBtnHash.setFont(new Font("Tahoma", Font.PLAIN, 14));
		numBtnHash.setBounds(514, 282, 31, 30);
		contentPane.add(numBtnHash);
	}

	public void setupChannelToggles() {
		/**
		 * Section of buttons that allows channels to be enabled and disabled
		 */
		channelToggles[1] = new JRadioButton("");
		channelToggles[1].addActionListener(new ToggleChannelListener(2));
		channelToggles[1].setBackground(new Color(255, 255, 255));
		channelToggles[1].setBounds(273, 144, 28, 25);
		channelToggles[1].setEnabled(false);
		contentPane.add(channelToggles[1]);

		channelToggles[3] = new JRadioButton("");
		channelToggles[3].addActionListener(new ToggleChannelListener(4));
		channelToggles[3].setBackground(new Color(255, 255, 255));
		channelToggles[3].setBounds(303, 144, 30, 25);
		channelToggles[3].setEnabled(false);
		contentPane.add(channelToggles[3]);

		channelToggles[5] = new JRadioButton("");
		channelToggles[5].addActionListener(new ToggleChannelListener(6));
		channelToggles[5].setBackground(new Color(255, 255, 255));
		channelToggles[5].setBounds(330, 144, 30, 25);
		channelToggles[5].setEnabled(false);
		contentPane.add(channelToggles[5]);

		channelToggles[7] = new JRadioButton("");
		channelToggles[7].addActionListener(new ToggleChannelListener(8));
		channelToggles[7].setBackground(new Color(255, 255, 255));
		channelToggles[7].setBounds(362, 144, 30, 25);
		channelToggles[7].setEnabled(false);
		contentPane.add(channelToggles[7]);

		channelToggles[0] = new JRadioButton("");
		channelToggles[0].addActionListener(new ToggleChannelListener(1));
		channelToggles[0].setBackground(Color.WHITE);
		channelToggles[0].setBounds(273, 80, 28, 25);
		channelToggles[0].setEnabled(false);
		contentPane.add(channelToggles[0]);

		channelToggles[2] = new JRadioButton("");
		channelToggles[2].addActionListener(new ToggleChannelListener(3));
		channelToggles[2].setBackground(Color.WHITE);
		channelToggles[2].setBounds(303, 80, 21, 25);
		channelToggles[2].setEnabled(false);
		contentPane.add(channelToggles[2]);

		channelToggles[4] = new JRadioButton("");
		channelToggles[4].addActionListener(new ToggleChannelListener(5));
		channelToggles[4].setBackground(Color.WHITE);
		channelToggles[4].setBounds(330, 80, 30, 25);
		channelToggles[4].setEnabled(false);
		contentPane.add(channelToggles[4]);

		channelToggles[6] = new JRadioButton("");
		channelToggles[6].addActionListener(new ToggleChannelListener(7));
		channelToggles[6].setBackground(Color.WHITE);
		channelToggles[6].setBounds(362, 80, 30, 25);
		channelToggles[6].setEnabled(false);
		contentPane.add(channelToggles[6]);
	}

	public void setupPowerSwap() {
		/**
		 * Button to turn the ChronoTimer on and off
		 */
		JButton btnPower = new JButton("Power");
		btnPower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (testChronoTimer.isOn()) {
					testChronoTimer.powerOff();
					powerStatus.setForeground(Color.BLACK);
					// disable inputs
					for (int i = 0; i < channelToggles.length; ++i) {
						channelToggles[i].setSelected(false);
						channelToggles[i].setEnabled(false);
						channelDropdowns[i].setEnabled(false);
						manualTriggers[i].setEnabled(false);
					}
					for (int i = 0; i < numBtns.length; i++) {
						numBtns[i].setEnabled(false);
					}
					numBtnClr.setEnabled(false);
					numBtnHash.setEnabled(false);
					leftArrow.setEnabled(false);
					rightArrow.setEnabled(false);
					downArrow.setEnabled(false);
					upArrow.setEnabled(false);
					btnFunction.setEnabled(false);
					btnSwap.setEnabled(false);
					usbPort.setEnabled(false);
				} else {
					testChronoTimer.powerOn();
					// enable inputs
					powerStatus.setForeground(Color.GREEN);
					for (int i = 0; i < channelToggles.length; ++i) {
						channelToggles[i].setEnabled(true);
						channelDropdowns[i].setEnabled(true);
						manualTriggers[i].setEnabled(true);
					}
					for (int i = 0; i < numBtns.length; i++) {
						numBtns[i].setEnabled(true);
					}
					numBtnClr.setEnabled(true);
					numBtnHash.setEnabled(true);
					leftArrow.setEnabled(true);
					rightArrow.setEnabled(true);
					downArrow.setEnabled(true);
					upArrow.setEnabled(true);
					btnFunction.setEnabled(true);
					btnSwap.setEnabled(true);
					usbPort.setEnabled(true);
				}
			}
		});
		btnPower.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnPower.setBounds(60, 29, 91, 30);
		contentPane.add(btnPower);

		/**
		 * Button that allows racers to be swapped
		 */
		btnSwap = new JButton("SWAP");
		btnSwap.setEnabled(false);
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
	}

	public void setupDisplay() {
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
	}

	public void setupChannelDropdowns() {
		/**
		 * Series of boxes that represent the sensors and type being connected
		 */
		String[] options = { "(Disconnect)", "GATE", "EYE", "PAD" };
		for (int i = 0; i < channelDropdowns.length; i++) {
			channelDropdowns[i] = new JComboBox(options);
			channelDropdowns[i].addActionListener(new ChannelDropdownListener(i, i));
			contentPane.add(channelDropdowns[i]);
			channelDropdowns[i].setEnabled(false);
		}
		channelDropdowns[0].setBounds(90, 411, 75, 22);
		channelDropdowns[2].setBounds(165, 411, 75, 22);
		channelDropdowns[4].setBounds(240, 411, 75, 22);
		channelDropdowns[6].setBounds(315, 411, 75, 22);
		channelDropdowns[1].setBounds(90, 447, 75, 22);
		channelDropdowns[3].setBounds(165, 447, 75, 22);
		channelDropdowns[5].setBounds(240, 447, 75, 22);
		channelDropdowns[7].setBounds(315, 447, 75, 22);
	}

	public void setupManualTriggers() {
		/**
		 * Series of buttons that allow manual trigger of channels
		 */
		for (int i = 0; i < sensorButtons.length; i++) {
			sensorButtons[i] = new Sensor(SensorType.PUSH);
			testChronoTimer.connectButton(sensorButtons[i], (i + 1));
		}
		for (int i = 0; i < manualTriggers.length; i++) {
			manualTriggers[i] = new JButton("");
			manualTriggers[i].addActionListener(new SensorButtonListener(i));
			contentPane.add(manualTriggers[i]);
			manualTriggers[i].setEnabled(false);
		}
		manualTriggers[0].setBounds(270, 62, 23, 18);
		manualTriggers[2].setBounds(301, 62, 23, 18);
		manualTriggers[4].setBounds(330, 62, 23, 18);
		manualTriggers[6].setBounds(360, 62, 23, 18);
		manualTriggers[1].setBounds(270, 125, 23, 18);
		manualTriggers[3].setBounds(301, 125, 23, 18);
		manualTriggers[5].setBounds(330, 125, 23, 18);
		manualTriggers[7].setBounds(362, 125, 23, 18);
	}

	public void setupFunctions() {
		leftArrow = new JButton("<");
		leftArrow.setBounds(35, 214, 42, 23);
		contentPane.add(leftArrow);
		leftArrow.setEnabled(false);

		rightArrow = new JButton(">");
		rightArrow.setBounds(80, 214, 42, 23);
		contentPane.add(rightArrow);
		rightArrow.setEnabled(false);

		downArrow = new JButton("V");
		downArrow.setBounds(126, 214, 42, 23);
		contentPane.add(downArrow);
		downArrow.setEnabled(false);

		upArrow = new JButton("^");
		upArrow.setBounds(172, 214, 42, 23);
		contentPane.add(upArrow);
		upArrow.setEnabled(false);

		/**
		 * Function button that allows user to select a function for the
		 * ChronoTimer
		 */
		btnFunction = new JButton("FUNCTION");
		btnFunction.setEnabled(false);
		btnFunction.setBounds(60, 181, 97, 25);
		btnFunction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numPadSelection = "";
				getFunction = true;
				funDisplay.setText("FUNC: " + numPadSelection);
			}
		});
		contentPane.add(btnFunction);
	}

	public void setupLabels() {
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

	public void setupExport() {
		usbPort = new JButton("");
		usbPort.setEnabled(false);
		usbPort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				testChronoTimer.exportCurrentRun();
			}
		});
		usbPort.setBounds(432, 435, 40, 10);
		contentPane.add(usbPort);
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

	public class ToggleChannelListener implements ActionListener {
		private int channelNum;

		public ToggleChannelListener(int num) {
			channelNum = num;
		}

		public void actionPerformed(ActionEvent e) {
			testChronoTimer.toggleChannel(channelNum);
		}
	}

	public class SensorButtonListener implements ActionListener {
		private int sensorIndex;

		public SensorButtonListener(int index) {
			sensorIndex = index;
		}

		public void actionPerformed(ActionEvent e) {
			sensorButtons[sensorIndex].trigger();
		}
	}

	public class ChannelDropdownListener implements ActionListener {
		private int ci;
		private int si;

		public ChannelDropdownListener(int channelIndex, int sensorIndex) {
			ci = channelIndex;
			si = sensorIndex;
		}

		public void actionPerformed(ActionEvent e) {
			if (channelDropdowns[ci].getSelectedItem().toString().equals("(Disconnect)")) {
				testChronoTimer.disconnectSensor(ci + 1);
			} else if (channelDropdowns[ci].getSelectedItem().toString().equals("GATE")) {
				sensors[si] = new Sensor(SensorType.GATE);
				testChronoTimer.connectSensor(sensors[si], ci + 1);
			} else if (channelDropdowns[ci].getSelectedItem().toString().equals("EYE")) {
				sensors[si] = new Sensor(SensorType.EYE);
				testChronoTimer.connectSensor(sensors[si], ci + 1);
			} else if (channelDropdowns[ci].getSelectedItem().toString().equals("PAD")) {
				sensors[si] = new Sensor(SensorType.PAD);
				testChronoTimer.connectSensor(sensors[si], ci + 1);
			}
		}
	}

	public class NumPadBtnListener implements ActionListener {
		int num;

		public NumPadBtnListener(int number) {
			num = number;
		}

		public void actionPerformed(ActionEvent e) {
			numPadSelection += num;
			if (getRunNum) {
				funDisplay.setText("NUM: " + numPadSelection);
			} else if (getClrNum) {
				funDisplay.setText("CLR: " + numPadSelection);
			} else if (getFunction) {
				funDisplay.setText("FUNC: " + numPadSelection);
			}
		}
	}
}
