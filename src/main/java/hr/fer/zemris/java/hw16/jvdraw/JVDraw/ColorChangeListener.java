package hr.fer.zemris.java.hw16.jvdraw.JVDraw;

import java.awt.Color;

/**
 * Listener for any changes in color performed by user.
 * @author vladimir
 *
 */
public interface ColorChangeListener {
	/**
	 * Method that is triggered when the color is changed
	 * @param source source of color change
	 * @param oldColor old color
	 * @param newColor new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}