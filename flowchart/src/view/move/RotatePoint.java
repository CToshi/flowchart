package view.move;

import application.Main;
import entities.PointEntity;
import javafx.scene.transform.Rotate;
import view.shape.DraggableRectangle;

public class RotatePoint extends DraggableRectangle {
	private MoveFrame target;
	private static final double size = 10;

	public RotatePoint(MoveFrame target) {
		this.target = target;
		this.setWidth(size);
		this.setHeight(size);
	}

	@Override
	protected void whenReleased() {
		target.fixPivot();
	}

	@Override
	protected void deal(double xDelta, double yDelta) {
		if (Math.abs(xDelta) < 0.001 || Math.abs(yDelta) < 0.001 || this.getTransforms().size() == 0) {
			return;
		}
		if (!(this.getTransforms().get(0) instanceof Rotate)) {
			return;
		}
		Rotate rotate = (Rotate) this.getTransforms().get(0);
		PointEntity pivot = new PointEntity(rotate.getPivotX(), rotate.getPivotY());
		// move(xDelta, yDelta);
		double angle = 90 - this.getMouse().rotate(-rotate.getAngle(), pivot).getAngleFrom(pivot);
		double msex = this.getMouse().rotate(-rotate.getAngle(), pivot).getX();
		double msey = this.getMouse().rotate(-rotate.getAngle(), pivot).getY();

		Main.test(rotate.getAngle(), msex, msey, this.getMouse().rotate(-rotate.getAngle(), pivot).getAngleFrom(pivot));
		Main.test(angle % 360);
		move(xDelta, yDelta);
//		target.setRotate(angle % 360);
		// PointEntity startPoint = new PointEntity(this.getX(), this.getY());

		//
		//
		// double a = startPoint.getDistanceFrom(self);
		// double b = pivot.getDistanceFrom(self);
		// double c = pivot.getDistanceFrom(startPoint);
		// double angle = Math.acos((b * b + c * c - a * a) / (2f * b * c)) *
		// 180f / Math.PI;
		// double angle = startPoint.getAngleFrom(pivot) -
		// nowPoint.getAngleFrom(pivot);
		//// Main.test(pivot.getX(), pivot.getY());
		// Main.test(xDelta, yDelta);
		// Main.test(nowPoint.getX(), nowPoint.getY(),
		// nowPoint.getAngleFrom(pivot), angle);
		// // move(xDelta, yDelta);
		// target.setRotate(rotate.getAngle() + angle);
	}

	public PointEntity toPointEntity() {
		return new PointEntity(this.getCenterX(), this.getCenterY());
	}
}
