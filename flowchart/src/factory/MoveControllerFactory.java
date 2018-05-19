package factory;

import entities.ArrowState;
import entities.DrawableState;
import entities.DrawableState.Type;
import javafx.scene.Cursor;
import entities.PointEntity;
import entities.ShapeState;
import ui.DrawPane;
import view.move.ArrowMoveController;
import view.move.MoveController;
import view.move.MoveFrame;
import view.move.PolygonalMoveController;
import view.shape.ArrowShape;
import view.shape.PolygonalArrowShape;

public class MoveControllerFactory {

	private static DrawPane drawPane;

	private static int controllerID = 0;

	private MoveControllerFactory() {

	}

	public static void setDrawPane(DrawPane drawPane) {
		MoveControllerFactory.drawPane = drawPane;
	}

	public static MoveController create(Type shapeType) {
		return create(shapeType, true);
	}

	private static MoveController create(Type shapeType, boolean isIDNeeded) {
		return create(0, 0, false, shapeType, isIDNeeded);
	}

	public static MoveController create(PointEntity center, Type shapeType) {
		return create(center.getX(), center.getY(), true, shapeType);
	}

	public static MoveController create(double x, double y, boolean isCenter, Type shapeType) {
		return create(x, y, isCenter, shapeType, true);
	}

	private static MoveController create(double x, double y, boolean isCenter, Type shapeType, boolean isIDNeeded) {
		int ID = 0;
		if (isIDNeeded) {
			ID = controllerID++;
		}
		switch (shapeType) {
		case ARROW:
			return new ArrowMoveController(MoveControllerFactory.drawPane,
					(ArrowShape) ShapeFactory.create(x, y, isCenter, shapeType), ID);
		case ARROW_HORIZONTAL:
			return new PolygonalMoveController(MoveControllerFactory.drawPane,
					(PolygonalArrowShape) ShapeFactory.create(x, y, shapeType), Cursor.MOVE, ID);
		case ARROW_ERECT:
			return new PolygonalMoveController(MoveControllerFactory.drawPane,
					(PolygonalArrowShape) ShapeFactory.create(x, y, shapeType), Cursor.MOVE, ID);
		default:
			MoveFrame frame = new MoveFrame(MoveControllerFactory.drawPane, ShapeFactory.create(x, y, isCenter, shapeType), ID);
			return frame;
		}
	}

	public static MoveController create(DrawableState drawableState) {
		return create(drawableState, false);
	}

	public static MoveController create(DrawableState drawableState, boolean isNeedID) {
		if (drawableState instanceof ShapeState) {
			ShapeState shapeState = (ShapeState) drawableState;
			MoveController mc = create(shapeState.getType(), isNeedID);
			int id = mc.getID();
			if(!isNeedID){
				id = shapeState.getID();
			}
			mc.setState(new ShapeState(shapeState.getRectangle(), shapeState.getText(), shapeState.getType(),
					shapeState.isSelected(), id));
			return mc;
		} else if (drawableState instanceof ArrowState) {
			ArrowState arrowState = (ArrowState) drawableState;
			MoveController mc = create(arrowState.getType(), false);
			mc.setState(arrowState);
			return mc;
		}
		try {
			throw new Exception("»¹Ã»×ö");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
