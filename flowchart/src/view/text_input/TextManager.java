package view.text_input;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.RectangleEntity;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import utility.Util;
import view.inter.Changable;
import view.inter.Drawable;

public class TextManager implements Drawable, Changable {
	private TextFlow textFlow;
	private RectangleEntity rect;
	private Text text;
//	private TextArea textArea;
	private CharSizeCalculator calculator;
	private String content;


	public TextManager(RectangleEntity rect) {
		Font font = new Font("System Regularc", 20);
		content = "128946238746238174";
		text = new Text(content);
		text.setFont(font);
		textFlow = new TextFlow(text);
		textFlow.setTextAlignment(TextAlignment.CENTER);
		this.rect = rect;
//		textArea = new TextArea();
//		textArea.setFont(font);
//		textArea.setWrapText(true);
//		textFlow.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
//		textArea.setDisable(true);
		calculator = new CharSizeCalculator(font);
		fixWidth();
	}

	public void setText(String text){
		content = text;
		fixWidth();
	}
	private void fixPosition() {
		Bounds bounds = calculator.getBounds(text.getText());
		double x = rect.getX() + (rect.getWidth() - bounds.getWidth()) / 2f;
		double y = rect.getY() + (rect.getHeight() - bounds.getHeight()) / 2f;
		textFlow.relocate(x, y);
	}

	private double getTextWidth(String s) {
		return calculator.getBounds(s).getWidth();
	}


	/**
	 * 从上往下扫每一行，如果该行宽度超过rect的宽度，则将尾部的文字平移到下一行开头，依次下去
	 */
	private void fixWidth() {
		ArrayList<StringBuffer> sList = new ArrayList<>();
		Pattern pattern = Pattern.compile("(.*)(\n)");
		Matcher matcher = pattern.matcher(content + '\n');
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

	@Override
	public LinkedList<Node> getNodes() {
		Node[] nodes = { textFlow };
		return Util.getList(nodes);
	}

	@Override
	public RectangleEntity getRectangle() {
		return rect;
	}
	@Override
	public void setRectangle(RectangleEntity rectangle) {
		this.rect = rectangle;
		fixWidth();
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

	public String getText() {
		return text.getText();
	}
}
