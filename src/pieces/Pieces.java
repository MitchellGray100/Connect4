package pieces;

public class Pieces {

	Color color;

	public Pieces(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public enum Color {
		YELLOW, RED;
	}
}
