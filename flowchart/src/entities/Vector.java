package entities;

public class Vector extends PointEntity {
	public Vector() {
		super(0, 0);
	}

	public Vector(PointEntity a, PointEntity b) {
		super(b.getX() - a.getX(), b.getY() - a.getY());
	}

	public double getMod() {
		return Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY());
	}

}