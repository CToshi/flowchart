package view.move;

import application.Main;
import entities.PointEntity;
import javafx.scene.input.MouseEvent;
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
	protected void whenReleased(MouseEvent mouse) {
	}

	@Override
	protected void deal(double xDelta, double yDelta) {
		Rotate rotate = (Rotate) this.getTransforms().get(0);
		PointEntity pivot = new PointEntity(rotate.getPivotX(), rotate.getPivotY());
		target.setRotate(rotate.getAngle()
				+ this.getMouse().getAngleFrom(new PointEntity(pivot.getX(), pivot.getY() - 100), pivot));
	}

	public PointEntity toPointEntity() {
		return new PointEntity(this.getCenterX(), this.getCenterY());
	}
}
