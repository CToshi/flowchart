package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class QzySaver {

	private DrawPane drawPane;
	private static QzySaver qzySaver = new QzySaver();

	private QzySaver(){

	}

	public static QzySaver getInstance(){
		return qzySaver;
	}

	public static void setDrawPane(DrawPane drawPane) {
		getInstance().drawPane = drawPane;
	}

	public void saveTo(File file){
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){
			oos.writeObject(drawPane.getAllState());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
