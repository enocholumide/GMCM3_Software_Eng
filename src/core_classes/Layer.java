/**
 * 
 */
package core_classes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;

import application_frames.Settings;

/**
 * 
 * @author OlumideEnoch
 *
 */
public class Layer  {
	
	/**The layer's current ID derived from the current session's table of contents*/
	private int id = 0;
	
	/**The data that represents the layer in the table of contents*/
	private Object[] tableData = {};
	
	/**The layer's list of features*/
	private List<Feature> listOfFeatures = new ArrayList<Feature>();
	
	/**The layer's name*/
	private String layerName;
	
	/**The layer's geometry type i.e polygon, polyline or point*/
	private String layerType;
	
	/**The layer's color, set to the default of black*/
	private Color layerColor = Settings.DEFAULT_LAYER_COLOR;
	
	/**The layer's line weight*/
	private int lineWeight = Settings.DEFAULT_LAYER_LINE_WEIGHT;
	
	/**The visibilty of the layer in the drawing session. Used by the paint component*/
	private boolean isVisible = true;
	
	/**The layer's saved state, turns unsaved when new features are added*/
	private boolean notSaved = false;
	
	
	
	/**
	 * Class constructor
	 * @param id Generated layer ID based on the current session from the table of contents
	 * @param layerName Layer's name as inputed on creation or auto generated
	 * @param isVisible The visibilty of the layer in the drawing session
	 * @param layerColor The layer's color
	 * @param layerType The layer's geometry type
	 */
	public Layer(int id, boolean isVisible, String layerType,  String layerName) {
		
		this.id = id;
		this.layerName = layerName;
		this.isVisible = isVisible;
		this.layerType = layerType;
		this.tableData = new Object[] {isVisible, layerType, layerName, "", id};
	}
	
	
	
	/**
	 * Gets the layer's unique ID in the drawing session
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Changes the layer ID during the drawing session.
	 * All the features belonging to this layer must be changed as well
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
		
		// !!!! Change the layer ID of all the features in the layer list
		for(Feature feature : listOfFeatures) {
			feature.setLayerID(id);
		}
	}

	/**
	 * Gets the table data that represents the layer in the table of contents
	 * @return the tableData
	 */
	public Object[] getTableData() {
		
		return this.tableData;
	}

	/**
	 * 
	 * @return the layerName
	 */
	public String getLayerName() {
		return layerName;
	}

	/**
	 * @param layerName the layerName to set
	 */
	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	/**
	 * @return the isActive
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	/**
	 * @return the layerColor
	 */
	public Color getLayerColor() {
		return layerColor;
	}

	/**
	 * @param layerColor the layerColor to set
	 */
	public void setLayerColor(Color layerColor) {
		this.layerColor = layerColor;
	}

	/**
	 * @return the layerType
	 */
	public String getLayerType() {
		return layerType;
	}

	/**
	 * @param layerType the layerType to set
	 */
	public void setLayerType(String layerType) {
		this.layerType = layerType;
	}

	/**
	 * @return the notSaved
	 */
	public boolean isNotSaved() {
		return notSaved;
	}

	/**
	 * @param notSaved the notSaved to set
	 */
	public void setNotSaved(boolean notSaved) {
		this.notSaved = notSaved;
	}

	/**
	 * @return the listOfFeatures
	 */
	public List<Feature> getListOfFeatures() {
		return listOfFeatures;
	}

	/**
	 * @param listOfFeatures the listOfFeatures to set
	 */
	public void setListOfFeatures(List<Feature> listOfFeatures) {
		this.listOfFeatures = listOfFeatures;
	}

	public int getNextFeatureID() {

		return this.listOfFeatures.size() + 1;
	}

	/**
	 * @return the lineWeight
	 */
	public int getLineWeight() {
		return lineWeight;
	}

	/**
	 * @param lineWeight the lineWeight to set
	 */
	public void setLineWeight(int lineWeight) {
		this.lineWeight = lineWeight;
	}

	public void highlightAllFeatures(boolean cond) {
		for(Feature feature : listOfFeatures) {
			feature.setHighlighted(cond);
		}
	}
	
	/**
	 * Gets a feature within the layer with the feature's ID
	 * @param id feature's ID that needs to be retrived
	 * @return feature within the layer with the feature's ID
	 */
	public Feature getFeatureWithID(int id) {
		
		for(Feature feature : listOfFeatures) {
			if(feature.getId() == id ) {
				return feature;
			}
		}
		
		return null;	
	}
	
	/**
	 * 
	 * @return the size of all the features in the layer
	 */
	public int getSize() {
		return listOfFeatures.size();
	}
	
	/**
	 * Removes last feature from the layer
	 */
	public void removeLastItem() {
		listOfFeatures.remove(getSize()-1);
	}

	/**
	 * @param tableData the tableData to set
	 */
	public void setTableData(Object[] tableData) {
		this.tableData = tableData;
	}
}
