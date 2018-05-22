package ui;

import java.util.LinkedList;

import factory.MoveControllerFactory;
import javafx.util.Pair;
import view.move.MoveController;
import view.move.MoveMsg;

public class CopyManager {
	private static final double step = 25;
	private DrawPane drawPane;
	private LinkedList<Pair<Integer, MoveController>> clipBoard = new LinkedList<>();
	private MoveMsg moveMsg = new MoveMsg(step, step);
	private int copyTimes;
	private int maxCopyTimes;

	public CopyManager(DrawPane drawPane) {
		this.drawPane = drawPane;
	}

	public void copy() {
		copyTimes = 1;
		maxCopyTimes = 1;
		clipBoard = drawPane.getAllSeleted();
		forward(moveMsg);
	}

	public void paste() {
		if (clipBoard.size() != 0) {
			drawPane.change(clipBoard);
			forward(moveMsg);
			copyTimes++;
			maxCopyTimes = Math.max(maxCopyTimes, copyTimes);
		}
	}

	public void getBack() {
		if(copyTimes > 0){
			copyTimes--;
			forward(new MoveMsg(-step, -step));
		}
	}
	public void forward(){
		if(copyTimes < maxCopyTimes){
			copyTimes++;
			forward(new MoveMsg(step, step));
		}
	}
	private void forward(MoveMsg moveMsg){
		MoveController controller;
		LinkedList<Pair<Integer, MoveController>> old = clipBoard;
		clipBoard = new LinkedList<Pair<Integer, MoveController>>();
		for (Pair<Integer, MoveController> pair : old) {
			controller = MoveControllerFactory.create(pair.getValue().getState(), true);
			controller.setChange(moveMsg);
			controller.setSelected(true);
			clipBoard.add(new Pair<Integer, MoveController>(controller.getID(), controller));
		}
	}
	public void clear(){
		copyTimes = 0;
		maxCopyTimes = 0;
		clipBoard = null;
	}
}
