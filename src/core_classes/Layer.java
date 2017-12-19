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
 * Class for the creation of a Layer
 * @author OlumideEnoch
 *
 */
public class Layer  {
	
	private Object[] tableData = {};
	
	private List<Feature> listOfFeatures = new ArrayList<Feature>();
	
	private String layerName = "";
	private String layerType = "";
	private Color layerColor = Settings.DEFAULT_LAYER_COLOR;
	private Color selectedLayerColor =  JColorChooser.showDialog(null, "Set Layer Color",layerColor);
	private int lineWeight = Settings.DEFAULT_LAYER_LINE_WEIGHT;
	
	private boolean isVisible = true;
	private boolean notSaved = false;
	
	private int id = 0;
	

	
	/**
	 * Creates an Object of the class Layer
	 * @param id The ID of a Layer
	 * @param isVisible The status of Visibility of a Layer
	 * @param layerType The type of a Layer
	 * @param layerName The name of a Layer
	 */
	public Layer(int id, boolean isVisible, String layerType,  String layerName) {
		//super(id);
		this.id = id;
		this.layerName = layerName;
		this.isVisible = isVisible;
		this.layerType = layerType;
		this.tableData = new Object[] {isVisible, layerType, layerName, "", id};
		
	}
	
	/**
	 * Returns the ID of the Layer
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the ID of the Layer
	 * @param id the ID to set
	 */
	public void setId(int id) {
		this.id = id;
		
		// !!!! Change the layer ID of all the features in the layer list
		for(Feature feature : listOfFeatures) {
			feature.setLayerID(id);
		}
	}

	/**
	 * Returns the table data of a Layer
	 * @return the tableData
	 */
	public Object[] getTableData() {
		
		return this.tableData;
	}

	/**
	 * Returns the name of a Layer
	 * @return the layerName
	 */
	public String getLayerName() {
		return layerName;
	}

	/**
	 * Sets the name of a Layer
	 * @param layerName the layerName to set
	 */
	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	/**
	 * Returns the status of the visibility of a Layer
	 * @return the isVisible
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * Sets the status of the visibility of a Layer
	 * @param isVisible set isVisible parameter
	 */
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	/**
	 * Returns the Color of a Layer
	 * @return the layerColor
	 */
	public Color getLayerColor() {
		return layerColor;
	}

	/**
	 * Sets the Color of a Layer
	 * @param layerColor the layerColor to set
	 */
	public void setLayerColor(Color layerColor) {
		this.layerColor = selectedLayerColor;
	}

	/**
	 * Returns the type of a Layer
	 * @return the layerType
	 */
	public String getLayerType() {
		return layerType;
	}

	/**
	 * Sets the type of a Layer
	 * @param layerType the layerType to set
	 */
	public void setLayerType(String layerType) {
		this.layerType = layerType;
	}

	/**
	 * Returns the saving status of a Layer
	 * @return the notSaved
	 */
	public boolean isNotSaved() {
		return notSaved;
	}

	/**
	 * Sets the saving status of a Layer
	 * @param notSaved the notSaved to set
	 */
	public void setNotSaved(boolean notSaved) {
		this.notSaved = notSaved;
	}

	/**
	 * Returns the feature list of a Layer
	 * @return the listOfFeatures
	 */
	public List<Feature> getListOfFeatures() {
		return listOfFeatures;
	}

	/**
	 * Sets the feature list of a Layer
	 * @param listOfFeatures the listOfFeatures to set
	 */
	public void setListOfFeatures(List<Feature> listOfFeatures) {
		this.listOfFeatures = listOfFeatures;
	}

	/**
	 * Returns the next Feature ID of a Layer
	 * @return the next Feature ID
	 */
	public int getNextFeatureID() {
		return this.listOfFeatures.size() + 1;
	}

	/**
	 * Returns the line weight of a Layer
	 * @return the lineWeight
	 */
	public int getLineWeight() {
		return lineWeight;
	}

	/**
	 * Sets the line weight of a Layer
	 * @param lineWeight the lineWeight to set
	 */
	public void setLineWeight(int lineWeight) {
		this.lineWeight = lineWeight;
	}
	
	/**
	 * Sets the highlighting status of the Features of a Layer
	 * @param cond the cond to set
	 */
	public void highlightAllFeatures(boolean cond) {
		for(Feature feature : listOfFeatures) {
			feature.setHighlighted(cond);
		}
	}
	
	/**
	 * Returns a Feature by ID
	 * @param id the Feature ID to set
	 * @return the Feature
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
	 * Returns the Size of a Feature list
	 * @return listOfFeatures the Feature list size
	 */
	public int getSize() {
		return listOfFeatures.size();
	}

	/**
	 * Removes the last item of a Feature list
	 */
	public void removeLastItem() {
		listOfFeatures.remove(getSize()-1);
	}

	/**
	 * Sets the table Data of a Layer
	 * @param tableData the tableData to set
	 */
	public void setTableData(Object[] tableData) {
		this.tableData = tableData;
	}
	
}
