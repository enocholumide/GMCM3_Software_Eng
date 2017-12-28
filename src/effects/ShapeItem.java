/**
 * 
 */
package effects;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import application_frames.SettingsFrame;

/**
 * Class for the creation of a ShapeItem
 * @author OlumideEnoch
 */
public class ShapeItem implements Shape {
	
	private Color color = SettingsFrame.defaultColor;
	private int shapeID;
	
	/**
	 * Creates the ShapeItem
	 */
	public ShapeItem() {
		super();
	}

	/**
	 * Returns the Color of a ShapeItem
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color of a ShapeItem
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}


	/**
	 * Returns the shape ID
	 * @return the shapeID
	 */
	public int getShapeID() {
		return shapeID;
	}

	/**
	 * Sets the shape ID
	 * @param shapeID the shapeID to set
	 */
	public void setShapeID(int shapeID) {
		this.shapeID = shapeID;
	}

	/**
	 * Returns the result of the spatial query contains (Point)
	 * @param arg0 the Point2D arg0 to set
	 * @return false Returns false
	 */
	@Override
	public boolean contains(Point2D arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Returns the result of the spatial query contains (Rectangle)
	 * @param arg0 the Rectangle2D arg0 to set
	 * @return false Returns false
	 */
	@Override
	public boolean contains(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Returns the result of the spatial query contains
	 * @param arg0 the double arg0 to set
	 * @param arg1 the double arg1 to set
	 * @return false Returns false
	 */
	@Override
	public boolean contains(double arg0, double arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Returns the result of the spatial query contains
	 * @param arg0 the double arg0 to set
	 * @param arg1 the double arg1 to set
	 * @param arg2 the double arg2 to set
	 * @param arg3 the double arg3 to set
	 * @return false Returns false
	 */
	@Override
	public boolean contains(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Returns the Bounds of a Rectangle
	 * @return null Returns null
	 */
	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns the Bounds of a Rectangle2D
	 * @return null Returns null
	 */
	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns the PathIterator
	 * @param arg0 the AffineTransform arg0 to set
	 * @return null Returns null
	 */
	@Override
	public PathIterator getPathIterator(AffineTransform arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns the PathIterator
	 * @param arg0 the AffineTransform arg0 to set
	 * @param arg1 the double arg1 to set
	 * @return null Returns null
	 */
	@Override
	public PathIterator getPathIterator(AffineTransform arg0, double arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns the result of the spatial query intersects
	 * @param arg0 the Rectangle2D arg0 to set
	 * @return false Returns false
	 */
	@Override
	public boolean intersects(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Returns the result of the spatial query intersects
	 * @param arg0 the double arg0 to set
	 * @param arg1 the double arg1 to set
	 * @param arg2 the double arg2 to set
	 * @param arg3 the double arg3 to set
	 * @return false Returns false
	 */
	@Override
	public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
