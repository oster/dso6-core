package fr.loria.ecoo.dso6.core;

import java.awt.*;
import javax.swing.*;

public class InfoWindow extends JFrame {
	protected JTextArea report;
	protected JProgressBar progress;
	protected JPanel northPanel;
	protected JPanel centerPanel;
	protected JPanel jProgressPanel;
	protected JPanel jProgressUnderStepPanel;
	protected JProgressBar progressUnderStep;
	
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
		
		jProgressPanel = new JPanel(new BorderLayout(1, 1));
		jProgressUnderStepPanel =  new JPanel(new BorderLayout(1, 1));
		
		progress = new JProgressBar(0, 100);
		progress.setStringPainted(true);
		jProgressPanel.add(progress,BorderLayout.CENTER);
		jProgressPanel.setBorder(BorderFactory.createTitledBorder("Global"));
		
		progressUnderStep = new JProgressBar(0, 100);
		progressUnderStep.setStringPainted(true);
		jProgressUnderStepPanel.add(progressUnderStep,BorderLayout.CENTER);
		jProgressUnderStepPanel.setBorder(BorderFactory.createTitledBorder("Step in progress"));
		
		northPanel.add(jProgressPanel, BorderLayout.CENTER);
		northPanel.add(jProgressUnderStepPanel, BorderLayout.SOUTH);
		
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
	
	public void updateProgressBarUnderStep(int value){
		progressUnderStep.setValue(value);
		this.validate();
	}
}
