package fr.loria.ecoo.dso6.core;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AuthorWindow extends JFrame {
	
	protected JTextField author;
	protected JPanel centerPanel;
	protected Object lock;
	
	public AuthorWindow() {
		setTitle("DSo6 - Author");
		setPreferredSize(new Dimension(600, 400));
		setLayout(new BorderLayout(1, 1));
		
		centerPanel = new JPanel(new BorderLayout(1, 1));
		centerPanel.setBorder(BorderFactory.createTitledBorder("Author"));
		add(centerPanel, BorderLayout.CENTER);
		
		author = new JTextField();
		centerPanel.add(author, BorderLayout.CENTER);
		
		lock = new Object();
		
		JButton button = new JButton("Confirm");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				synchronized(AuthorWindow.this.lock) {
					AuthorWindow.this.lock.notify();
				}
				AuthorWindow.this.setVisible(false);
			}
		});
		add(button, BorderLayout.SOUTH);

		setVisible(true);

		pack();
	}
}
