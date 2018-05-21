package view.move;

import java.util.LinkedList;

import javafx.util.Pair;
import ui.DrawPane;

/**
 * 同步移动控制，用于多选图形的同时移动
 *
 * @author Toshi
 *
 */
public class SyncMoveController {
	private static SyncMoveController syncMoveController = new SyncMoveController();
	private MoveMsg moveMsg;
	private DrawPane drawPane;
	private LinkedList<Pair<Integer, MoveController>> list;
	public static void setDrawPane(DrawPane drawPane) {
		syncMoveController.drawPane = drawPane;
	}

	private SyncMoveController() {
		moveMsg = new MoveMsg(0, 0);
	}

	public static SyncMoveController getInstance() {
		return syncMoveController;
	}

	public void initialMoving() {
		moveMsg.setDeltaX(0);
		moveMsg.setDeltaY(0);
		list = drawPane.getAllSeleted();
	}

	public void informMoving(MoveMsg moveMsg) {
		for(Pair<Integer, MoveController> entry:list){
			entry.getValue().setChange(moveMsg);
		}
		this.moveMsg.add(moveMsg);
	}

	public void movingFinished() {
		drawPane.change(list);
	}
}
