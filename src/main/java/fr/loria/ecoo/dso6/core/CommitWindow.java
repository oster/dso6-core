package fr.loria.ecoo.dso6.core;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CommitWindow extends JFrame {
	protected JTextArea message;
	protected Object lock;

	public CommitWindow() {
		setTitle("DSo6 - Commit");
		setPreferredSize(new Dimension(600, 400));
		setLayout(new BorderLayout(1, 1));

		JPanel centerPanel = new JPanel(new BorderLayout(1, 1));
		centerPanel.setBorder(BorderFactory.createTitledBorder("Commit Message"));
		add(centerPanel, BorderLayout.CENTER);

		message = new JTextArea();
		message.setLineWrap(true);
		centerPanel.add(new JScrollPane(message), BorderLayout.CENTER);

		lock = new Object();

		JButton button = new JButton("Confirm");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				synchronized(CommitWindow.this.lock) {
					CommitWindow.this.lock.notify();
				}
				CommitWindow.this.setVisible(false);
			}
		});
		add(button, BorderLayout.SOUTH);

		setVisible(true);

		pack();
	}
}
