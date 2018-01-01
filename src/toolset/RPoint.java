package toolset;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * @author OlumideEnoch
 *
 */
public class RPoint  {
	
	private Color color = Color.BLACK;
	private Point2D realPoint;
	private Point2D imagePoint;
	private Ellipse2D imagePointShape;
	
	private int r = 10;
	
	
	public RPoint() {
		super();
	}
	
	public RPoint(Point2D point) {
		super();
		this.realPoint = point;
		this.setImagePointShape(new Ellipse2D.Double(point.getX() - (r/2), point.getY() - (r/2), r , r));
	}
	
	public RPoint(Point2D point, Color color) {
		super();
		this.realPoint = point;
		this.color = color;
		this.setImagePointShape(new Ellipse2D.Double(point.getX() - (r/2), point.getY() - (r/2), r , r));
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Point2D getRealPoint() {
		return realPoint;
	}

	public void setRealPoint(Point2D realPoint) {
		this.realPoint = realPoint;
	}

	public Point2D getImagePoint() {
		return imagePoint;
	}

	/**
	 * @return the imagePointShape
	 */
	public Ellipse2D getImagePointShape() {
		return imagePointShape;
	}

	/**
	 * @param imagePointShape the imagePointShape to set
	 */
	public void setImagePointShape(Ellipse2D imagePointShape) {
		this.imagePointShape = imagePointShape;
	}

	public void setImagePoint(Point2D imagePoint) {
		this.imagePoint = imagePoint;
		this.setImagePointShape(new Ellipse2D.Double(imagePoint.getX() - (r/2), imagePoint.getY() - (r/2), r , r));
	}

}
