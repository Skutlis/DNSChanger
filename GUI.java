package WindowsSettings;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;

public class GUI implements ActionListener {

		private JLabel empty;
		private JLabel server;
		private JLabel message;
		private JFrame frame;
		private JPanel panel;
		private List<String> words = new ArrayList<String>();
		private JComboBox<String> cmdChoice;
		private JButton button;
		private DNS dns = new DNS();
		
		public GUI() {
			
			//Create new frame(visual)
			frame = new JFrame();
			
			/**
			 * Create new button with text "Click me".
			 * We can add functions to our buttons, this to
			 * specify that we use actionslistener from this class.
			 * Opens button not as field variable because ordinarly we
			 * want more than one button
			 */
			button = new JButton("Change DNS");
			button.addActionListener(this);
			
			/**
			 * Create new label with text "Number of clicks"
			 * have to be accessable for methods outside contructor, 
			 * therefor the JLabel label is set as a field variable
			 * 
			 */
			message = new JLabel();
			empty = new JLabel();
			server = new JLabel("Server");
			
			List<String> routers = dns.routers();
			String[] choice = new String[routers.size()];
			for(int i = 0; i < routers.size(); i++) {
				choice[i] = routers.get(i);
			}
			
			cmdChoice = new JComboBox<String>(choice);
			cmdChoice.setSelectedIndex(choice.length-1);
			cmdChoice.addActionListener(this);
			
			/**
			 * Create new panel, which holds all of our different objects.
			 * Add our objects to the panel
			 */
			panel = new JPanel();
			panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 40));
			panel.setLayout(new GridLayout(3, 2));
			panel.setBackground(Color.gray);
			panel.add(server);
			panel.add(empty);
			panel.add(cmdChoice);
			panel.add(button);
			panel.add(message);
			
			
			
			
			/**
			 * Adds our panel to our frame, so it can become visible
			 * Define default exit for panel and title for our frame
			 */
			frame.add(panel, BorderLayout.CENTER);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle("DNS Router");
			frame.pack();
			frame.setVisible(true);
		}

		public static void main(String[] args) {
			new GUI();

		}

		
		@Override
		public void actionPerformed(ActionEvent e) {
			message.setText("");
			if(e.getActionCommand().equals("Change DNS")) {
				if(dns.DNSpath((String)cmdChoice.getSelectedItem())) {
					try {
						if(dns.change()) {
							panel.remove(server);
							panel.remove(cmdChoice);
							empty.setText("SUCCESS");
							button.setText("Exit");
						}
						else {
							message.setText("ERROR");
						}
					}catch (IOException E) {
					
					}
				}else {
					message.setText("ERROR");
			}
			}
			else if(e.getActionCommand().equals("Exit")) {
				System.exit(0);
			}
			}
			}


