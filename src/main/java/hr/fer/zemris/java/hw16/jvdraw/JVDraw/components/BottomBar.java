package hr.fer.zemris.java.hw16.jvdraw.JVDraw.components;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.JVDraw.IColorProvider;

/**
 * Bottom bar which shows current background and foreground color
 * @author vladimir
 *
 */
public class BottomBar extends JLabel implements ColorChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * color provider
	 */
	private IColorProvider fgColorProvider;
	/**
	 * color provider
	 */
	private IColorProvider bgColorProvider;
	
	/**
	 * public constructor
	 * @param fgColorProvider
	 * @param bgColorProvider
	 */
	public BottomBar(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
		
		newColorSelected(fgColorProvider, null, null);
		
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		setText(String.format("Foreground color: (%d, %d, %d), background color: (%d, %d, %d).", 
				fgColorProvider.getCurrentColor().getRed(),
				fgColorProvider.getCurrentColor().getBlue(),
				fgColorProvider.getCurrentColor().getGreen(),
				bgColorProvider.getCurrentColor().getRed(),
				bgColorProvider.getCurrentColor().getBlue(),
				bgColorProvider.getCurrentColor().getGreen()));
		
	}

}
