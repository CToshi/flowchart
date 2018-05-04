package view.move;

import entities.DrawableState;
import entities.RectangleEntity;
import view.inter.Drawable;

public interface MoveController extends Drawable{

	public int getID();
	public void setSelected(boolean isSelected);
	public boolean isSelected();
	public MoveController clone();
	public RectangleEntity getRectangle();
	public DrawableState getState();
	public void setState(DrawableState state);
}
