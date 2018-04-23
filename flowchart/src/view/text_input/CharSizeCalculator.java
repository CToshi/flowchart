package view.text_input;

import javafx.geometry.Bounds;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class CharSizeCalculator {

	private TextFlow textFlow;
	private Text text;

	public CharSizeCalculator(Font font) {
		text = new Text();
		text.setFont(font);
		textFlow = new TextFlow(text);
	}
	public CharSizeCalculator(double size) {
		this("System Regularc", size);
	}
	public CharSizeCalculator(String fontName, double size) {
		this(new Font(fontName, size));
	}

	public Bounds getBounds(String s) {
		text.setText(s);
		Bounds bounds = textFlow.getBoundsInLocal();
		return bounds;
	}


}
