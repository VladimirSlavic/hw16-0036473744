package hr.fer.zemris.java.hw16.jvdraw.JVDraw;

import java.awt.Color;

/**
 * Subject interface in the observable pattern for notifying all listeners that the color has changed.
 * @author vladimir
 *
 */
public interface IColorProvider {
	/**
	 * Method for getting the current color
	 * @return current color
	 */
	public Color getCurrentColor();

	/**
	 * Method for adding a listener
	 * @param l
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Method for removing a listener
	 * @param l
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}