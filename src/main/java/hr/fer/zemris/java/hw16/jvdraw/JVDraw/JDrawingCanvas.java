package hr.fer.zemris.java.hw16.jvdraw.JVDraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

/**
 * Class represents the canvas upon which the user draws all objects
 * @author vladimir
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Current geometrical object
	 */
	private GeometricalObject currentObject;
	
	/**
	 * foreground color provider
	 */
	private IColorProvider providerfg;
	/**
	 * background color provider
	 */
	private IColorProvider providerbg;
	
	/**
	 * datamodel
	 */
	private DrawingModel model;
	
	/**
	 * first clicked point
	 */
	private Point hotpointStart;
	
	/**
	 * second clicked point
	 */
	private Point hotpointEnd;
	
	/**
	 * current type of object being drawn
	 */
	private DrawType drawType;
	
	/**
	 * has any changes happened in canvas after saving
	 */
	private boolean isChanged = false;
	/**
	 * @return the isChanged
	 */
	public boolean isChanged() {
		return isChanged;
	}

	/**
	 * @param isChanged the isChanged to set
	 */
	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}

	/**
	 * line id
	 */
	private int lineID = 0;
	/**
	 * circle id
	 */
	private int circleID = 0;
	/**
	 * filled circle id
	 */
	private int fcircleID = 0;
	
	/**
	 * public constructor
	 * @param providerfg foreground color provider
	 * @param providerbg background color provider
	 * @param model data model
	 * @param drawType 
	 */
	public JDrawingCanvas(IColorProvider providerfg, IColorProvider providerbg, DrawingModel model, DrawType drawType) {
		super();
		this.providerfg = providerfg;
		this.providerbg = providerbg;
		this.model = model;
		this.drawType = drawType;
		setDrawType(drawType);
		
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(hotpointStart == null){
					hotpointStart = e.getPoint();
					currentObject.setS(hotpointStart);
				}else{
					hotpointEnd = e.getPoint();
					currentObject.setF(hotpointEnd);
					currentObject.setBackgroundC(providerbg.getCurrentColor());
					currentObject.setForegroundC(providerfg.getCurrentColor());
					drawAndAddObject(true);
				}
			}
			
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {

			
			@Override
			public void mouseMoved(MouseEvent e) {

				if(hotpointStart == null) return;
				
				hotpointEnd = e.getPoint();
				currentObject.setF(hotpointEnd);
				drawAndAddObject(false);
			}
			
			
		});
	}

	/**
	 * Method for updating the current id for object
	 */
	private void updateID(){
		if(drawType == DrawType.LINE){
			lineID++;
			return;
		}
		
		if(drawType == DrawType.CIRCLE){
			circleID++;
			return;
		}
		
		if(drawType == DrawType.FILLED_CIRCLE){
			fcircleID++;
			return;
		}
	}
	
	/**
	 * Method for drawing and adding the object if the second click has occurred
	 * @param isSecondClick true if second click has occurred
	 */
	private void drawAndAddObject(boolean isSecondClick) {

		if(isSecondClick){
			updateID();
		}
		
		//currentObject.drawShape(getGraphics(), providerfg.getCurrentColor(), providerbg.getCurrentColor());
		
		if(isSecondClick){
			model.add(currentObject);
			setDrawType(drawType);
		}else{
			repaint();
		}
		
	}
	
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		setChanged(true);
		repaint();
		
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		setChanged(true);
		repaint();		
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		setChanged(true);
		repaint();
	}
	
	/**
	 * Method for setting the new draw type and resetting the data
	 * @param newType
	 */
	public void setDrawType(DrawType newType){
		drawType = newType;
		hotpointStart = null;
		hotpointEnd = null;
		if(drawType.equals(DrawType.CIRCLE)){
			currentObject = new Circle();
			currentObject.setId(circleID);
			return;
		}
	
		if(drawType.equals(DrawType.LINE)){
			currentObject = new Line();
			currentObject.setId(lineID);
			return;
		}
		
		if(drawType.equals(DrawType.FILLED_CIRCLE)){
			currentObject = new FilledCircle();
			currentObject.setId(fcircleID);
		}
		
	}
	
	/**
	 * Method for resetting all id's to 0 again.
	 */
	public void resetIds(){
		lineID = 0;
		circleID = 0;
		fcircleID = 0;
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		if (model != null) {
			for (int i = 0; i < model.getSize(); i++) {
				model.getObject(i).drawShape(g);
			}
		}
		if (currentObject != null && currentObject.s != null) {
			currentObject.drawShape(g, providerfg.getCurrentColor(), providerbg.getCurrentColor());
		}
	}

	/**
	 * @return the currentObject
	 */
	public GeometricalObject getCurrentObject() {
		return currentObject;
	}

	/**
	 * @param currentObject the currentObject to set
	 */
	public void setCurrentObject(GeometricalObject currentObject) {
		this.currentObject = currentObject;
	}

	/**
	 * @return the providerfg
	 */
	public IColorProvider getProviderfg() {
		return providerfg;
	}

	/**
	 * @param providerfg the providerfg to set
	 */
	public void setProviderfg(IColorProvider providerfg) {
		this.providerfg = providerfg;
	}

	/**
	 * @return the providerbg
	 */
	public IColorProvider getProviderbg() {
		return providerbg;
	}

	/**
	 * @param providerbg the providerbg to set
	 */
	public void setProviderbg(IColorProvider providerbg) {
		this.providerbg = providerbg;
	}

	/**
	 * @return the model
	 */
	public DrawingModel getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(DrawingModel model) {
		this.model = model;
	}

	/**
	 * @return the hotpointStart
	 */
	public Point getHotpointStart() {
		return hotpointStart;
	}

	/**
	 * @param hotpointStart the hotpointStart to set
	 */
	public void setHotpointStart(Point hotpointStart) {
		this.hotpointStart = hotpointStart;
	}

	/**
	 * @return the hotpointEnd
	 */
	public Point getHotpointEnd() {
		return hotpointEnd;
	}

	/**
	 * @param hotpointEnd the hotpointEnd to set
	 */
	public void setHotpointEnd(Point hotpointEnd) {
		this.hotpointEnd = hotpointEnd;
	}

	/**
	 * @return the lineID
	 */
	public int getLineID() {
		return lineID;
	}

	/**
	 * @param lineID the lineID to set
	 */
	public void setLineID(int lineID) {
		this.lineID = lineID;
	}

	/**
	 * @return the circleID
	 */
	public int getCircleID() {
		return circleID;
	}

	/**
	 * @param circleID the circleID to set
	 */
	public void setCircleID(int circleID) {
		this.circleID = circleID;
	}

	/**
	 * @return the fcircleID
	 */
	public int getFcircleID() {
		return fcircleID;
	}

	/**
	 * @param fcircleID the fcircleID to set
	 */
	public void setFcircleID(int fcircleID) {
		this.fcircleID = fcircleID;
	}

	/**
	 * @return the drawType
	 */
	public DrawType getDrawType() {
		return drawType;
	}
	
	
	
}
