/**
 * 
 */
package features;

import java.awt.Shape;
import java.awt.geom.Path2D;

import core_classes.Feature;
import java.awt.Polygon;

/**
 * @author Isaac
 * Class for representing polygons in our system.  Inherits from Feature.
 */
public class PolygonItem extends Feature {
	
	private Shape shape;
	
	/**
	 * 
	 * @param id
	 * @param shape
	 */
	public PolygonItem(int id, Path2D shape) {
		super(id);
		this.shape = shape;
	}

	/**
	 * @return the shape
	 */
	public Shape getShape() {
		return shape;
	}

	/**
	 * @param shape the shape to set
	 */
	public void setShape(Shape shape) {
		this.shape = shape;
	}
}
