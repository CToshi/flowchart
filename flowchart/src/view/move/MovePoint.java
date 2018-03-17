package view.move;

import javafx.scene.Cursor;
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

	// public MovePoint(MoveFrame target, boolean xChangable, boolean
	// yChangable) {
	// this(target, xChangable, yChangable, Cursor.DEFAULT);
	// }
	public MovePoint(MoveFrame target, boolean xChangable, boolean yChangable, Cursor cursor) {
		super(0, 0, size, size, cursor);
		this.setWidth(size);
		this.setHeight(size);
		this.target = target;
		this.xChangable = xChangable;
		this.yChangable = yChangable;
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
