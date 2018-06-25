package hr.fer.zemris.java.hw16.jvdraw.JVDraw.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.JVDraw.IColorProvider;

/**
 * class representing an option for user to choose colors for drawing in the application
 * @author vladimir
 *
 */
public class JColorArea extends JComponent implements IColorProvider{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * currently selected color
	 */
	private Color selectedColor;
	
	/**
	 * list of all listeners
	 */
	private List<ColorChangeListener> listeners;
	
	/**
	 * dimensions of component
	 */
	private static final Dimension DIM = new Dimension(15,15);
	
	/**
	 * public constructor
	 * @param selectedColor default color
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;
		listeners = new ArrayList<>();
		
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Color newCol = JColorChooser.showDialog(JColorArea.this, "Choose a color!", Color.red);
				if(newCol != null){
					update(selectedColor, newCol);
					JColorArea.this.selectedColor = newCol;
					repaint();
				}
			}
			
			
		});
	}
	
	
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize() {
		return DIM;
	}



	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		if(l == null){
			throw new IllegalArgumentException("Cannot add null as DataListener!");
		}
		
		listeners = new ArrayList<>(listeners);
		
		if(!listeners.contains(l)){
			listeners.add(l);	
		}
		
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		if(l == null){
			throw new IllegalArgumentException("Cannot remove null reference from list of data listeners.");
		}
		
		listeners = new ArrayList<>(listeners);
		listeners.add(l);
		
	}

	/**
	 * Method fired when the color is changes; used to notify all listeners on the change
	 * @param oldColor old color
	 * @param newColor new color
	 */
	public void update(Color oldColor, Color newColor){
		
		for(ColorChangeListener listener : listeners){
			listener.newColorSelected(this, oldColor, newColor);
		}
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(0, 0, getPreferredSize().width, getPreferredSize().width);
	}
	
	
}
