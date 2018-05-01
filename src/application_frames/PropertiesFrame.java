package application_frames;

import javax.swing.JFrame;

import custom_components.CustomJFrame;
import toolset.Tools;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JSeparator;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import core_classes.Layer;
import core_components.TableOfContents;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * 
 * @author Olumide
 *
 */

public class PropertiesFrame extends CustomJFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel panel = new JPanel();
	
	private int layerLineWeight = SettingsFrame.DEFAULT_LAYER_LINE_WEIGHT;
	private int transparencyLevel = SettingsFrame.DEFAULT_LAYER_TRANSPARENCY_LEVEL;
	private Color layerFillColor = SettingsFrame.DEFAULT_LAYER_COLOR;
	private Color layerOutlineColor = SettingsFrame.DEFAULT_LAYER_COLOR;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PropertiesFrame frame = new PropertiesFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	*/

	/**
	 * Create the frame.
	 * @param layer 
	 */
	public PropertiesFrame(Layer layer) {
		
		super.setTitle("Set " + layer.getLayerName() +  " layer properties" );
		
		Color c = layer.getFillColor();
		Color o = layer.getOutlineColor();
				
		layerFillColor = new Color(c.getRed(), c.getGreen(), c.getBlue());
		layerOutlineColor = new Color(o.getRed(), o.getGreen(), o.getBlue());
		layerLineWeight = layer.getLineWeight();
		transparencyLevel = layer.getTransparencyLevel();
		
		
		setBounds(100, 100, 450, 399);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Cancel");
		btnNewButton.setBounds(335, 314, 89, 35);
		getContentPane().add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				// Revert back the changes
				layer.setFillColor(layerFillColor);
				layer.setOutlineColor(layerOutlineColor);
				layer.setLineWeight(layerLineWeight);
				layer.setTransparencyLevel(transparencyLevel);
				
				
				dispose();
				
			}
		});
		
		JButton btnFinish = new JButton("FInish");
		btnFinish.setBounds(236, 314, 89, 35);
		getContentPane().add(btnFinish);
		
		btnFinish.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 205, 414, -1);
		getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 304, 414, 2);
		getContentPane().add(separator_1);
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 434, 93);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Properties");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(10, 11, 108, 30);
		panel.add(lblNewLabel);
		
		JLabel lblEditLayerProperties = new JLabel("Edit layer properties");
		lblEditLayerProperties.setBounds(40, 54, 126, 14);
		panel.add(lblEditLayerProperties);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(Tools.getIconImage("/images/settings1.PNG", 45, 45) );
		lblNewLabel_1.setBounds(379, 23, 45, 45);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Fill");
		lblNewLabel_2.setBounds(10, 104, 57, 27);
		getContentPane().add(lblNewLabel_2);
		
		JPanel fillPanel = new JPanel();
		fillPanel.setBackground(layerFillColor);
		fillPanel.setBounds(89, 109, 35, 20);
		getContentPane().add(fillPanel);
		
		JLabel lblOutline = new JLabel("Outline");
		lblOutline.setBounds(10, 145, 46, 14);
		getContentPane().add(lblOutline);
		
		JPanel outline_panel = new JPanel();
		outline_panel.setBorder(new LineBorder(layerOutlineColor, 1));
		outline_panel.setBackground(Color.WHITE);
		outline_panel.setBounds(89, 142, 35, 20);
		getContentPane().add(outline_panel);
		
		JLabel lblThickness = new JLabel("Thickness");
		lblThickness.setBounds(10, 178, 69, 14);
		getContentPane().add(lblThickness);
		
		JSpinner thicknessSpinner = new JSpinner();
		thicknessSpinner.setModel(new SpinnerNumberModel(new Integer(5), new Integer(0), null, new Integer(1)));
		thicknessSpinner.setBounds(89, 175, 68, 20);
		getContentPane().add(thicknessSpinner);
		
		JLabel lblTransparency = new JLabel("Transparency");
		lblTransparency.setBounds(178, 178, 81, 14);
		getContentPane().add(lblTransparency);
		
		JSpinner transparencySpinner = new JSpinner();
		transparencySpinner.setModel(new SpinnerNumberModel(layer.getTransparencyLevel(), 0, 255, 1));
		transparencySpinner.setBounds(257, 175, 68, 20);
		getContentPane().add(transparencySpinner);
		
		//outline_panel.
		
		thicknessSpinner.getModel().addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				
				layer.setLineWeight((int) thicknessSpinner.getValue());
				
				MainFrame.panel.repaint();
				TableOfContents.updateTableCell(layer.getRenderingIndex(), TableOfContents.LAYER_ICON_COL_INDEX);
			}
		});
		
		transparencySpinner.getModel().addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				
				layer.setTransparencyLevel((int) transparencySpinner.getValue());
				
				MainFrame.panel.repaint();
				TableOfContents.updateTableCell(layer.getRenderingIndex(), TableOfContents.LAYER_ICON_COL_INDEX);

			}
		});
		
		fillPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				Color color = JColorChooser.showDialog(null, "Change color", layer.getFillColor());
				if(color != null) {
					
					layer.setFillColor(color);
					fillPanel.setBackground(color);
					
					MainFrame.panel.repaint();
					TableOfContents.updateTableCell(layer.getRenderingIndex(), TableOfContents.LAYER_ICON_COL_INDEX);
					
				}
			}
			
		});
		
		outline_panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				//new CustomColorPicker();
				Color color = JColorChooser.showDialog(null, "Change color", layer.getFillColor());
				if(color != null) {
					
					layer.setOutlineColor(color);
					outline_panel.setBorder(BorderFactory.createLineBorder(color));
					
					MainFrame.panel.repaint();
					TableOfContents.updateTableCell(layer.getRenderingIndex(), TableOfContents.LAYER_ICON_COL_INDEX);
				}
			}
			
		});

	}
}
