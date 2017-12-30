/**
 * 
 */
package core_classes;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import application_frames.Settings;
import toolset.Tools;

/**
 * Class for the creation of a Feature
 * @author OlumideEnoch
 *
 */
public class Feature {

    private int id;
    private Shape shape;
    private List<Rectangle2D> vertices = new ArrayList<Rectangle2D> ();
    private String featureType;
    private Point2D center;
    private boolean isEllipse = false;
    private double radiusX, radiusY;
	private double[][] coordinatesArrayXY;
	private int layerID;
	private boolean isHighlighted = false;
	private boolean isVisibile = true;

    /**
     * Creates an Object of the class Layer
     * @param id the ID of a Feature
     */
    public Feature(int id) {
        this.id = id;
    }

    /**
     * Returns the ID of a Feature
     * @return id
     */
    public int getId() {
        return this.id;
    }

	/**
	 * Returns a list of vertices. The vertices are used for rendering. They are all Rectangle 2D <br>
	 * @return The vertices as a list of Rectangle 2D
	 */
	public List<Rectangle2D> getVertices() {
		return vertices;
	}
	
	/**
	 * Sets the vertices (Rectangle 2D used for rendering.
	 * @param vertices the vertices to set
	 */
	public void setVertices(List<Rectangle2D> vertices) {
		this.vertices = vertices;
	}
	
	/**
	 * Sets the vertices of a Feature from an input integer array
	 * @param xp Array of x coordinates of the vertices
	 * @param yp Array of y coordinates of the vertices
	 */
	
	public void setVerticesFromArray(int[] xp, int[] yp) {
		
		double[] x = Tools.copyFromIntArray(xp);
		double[] y = Tools.copyFromIntArray(xp);
		
		int snapSize = Settings.snappingTolerance;
		
		for(int i = 0; i < x.length; i++) {
			this.vertices.add(new Rectangle2D.Double(x[i] - (snapSize/2), y[i] - (snapSize/2), snapSize, snapSize));
		}	
	}
	
	/**
	 * Sets the vertices of a Feature from an input double array
	 * @param x Array of x coordinates of the vertices
	 * @param y Array of y coordinates of the vertices
	 */
	public void setVerticesFromDoubleArray(double[] x, double[] y) {
		
		int snapSize = Settings.snappingTolerance;
		
		for(int i = 0; i < x.length; i++) {
			this.vertices.add(new Rectangle2D.Double(x[i] - (snapSize/2), y[i] - (snapSize/2), snapSize, snapSize));
		}	
	}
	
	/**
	 * Returns the Shape of a Feature
	 * @return the Shape
	 */
	public Shape getShape() {
		return shape;
	}

	/**
	 * Sets the shape of a Feature
	 * @param shape the shape to set
	 */
	public void setShape(Shape shape) {
		this.shape = shape;
	}

	/**
	 * Returns the type of a Feature
	 * @return the featureType
	 */
	public String getFeatureType() {
		return featureType;
	}

	/**
	 * Sets the type of a Feature
	 * @param featureType the featureType to set
	 */
	public void setFeatureType(String featureType) {
		this.featureType = featureType;
	}

	/**
	 * Returns true, if a Feature is an Ellipse
	 * @return the isEllipse
	 */
	public boolean isEllipse() {
		return isEllipse;
	}

	/**
	 * Sets the parameters of an Ellipse
	 * @param isEllipse the isEllipse to set
	 * @param center the center of an Ellipse to set
	 * @param radiusX the x radius of an Ellipse to set
	 * @param radiusY the y radius of an Ellipse to set
	 */
	public void setEllipse(boolean isEllipse, Point2D center, double radiusX, double radiusY) {
		
		this.radiusX = radiusX;
		this.radiusY = radiusY;
		this.setCenter(center);
		this.isEllipse = isEllipse;
	}

	/**
	 * Returns the center coordinates of a Feature's vertices
	 * @return the coordinatesArrayXY
	 */
	public double[][] getCoordinatesArrayXY() {

		double[] x = new double[vertices.size()];
		double[] y = new double[vertices.size()];
		
		for (int i = 0 ; i < vertices.size(); i++) {
			x[i] = vertices.get(i).getCenterX();
			y[i] = vertices.get(i).getCenterY();
		}

		double[][] newCoords = {x, y};
		
		coordinatesArrayXY = newCoords;
		
		return coordinatesArrayXY;
	}

	/**
	 * Sets the coordinates array of a Feature for x and y
	 * @param coordinatesArrayXY the coordinatesArrayXY to set
	 */
	public void setCoordinatesArrayXY(double[][] coordinatesArrayXY) {
		this.coordinatesArrayXY = coordinatesArrayXY;
	}
	
	/**
	 * Returns the x radius of an Ellipse
	 * @return the radiusX
	 */
	public double getRadiusX() {
		return radiusX;
	}

	/**
	 * Returns the y radius of an Ellipse
	 * @return the radiusY
	 */
	public double getRadiusY() {
		return radiusY;
	}

	/**
	 * Returns the Layer ID of which a Feature belongs to
	 * @return the layerID
	 */
	public int getLayerID() {
		return layerID;
	}

	/**Sets the ID of the Layer a Feature belongs to
	 * @param layerID the layerID to set
	 */
	public void setLayerID(int layerID) {
		this.layerID = layerID;
	}

	/**
	 * Returns the highlighting status of a Feature
	 * @return the isHighlighted
	 */
	public boolean isHighlighted() {
		return isHighlighted;
	}

	/**
	 * Sets the highlighting status of a Feature
	 * @param isHighlighted the isHighlighted to set
	 */
	public void setHighlighted(boolean isHighlighted) {
		this.isHighlighted = isHighlighted;
	}

	/**
	 * Returns the visibility status of a Feature
	 * @return the isVisibile
	 */
	public boolean isVisibile() {
		return isVisibile;
	}

	/**
	 * Sets the visibility status of a Feature
	 * @param isVisibile the isVisibile to set
	 */
	public void setVisibile(boolean isVisibile) {
		this.isVisibile = isVisibile;
	}

	/**
	 * Returns the center of a Feature
	 * @return the center
	 */
	public Point2D getCenter() {
		return center;
	}

	/**
	 * Sets the center of a Feature
	 * @param center the center to set
	 */
	public void setCenter(Point2D center) {
		this.center = center;
	}
}
