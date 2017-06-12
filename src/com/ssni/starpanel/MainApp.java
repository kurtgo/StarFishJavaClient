package com.ssni.starpanel;

import java.awt.EventQueue;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.json.*;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.LinkFormat;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import javax.swing.JSplitPane;

public class MainApp {

	private JFrame frame;
	private JPanel table;
	private JLabel label;
	private JTextField textField;
	private JLabel label_1;
	private JTextField textField_1;
	  private WindowHandler handler = null;
	  private LogWindow log;
	  private JMenuBar menuBar;
	  private JMenu mnFile;
	  private JMenu mnNewMenu;
	  private JMenuItem mntmNewMenuItem;
	  private JMenuItem mntmNewMenuItem2;
	  private JMenuItem mntmClear;
	  private final Action action = new SwingAction();
	  private JSplitPane splitPane;


	  final static Logger logger = Logger.getLogger(MainApp.class);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApp window = new MainApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainApp() {
		
		initialize();

		logger.info("Requesting session");
		try {
			URI uri = new URI("api.coap-staging.developer.ssni.com:5683/.well-known/core");
			
			CoapClient client = new CoapClient(uri);
			
			CoapResponse response = client.get();
			logger.info("Response: " + response.getResponseText());
			//JSONObject j = new JSONObject(new JSONTokener(response.getResponseText()));
			//logger.info(response.getOptions().getContentFormat());

			if (ResponseCode.isSuccess(response.getCode()) == true) {
				
				if (response.getOptions().getContentFormat()==MediaTypeRegistry.APPLICATION_LINK_FORMAT) {
					Set<WebLink> data =  LinkFormat.parse(response.getResponseText());
					for (Iterator<WebLink> x =data.iterator(); x.hasNext();) {
						WebLink r = x.next();
						logger.info("Data: " + x.next().getURI());
						if (r.getURI().equals("sessions")){
							
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("Exception trying to get the session", e);
		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(300, 300, 800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		// MENU setup
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmNewMenuItem = new JMenuItem("About");
		mnFile.add(mntmNewMenuItem);

		mntmNewMenuItem2 = new JMenuItem("Quit");
		mnFile.add(mntmNewMenuItem2);
		
		mnNewMenu = new JMenu("Log");
		menuBar.add(mnNewMenu);
		
		mntmClear = new JMenuItem("clear");
		mntmClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log.clear();
				
			}
		});
		mnNewMenu.add(mntmClear);

		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new MigLayout("", "[][][grow]", "[]"));
		
		label = new JLabel("ClientId");
		panel.add(label);
		
		textField = new JTextField();
		panel.add(textField, "wrap" );
		textField.setColumns(36);

		label_1 = new JLabel("ClientSecret");
		panel.add(label_1);
		
		textField_1 = new JTextField();
		panel.add(textField_1, "wrap");
		textField_1.setColumns(54);
		
	
		table = new MacTable();
		
		//splitPane.add(table);
		
		log = new LogWindow(5,80);
		//splitPane.add(log);

		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, table, log);
		splitPane.setResizeWeight(1.0);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		handler = WindowHandler.getInstance(log);
		
	    //obtaining a logger instance and setting the handler
	    //logger = Logger.getLogger("sam.logging.handler");
	    //logger.addHandler(handler);

	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
