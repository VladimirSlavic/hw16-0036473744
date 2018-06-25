package hr.fer.zemris.java.hw16.jvdraw.JVDraw;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw.components.Bounds;

/**
 * Drawing model interface that stores all information of currently drawn
 * objects in the canvas. Also a subject in the observable pattern and fires
 * message updates to observers when the canvas is changed
 * 
 * @author vladimir
 *
 */
interface DrawingModel {
	/**
	 * Current amount of objects
	 * @return amount of objects
	 */
	public int getSize();

	/**
	 * Gets object at given index
	 * @param index 
	 * @return GeometricalObject
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Method for adding a new object
	 * @param object shape
	 */
	public void add(GeometricalObject object);

	/**
	 * Method for removing a geometrical object
	 * @param index 
	 */
	public void remove(int index);

	/**
	 * Method for updating a geometrical object
	 * @param index 
	 */
	public void update(int index);

	/**
	 * Method for adding a listener
	 * @param l
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Method for removing a listener
	 * @param l
	 */
	public void removeDrawingModelListener(DrawingModelListener l);

	/**
	 * Method for clearing all the data in the model
	 */
	public void clear();

	/**
	 * Method for removing selected indexes
	 * @param indexes
	 */
	public void remove(int... indexes);

	/**
	 * Method for getting current boundaries in the canvas
	 * @return Bounds of canvas
	 */
	public Bounds getBounds();
}