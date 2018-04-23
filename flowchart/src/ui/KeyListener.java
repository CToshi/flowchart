package ui;

import javafx.scene.input.KeyCode;

public abstract class KeyListener {
	private KeyCode[] codes;

	public KeyListener(KeyCode... codes) {
		this.codes = codes;
	}
//	public void setKeyCodes(KeyCode...codes){
//		this.codes = codes;
//	}

	public boolean equals(KeyCode[] codes) {
		if (codes.length != this.codes.length) {
			return false;
		}
		for (int i = 0; i < codes.length; i++) {
			if (codes[i] != this.codes[i]) {
				return false;
			}
		}
		return true;
	}

	public abstract void run();
}
