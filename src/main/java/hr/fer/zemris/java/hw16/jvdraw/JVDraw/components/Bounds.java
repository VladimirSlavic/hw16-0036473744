package hr.fer.zemris.java.hw16.jvdraw.JVDraw.components;

/**
 * Bounds class which represnts how much space in canvas is needed to get the minimum width and height for all objects
 * @author vladimir
 *
 */
public class Bounds {

	/**
	 * starting x point
	 */
	private int xUp;
	/**
	 * starting y point
	 */
	private int yUp;
	/**
	 * ending y point
	 */
	private int yDown;
	/**
	 * ending x point
	 */
	private int xDown;
	/**
	 * @return the xUp
	 */
	public int getxUp() {
		return xUp;
	}
	/**
	 * @param xUp the xUp to set
	 */
	public void setxUp(int xUp) {
		this.xUp = xUp;
	}
	/**
	 * @return the yUp
	 */
	public int getyUp() {
		return yUp;
	}
	/**
	 * @param yUp the yUp to set
	 */
	public void setyUp(int yUp) {
		this.yUp = yUp;
	}
	/**
	 * @return the yDown
	 */
	public int getyDown() {
		return yDown;
	}
	/**
	 * @param yDown the yDown to set
	 */
	public void setyDown(int yDown) {
		this.yDown = yDown;
	}
	/**
	 * @return the xDown
	 */
	public int getxDown() {
		return xDown;
	}
	/**
	 * @param xDown the xDown to set
	 */
	public void setxDown(int xDown) {
		this.xDown = xDown;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bounds [xUp=" + xUp + ", yUp=" + yUp + ", yDown=" + yDown + ", xDown=" + xDown + "]";
	}
	
	
	
	
}
