package fr.loria.ecoo.dso6.core;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SelectWorkspaceWindow extends JFrame {
	protected JComboBox workspaces;
	protected Object lock;

	public SelectWorkspaceWindow(String values[]) {
		setTitle("DSo6 - Select Workspace");
		setPreferredSize(new Dimension(600, 200));
		setLayout(new GridLayout(2, 1));

		JPanel centerPanel = new JPanel(new BorderLayout(1, 1));
		centerPanel.setBorder(BorderFactory.createTitledBorder("Select Workspace"));
		add(centerPanel, BorderLayout.CENTER);

		workspaces = new JComboBox(values);
		centerPanel.add(workspaces, BorderLayout.CENTER);

		lock = new Object();

		JButton button = new JButton("Confirm");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				synchronized(SelectWorkspaceWindow.this.lock) {
					SelectWorkspaceWindow.this.lock.notify();
				}
				SelectWorkspaceWindow.this.setVisible(false);
			}
		});
		add(button, BorderLayout.SOUTH);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);

		pack();
	}
}
