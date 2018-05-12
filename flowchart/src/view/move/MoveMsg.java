package view.move;

public class MoveMsg {
	private double deltaX;
	private double deltaY;

	public MoveMsg(double deltaX, double deltaY) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}
	public double getDeltaX() {
		return deltaX;
	}
	public void setDeltaX(double deltaX) {
		this.deltaX = deltaX;
	}
	public double getDeltaY() {
		return deltaY;
	}
	public void setDeltaY(double deltaY) {
		this.deltaY = deltaY;
	}
	@Override
	public String toString() {
		return String.format("[%.2f, %.2f]", deltaX, deltaY);
	}
	public void add(MoveMsg moveMsg){
		deltaX += moveMsg.getDeltaX();
		deltaY += moveMsg.getDeltaY();
	}

}
