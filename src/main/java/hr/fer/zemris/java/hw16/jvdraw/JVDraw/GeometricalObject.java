package hr.fer.zemris.java.hw16.jvdraw.JVDraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Abstract representation of a geometrical shape.
 * @author vladimir
 *
 */
public abstract class GeometricalObject {

	/**
	 * First point clicked by user
	 */
	protected Point s;
	
	/**
	 * Second point clicked by user
	 */
	protected Point f;
	
	/**
	 * foreground color
	 */
	protected Color foregroundC;
	/**
	 * background color
	 */
	protected Color backgroundC;
	
	/**
	 * id of current object
	 */
	protected int id;
	
	/**
	 * radius of object
	 */
	private int radius;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * public constructor
	 */
	public GeometricalObject() {
	}
	
	/**
	 * public constructor
	 * @param p1 first point clicked
	 * @param p2 second clicked point
	 */
	public GeometricalObject(Point p1, Point p2) {
		this.s = p1;
		this.f = p2;
		this.radius = calRadius();
	}
	
	/**
	 * @return the foregroundC
	 */
	public Color getForegroundC() {
		return foregroundC;
	}

	/**
	 * @param foregroundC the foregroundC to set
	 */
	public void setForegroundC(Color foregroundC) {
		this.foregroundC = foregroundC;
	}

	/**
	 * @return the backgroundC
	 */
	public Color getBackgroundC() {
		return backgroundC;
	}

	/**
	 * @param backgroundC the backgroundC to set
	 */
	public void setBackgroundC(Color backgroundC) {
		this.backgroundC = backgroundC;
	}

	/**
	 * @return the s
	 */
	public Point getS() {
		return s;
	}

	/**
	 * @param s the s to set
	 */
	public void setS(Point s) {
		this.s = s;
	}

	/**
	 * @return the f
	 */
	public Point getF() {
		return f;
	}

	/**
	 * @param f the f to set
	 */
	public void setF(Point f) {
		this.f = f;
		this.radius = calRadius();
	}

	/**
	 * Method for drawing a shape
	 * @param g graphics
	 */
	public abstract void drawShape(Graphics g);
	
	/**
	 * Method for drawing a shape
	 * @param g graphics
	 * @param fg foreground color
	 * @param bg  background color
	 */
	public abstract void drawShape(Graphics g, Color fg, Color bg);
	/**
	 * Method for drawing a shape
	 * @param g graphics
	 * @param xOffsett offset when generating image in x direction
	 * @param yOffset  offset when generating image in y direction
	 */
	public abstract void drawShape(Graphics g, int xOffsett, int yOffset);
	
	/**
	 * Method for updating current information on object
	 */
	public abstract void update();
	
	/**
	 * Method for getting File type representation of the shape
	 * @return string of shape
	 */
	public abstract String getFileString();
	
	/**
	 * Method for calculating the radius
	 * @return radius of object
	 */
	public int calRadius(){
		double radius = Math.pow((f.x - s.x), 2) + Math.pow((f.y - s.y), 2);
		radius = Math.sqrt(radius);
		return (int) radius;
	}
	
	
	
	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public String toString() {
		return "Atttar";
	}
	
	/**
	 * Method for getting the max in the x direction
	 * @return max x
	 */
	public abstract int getMaxX();
	/**
	 * Method for getting the max in the y  direction
	 * @return max y
	 */
	public abstract int getMaxY();
	/**
	 * Method for getting the minimum in the x direction
	 * @return max x
	 */
	public abstract int getMinX();
	/**
	 * Method for getting the minimum in the y direction
	 * @return max y
	 */
	public abstract int getMinY();
	
}
