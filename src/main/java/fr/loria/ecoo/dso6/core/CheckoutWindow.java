package fr.loria.ecoo.dso6.core;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CheckoutWindow extends JFrame {

	protected JTextField author;
	protected JTextField path;
	protected JPanel centerPanel;
	protected JPanel northPanel;
	protected Object lock;
	protected boolean flag = false;

	public CheckoutWindow() {
		setTitle("DSo6 - Checkout");
		setPreferredSize(new Dimension(600, 200));
		setLayout(new GridLayout(3, 1));

		centerPanel = new JPanel(new BorderLayout(1, 1));
		centerPanel.setBorder(BorderFactory.createTitledBorder("Author"));
		add(centerPanel);

		author = new JTextField();
		centerPanel.add(author, BorderLayout.CENTER);


		northPanel = new JPanel(new BorderLayout(1, 1));
		northPanel.setBorder(BorderFactory.createTitledBorder("Path to working copy"));
		add(northPanel);
		path = new JTextField();
		path.setEditable(false);
		northPanel.add(path, BorderLayout.CENTER);
		JButton browse = new JButton("Browse");
		browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(jfc.showOpenDialog(CheckoutWindow.this) == JFileChooser.APPROVE_OPTION)
					CheckoutWindow.this.path.setText(jfc.getSelectedFile().getAbsolutePath());
			}
		});
		northPanel.add(browse, BorderLayout.EAST);


		lock = new Object();
		JButton button = new JButton("Confirm");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				synchronized(CheckoutWindow.this.lock) {
					flag = true;
					CheckoutWindow.this.lock.notify();
				}
				CheckoutWindow.this.setVisible(false);
			}
		});
		add(button);

		setVisible(true);

		pack();
	}
}
