package ui;

import java.util.HashMap;
import java.util.Map.Entry;

import entities.PointEntity;
import entities.RectangleEntity;
import javafx.beans.binding.DoubleExpression;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import view.inter.Draggable;
import view.move.MoveFrame;

/**
 * 图形的绘制区域
 *
 * @author Toshi
 *
 */
public class DrawPane extends Pane {
	// private DrawManager manager;
	private RootPane parent;
	private boolean hasSelected;
	/**
	 * 存放moveframe, 用以取消选中、撤销和反撤销操作
	 */
	private HashMap<Integer, MoveFrame> map;

	private Rectangle selectRect;

	public DrawPane(RootPane parent, DoubleExpression width, DoubleExpression height) {
		this.parent = parent;
		this.prefWidthProperty().bind(width);
		this.prefHeightProperty().bind(height);
		this.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		
		BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1));
		Border border = new Border(borderStroke);
		this.setBorder(border);
		
		this.setOnMousePressed(mouse -> {
		});
		map = new HashMap<>();

		selectRect = new Rectangle();
		selectRect.setFill(Color.TRANSPARENT);

		add(selectRect);
		new Draggable() {
			@Override
			protected void whenReleased(MouseEvent mouse) {
				/**
				 * 放开时，隐藏框，遍历所有MoveFrame，如果该frame处于选中框(selectRect)中，则设置为选中
				 */
				selectRect.setStroke(Color.TRANSPARENT);
				RectangleEntity rectE = new RectangleEntity(selectRect);
				for (Entry<Integer, MoveFrame> entry : map.entrySet()) {
					MoveFrame frame = entry.getValue();
					if (rectE.contains(frame.getRectangle())) {
						frame.setSelected(true, false);
					}
				}
			}

			@Override
			protected void whenPressed(MouseEvent mouse) {
				/**
				 * 按下时，位置移动到按下点，长宽设为0，显示框颜色，关闭所有图形的选中
				 */
				selectRect.setX(mouse.getX());
				selectRect.setY(mouse.getY());
				selectRect.setWidth(0);
				selectRect.setHeight(0);
				selectRect.setStroke(Color.WHITE);
				if (!DrawPane.this.hasSelected) {
					closeOthers(null);
				}
			}

			@Override
			protected void whenDragged(double xDelta, double yDelta) {
				selectRect.setX(Math.min(getMousePosition().getX(), getStartPosition().getX()));
				selectRect.setY(Math.min(getMousePosition().getY(), getStartPosition().getY()));
				selectRect.setWidth(Math.abs(getMousePosition().getX() - getStartPosition().getX()));
				selectRect.setHeight(Math.abs(getMousePosition().getY() - getStartPosition().getY()));
			}

			@Override
			protected Node getNode() {
				return DrawPane.this;
			}
		};
	}

	public boolean isOutBound(double x, double y) {
		return !(0 <= x && x <= this.getWidth() && 0 <= y && y <= this.getHeight());
	}

	/**
	 * 用于取消选中、撤销及反撤销操作。会将MoveFrame加入到map中，当MoveFrame有移动或改变大小操作时需要自行调用该函数。
	 *
	 * @param moveFrame
	 */
	public void add(MoveFrame moveFrame) {
		add(moveFrame.getNodes());
		map.put(moveFrame.getID(), moveFrame);
	}

	/**
	 * DrawPane会根据有没有MoveFrame被选中而执行动作，当没有MoveFrame被选中时DrawPane会设置所有MoveFrame为未选中状态
	 *
	 * @param hasSelected
	 */
	public void setHasSelected(boolean hasSelected) {
		this.hasSelected = hasSelected;
	}

	/**
	 * 将nodes加到DrawPane中显示出来
	 *
	 * @param nodes
	 *
	 */
	public void add(Node... nodes) {
		for (Node e : nodes) {
			this.getChildren().add(e);
		}
	}

	// public void remove(Node... nodes) {
	// for (Node e : nodes) {
	// // if (this.getChildren().contains(e)) {
	// this.getChildren().remove(e);
	// // }
	// }
	// }

	public PointEntity getCenter() {
		return new PointEntity(this.getWidth() / 2f, this.getHeight() / 2f);
	}

	/**
	 * 关闭除frame外所有MoveFrame的选中状态
	 *
	 * @param frame 不关闭的那个frame，为null时关闭所有MoveFrame的选中
	 */
	public void closeOthers(MoveFrame frame) {
		for (Entry<Integer, MoveFrame> entry : map.entrySet()) {
			if (!entry.getValue().equals(frame)) {
				entry.getValue().setSelected(false, false);
			}
		}
	}

	public boolean hasKey(KeyCode... keyCodes) {
		return parent.hasKey(keyCodes);

	}
}
