package view.text_input;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.Main;
import entities.RectangleEntity;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import utility.Util;
import view.inter.Changable;
import view.inter.Drawable;
import view.move.MoveFrame;

public class TextManager implements Drawable, Changable {
	private TextFlow textFlow;
	private RectangleEntity rect;
	private Text text;
	private TextArea textArea;
	private MoveFrame parent;
	public TextManager(MoveFrame parent) {
		text = new Text("128946238746238174");
		textFlow = new TextFlow(text);
		textFlow.setTextAlignment(TextAlignment.CENTER);
		this.rect = parent.getRectangle();
		textArea = new TextArea("128946238746238174");
		textArea.setWrapText(true);
		textFlow.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
		this.parent = parent;
	}

	@Override
	public LinkedList<Node> getNodes() {
		Node[] nodes = { textArea, textFlow};
		return Util.getList(nodes);
	}

	@Override
	public RectangleEntity getRectangle() {
		return rect;
	}

	@Override
	public void setRectangle(RectangleEntity rectangle) {
		this.rect = rectangle;
		fixPosition();
	}

	private void fixPosition() {
		Bounds bounds = textFlow.getBoundsInLocal();
		Bounds b2 = textFlow.getBoundsInParent();
		double x = rect.getX() + (rect.getWidth() - bounds.getWidth()) / 2f;
		double y = rect.getY() + (rect.getHeight() - bounds.getHeight()) / 2f;
		textFlow.relocate(x, y);
		textArea.relocate(rect.getX(), rect.getY());
		textArea.setMinSize(rect.getWidth(), rect.getHeight());
		textArea.setMaxSize(rect.getWidth(), rect.getHeight());
		textArea.setDisable(true);
	}

	@Override
	public void setX(double value) {
		rect.setX(value);
	}

	@Override
	public void setY(double value) {
		rect.setY(value);
	}

	@Override
	public void setWidth(double value) {
		rect.setWidth(value);
	}

	@Override
	public void setHeight(double value) {
		rect.setHeight(value);
	}

	private double getTextWidth(String s) {
		return new TextFlow(new Text(s)).getBoundsInLocal().getWidth();
	}

	public void showInput() {
		if (!textArea.isDisable())
			return;
		textArea.setText(text.getText());
		text.setText("");
		textArea.setDisable(false);
//		parent.deleteFromPane(textFlow);
	}

	public void closeInput() {
		if (textArea.isDisable())
			return;
		text.setText(textArea.getText());
		fixWidth();
		textArea.setText("");
		textArea.setDisable(true);
//		parent.addNodeToPane(txtFlow);
	}

	/**
	 * 从上往下扫每一行，如果该行宽度超过rect的宽度，则将尾部的文字平移到下一行开头，依次下去
	 */
	private void fixWidth() {
		ArrayList<StringBuffer> sList = new ArrayList<>();
		Pattern pattern = Pattern.compile("(.*)(\n)");
		Matcher matcher = pattern.matcher(text.getText() + '\n');
		while (matcher.find()) {
			sList.add(new StringBuffer(matcher.group(1)));
		}
		for (int i = 0; i < sList.size(); i++) {
			boolean isLast = i == sList.size() - 1;
			if (isLast) {
				sList.add(new StringBuffer());
			}
			StringBuffer next = sList.get(i + 1);
			StringBuffer now = sList.get(i);
			boolean hasChange = false;
			while (getTextWidth(now.toString()) > rect.getWidth()) {
				if (now.length() == 1) {
					break;
				}
				hasChange = true;
				next.insert(0, now.charAt(now.length() - 1));
				now.deleteCharAt(now.length() - 1);
			}
			if (isLast && !hasChange) {
				sList.remove(sList.get(i + 1));
			}
		}
		if (sList.size() == 0) {
			return;
		}
		StringBuffer addUpString = new StringBuffer(sList.get(0));
		for (int i = 1; i < sList.size(); i++) {
			addUpString.append(sList.get(i).insert(0, '\n'));
		}
		text.setText(addUpString.toString());
		fixPosition();
	}

	private static final char CONTROL_LIMIT = ' ';
	private static final char PRINTABLE_LIMIT = '\u007e';
	private static final char[] HEX_DIGITS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
			'c', 'd', 'e', 'f' };

	public static String toPrintableRepresentation(String source) {

		if (source == null)
			return null;
		else {

			final StringBuilder sb = new StringBuilder();
			final int limit = source.length();
			char[] hexbuf = null;

			int pointer = 0;

			sb.append('"');

			while (pointer < limit) {

				int ch = source.charAt(pointer++);

				switch (ch) {

				case '\0':
					sb.append("\\0");
					break;
				case '\t':
					sb.append("\\t");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\r':
					sb.append("\\r");
					break;
				case '\"':
					sb.append("\\\"");
					break;
				case '\\':
					sb.append("\\\\");
					break;

				default:
					if (CONTROL_LIMIT <= ch && ch <= PRINTABLE_LIMIT)
						sb.append((char) ch);
					else {

						sb.append("\\u");

						if (hexbuf == null)
							hexbuf = new char[4];

						for (int offs = 4; offs > 0;) {

							hexbuf[--offs] = HEX_DIGITS[ch & 0xf];
							ch >>>= 4;
						}

						sb.append(hexbuf, 0, 4);
					}
				}
			}

			return sb.append('"').toString();
		}
	}
}
