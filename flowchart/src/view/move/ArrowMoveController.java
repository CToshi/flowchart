package view.move;

import java.util.LinkedList;

import entities.RectangleEntity;
import javafx.scene.Node;
import view.shape.ArrowShape;

public class ArrowMoveController implements Cloneable,MoveController{

	private ArrowShape arrowShape;
	private DraggablePoint startPoint;
	private DraggablePoint endPoint;
	private LinkedList<Node> linkedList;
	
	
	@Override
	public LinkedList<Node> getNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSelected(boolean isSelected) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MoveController clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RectangleEntity getRectangle() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
