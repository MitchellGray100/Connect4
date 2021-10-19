package board;

import pieces.Pieces;
import pieces.Pieces.Color;

public abstract class AbstractBoard implements Board {
	Pieces[][] board = new Pieces[6][7];
	Pieces.Color aiColor;
	int turn = 0;

	@Override
	public boolean placePiece(int column, Color color) {
		if (column < 0 || column > 6) {
			return false;
		}
		for (int r = 0; r < 6; r++) {
			if (board[r][column] != null) {
				if (column - 1 < 0) {
					return false;
				} else {
					board[r][column - 1] = new Pieces(color);
					break;
				}
			}
		}
		return true;
	}

	@Override
	public int aiPlacePiece(Color color) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean pieceEndsGame(int row, int column) {
		Color color = board[row][column].getColor();
		if (row < 0 || column < 0 || row > 5 || column > 6) {
			return false;
		}

		// Top left to bottom right diagonals
		if (pieceEndsGameHelper(row - 3, column - 3, color) && pieceEndsGameHelper(row - 2, column - 2, color)
				&& pieceEndsGameHelper(row - 1, column - 1, color) && pieceEndsGameHelper(row, column, color)) {
			return true;
		} else if (pieceEndsGameHelper(row - 2, column - 2, color) && pieceEndsGameHelper(row - 1, column - 1, color)
				&& pieceEndsGameHelper(row, column, color) && pieceEndsGameHelper(row + 1, column + 1, color)) {
			return true;
		} else if (pieceEndsGameHelper(row - 1, column - 1, color) && pieceEndsGameHelper(row, column, color)
				&& pieceEndsGameHelper(row + 1, column + 1, color) && pieceEndsGameHelper(row + 2, column + 2, color)) {
			return true;
		} else if (pieceEndsGameHelper(row, column, color) && pieceEndsGameHelper(row + 1, column + 1, color)
				&& pieceEndsGameHelper(row + 2, column + 2, color) && pieceEndsGameHelper(row + 3, column + 3, color)) {
			return true;
		}

		// Top Middle to Bottom Middle
		if (pieceEndsGameHelper(row - 3, column, color) && pieceEndsGameHelper(row - 2, column, color)
				&& pieceEndsGameHelper(row - 1, column, color) && pieceEndsGameHelper(row, column, color)) {
			return true;
		} else if (pieceEndsGameHelper(row - 2, column, color) && pieceEndsGameHelper(row - 1, column, color)
				&& pieceEndsGameHelper(row, column, color) && pieceEndsGameHelper(row + 1, column, color)) {
			return true;
		} else if (pieceEndsGameHelper(row - 1, column, color) && pieceEndsGameHelper(row, column, color)
				&& pieceEndsGameHelper(row + 1, column, color) && pieceEndsGameHelper(row, column, color)) {
			return true;
		} else if (pieceEndsGameHelper(row, column, color) && pieceEndsGameHelper(row + 1, column, color)
				&& pieceEndsGameHelper(row + 2, column, color) && pieceEndsGameHelper(row + 3, column, color)) {
			return true;
		}

		// Right Middle to Left Middle
		if (pieceEndsGameHelper(row, column - 3, color) && pieceEndsGameHelper(row, column - 2, color)
				&& pieceEndsGameHelper(row, column - 1, color) && pieceEndsGameHelper(row, column, color)) {
			return true;
		} else if (pieceEndsGameHelper(row, column - 2, color) && pieceEndsGameHelper(row, column - 1, color)
				&& pieceEndsGameHelper(row, column, color) && pieceEndsGameHelper(row, column + 1, color)) {
			return true;
		} else if (pieceEndsGameHelper(row, column - 1, color) && pieceEndsGameHelper(row, column, color)
				&& pieceEndsGameHelper(row, column + 1, color) && pieceEndsGameHelper(row, column + 2, color)) {
			return true;
		} else if (pieceEndsGameHelper(row, column, color) && pieceEndsGameHelper(row, column + 1, color)
				&& pieceEndsGameHelper(row, column + 2, color) && pieceEndsGameHelper(row, column + 3, color)) {
			return true;
		}

		// Bottom Left to Top Right
		if (pieceEndsGameHelper(row + 3, column - 3, color) && pieceEndsGameHelper(row + 2, column - 2, color)
				&& pieceEndsGameHelper(row + 1, column - 1, color) && pieceEndsGameHelper(row, column, color)) {
			return true;
		} else if (pieceEndsGameHelper(row + 2, column - 2, color) && pieceEndsGameHelper(row + 1, column - 1, color)
				&& pieceEndsGameHelper(row, column, color) && pieceEndsGameHelper(row - 1, column + 1, color)) {
			return true;
		} else if (pieceEndsGameHelper(row + 1, column - 1, color) && pieceEndsGameHelper(row, column, color)
				&& pieceEndsGameHelper(row - 1, column + 1, color) && pieceEndsGameHelper(row - 2, column + 2, color)) {
			return true;
		} else if (pieceEndsGameHelper(row, column, color) && pieceEndsGameHelper(row - 1, column + 1, color)
				&& pieceEndsGameHelper(row - 2, column + 2, color) && pieceEndsGameHelper(row - 3, column + 3, color)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean pieceEndsGameHelper(int shiftedRow, int shiftedColumn, Color color) {
		if (shiftedRow < 0 || shiftedColumn < 0 || shiftedRow > 5 || shiftedColumn > 6) {
			return false;
		}
		if (board[shiftedRow][shiftedColumn] == null || board[shiftedRow][shiftedColumn].getColor() != color) {
			return false;
		}
		return true;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public Pieces.Color getAiColor() {
		return aiColor;
	}

	public void setAiColor(Pieces.Color aiColor) {
		this.aiColor = aiColor;
	}

	public void placePiece() {

	}

}
