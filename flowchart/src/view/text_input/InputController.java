package view.text_input;

import entities.RectangleEntity;
import javafx.scene.control.TextArea;

public class InputController {

	private TextArea textArea;
	private static InputController inputController = new InputController();

	private InputController(){
		this.textArea = new TextArea();
	}

	public static InputController getInstance(){
		return inputController;
	}

	public TextArea getTextArea() {
		return textArea;
	}

	public void setInformation(RectangleEntity rectangle,String text){
		textArea.setText(text);
		textArea.setLayoutX(rectangle.getX());
		textArea.setLayoutY(rectangle.getY());
		textArea.setPrefSize(rectangle.getWidth(), rectangle.getHeight());
	}
}
