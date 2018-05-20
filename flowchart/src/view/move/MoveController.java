package view.move;

import java.util.LinkedList;

import entities.DrawableState;
import entities.RectangleEntity;
import view.inter.Drawable;

public interface MoveController extends Drawable{

	public int getID();
	public void setSelected(boolean isSelected);
	public boolean isSelected();
	public RectangleEntity getRectangle();
	public DrawableState getState();
	public void setState(DrawableState state);
	public LinkedList<LinkedPoint> getConnectionPoints();
	public void setChange(MoveMsg moveMsg);
	public void setLinkedPointsHidden(boolean isHidden);
}
