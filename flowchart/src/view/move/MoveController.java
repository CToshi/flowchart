package view.move;

import entities.RectangleEntity;
import view.inter.Drawable;

public interface MoveController extends Drawable{

	public int getID();
	public void setSelected(boolean isSelected);
	public boolean isSelected();
	public MoveController clone();
	public RectangleEntity getRectangle();

}
