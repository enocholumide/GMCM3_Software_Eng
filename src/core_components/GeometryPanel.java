package core_components;

import java.awt.Color;

import java.awt.Component;
import java.util.Random;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import core_classes.Layer;

/**
 * Class for creating the GeometryPanel
 */
public class GeometryPanel extends DefaultCellEditor {

	private static final long serialVersionUID = 1L;
	protected JButton btn;
	protected GeometryTableIcon panel;
	private Layer layer;
	
	final Random r= new Random();
	
	/**
	 * Creates the GeometryPanel
	 * @param text the text to set
	 */
	public GeometryPanel(JTextField text) {
		super(text);
		panel = new GeometryTableIcon();
	}
	
	// Override some default methods
	/**
	 * Returns the TableCellEditorComponent
	 * @param table the table to set
	 * @param obj the obj to set
	 * @param isSelected the isSelected to set
	 * @param row the row to set
	 * @param column the column to set
	 * @return the panel
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object obj, boolean isSelected, int row, int column ) {


		int id = (int) table.getModel().getValueAt(row, 4);
		layer = TableOfContents.findLayerWithID(id);

		layer.setLayerColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
		panel.setLayer(layer);
		
		return panel;
		
	}
	
	/**
	 * Returns the CellEditorValue
	 * @return the Object getCellEditorValue
	 */
	@Override
	public Object getCellEditorValue() {
	// TODO Auto-generated method stub
		return super.getCellEditorValue();
	}
	
	/**
	 * Returns the stopCellEditing
	 * @return the stopCellEditing
	 */
	@Override
	public boolean stopCellEditing() {
		return super.stopCellEditing();
	}
	
	/**
	 * Returns the fireEditingStopped
	 * @return the fireEditingStopped
	 */
	@Override
	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}

	}