package view.move;

import java.util.LinkedList;

import entities.DrawableState;
import entities.PointEntity;
import entities.RectangleEntity;
import view.inter.Drawable;

public interface MoveController extends Drawable{

	public int getID();
	public void setSelected(boolean isSelected);
	public boolean isSelected();
	public RectangleEntity getRectangle();
	public DrawableState getState();
	public void setState(DrawableState state);
	public LinkedList<PointEntity> getConnectionPoints();
	public void addConnection(MoveController moveController);
	public void removeConnection(MoveController moveController);
	public void setChange(MoveMsg changeMsg);
}
