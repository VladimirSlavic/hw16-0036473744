package hr.fer.zemris.java.hw16.jvdraw.JVDraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw.components.JColorArea;

/**
 * Class representing a line
 * @author vladimir
 *
 */
public class Line extends GeometricalObject {

	/**
	 * public constructor
	 */
	public Line() {
		super();
	}

	/**
	 * public constructor
	 * @param p1 first point clicked
	 * @param p2 second point clicked
	 */
	public Line(Point p1, Point p2) {
		super(p1, p2);
	}

	@Override
	public void drawShape(Graphics g){
		drawShape(g, foregroundC, backgroundC);
	}
	
	@Override
	public void drawShape(Graphics g, int xOffsett, int yOffset) {
		draw( g,  getForegroundC(),  getBackgroundC(),(int) s.getX() - xOffsett, (int) s.getY()- yOffset,(int) f.getX()- xOffsett, (int)f.getY()- yOffset);
	}
	
	@Override
	public void drawShape(Graphics g, Color fg, Color bg) {
		
		draw( g,  fg,  bg,(int) s.getX(), (int) s.getY(),(int) f.getX(), (int)f.getY());

	}
	
	/**
	 * Method for drawing a line on the canvas
	 * @param g graphics
	 * @param fg foreground color
	 * @param bg background color
	 * @param x0 staring x
	 * @param y0 starting y
	 * @param x1 ending x
	 * @param y1 ending y
	 */
	public void draw(Graphics g, Color fg, Color bg, int x0, int y0, int x1, int y1){
		
		g.setColor(fg);
		g.drawLine( x0,  y0, x1, y1);
		
	}

	@Override
	public String toString() {
		return "Line " + id;
	}

	@Override
	public void update() {
		JPanel panel = new JPanel();
		JTextField firstPoint = new JTextField(s.x+","+s.y);
		JTextField secPoint = new JTextField(f.x+","+f.y);
		JColorArea jcol = new JColorArea(foregroundC);
		
		panel.add(new JLabel("start point"));
		panel.add(firstPoint);
		panel.add(new JLabel("end point"));
		panel.add(secPoint);
		panel.add(jcol);
		int status = JOptionPane.showConfirmDialog(null, panel);
		if(status == JOptionPane.OK_OPTION){
			String[] p1 = firstPoint.getText().split(",");
			if(p1.length == 2){
				s.x = Integer.parseInt(p1[0]);
				s.y = Integer.parseInt(p1[1]);
			}
			
			String[] p2 = secPoint.getText().split(",");
			if(p2.length == 2){
				f.x = Integer.parseInt(p2[0]);
				f.y = Integer.parseInt(p2[1]);
			}
						
			setForegroundC(jcol.getCurrentColor());			
		}
		
	}

	@Override
	public String getFileString() {
		//LINE x0 y0 x1 y1 red green blue
		return String.format("LINE %d %d %d %d %d %d %d",(int) s.getX(), (int)s.getY(),(int) f.getX(), (int)f.getY(), 
				foregroundC.getRed(), foregroundC.getBlue(), foregroundC.getGreen());
	}

	@Override
	public int getMaxX() {
		return (int) Math.max(s.getX(), f.getX());
	}

	@Override
	public int getMaxY() {
		return (int) Math.max(s.getY(), f.getY());
	}

	@Override
	public int getMinX() {
		return (int) Math.min(s.getX(), f.getX());
	}

	@Override
	public int getMinY() {
		return (int) Math.min(s.getY(), f.getY());
	}
	
}
