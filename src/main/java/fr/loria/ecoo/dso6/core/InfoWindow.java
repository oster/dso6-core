package fr.loria.ecoo.dso6.core;

import java.awt.*;
import javax.swing.*;

public class InfoWindow extends JFrame {
	protected JTextArea report;
	protected JProgressBar progress;
	protected JPanel northPanel;
	protected JPanel centerPanel;
	
	public InfoWindow() {
		setTitle("DSo6");
		setPreferredSize(new Dimension(600, 400));
		setLayout(new BorderLayout(1, 1));

		centerPanel = new JPanel(new BorderLayout(1, 1));
		centerPanel.setBorder(BorderFactory.createTitledBorder("Log"));
		
		report = new JTextArea();
		report.setLineWrap(true);
		report.setEditable(false);

		centerPanel.add(new JScrollPane(report), BorderLayout.CENTER);
		add(centerPanel, BorderLayout.CENTER);
		
		northPanel = new JPanel(new BorderLayout(1, 1));
		
		progress = new JProgressBar(0, 100);
		progress.setStringPainted(true);
		northPanel.add(progress,BorderLayout.CENTER);
		
		northPanel.setBorder(BorderFactory.createTitledBorder("Progression"));
		add(northPanel, BorderLayout.NORTH);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);

		pack();
	}

	public void enableClose() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void updateProgressBar(int value){
		progress.setValue(value);
		this.validate();
	}
}
