package view.move;

import java.util.LinkedList;
import java.util.Map.Entry;

import application.Main;
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
	private LinkedList<Integer> ids;
	private LinkedList<MoveController> controllers;

	public static void setDrawPane(DrawPane drawPane) {
		syncMoveController.drawPane = drawPane;
	}

	private SyncMoveController() {
		moveMsg = new MoveMsg(0, 0);
		ids = new LinkedList<>();
		controllers = new LinkedList<>();
	}

	public static SyncMoveController getInstance() {
		return syncMoveController;
	}

	public void initialMoving() {
		moveMsg.setDeltaX(0);
		moveMsg.setDeltaY(0);
		LinkedList<Entry<Integer, MoveController>> list = drawPane.getAllSeleted();
//		for(int i = 0;i<list.size();){
//			if(!(list.get(i).getValue() instanceof MoveFrame)){
//				list.remove(i);
//			}else{
//				i++;
//			}
//		}
		ids.clear();
		controllers.clear();
		for(Entry<Integer, MoveController> entry:list){
			ids.add(entry.getKey());
			controllers.add(entry.getValue());
		}
	}

	public void informMoving(MoveMsg moveMsg) {
		for(MoveController controller:controllers){
			controller.setChange(moveMsg);
		}
		this.moveMsg.add(moveMsg);
	}

	public void movingFinished() {
		drawPane.change(ids.toArray(new Integer[0]), controllers.toArray(new MoveController[0]));
	}
}
