package fr.loria.ecoo.dso6.core;

import java.awt.*;
import javax.swing.*;

public class InfoWindow extends JFrame {
	protected JTextArea report;

	public InfoWindow() {
		setLayout(new FlowLayout());

		report = new JTextArea();
		report.setLineWrap(true);

		add(report);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);

		pack();
	}

	public void enableClose() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
