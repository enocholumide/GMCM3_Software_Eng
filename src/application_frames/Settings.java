package application_frames;


import java.awt.Dimension;


import java.awt.*;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import custom_components.CustomJFrame;
import database.DatabaseConnection;
import file_handling.DatabaseCredentialsManager;
import toolset.Tools;

import javax.swing.JSeparator;
import javax.swing.JLabel;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.SwingConstants;

/**
 * Class contains the general settings for the application
 * @author Olumide Igbiloba
 * In this class the general settings of the application are defined
 *
 */
public class Settings extends CustomJFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public static JTextField dbHost;
	public static JTextField dbPort;
	public static JTextField dbName;
	public static JPasswordField dbPassword;
	public static JTextField dbUsername;
	public static JTextField userDefaultDirectory;
	public static JTextField userProfile;
	public static JTextField userSoftwareUse;
	public static JTextField userOccupation;
	
	public static JLabel settingsMessage;

	public int[] windowSize = getDefaultWindowSize();
	public static Rectangle window = getWindow(0);

	/**
	 * Create the frame.
	 * @param openMainFrame the openMainFrame to be set
	 */
	public Settings(boolean openMainFrame) {
		
		super("Settings");
		
		addWindowListener(new WindowAdapter() {
			@Override 
			public void windowClosing(WindowEvent e) { 
				handleWindowClosingEvent(e);
			} 
		});
		
		//1222, 750
		
		setBounds(Settings.window.x  + (Settings.window.width - 1222) / 2, 
				Settings.window.y + (Settings.window.height - 750) / 2,
				1222, 
				750);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 80, 711);
		panel.setBackground(Color.DARK_GRAY);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(3, 5, 75, 75);
		panel.add(label);
		label.setIcon(Tools.getIconImage("/images/settings.png", 75, 75));
		
		JLabel label_1 = new JLabel();
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setIcon(Tools.getIconImage("/images/help.png", 40, 40));
		label_1.setBounds(20, 658, 40, 40);
		panel.add(label_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(79, 0, 1127, 143);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setFont(new Font("Tahoma", Font.BOLD, 23));
		lblSettings.setBounds(21, 27, 402, 32);
		panel_1.add(lblSettings);
		
		JLabel lblGeneralSettings = new JLabel("General settings");
		lblGeneralSettings.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblGeneralSettings.setBounds(57, 81, 310, 20);
		panel_1.add(lblGeneralSettings);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(SystemColor.controlShadow);
		separator.setBounds(79, 176, 1127, 2);
		contentPane.add(separator);
		
		settingsMessage = new JLabel("CLick to the green button to test connection to your database");
		settingsMessage.setFont(new Font("Tahoma", Font.BOLD, 12));
		settingsMessage.setBounds(90, 151, 1106, 14);
		contentPane.add(settingsMessage);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.inactiveCaption);
		panel_2.setBounds(90, 189, 536, 215);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(10, 50, 516, 154);
		panel_2.add(panel_3);
		panel_3.setLayout(null);
		
		dbHost = new JTextField();
		dbHost.setBounds(95, 11, 411, 38);
		panel_3.add(dbHost);
		dbHost.setColumns(10);
		
		JButton btnNewButton = new JButton("Host");
		btnNewButton.setEnabled(false);
		btnNewButton.setMargin(new Insets(1,5,1,1));
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton.setBackground(SystemColor.controlHighlight);
		btnNewButton.setBounds(10, 11, 86, 38);
		panel_3.add(btnNewButton);
		
		dbPort = new JTextField();
		dbPort.setColumns(10);
		dbPort.setBounds(95, 60, 159, 38);
		panel_3.add(dbPort);
		
		JButton btnPort = new JButton("Port");
		btnPort.setMargin(new Insets(1,5,1,1));
		btnPort.setHorizontalAlignment(SwingConstants.LEFT);
		btnPort.setEnabled(false);
		btnPort.setBackground(SystemColor.controlHighlight);
		btnPort.setBounds(10, 60, 86, 38);
		panel_3.add(btnPort);
		
		JButton btnDatabas = new JButton("Database");
		btnDatabas.setMargin(new Insets(1,5,1,1));
		btnDatabas.setHorizontalAlignment(SwingConstants.LEFT);
		btnDatabas.setEnabled(false);
		btnDatabas.setBackground(SystemColor.controlHighlight);
		btnDatabas.setBounds(264, 60, 86, 38);
		panel_3.add(btnDatabas);
		
		dbName = new JTextField();
		dbName.setColumns(10);
		dbName.setBounds(347, 60, 159, 38);
		panel_3.add(dbName);
		
		JButton btnPassword = new JButton("Password");
		btnPassword.setMargin(new Insets(1,5,1,1));
		btnPassword.setHorizontalAlignment(SwingConstants.LEFT);
		btnPassword.setEnabled(false);
		btnPassword.setBackground(SystemColor.controlHighlight);
		btnPassword.setBounds(264, 109, 86, 38);
		panel_3.add(btnPassword);
		
		dbPassword = new JPasswordField();
		dbPassword.setColumns(10);
		dbPassword.setBounds(347, 109, 159, 38);
		panel_3.add(dbPassword);
		
		dbUsername = new JTextField(DatabaseConnection.dbUser);
		dbUsername.setColumns(10);
		dbUsername.setBounds(95, 109, 159, 38);
		panel_3.add(dbUsername);
		
		JButton btnUsername = new JButton("Username");
		btnUsername.setMargin(new Insets(1,5,1,1));
		btnUsername.setHorizontalAlignment(SwingConstants.LEFT);
		btnUsername.setEnabled(false);
		btnUsername.setBackground(SystemColor.controlHighlight);
		btnUsername.setBounds(10, 109, 86, 38);
		panel_3.add(btnUsername);
		
		JLabel lblDatabaseConnection = new JLabel("Database connection");
		lblDatabaseConnection.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDatabaseConnection.setBounds(10, 11, 280, 28);
		panel_2.add(lblDatabaseConnection);
		
		JButton btnTestConnection = new JButton("");
		btnTestConnection.setBackground(SystemColor.inactiveCaption);
		btnTestConnection.setToolTipText("Test database connection");
		btnTestConnection.setBounds(503, 11, 23, 23);
		panel_2.add(btnTestConnection);
		btnTestConnection.setBorderPainted(false);
		btnTestConnection.setFocusPainted(false);
		btnTestConnection.setIcon(Tools.getIconImage("/images/testdb.png", 20, 20));
		
		btnTestConnection.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String host = (Settings.dbHost.getText());
					int port = Integer.parseInt((Settings.dbPort.getText()));
					String database = (Settings.dbName.getText());
					String user = Settings.dbUsername.getText();
					String password = String.valueOf(Settings.dbPassword.getPassword());
					
					
				
					
					new DatabaseConnection(host, port, database, user, password);
					
					settingsMessage.setForeground(Color.BLACK);
					settingsMessage.setText("Database connection successfull");
					
				} catch (ClassNotFoundException | SQLException | NumberFormatException e1) {
					
					settingsMessage.setForeground(Color.RED);
					settingsMessage.setText("CANNOT CONNECT TO DATABASE \t\t " + e1.getMessage());
				}
			}
		});
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBackground(SystemColor.inactiveCaption);
		panel_4.setBounds(660, 189, 536, 215);
		contentPane.add(panel_4);
		
		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBackground(Color.WHITE);
		panel_5.setBounds(10, 50, 516, 154);
		panel_4.add(panel_5);
		
		userProfile = new JTextField("John Doe");
		userProfile.setColumns(10);
		userProfile.setBounds(95, 11, 159, 38);
		panel_5.add(userProfile);
		
		JButton btnGridSize = new JButton("Profile");
		btnGridSize.setMargin(new Insets(1, 5, 1, 1));
		btnGridSize.setHorizontalAlignment(SwingConstants.LEFT);
		btnGridSize.setEnabled(false);
		btnGridSize.setBackground(SystemColor.controlHighlight);
		btnGridSize.setBounds(10, 11, 86, 38);
		panel_5.add(btnGridSize);
		
		userSoftwareUse = new JTextField("Educational purpose");
		userSoftwareUse.setColumns(10);
		userSoftwareUse.setBounds(95, 60, 411, 38);
		panel_5.add(userSoftwareUse);
		
		JButton btnUse = new JButton("Use");
		btnUse.setMargin(new Insets(1, 5, 1, 1));
		btnUse.setHorizontalAlignment(SwingConstants.LEFT);
		btnUse.setEnabled(false);
		btnUse.setBackground(SystemColor.controlHighlight);
		btnUse.setBounds(10, 60, 86, 38);
		panel_5.add(btnUse);
		
		JButton btnDirectory = new JButton("Directory");
		btnDirectory.setBounds(10, 109, 86, 38);
		panel_5.add(btnDirectory);
		btnDirectory.setMargin(new Insets(1, 5, 1, 1));
		btnDirectory.setHorizontalAlignment(SwingConstants.LEFT);
		btnDirectory.setEnabled(false);
		btnDirectory.setBackground(SystemColor.controlHighlight);
		
		userDefaultDirectory = new JTextField(System.getProperty("user.dir"));
		userDefaultDirectory.setBounds(95, 109, 411, 38);
		panel_5.add(userDefaultDirectory);
		userDefaultDirectory.setColumns(10);
		
		JButton btnOccupation = new JButton("Occupation");
		btnOccupation.setMargin(new Insets(1, 5, 1, 1));
		btnOccupation.setHorizontalAlignment(SwingConstants.LEFT);
		btnOccupation.setEnabled(false);
		btnOccupation.setBackground(SystemColor.controlHighlight);
		btnOccupation.setBounds(264, 11, 86, 38);
		panel_5.add(btnOccupation);
		
		userOccupation = new JTextField("Student");
		userOccupation.setColumns(10);
		userOccupation.setBounds(349, 11, 159, 38);
		panel_5.add(userOccupation);
		
		JLabel lblFile = new JLabel("General");
		lblFile.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFile.setBounds(10, 11, 280, 28);
		panel_4.add(lblFile);
		
		JPanel panel_6 = new JPanel();
		panel_6.setLayout(null);
		panel_6.setBackground(SystemColor.inactiveCaption);
		panel_6.setBounds(90, 415, 1106, 223);
		contentPane.add(panel_6);
		
		JPanel panel_7 = new JPanel();
		panel_7.setLayout(null);
		panel_7.setBackground(Color.WHITE);
		panel_7.setBounds(10, 50, 1086, 162);
		panel_6.add(panel_7);
		
		JLabel lblDrawingSettings = new JLabel("Drawing settings");
		lblDrawingSettings.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDrawingSettings.setBounds(10, 11, 280, 28);
		panel_6.add(lblDrawingSettings);
		
		JButton btnFinish = new JButton("Close");
		btnFinish.setBorderPainted(false);
		btnFinish.setFocusPainted(false);
		btnFinish.setForeground(SystemColor.text);
		btnFinish.setBackground(Settings.HIGHLIGHTED_STATE_COLOR);
		btnFinish.setBounds(1057, 662, 139, 38);
		contentPane.add(btnFinish);
		
		btnFinish.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(openMainFrame) {
					
					try {
						
						String host = (Settings.dbHost.getText());
						int port = Integer.parseInt((Settings.dbPort.getText()));
						String database = (Settings.dbName.getText());
						String user = Settings.dbUsername.getText();
						String password = String.valueOf(Settings.dbPassword.getPassword());
						
						
						new MainFrame(new DatabaseConnection(host, port, database, user, password)).setVisible(true);
						;
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					
				}
				dispose();
			}
			
		});
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(SystemColor.controlShadow);
		separator_1.setBounds(79, 649, 1127, 2);
		contentPane.add(separator_1);
		
		JLabel lblcLicense = new JLabel("(c) 2017 License : -----");
		lblcLicense.setFont(new Font("Tahoma", Font.ITALIC, 13));
		lblcLicense.setBounds(90, 674, 310, 20);
		contentPane.add(lblcLicense);
		
		JButton btnSave = new JButton("Save");
		btnSave.setForeground(Color.WHITE);
		btnSave.setFocusPainted(false);
		btnSave.setBorderPainted(false);
		btnSave.setBackground(Color.DARK_GRAY);
		btnSave.setBounds(908, 662, 139, 38);
		contentPane.add(btnSave);

		// Get DB params from credential manager
		try {
			DatabaseCredentialsManager databaseCredentialsManager = new DatabaseCredentialsManager();
			dbHost.setText(databaseCredentialsManager.host);
			dbPort.setText(String.valueOf(databaseCredentialsManager.port));
			dbName.setText(databaseCredentialsManager.database);
			dbUsername.setText(databaseCredentialsManager.user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAllChanges();
			}
		});
	}

	private static Rectangle getWindow(int i) {
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		
		return gs[i].getDefaultConfiguration().getBounds();
		
	}
	
	/**
	 * Saves all changes of the settings
	 */

	/**
	 * Computes the width and height of the application.  If there are multiple displays attached, for some reason it
	 * thinks that the first display's width is the sum of all display widths.  So we have to subtract the widths of
	 * all other displays from the first if there are multiple displays attached.
	 * @return int[] in which the first element is the width and the second is the height.
	 */
	public static int[] getDefaultWindowSize() {

		// Get devices
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();

		// If there is more than one device (gs.length > 1), subtract the widths of all subsequent displays from its width.
		int width;
		if (gs.length > 1) {
			width = gs[0].getDisplayMode().getWidth();
			int otherWidths = 0;
			for (int i=1; i<gs.length; i++) {
				otherWidths += gs[i].getDisplayMode().getWidth();
			}
			width = width - otherWidths;
		// Otherwise, just take the first (and only) display's width.
		} else {
			width = gs[0].getDisplayMode().getWidth();
		}

		// Height should stay the same.
		int height = gs[0].getDisplayMode().getHeight();

		return new int[] {width, height};

	}


	protected void saveAllChanges() {
		
		
		try {
			
			// DB Params
			
			DatabaseConnection.dbName = dbName.getText();
			DatabaseConnection.dbUser = dbUsername.getText();
			DatabaseConnection.dbHost = dbHost.getText();
			DatabaseConnection.dbPort = Integer.parseInt(dbPort.getText());
			
			MainFrame.dbConnection = new DatabaseConnection(DatabaseConnection.dbHost, DatabaseConnection.dbPort,
					DatabaseConnection.dbName, DatabaseConnection.dbUser, String.valueOf(dbPassword.getPassword()) );

			// Save DB Params

			DatabaseCredentialsManager databaseCredentialsManager = new DatabaseCredentialsManager();
			databaseCredentialsManager.setDatabaseCredentials(dbHost.getText(), Integer.parseInt(dbPort.getText()),
					dbName.getText(), dbUsername.getText());

			// Drawing settings
			// TODO: Drawing settings
			
			// Log some messages
			
			settingsMessage.setText("Settings saved");
			settingsMessage.setForeground(Settings.defaultColor);
			
		} catch (Exception e) {
			
			settingsMessage.setText("Something went wrong:  " + e.getMessage());
			settingsMessage.setForeground(Settings.DEFAULT_ERROR_COLOR);
		}
		
	}

	/**
	 * Handles the Window Closing Event
	 * @param e the WindowEvent to set
	 */
	protected void handleWindowClosingEvent(WindowEvent e) {
		dispose();
	}

	// Software information
	public static final String TITLE = "GMCM3_Software_Eng";

	public static final int DEFAULT_DPI = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();

	public static GraphicsDevice[] gs;
	
	// Drawing settings
	public static int gridSizeMM = 5;
	public static int snappingTolerance = 20;
	public static int gridMajorInterval = 5;
	public static int cursorSize = 25;

	public static double mouseOffset = 20;
	public static boolean DRAW_GUIDES_AND_TIPS = true;
	
	public static int POINT_SIZE = 5;

	// GUI parameters
	public static final ImageIcon LAYER_DELETE_ICON = Tools.getIconImage("/images/bin.png", 15, 15);
	
	public static final String DRAW_CONTINUE = "continue";
	public static final String CLOSE_POLYGON_MESSAGE = "Click the first point to finish polygon";
	public static final String CLOSE_POLYLINE_MESSAGE = "Double click the first point to close the polyline";
	public static final String FINISH_POLYLINE_MESSAGE = "Double click the last point to finish drawing the polyline";
	public static final String DEFAULT_MOUSE_TIP = "Click the last point to finish shape";

	public static final int DEFAULT_LAYER_LINE_WEIGHT = 3;
	public static final int TRANSPARENCY_LEVEL_1 = 180;
	public static final int TRANSPARENCY_LEVEL_2 = 100;
	public static final int TOOL_TIP_PADDING = 5;
	
	
	// Geometry Identifiers
	// TODO: Change to int to reduce memory
	public static final String POLYLINE_GEOMETRY = "Polyline";
	public static final String POINT_GEOMETRY = "Point";
	public static final String POLYGON_GEOMETRY = "Polygon";
	
	public static Color cursorColor = new Color(244, 98, 66);
	public static Color defaultColor = Color.BLACK;
	public static Color DEFAULT_ERROR_COLOR = Color.RED;
	
	public static final Color DEFAULT_SELECTION_COLOR = new Color (135, 234, 105);
	public static final Color DEFAULT_LAYER_COLOR = Color.BLACK;
	public static final Color DEFAULT_VERTIX_COLOR = new Color(31, 105, 224);
	public static final Color MUTE_STATE_COLOR = Color.LIGHT_GRAY;
	public static final Color DEFAULT_STATE_COLOR = new Color(31, 105, 224);
	public static final Color HIGHLIGHTED_STATE_COLOR = new Color(239, 66, 14);
	public static final Color FEATURE_CREATED_COLOR = new Color (16, 91, 26);
	public static final Color FEATURE_HIGHLIGHTED_STATE_COLOR = Color.CYAN;
	public static final int MONITOR_SCREEN = 1;
	public static final Dimension MAINFRAME_SIZE = new Dimension(1366, 768);
}
