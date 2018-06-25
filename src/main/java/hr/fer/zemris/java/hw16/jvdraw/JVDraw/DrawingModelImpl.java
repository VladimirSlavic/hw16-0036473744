package hr.fer.zemris.java.hw16.jvdraw.JVDraw;

import java.util.*;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw.components.Bounds;

/**
 * Class that is a implementation of the DrawingModel. Stores all data of currently drawn objects.
 * @author vladimir
 *
 */
public class DrawingModelImpl implements DrawingModel {

	/**
	 * List of all objects
	 */
	private List<GeometricalObject> objects;

	/**
	 * List of all listeners
	 */
	private List<DrawingModelListener> listeners;

	/**
	 * Public constructor
	 */
	public DrawingModelImpl() {
		objects = new ArrayList<>();
		listeners = new ArrayList<>();
	}

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		if (index < 0 || index > objects.size()) {
			throw new IndexOutOfBoundsException();
		}

		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject l) {
		if (l == null) {
			throw new IllegalArgumentException("Cannot add null as DataListener!");
		}

		objects = new ArrayList<>(objects);

		if (!objects.contains(l)) {
			int index = objects.size();
			objects.add(l);
			fireAddUpdate(index);
		}

	}

	private void fireAddUpdate(int index) {

		for (DrawingModelListener l : listeners) {
			l.objectsAdded(this, index, index);
		}

	}

	private void fireRemoveUpdate(int index, int index2) {

		for (DrawingModelListener l : listeners) {
			l.objectsRemoved(this, index, index2);
		}

	}

	private void fireUpdateUpdate(int index) {
		
		for (DrawingModelListener l : listeners) {
			l.objectsChanged(this, index, index);
		}

	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		if (l == null) {
			throw new IllegalArgumentException("Cannot add null as DataListener!");
		}

		listeners = new ArrayList<>(listeners);

		if (!listeners.contains(l)) {
			listeners.add(l);
		}

	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		if (l == null) {
			throw new IllegalArgumentException("Cannot remove null reference from list of data listeners.");
		}

		listeners = new ArrayList<>(listeners);
		listeners.add(l);

	}

	@Override
	public void remove(int index) {

		if (index < 0 || index > objects.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		objects.remove(index);
		fireRemoveUpdate(index, index);

	}

	@Override
	public void update(int index) {
		if (index < 0 || index > objects.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		fireUpdateUpdate(index);

	}

	@Override
	public void clear() {
		
		if(objects == null || objects.isEmpty()){return;}
		
		int indexLast = objects.size() - 1;
		
		objects.clear();
		fireRemoveUpdate(0, indexLast);
	}

	@Override
	public void remove(int... indexes) {
		
		try{
			
			List<GeometricalObject> copyObjects = new ArrayList<>();
			
			for(int i : indexes){
				if(i < 0 || i >= getSize()) continue;
				
				copyObjects.add(objects.get(i));
			}
			
			for(GeometricalObject go : copyObjects){
				int removeIndex = objects.indexOf(go);
				objects.remove(go);
				fireRemoveUpdate(removeIndex, removeIndex);
			}
			
		}catch(Exception e){e.printStackTrace();}
		
	}

	@Override
	public Bounds getBounds() {
		int minX, maxX, minY, maxY;
		
		if(objects == null || objects.isEmpty()){
			return null;
		}
		
		GeometricalObject g = objects.get(0);
		minX = g.getS().x;
		maxX = g.getS().x;
		minY = g.getS().y;
		maxY = g.getS().y;
		
		for(int i = 0; i < getSize(); i++){
			GeometricalObject testObj = objects.get(i);
			
			minX = Math.min(minX, testObj.getMinX());
			
			maxX = Math.max(maxX, testObj.getMaxX());
			
			minY = Math.min(minY, testObj.getMinY());
			
			maxY = Math.max(maxY, testObj.getMaxY());
		}
		Bounds bounds = new Bounds();
		bounds.setxUp(minX);
		bounds.setxDown(maxX);
		bounds.setyUp(minY);
		bounds.setyDown(maxY);
		
		return bounds;
	}

}
