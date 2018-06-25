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
 * Class representing a filled circle
 * @author vladimir
 *
 */
public class FilledCircle extends GeometricalObject {

	/**
	 * Public constructor
	 */
	public FilledCircle() {
		super();
	}

	/**
	 * Public constructor
	 * @param p1 first point clicked
	 * @param p2 second clicked point
	 */
	public FilledCircle(Point p1, Point p2) {
		super(p1, p2);
	}

	@Override
	public void drawShape(Graphics g) {
		drawShape(g, foregroundC, backgroundC);
	}

	@Override
	public void drawShape(Graphics g, Color fg, Color bg) {
		double radius = getRadius();

		draw(g, fg, bg, (int) (s.getX()), s.y, (int) radius);

	}

	@Override
	public String toString() {
		return "Filled Circle " + id;
	}

	@Override
	public void update() {
		JPanel panel = new JPanel();
		JTextField firstPoint = new JTextField(s.x + "," + s.y);
		JTextField secPoint = new JTextField("" + getRadius());
		JColorArea jcol = new JColorArea(foregroundC);
		JColorArea background = new JColorArea(backgroundC);

		panel.add(new JLabel("center point"));
		panel.add(firstPoint);
		panel.add(new JLabel("radius"));
		panel.add(secPoint);
		panel.add(jcol);
		panel.add(background);
		int status = JOptionPane.showConfirmDialog(null, panel);

		if (status == JOptionPane.OK_OPTION) {
			String[] p1 = firstPoint.getText().split(",");
			if (p1.length == 2) {
				s.x = Integer.parseInt(p1[0]);
				s.y = Integer.parseInt(p1[1]);
			}

			setRadius(Integer.parseInt(secPoint.getText()));

			setForegroundC(jcol.getCurrentColor());
			setBackgroundC(background.getCurrentColor());

		}

	}

	@Override
	public int getMaxX() {
		return (int) (s.getX() + 2 * getRadius());
	}

	@Override
	public int getMaxY() {
		return (int) (s.getY() + 2 * getRadius());
	}

	@Override
	public int getMinX() {
		return (int) (s.getX() - getRadius());
	}

	@Override
	public int getMinY() {
		return (int) (s.getY() - getRadius());
	}

	@Override
	public String getFileString() {
		return String.format("FCIRCLE %d %d %d %d %d %d %d %d %d", (int) s.getX(), (int) s.getY(), getRadius(),
				foregroundC.getRed(), foregroundC.getGreen(), foregroundC.getBlue(), backgroundC.getRed(),
				backgroundC.getGreen(), backgroundC.getBlue());

	}

	private void draw(Graphics g, Color fg, Color bg, int xc, int yc, int radius) {
		g.setColor(bg);

		g.fillOval((int) (xc - radius), yc - radius, (2 * radius), (2 * radius));
		g.setColor(fg);
		g.drawOval((int) (xc - radius), yc - radius, (2 * radius), (2 * radius));
	}

	@Override
	public void drawShape(Graphics g, int xOffsett, int yOffset) {
		draw(g, getForegroundC(), getBackgroundC(), (int) (s.getX() - xOffsett), s.y - yOffset, getRadius());

	}
}
