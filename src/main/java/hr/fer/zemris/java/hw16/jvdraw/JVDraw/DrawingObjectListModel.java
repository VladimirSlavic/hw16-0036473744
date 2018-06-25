package hr.fer.zemris.java.hw16.jvdraw.JVDraw;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Class that represents the DataModel for any JLists. Uses that datamodel
 * object and delegates all work towards the datamodel. Used only to represent the known information about objects in a JList.
 * 
 * @author vladimir
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Reference to the datamodel
	 */
	private DrawingModel model;

	/**
	 * List of all listeners.
	 */
	private List<ListDataListener> listeners;

	/**
	 * Public constructor
	 * @param model data model
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		listeners = new ArrayList<>();
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {

		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index0, index1);
		for (ListDataListener l : listeners) {
			l.intervalAdded(event);
		}

	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index0, index1);
		for (ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, index0, index1);
		for (ListDataListener l : listeners) {
			l.intervalAdded(event);
		}

	}

	@Override
	public void addListDataListener(ListDataListener l) {

		if (l == null) {
			throw new IllegalArgumentException("Cannot add null as DataListener!");
		}

		listeners = new ArrayList<>(listeners);

		if (!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	@Override
	public void removeListDataListener(ListDataListener l) {

		if (l == null) {
			throw new IllegalArgumentException("Cannot remove null reference from list of data listeners.");
		}

		listeners = new ArrayList<>(listeners);
		listeners.add(l);
	}

}
