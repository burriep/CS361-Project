package simulator;

import java.awt.*;
import java.awt.Color;
import java.awt.Component;

import java.awt.Font;

import javax.swing.*;

public class SimulatorInterface extends JFrame {

	public SimulatorInterface() {
		setTitle("ChronoTimer");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		setup();
		setVisible(true);

	}

	public void setup() {
		JPanel mainLayout = new JPanel();
		mainLayout.setLayout(new GridLayout());
		mainLayout.setBorder(BorderFactory.createTitledBorder("ChronoTimer - Main Panel"));
		add(mainLayout);
		
		
		JPanel leftMain = new JPanel();
		leftMain.setLayout(new BorderLayout());
		mainLayout.add(leftMain, BorderLayout.WEST);
		
		JLabel header = new JLabel("ChronoTimer 1009");
		header.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font font = header.getFont();
		header.setFont(new Font(font.getFontName(), Font.BOLD, 20));
		mainLayout.add(header);
		
		JButton powerBtn = new JButton("Power");
		powerBtn.setLayout(null);
		powerBtn.setBounds(0, 0, 100, 20);
		JButton printerBtn = new JButton("Printer Pwr");
		JButton swapBtn = new JButton("SWAP");
		JButton functionBtn = new JButton("FUNCTION");
		leftMain.add(powerBtn);
		leftMain.add(printerBtn);
		leftMain.add(swapBtn);
		mainLayout.add(functionBtn);
		
		JPanel backLayout = new JPanel();
		backLayout.setLayout(new BoxLayout(backLayout, BoxLayout.Y_AXIS));
		backLayout.setBorder(BorderFactory.createTitledBorder("ChronoTimer - Back Panel"));
		add(backLayout, BorderLayout.SOUTH);
	}

}
