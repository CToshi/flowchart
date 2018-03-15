package view.move;

import javafx.beans.property.ReadOnlyDoubleProperty;
import view.Changable;
import view.shape.DraggableRectangle;

public class MovePoint extends DraggableRectangle{
	private static final double size = 10;
	private Changable target;
	public MovePoint(Changable target, ReadOnlyDoubleProperty x, ReadOnlyDoubleProperty y) {
		this.setWidth(size);
		this.setHeight(size);
//		shape = new Rectangle(size, size);
		this.xProperty().bind(x.subtract(size / 2f));
		this.yProperty().bind(y.subtract(size / 2f));
		this.target = target;
	}
	@Override
	public void xAdd(double delta) {
		target.xAdd(delta);
		target.widthAdd(-delta);
	}
	@Override
	public void yAdd(double delta) {
		target.yAdd(delta);
		target.heightAdd(-delta);
	}
}
