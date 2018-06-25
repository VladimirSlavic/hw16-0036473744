package hr.fer.zemris.java.hw16.jvdraw.JVDraw;

/**
 * Interface that represents a listener on the current data model. Awaits any
 * changes in the canvas.
 * 
 * @author vladimir
 *
 */
public interface DrawingModelListener {
	/**
	 * Signals that a new object has been added to the canvas
	 * 
	 * @param source
	 *            subject
	 * @param index0
	 *            starting index
	 * @param index1
	 *            ending index
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Signals that a new object has been removed to the canvas
	 * 
	 * @param source
	 *            subject
	 * @param index0
	 *            starting index
	 * @param index1
	 *            ending index
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Signals that a new object has been updated to the canvas
	 * 
	 * @param source
	 *            subject
	 * @param index0
	 *            starting index
	 * @param index1
	 *            ending index
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
