package view;

import entities.RotateRectangleEntity;

public interface Selectable {
	RotateRectangleEntity getJudgeRectangle();
	void selected();
}
