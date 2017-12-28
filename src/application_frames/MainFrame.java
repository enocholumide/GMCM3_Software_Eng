package application_frames;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import java.awt.Color;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;

import core_classes.Feature;
import core_classes.Layer;
import core_components.DrawIconButton;
import core_components.DrawingJPanel;
import core_components.TableOfContents;
import core_components.ToolIconButton;
import custom_components.CustomJFrame;
import database.DatabaseConnection;
import features.PointItem;
import file_handling.FileHandler;
import file_handling.SessionManager;
import toolset.Tools;

import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.border.LineBorder;

/**
 * 
 * Main frame of the application.<br>
 * The Class is overloaded with two constructors, one with a database connection and other without 
 * a database connection <br>
 * <br>
 * It the Group layout primarily to arrange the components contained.<br>
 * The frame is splited into two, using the JSplit pane.<br>
 * <br>
 * The initial bounds of the mainframe is 15inch (resolution 1366 x 768), which can be resized.<br>
 * The left side contains the current drawing description and the list of layers
 * arranged in table of contents. The width can also be resized during runtime<br>
 * <br>
 * The other (right) part is composed of the drawing panel (at the middle), 
 * the tool bar (top) and the message/ log area at the bottom.<br>
 * This other part is managed by using the Group layout and stretches to the left when window is resized.<br>
 * <br>
 * Most of the activity. actions happens at the DrawingJPanel class, the MainFrame handles common tasks such
 * as adding new layer to the table of contents, importing and exporting files and chosing the a shape to be drawn and 
 * general interface to other application frames in the program e.g. DatabaseCatalog, Settings Frame etc.
 * 
 * @author Olumide Igbiloba
 *
 */
public class MainFrame extends CustomJFrame {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7752427807628614402L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**Current project/ document name*/
	private JTextField projectName;
	
	/**The drawing panel for drawing shapes*/
	public static DrawingJPanel panel;
	
	/**List showing the available layers at the table of contents*/
	public static JComboBox<String[]> layerListComboBox;
	
	/**Model of the layer list combo box*/
	private static DefaultComboBoxModel<String[]> model;
	
	/**Table of contents arranging the list of layers*/
	public static TableOfContents tableOfContents;
	
	/**Text field for showing messages on the frame*/
	private static JTextArea logText;
	
	/**Draw button group*/
	public static ButtonGroup drawButtonGroup = new ButtonGroup();
	
	/**Tools button group*/
	public static ButtonGroup toolsButtonGroup = new ButtonGroup();
	
	/**Log button*/
	private static JButton logButton;
	
	/**Button that toggles edit session*/
	public static ToolIconButton btnDrawEdit;
	
	/**Button that toggles query session*/
	public static ToolIconButton queryButton;
	
	/**Button that toggles selection mode*/
	public static ToolIconButton selectionButton;
	
	/**Database connection object*/
	public static DatabaseConnection dbConnection;
	
	private DatabaseCatalog dbCatalog;
	
	private Settings settingsFrame;
	
	public static List<ToolIconButton> buttonsList = new ArrayList<ToolIconButton>();
	
	JPopupMenu sessionMenu;

	/**
	 * Starts the application
	 */
	public MainFrame() {

		setUp();
		//initialize();
		
	}
	
	/**
	 * Constructs the mainframe with a database connnection.
	 * 
	 * @param dbConnection database connection for storing/ retrieving drawn shapes
	 */
	public MainFrame(DatabaseConnection dbConnection) {
		
		MainFrame.dbConnection = dbConnection;
		initialize();

		log("Application started. GMCM3 Software Engineering HSKA Karlsruhe "
				+ "https://github.com/enocholumide/GMCM3_Software_Eng.git "
				+ "\t Database connected");
		
	}

	/**
	 * Sets up the application by displaying the settings frame.<br>
	 * This is needed to set up the database connection and other drawing preferences <br>
	 */
	private void setUp() {
	
		settingsFrame = new Settings(true);
		settingsFrame.setVisible(true);
	
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		// Position at the middle of the screen
		setBounds(Settings.window.x  + (Settings.window.width - Settings.MAINFRAME_SIZE.width) / 2, 
				Settings.window.y + (Settings.window.height - Settings.MAINFRAME_SIZE.height) / 2,
				Settings.MAINFRAME_SIZE.width, 
				Settings.MAINFRAME_SIZE.height);
		
		// Adding window listener
		addWindowListener(new WindowAdapter() {
			@Override 
			public void windowClosing(WindowEvent e) { 
				handleWindowClosingEvent(e);
			} 
		});
		
		// Side panel, containing the drawing description and the table of contents
		JPanel sidePanel = new JPanel();
		sidePanel.setBackground(Color.WHITE);
		
		// Right panel, containing the tool bar, drawign area and the message area
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(Color.WHITE);
		
		// Put the panels in a split pane
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				sidePanel, rightPanel);
		
		// Application logo
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBackground(Color.LIGHT_GRAY);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(Tools.getIconImage("/images/Images-icon.png", 60, 60));
		
		JLabel label = new JLabel("");
		
		// Project name textfield
		projectName = new JTextField();
		projectName.setHorizontalAlignment(SwingConstants.CENTER);
		projectName.setText("Untitled");
		projectName.setFont(new Font("Tahoma", Font.BOLD, 18));
		projectName.setColumns(10);
		
		JLabel lblTableOfContents = new JLabel("Table of contents");
		lblTableOfContents.setHorizontalAlignment(SwingConstants.CENTER);
		lblTableOfContents.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		// Table of contents inside a Jscroll pane
		tableOfContents = new TableOfContents();
		JScrollPane scrollPane2 = new JScrollPane(tableOfContents);
		GroupLayout gl_sidePanel = new GroupLayout(sidePanel);
		gl_sidePanel.setHorizontalGroup(
			gl_sidePanel.createParallelGroup(Alignment.LEADING)
				.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
				.addGroup(gl_sidePanel.createSequentialGroup()
					.addComponent(label)
					.addContainerGap())
				.addComponent(projectName, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
				.addComponent(lblTableOfContents, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
				.addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
		);
		gl_sidePanel.setVerticalGroup(
			gl_sidePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_sidePanel.createSequentialGroup()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(projectName, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblTableOfContents, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE))
		);
		sidePanel.setLayout(gl_sidePanel);
		
		JPanel toolBar = new JPanel();
		toolBar.setBackground(Color.WHITE);
		
		panel = new DrawingJPanel();
		panel.setBorder(new LineBorder(Color.WHITE));
		panel.setBackground(Color.WHITE);
		
		
		JPanel panel_9 = new JPanel();
		panel_9.setBackground(Color.BLACK);
		GroupLayout gl_rightPanel = new GroupLayout(rightPanel);
		gl_rightPanel.setHorizontalGroup(
			gl_rightPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_9, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(toolBar, GroupLayout.DEFAULT_SIZE, 1154, Short.MAX_VALUE)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1154, Short.MAX_VALUE)
		);
		gl_rightPanel.setVerticalGroup(
			gl_rightPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_rightPanel.createSequentialGroup()
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_9, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
		);
		
		logButton = new JButton("LOG");
		logButton.setBorderPainted(false);
		logButton.setFocusPainted(false);
		logButton.setForeground(Color.WHITE);
		logButton.setBackground(Color.BLACK);
		
		logText = new JTextArea();
		logText.setEditable(false);
		logText.setForeground(Color.WHITE);
		logText.setBackground(Color.DARK_GRAY);
		//logText
		DefaultCaret caret = (DefaultCaret) logText.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		JScrollPane scrollPane = new JScrollPane(logText);
		scrollPane.setViewportView(logText);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		scrollPane.setBackground(Color.DARK_GRAY);
		
		GroupLayout gl_panel_9 = new GroupLayout(panel_9);
		gl_panel_9.setHorizontalGroup(
			gl_panel_9.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_9.createSequentialGroup()
					.addComponent(logButton, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1045, Short.MAX_VALUE))
		);
		gl_panel_9.setVerticalGroup(
			gl_panel_9.createParallelGroup(Alignment.LEADING)
				.addComponent(logButton, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
		);
		panel_9.setLayout(gl_panel_9);
		
		JPanel fileRibbon = new JPanel();
		fileRibbon.setBackground(Color.WHITE);
		fileRibbon.setBorder(new CompoundBorder(new TitledBorder(null, "File", TitledBorder.LEFT, TitledBorder.TOP), new EmptyBorder(5,5,5,5)));
		fileRibbon.setLayout(new GridLayout(1, 2, 5, 5));
		
		ToolIconButton filesBtn = new ToolIconButton("Files", "/images/file.png", 60, 60);
		filesBtn.setToolTipText("Open previous projects");
		fileRibbon.add(filesBtn);
		
		ToolIconButton importBtn = new ToolIconButton("Import", "/images/import.png", 60, 60);
		importBtn.setToolTipText("Import projects from csv");
		fileRibbon.add(importBtn);
		importBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				FileHandler.readFromGeoJson();
				
			}
			
		});
		
		ToolIconButton exportBtn = new ToolIconButton("Export", "/images/export.png", 60, 60);
		fileRibbon.add(exportBtn);
		
		exportBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FileHandler.writeToCSV(TableOfContents.layerList);
			}
		});
		
		JPanel selectorRibbon = new JPanel();
		selectorRibbon.setBackground(Color.WHITE);
		selectorRibbon.setBorder(new CompoundBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Selector", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)), new EmptyBorder(5, 5, 5, 5)));
		selectorRibbon.setLayout(new GridLayout(1, 2, 5, 5));
		
		selectionButton = new ToolIconButton("Select", "/images/select.png", 60, 60);
		selectorRibbon.add(selectionButton);
		
		queryButton = new ToolIconButton("Query", "/images/query.png", 60,60);
		queryButton.setToolTipText("Select items with rectangle");
		selectorRibbon.add(queryButton);
		
		JPanel editorRibbon = new JPanel();
		editorRibbon.setBackground(Color.WHITE);
		editorRibbon.setBorder(new CompoundBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Editor", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)), new EmptyBorder(5, 5, 5, 5)));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new EmptyBorder(0, 0, 5, 0));
		panel_5.setBackground(Color.WHITE);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new EmptyBorder(0, 0, 5, 0));
		panel_6.setBackground(Color.WHITE);
		panel_6.setLayout(new GridLayout(1, 2, 5, 5));
		
		ToolIconButton btnSaveEdit = new ToolIconButton("Save edit", "/images/save.png", 60,60);
		btnSaveEdit.setToolTipText("Save edited layers");
		panel_6.add(btnSaveEdit);
		
		ToolIconButton btnAddLayer = new ToolIconButton("Save edit", "/images/add_layer.png", 80,80);
		btnAddLayer.setToolTipText("Add more layers");
		panel_6.add(btnAddLayer);
		
		layerListComboBox = new JComboBox<String[]>();
		layerListComboBox.setBackground(Settings.DEFAULT_STATE_COLOR);
		layerListComboBox.setForeground(Color.WHITE);
		
		ToolIconButton btnSnap = new ToolIconButton("Snap", "/images/snap.png", 35,35);
		btnSnap.setToolTipText("Turn of snap");
		ToolIconButton btnGrid = new ToolIconButton("Grid", "/images/grid.png", 35, 35);
		btnGrid.setToolTipText("Turn on grid");
		
		GroupLayout gl_editorRibbon = new GroupLayout(editorRibbon);
		gl_editorRibbon.setHorizontalGroup(
			gl_editorRibbon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_editorRibbon.createSequentialGroup()
					.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_editorRibbon.createParallelGroup(Alignment.LEADING)
						.addComponent(layerListComboBox, 0, 104, Short.MAX_VALUE)
						.addGroup(gl_editorRibbon.createSequentialGroup()
							.addComponent(btnSnap, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnGrid, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
		);
		gl_editorRibbon.setVerticalGroup(
			gl_editorRibbon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_editorRibbon.createSequentialGroup()
					.addGroup(gl_editorRibbon.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_editorRibbon.createSequentialGroup()
							.addComponent(layerListComboBox, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_editorRibbon.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnSnap, 0, 0, Short.MAX_VALUE)
								.addComponent(btnGrid, GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED, 5, Short.MAX_VALUE))
						.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 72, Short.MAX_VALUE)
						.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 72, Short.MAX_VALUE))
					.addContainerGap())
		);
		panel_5.setLayout(new GridLayout(1, 2, 5, 5));
		
		ToolIconButton btnDelete = new ToolIconButton("Delete", "/images/delete.png", 60,60);
		btnDelete.setToolTipText("Delete selected items");
		panel_5.add(btnDelete);
		
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!panel.editModeIsOn) {
					log("Attempted to delete feature, but edit mode is off");
					panel.showAnimatedHint("Edit mode if off", Settings.DEFAULT_ERROR_COLOR);
				} else
					panel.deleteSelectedItem();
			}
		});
		
		btnDrawEdit = new ToolIconButton("Editing", "/images/edit.png", 60,60);
		panel_5.add(btnDrawEdit);
		btnDrawEdit.setToolTipText("Start edit session");
		editorRibbon.setLayout(gl_editorRibbon);
		
		JPanel drawRibbon = new JPanel();
		drawRibbon.setBackground(Color.WHITE);
		drawRibbon.setBorder(new CompoundBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Draw", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)), new EmptyBorder(5, 5, 5, 5)));
		drawRibbon.setLayout(new GridLayout(2, 8, 5, 5));
		
		DrawIconButton geomRec = new DrawIconButton("Rectangle", Settings.POLYGON_GEOMETRY ,"/images/rectangle.png", 25, 25);
		geomRec.setToolTipText("Rectangle");
		drawRibbon.add(geomRec);
		
		DrawIconButton geomTriangle = new DrawIconButton("Triangle", Settings.POLYGON_GEOMETRY, "/images/triangle.png", 25, 25);
		geomTriangle.setToolTipText("Triangle");
		drawRibbon.add(geomTriangle);
		
		DrawIconButton geomCircle = new DrawIconButton("Circle", Settings.POLYGON_GEOMETRY, "/images/circle.png", 25, 25);
		geomCircle.setToolTipText("Circle");
		drawRibbon.add(geomCircle);
		
		DrawIconButton geomFreeformPolygon = new DrawIconButton("Freeform Polygon", Settings.POLYGON_GEOMETRY, "/images/polygon.png", 30, 30);
		geomFreeformPolygon.setToolTipText("Freeform Polygon");
		drawRibbon.add(geomFreeformPolygon);
		
		DrawIconButton geomPoint = new DrawIconButton("Point", Settings.POINT_GEOMETRY, "/images/point.png", 25, 25);
		geomPoint.setToolTipText("Point");
		drawRibbon.add(geomPoint);
		
		DrawIconButton geomSingleLine = new DrawIconButton("Line", Settings.POLYLINE_GEOMETRY, "/images/line.png", 25, 25);
		geomSingleLine.setToolTipText("Single line");
		drawRibbon.add(geomSingleLine);
		
		DrawIconButton geomMultiLine = new DrawIconButton("Multiline", Settings.POLYLINE_GEOMETRY ,"/images/polyline.png", 25, 25);
		geomMultiLine.setToolTipText("Multi line");
		drawRibbon.add(geomMultiLine);
		
		DrawIconButton geomEllipse = new DrawIconButton("Ellipse", "Polygon", "/images/ellipse.png", 30, 30);
		geomEllipse.setToolTipText("Ellipse");
		drawRibbon.add(geomEllipse);
		
		JPanel configureRibbon = new JPanel();
		configureRibbon.setBackground(Color.WHITE);
		configureRibbon.setBorder(new CompoundBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Configure", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)), new EmptyBorder(5, 5, 5, 5)));
		configureRibbon.setLayout(new GridLayout(1, 2, 5, 5));
		
		ToolIconButton databaseButton = new ToolIconButton("Database", "/images/database.png", 60, 60);
		configureRibbon.add(databaseButton);
		
		ToolIconButton settingsButton = new ToolIconButton("Settings", "/images/settings.png", 60, 60);
		configureRibbon.add(settingsButton);
		GroupLayout gl_toolBar = new GroupLayout(toolBar);
		gl_toolBar.setHorizontalGroup(
			gl_toolBar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_toolBar.createSequentialGroup()
					.addComponent(fileRibbon, GroupLayout.PREFERRED_SIZE, 229, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(selectorRibbon, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(editorRibbon, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(drawRibbon, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(configureRibbon, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE))
		);
		gl_toolBar.setVerticalGroup(
			gl_toolBar.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_toolBar.createSequentialGroup()
					.addGroup(gl_toolBar.createParallelGroup(Alignment.TRAILING)
						.addComponent(configureRibbon, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
						.addComponent(drawRibbon, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 104, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_toolBar.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(editorRibbon, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
							.addComponent(selectorRibbon, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(fileRibbon, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addContainerGap())
		);
		toolBar.setLayout(gl_toolBar);
		rightPanel.setLayout(gl_rightPanel);
		splitPane.setDividerLocation((int)(getBounds().width / 8));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 1330, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
		
		addComponentListener(new ComponentListener() {
			
		    public void componentResized(ComponentEvent e) {
		        System.out.println(getBounds()); 
		        
		        
		        if(getBounds().width > 1366) {
		        	splitPane.setDividerLocation((int)(getBounds().width / 4));
			        revalidate();
			        repaint();
		        }
		    }

			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		buttonsList.add(filesBtn);
		buttonsList.add(importBtn);
		buttonsList.add(exportBtn);
		
		drawButtonGroup.add(geomRec);
		drawButtonGroup.add(geomTriangle);
		drawButtonGroup.add(geomCircle);
		drawButtonGroup.add(geomFreeformPolygon);
		drawButtonGroup.add(geomPoint);
		drawButtonGroup.add(geomSingleLine);
		drawButtonGroup.add(geomMultiLine);
		drawButtonGroup.add(geomEllipse);
		
		// ---------------------------------------------------------------------------
		// ACTION LISTENERS 
		// ---------------------------------------------------------------------------


		filesBtn.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				// Initialize a SessionManager with all the active layers & a file extension filter for later
				SessionManager sessionManager = new SessionManager(tableOfContents);
				FileNameExtensionFilter sessionFileFilter = new FileNameExtensionFilter("GMCM Application Session Files", "gmcm");

				// Build & display the save/close menu for the files button
				sessionMenu = new JPopupMenu();
				JMenuItem saveSessionMenuItem = new JMenuItem ("Save Session");
				JMenuItem openSessionMenuItem = new JMenuItem ("Open Session");
				sessionMenu.add(saveSessionMenuItem);
				sessionMenu.add(openSessionMenuItem);
				sessionMenu.setLocation(e.getXOnScreen(), e.getYOnScreen());
				sessionMenu.setVisible(true);

				// Add action listener for "Save Session" option.
				saveSessionMenuItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						// Hide the menu once clicked and open a save dialog.
						sessionMenu.setVisible(false);
						JFileChooser saveSessionFileChooser = new JFileChooser();
						saveSessionFileChooser.addChoosableFileFilter(sessionFileFilter);

						// Once the user selects a file name, write the session to it.
						int saveSessionReturnVal = saveSessionFileChooser.showSaveDialog(filesBtn);
						if (saveSessionReturnVal == JFileChooser.APPROVE_OPTION) {
							File saveSession = saveSessionFileChooser.getSelectedFile();
							String saveSessionPath = saveSession.getPath();
							if (!saveSessionPath.substring(saveSessionPath.length()-5).equals(".gmcm")) {
								saveSessionPath += ".gmcm";
							}
							sessionManager.saveCurrentSession(saveSessionPath);
						}
					}
				});

				// Add action listener for "Open Session" option.
				openSessionMenuItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						// Hide the menu once clicked and open an open dialog.
						sessionMenu.setVisible(false);
						JFileChooser openSessionFileChooser = new JFileChooser();
						openSessionFileChooser.addChoosableFileFilter(sessionFileFilter);

						// When the user selects a file, read it with the SessionManager and try to add the layers from the database, if they exist.
						int openSessionReturnVal = openSessionFileChooser.showOpenDialog(filesBtn);
						if (openSessionReturnVal == JFileChooser.APPROVE_OPTION) {
							File openSession = openSessionFileChooser.getSelectedFile();
							ArrayList<String> layersToOpen = sessionManager.openSession(openSession.getPath());
							for (int i=0; i<layersToOpen.size(); i++) {
								String layerName = layersToOpen.get(i);
								try {
									ResultSet layerContents = dbConnection.readTable(layerName);
									createLayerFromResultSet(layerContents, layerName);
								} catch (Exception ex) {
									log("Table '" + layerName + "' does not exist in database.");
								}
							}
						}
					}
				});

				sessionMenu.addFocusListener(new FocusListener() {

					@Override
					public void focusLost(FocusEvent arg0) {
						sessionMenu.setVisible(false);
					}

					@Override
					public void focusGained(FocusEvent arg0) {
					}

				});

			}

		});
		
		selectionButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if(panel.editModeIsOn) {
					
					MainFrame.btnDrawEdit.setButtonReleased(true);
					MainFrame.btnDrawEdit.doClick();
					MainFrame.btnDrawEdit.setBackground(Settings.DEFAULT_STATE_COLOR);

				}
				
				if(panel.queryModeIsOn) {
					
					MainFrame.queryButton.setButtonReleased(true);
					MainFrame.queryButton.doClick();
					MainFrame.queryButton.setBackground(Settings.DEFAULT_STATE_COLOR);

				}
				
				panel.toggleSelectionMode();
				
			}
		});
		
		queryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(panel.editModeIsOn) {
					
					MainFrame.btnDrawEdit.setButtonReleased(true);
					MainFrame.btnDrawEdit.doClick();
					MainFrame.btnDrawEdit.setBackground(Settings.DEFAULT_STATE_COLOR);

				}
				
				if(panel.selectionModeIsOn) {
					
					MainFrame.selectionButton.setButtonReleased(true);
					MainFrame.selectionButton.doClick();
					MainFrame.selectionButton.setBackground(Settings.DEFAULT_STATE_COLOR);

				}
				
				panel.toggleQueryMode();
			}	
		});
		
		btnSaveEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(layerListComboBox.getItemCount() > 0) {
					
					int index = layerListComboBox.getSelectedIndex();
			        int layerid = (int) tableOfContents.getModel().getValueAt(index, TableOfContents.LAYER_ID_COL_INDEX);
			        Layer layer = TableOfContents.findLayerWithID(layerid);
			        
			        if(layer.isNotSaved()) {
			        	
			        	saveLayerToDB(layer);
			        	
			        } else { log("Nothing to save"); }
			        
				} else
					log("No layer selected");
			}
		});
		
		databaseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(dbCatalog != null) {
					dbCatalog.dispose();
				} 

				dbCatalog = new DatabaseCatalog();
				dbCatalog.setVisible(true);
				
				
			}
		});
		
		settingsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(settingsFrame != null) {
					settingsFrame.dispose();
				} 

				settingsFrame = new Settings(false);
				settingsFrame.setVisible(true);				
			}
		});
		
		btnGrid.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.toggleGrid();
			}
			
		});
		
		btnAddLayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				handleAddNewLayerIntent();
				updateDrawButtonGroup();
			}	
		});
		
		btnSnap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.toggleSnap();
				
			}
		});
		
		layerListComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				updateDrawButtonGroup();
				handleLayerSaving(e);
				
			}
		});
		
		btnDrawEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEditingSession("new");
			}
			
		});
		
		// ---------------------------------------------------------------------------
		
	}
	
	
	/**
	 * Handles when the user clicks the add new layer button
	 */
	private void handleAddNewLayerIntent() {
		
		// 0. Disable query mode
		panel.disableQueryMode();
		
		// 1. Create list of possible geometry
		// -----------------------------------
		String[] geom = {Settings.POLYGON_GEOMETRY, Settings.POLYLINE_GEOMETRY, Settings.POINT_GEOMETRY};
		
		// 2. Put list inside a combo box
		// ------------------------------
		JComboBox<String> geomList = new JComboBox<String>(geom);
		
	    // a. Create a Jpanel and set the layout
		
	    JPanel layerPanel = new JPanel();
	    layerPanel.setLayout(new GridLayout(4,1));
	  
	    // b. Create the text fields
	    String autoGeneratedLayerName = "New_Layer" + TableOfContents.getNewLayerID();
	    JTextField layerNameTextField = new JTextField(autoGeneratedLayerName);
	   
	    // c. Add componets to panel
	    layerPanel.add(new JLabel("Add a new layer"));
	    layerPanel.add(new JSeparator());
	    layerPanel.add(geomList);
	    layerPanel.add(layerNameTextField);
		
		// 3. Show a JOption pane to select a new geometry type
		// -----------------------------------------------------
		int response = JOptionPane.showConfirmDialog( null, layerPanel , "Choose geometry type", JOptionPane.OK_CANCEL_OPTION);
		
		// 4. On OK option
		//-------------------------------------
		if(response == JOptionPane.OK_OPTION) {
			
			// 4.1 Get the selected item on the combo box
			if( geomList.getSelectedItem().toString().equals(Settings.POLYGON_GEOMETRY ) ||
			    geomList.getSelectedItem().toString().equals(Settings.POLYLINE_GEOMETRY) ||
			    geomList.getSelectedItem().toString().equals(Settings.POINT_GEOMETRY )
					) {

				// 4.2 Create a new layer
				
				String layerName = layerNameTextField.getText().toString();
				if(layerName.length() < 1) { layerName = autoGeneratedLayerName; }
				
				createNewLayer(geomList.getSelectedItem().toString(), layerName);
				
			}
		}
	}
	
	
	/**
	 * Handles when the user wants to start editing session <br>
	 * Checks if there are no layer on the table of contents first then
	 * starts the editing session. <br>
	 * The background of the button is left active during the duration of the edit session
	 * @param signal directive to start new drawing session or continue drawing session
	 */
	private void handleEditingSession(String signal) {
		
		if(layerListComboBox.getModel().getSize() > 0) {
			
	
			String selectedFeatureType = getCurrentFeatureType();
			panel.toggleEditSession(layerListComboBox.getSelectedIndex(), selectedFeatureType,  signal);
			
			log("Edit session started on " + DrawingJPanel.currentLayer.getLayerName());
	
		} else {
			
			log("Drawing was attempted but no layer to edit on the list");
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "No layer is selected, add a layer", "Choose a layer", JOptionPane.ERROR_MESSAGE);
			
			// Release the button from pressed state
			btnDrawEdit.setButtonReleased(true);
		}
	}

	/**
	 * Protocol to save and load layers from the database <br>
	 * Event is invoked when a layer is not saved and another layer was selected to be edited
	 * @param e item event coming from the combo box model
	 */
	private void handleLayerSaving(ItemEvent e) {
		
		if (e.getStateChange() == ItemEvent.DESELECTED) {
	         
           int index = model.getIndexOf(e.getItem());
           int layerid = (int) tableOfContents.getModel().getValueAt(index, TableOfContents.LAYER_ID_COL_INDEX);
           Layer layer = TableOfContents.findLayerWithID(layerid);
           
           if(layer.isNotSaved() && layer.getListOfFeatures().size() > 0) {
        	   
        	   // protocol to save 
        	   Toolkit.getDefaultToolkit().beep();
        	   int response =    JOptionPane.showOptionDialog(
        			   				null,
                                   "Do you want to save changes to " + layer.getLayerName(),
                                   "Save changes",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    null,
                                    null);
        	   
        	   if(response == JOptionPane.YES_OPTION) {
        		   
        		   // Save the items to the database!
        		   saveLayerToDB(layer);
   
        		   

        	   } else if(response == JOptionPane.NO_OPTION) {
        		   
        		   // Reload previous saved item from DB
        		   
        		   // To test, will just clear away
        		   layer.getListOfFeatures().clear();
        		   
        		   panel.repaint();

        	   };
        	   
        	   layer.setNotSaved(false);
           }
           
           try {
        	
        	   // Change the current layer at the drawing panel
	           int toLayerIndex = layerListComboBox.getSelectedIndex();
	           
	           String currentFeature = getCurrentFeatureType();
	           panel.toggleEditSession(toLayerIndex, currentFeature, "continue");
			
			} catch (Exception e1) {

				log("Select a shape to draw in the layer");
			}
		}
	}
	
	/**
	 * Saves a layer to the database<br>
	 * Checks for existing layers from the database<br>
	 * Prompts to overwrite the layer if the same name was found<br>
	 * After saving, the layer notSavedState will be turned to false.
	 * @param layer layer to be saved to the database
	 * @return true if the operation was successful
	 */
	public static boolean saveLayerToDB(Layer layer) {


		try {
			 
 		   // Checks for existing layers from the database
 		   boolean layerDoesNotExist = true;
 		   
 		   for(String existingTable[] : dbConnection.getTables()) {
 			   if(existingTable[0].equals(layer.getLayerName())) {
 				  layerDoesNotExist = false;
 				  break;
 			   }
 		   }
 		   
 		   // If layerDoesNotExist, just write to the db
 		   if(layerDoesNotExist) {
 			   
 			   	dbConnection.writeTable(layer.getLayerName(), layer);
				
				panel.showAnimatedHint("Saved!", Settings.FEATURE_CREATED_COLOR);
				log("Layer saved to DB");
				
				layer.setNotSaved(false);

				return true;
				
 		   } else {
 			   
 			  panel.showAnimatedHint("Layer name exists!", Settings.DEFAULT_ERROR_COLOR);
 			  log("Layer with same name was found in the database, confirm to overwrite");
 			  
 			  int response = JOptionPane.showConfirmDialog(null, "Overwrite existing layer?", "Confirm", JOptionPane.YES_NO_OPTION );
 			  
 			  if(response == JOptionPane.YES_OPTION) {
 				  
 				 dbConnection.writeTable(layer.getLayerName(), layer);
 				
 				 panel.showAnimatedHint("Saved!", Settings.FEATURE_CREATED_COLOR);
 				 log("Layer saved to DB");
 				
 				 layer.setNotSaved(false);

 				 return true;
 			  }
 		   }
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			panel.showAnimatedHint("Something went wrong /n Cannot save to DB", Settings.DEFAULT_ERROR_COLOR);
			log(e.getMessage());
			
			return false;
		}
		
		return false;
	}

	public static void createNewLayer(String layerType, String layerName) {
		
		// 1. Create a new layer
		Layer newLayer = new Layer(TableOfContents.getNewLayerID(), true, layerType, layerName );
		
		// 2. Add to the table of content
		tableOfContents.addRowLayer(newLayer);
		
		// 3. Log some message
		String message = "New " + newLayer.getLayerType() + " layer: "+ newLayer.getLayerName() + " was created";
		log(message);
		panel.showAnimatedHint(message, Settings.DEFAULT_STATE_COLOR);
	
	}
	
	/**
	 * Creates a new layer from a database result set
	 * @param resultSet result set from database query
	 * @param layerName the new layer name to store the features contained in the result set
	 */
	public static void createLayerFromResultSet(ResultSet resultSet, String layerName) {
	
		try {
			
			String layerType = "";
			Layer newLayer = new Layer(TableOfContents.getNewLayerID(), true, "", layerName);

			while (resultSet.next()) {
				
				boolean isEllipse = resultSet.getBoolean(3);
				
				layerType = resultSet.getString(2);
				
				Double[] aX = (Double[]) resultSet.getArray(4).getArray();
				Double[] aY = (Double[]) resultSet.getArray(5).getArray();
				
				if(isEllipse) {
					
					double x = aX[0];
					double y = aY[0];
					double rx = resultSet.getDouble(6);
					double ry = resultSet.getDouble(7);
						
					// Ellipse
					Feature feature = new Feature(newLayer.getNextFeatureID());
					Shape circleShape = new Ellipse2D.Double(x - rx, y - ry , rx * 2, ry * 2);
					
					String featureType = "Ellipse";
					if(rx == ry) {
						featureType = "Circle";
					}
					
					feature.setEllipse(isEllipse, new Point2D.Double(x,y), rx, ry);
					feature.setShape(circleShape);
					feature.setFeatureType(featureType);
					feature.setVisibile(true);
					newLayer.setLayerType(layerType);
					newLayer.getListOfFeatures().add(feature);
					
				
				} 
				
				else if (layerType.equals(Settings.POINT_GEOMETRY)) {
					for(int i = 0; i < Math.min(aX.length, aY.length); i++) {
						
						Point2D pointCoord = new Point2D.Double(aX[0], aY[0]);
						
						System.out.println(aX[0] + " " +aY[0]);
						
						Feature point  = new PointItem(newLayer.getNextFeatureID(), pointCoord);
						point.setFeatureType(Settings.POINT_GEOMETRY);
						point.setLayerID(newLayer.getId());
						point.setShape(new Ellipse2D.Double(pointCoord.getX() - Settings.POINT_SIZE/2,
								pointCoord.getY() - Settings.POINT_SIZE/2,
								Settings.POINT_SIZE, Settings.POINT_SIZE));
						
						newLayer.getListOfFeatures().add(point);
						newLayer.setLayerType(Settings.POINT_GEOMETRY);
						newLayer.setNotSaved(true);
						
					}
				}
				
				else {
					
					// Normal path - polygon and polyline
					List<Rectangle2D> vertices = new ArrayList<Rectangle2D>();

					for(int i = 0; i < Math.min(aX.length, aY.length); i++) {
						vertices.add(new Rectangle2D.Double(aX[i] - (Settings.snappingTolerance / 2), aY[i] - (Settings.snappingTolerance / 2),
								Settings.snappingTolerance, Settings.snappingTolerance));
					}

					newLayer.setLayerType(layerType); // ! important
					
					panel.finishPath(vertices, newLayer);

				}		
			}
			
			newLayer.setNotSaved(false);
			tableOfContents.addRowLayer(newLayer);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the current feature type selected on the drawButtonGroup
	 * @return
	 */
	public static String getCurrentFeatureType() {
		
		if(drawButtonGroup.getSelection() != null) {
			
			String selectedFeatureType = drawButtonGroup.getSelection().getActionCommand();
		
			return selectedFeatureType;
		} else
			
			return null;
	}

	/**
	 * Logs messages on the frame with simple animation
	 * @param string message to log
	 */
	public static void log(String string) {
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		logText.append("\n@" + dtf.format(now)+ " " + string );
		
		final long time = System.nanoTime() / 1000000000;

		final ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor();
		ex.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {

				if( ((System.nanoTime() / 1000000000) - time) % 2 == 0) {
					logButton.setBackground(Color.BLACK);
				} else {
					logButton.setBackground(Color.RED);
				}
					
				if( ((System.nanoTime() / 1000000000) - time) > 2) {
					logButton.setBackground(Color.BLACK);
					ex.shutdown();
				}
			}
			
		}, 0, 1, TimeUnit.SECONDS);
		
		logButton.setBackground(Color.BLACK);
	}
	
	/**
	 * Updates the list of layer names on the combo box list. <br>
	 * 
	 * The index of an item on the list is equivalent to the index on the table of contents
	 * @param listOfLayersInString new layer names to be updated
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void updateLayerComboBoxModel(String[] listOfLayersInString) {
		
		model = new DefaultComboBoxModel( listOfLayersInString );
		MainFrame.layerListComboBox.setModel( model );
	}
	
	/**
	 * Updates the list of drawable geometries based on the current layer selected<br>
	 * This is done when:
	 * a. Before edit session is started
	 * b. When the layer in the combo box is changed
	 */
	public static void updateDrawButtonGroup() {
		
		drawButtonGroup.clearSelection();
		
		// 0. Only if there are layers
		//----------------------------------------
		if(layerListComboBox.getItemCount() > 0) {
			
			// 1. Get the current index at the layer list
			// ------------------------------------------
			int index = layerListComboBox.getSelectedIndex();
			
			// 2. Retreive the layer id at the table of currents ()
			// ----------------------------------------------------
			int layerID = (int) tableOfContents.getModel().getValueAt(index, TableOfContents.LAYER_ID_COL_INDEX );
			
			// 3. Retreive the layer itself
			// ----------------------------------------------------
			Layer layer = TableOfContents.findLayerWithID(layerID);
			
			// 4. Reconfirm, the layer cannot be null, just to keep track of things
			//    in case something breaks;
			// ---------------------------------------------------------------------
			if(layer != null) {
				
				// 4.1 Get the layer type
				// -----------------------
				String layerType = layer.getLayerType();
				
				// 4.2 Loop through all the buttons in the draw button group
				// ----------------------------------------------------------
				for (Enumeration<AbstractButton> buttons = drawButtonGroup.getElements(); buttons.hasMoreElements();) {
		            
					DrawIconButton button = (DrawIconButton) buttons.nextElement();
					
					// Get the layer type of the button
					// TODO validate constructing a draw button later!
					
					// Check if the draw button can be drawn on the current layer type
					if(!(layerType.equals(button.getGeometryFamily()))) {
						
						button.setEnabled(false);
						
					} else {
					
						button.setEnabled(true);
					}
		        }
			} 
			
			else {
				
				log("Error, layer not found with the current index!, something went wrong !");
			}	
		} 
		
		else {
			
			// If layer list is empty
			// Disable all draw buttons
			 disableAllDrawButtons();
			
			// Disable the edit start button
			btnDrawEdit.setButtonReleased(false);
			btnDrawEdit.setBackground(Settings.DEFAULT_STATE_COLOR);
			
		}
			
	}
	
	/**
	 * Disable all draw buttons.
	 * 
	 */
	public static void disableAllDrawButtons() {
		
		for (Enumeration<AbstractButton> buttons = drawButtonGroup.getElements(); buttons.hasMoreElements();) {
			DrawIconButton button = (DrawIconButton) buttons.nextElement();
			button.setEnabled(false);
		}
	}

	/**
	 * Releases all draw buttons that cannot be drawn on a specific geometry type.<br>
	 * The buttons are identified from their corresponding action commands.
	 * @param geometry
	 */
	public static void releaseAllOtherToolsButton(String geometry) {
		
		// 1. Loop through all the buttons in the draw button group
		// ----------------------------------------------------------
		for (Enumeration<AbstractButton> buttons = toolsButtonGroup.getElements(); buttons.hasMoreElements();) {
            
			ToolIconButton button = (ToolIconButton) buttons.nextElement();
			
			// a. Get the layer type of the button
			// TODO validate constructing a draw button later!
			
			// b. Check if the draw button can be drawn on the current layer type
			if(!(geometry.equals(button.getActionCommand()))) {
				
				button.setButtonReleased(true);
				button.setSelected(false);
				button.setBackground(Settings.DEFAULT_STATE_COLOR);
				
			}
        }
	}

	/**
	 * Closes the application appropriately
	 * @param e Window Event
	 */
	protected void handleWindowClosingEvent(WindowEvent e) {
		dispose();
		System.exit(0);
	}
}