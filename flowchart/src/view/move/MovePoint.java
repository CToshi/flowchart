package view.move;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import view.shape.DraggableRectangle;

public class MovePoint extends DraggableRectangle {
	private static final double size = 10;
	private MoveFrame target;
	private MovePoint other;
	private boolean xChangable;
	private boolean yChangable;
	private Paint fill;

	public MovePoint(MoveFrame target, boolean xChangable, boolean yChangable) {
		this.setWidth(size);
		this.setHeight(size);
		this.target = target;
		this.xChangable = xChangable;
		this.yChangable = yChangable;
	}

	public void setCenterXY(double x, double y) {
		this.setCenterX(x);
		this.setCenterY(y);
	}

	public void setCenterX(double value) {
		this.setX(value - size / 2f);
	}

	public void setCenterY(double value) {
		this.setY(value - size / 2f);
	}

	public double getCenterX() {
		return this.getX() + size / 2f;
	}

	public double getCenterY() {
		return this.getY() + size / 2f;
	}

	public void setOtherPoint(MovePoint other) {
		this.other = other;
	}

	@Override
	protected void deal(double xDelta, double yDelta) {
		if (xChangable) {
			this.setX(this.getX() + xDelta);
			target.setX(Math.min(this.getCenterX(), other.getCenterX()));
			target.setWidth(Math.abs(this.getCenterX() - other.getCenterX()));
		}
		if (yChangable) {
			this.setY(this.getY() + yDelta);
			target.setY(Math.min(this.getCenterY(), other.getCenterY()));
			target.setHeight(Math.abs(this.getCenterY() - other.getCenterY()));
		}
	}

	@Override
	protected void whenClicked() {
		target.setHidden();
	}

	@Override
	protected void whenReleased() {
		target.fixPosition();
		target.setShow();
	}

	public void setHide() {
		fill = this.getFill();
		this.setFill(Color.TRANSPARENT);
	}

	public void setShow() {
		this.setFill(fill);
	}

}
