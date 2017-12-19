/**
 * 
 */
package core_components;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import application_frames.Settings;

/**
 * Class for the creation of the DrawingCursor
 * WORK IN PROGRESS
 * @author OlumideEnoch
 *
 */
public class DrawingCursor {
	
	private Rectangle cursor;
	private Color color = Settings.cursorColor;
	private List<Line2D> lines = new ArrayList<Line2D>();
	
	/**
	 * Creates the DrawCursor
	 * @param cursor the cursor to set
	 * @param color the color to set
	 */
	public DrawingCursor(Rectangle cursor, Color color) {
		super();
		this.cursor = cursor;
		this.color = color;
		setLines();
	}
	
	/**
	 * Creates the DrawCursor
	 */
	public DrawingCursor() {
		super();
	}
	
	/**
	 * Returns the cursor
	 * @return the cursor
	 */
	public Rectangle getCursor() {
		return cursor;
	}
	/**
	 * Sets the cursor
	 * @param cursor the cursor to set
	 */
	public void setCursor(Rectangle cursor) {
		this.cursor = cursor;
		setLines();
	}
	/**
	 * Returns the color
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * Sets the color
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Returns the lines
	 * @return the lines
	 */
	public List<Line2D> getLines() {
		return lines;
	}

	/**
	 * Sets the lines
	 * @param lines the lines to set
	 */
	private void setLines() {
		
		this.lines.clear();
		
		double cx = this.cursor.getCenterX();
		double cy = this.cursor.getCenterY();
		
		double wi = this.cursor.getWidth();
		
		double e = 1.5;
		
		//left
		this.lines.add(new Line2D.Double(
				cx-wi*e,
				cy,
				cx-wi/2,
				cy));
		
		//right
		this.lines.add(new Line2D.Double(
				cx+wi/2,
				cy,
				cx+wi*e,
				cy));
		
		//up
		this.lines.add(new Line2D.Double(
				cx,
				cy-wi/2,
				cx,
				cy-wi*e));
		
		//bottom
		this.lines.add(new Line2D.Double(
				cx,
				cy+wi*e,
				cx,
				cy+wi/2));
		
	}
	
}
