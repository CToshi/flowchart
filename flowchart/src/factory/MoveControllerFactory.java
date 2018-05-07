package factory;

import entities.PointEntity;
import entities.ShapeState.Type;
import ui.DrawPane;
import view.move.ArrowMoveController;
import view.move.MoveController;
import view.move.MoveFrame;
import view.move.PolygonalMoveController;
import view.shape.ArrowShape;
import view.shape.PolygonalArrowShape;

public class MoveControllerFactory {

	private static DrawPane drawPane;

	private MoveControllerFactory(){

	}

	public static void setDrawPane(DrawPane drawPane) {
		MoveControllerFactory.drawPane = drawPane;
	}

	public static MoveController create(PointEntity center,Type shapeType){
		return create(center.getX(), center.getY(),false,shapeType);
	}

	public static MoveController create(double x,double y,boolean isCenter,Type shapeType){
		switch (shapeType) {
		case POLYGONALARROW:
			return new PolygonalMoveController(MoveControllerFactory.drawPane, (PolygonalArrowShape)ShapeFactory.create(x,y,shapeType));
		case ARROW:
			return new ArrowMoveController(MoveControllerFactory.drawPane,(ArrowShape)ShapeFactory.create(x,y,shapeType));
		default:
			return new MoveFrame(MoveControllerFactory.drawPane, ShapeFactory.create(x,y,shapeType));
		}
	}

}
