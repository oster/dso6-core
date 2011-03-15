package fr.loria.ecoo.dso6.core;

import java.awt.*;
import javax.swing.*;

public class InfoWindow extends JFrame {
	protected JTextArea report;

	public InfoWindow() {
		setTitle("DSo6");
		setPreferredSize(new Dimension(600, 400));
		setLayout(new BorderLayout(1, 1));

		report = new JTextArea();
		report.setLineWrap(true);
		report.setEditable(false);

		add(new JScrollPane(report), BorderLayout.CENTER);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);

		pack();
	}

	public void enableClose() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
