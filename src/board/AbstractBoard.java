package board;

import pieces.Pieces;
import pieces.Pieces.Color;

public abstract class AbstractBoard implements Board {
	Pieces[][] board = new Pieces[6][7];
	Pieces.Color aiColor;
	int turn = 0;

	@Override
	public int dropAir(int column) {
		int row = -1;
		if (column < 0 || column > 6) {
			return row;
		}
		for (int r = 0; r < 6; r++) {
			if (board[r][column] != null) {
				if (r - 1 < 0) {
					return row;
				} else {
					row = r - 1;
					break;
				}
			} else {
				if (r == 5) {
					row = r;
					break;

				}
			}

		}
		return row;
	}

	@Override
	public int placePiece(int column, Color color) {
		int row = -1;
		if (column < 0 || column > 6) {
			return row;
		}
		for (int r = 0; r < 6; r++) {
			if (board[r][column] != null) {
				if (r - 1 < 0) {
					return row;
				} else {
					board[r - 1][column] = new Pieces(color);
					row = r - 1;
					break;
				}
			} else {
				if (r == 5) {
					board[r][column] = new Pieces(color);
					row = r;
					break;

				}
			}

		}
		return row;
	}

	public int aiPlacePieceHelper(int r, int c, int x, int y, Color color) {
		int score = 0;
		if (r < 0 || r > 5 || c < 0 || c > 6) {
			return -1;
		}
		for (int i = 1; i <= 2; i++) {
			if (r + x * i > 5 || r + x * i < 0 || c + y * i > 6 || c + y * i < 0) {
				break;
			} else {
				if (board[r + x * i][c + y * i] == null || board[r + x * i][c + y * i].getColor() != color) {
					break;
				} else {
					score++;
				}
			}
		}
		return score;
	}

	@Override
	public int aiPlacePiece(Color color) {
		Color antiColor;
		if (color == Color.RED) {
			antiColor = Color.YELLOW;
		} else {
			antiColor = Color.RED;
		}
		int maxScore = -1;
		int maxPos = -1;
		int droppedAir;
		int score;
		for (int i = 0; i < 7; i++) {
			score = 0;
			droppedAir = dropAir(i);
			if (droppedAir != -1) {
				board[droppedAir][i] = new Pieces(color);
			} else {
				continue;
			}
			if (droppedAir == -1) {
				score = -1000;
			}
			if (pieceEndsGame(droppedAir, i)) {
				maxScore = 1000;
				maxPos = i;
			} else {
				if (droppedAir == 0) {
					if (isTieGame()) {
						maxScore = 1000;
					}
				}

				if (i > 2 && i < 5) {
					score++;
				}
				score = Math.max(Math.max(
						Math.max(
								Math.max(
										Math.max(
												Math.max(
														Math.max(
																Math.max(aiPlacePieceHelper(droppedAir, i, 1, 1, color),
																		aiPlacePieceHelper(droppedAir, i, -1, -1,
																				color)),
																aiPlacePieceHelper(droppedAir, i, -1, 0, color)),
														aiPlacePieceHelper(droppedAir, i, -1, 1, color)),
												aiPlacePieceHelper(droppedAir, i, 0, -1, color)),
										aiPlacePieceHelper(droppedAir, i, 0, 1, color)),
								aiPlacePieceHelper(droppedAir, i, 1, -1, color)),
						aiPlacePieceHelper(droppedAir, i, 1, 0, color)), score);
				score += Math.max(Math.max(
						Math.max(
								Math.max(
										Math.max(
												Math.max(Math.max(
														Math.max(aiPlacePieceHelper(droppedAir, i, 1, 1, antiColor),
																aiPlacePieceHelper(droppedAir, i, -1, -1, color)),
														aiPlacePieceHelper(droppedAir, i, -1, 0, antiColor)),
														aiPlacePieceHelper(droppedAir, i, -1, 1, antiColor)),
												aiPlacePieceHelper(droppedAir, i, 0, -1, antiColor)),
										aiPlacePieceHelper(droppedAir, i, 0, 1, antiColor)),
								aiPlacePieceHelper(droppedAir, i, 1, -1, antiColor)),
						aiPlacePieceHelper(droppedAir, i, 1, 0, antiColor)), score);
			}
			if (score > maxScore) {
				maxScore = score;
				maxPos = i;
			}
			board[droppedAir][i] = null;
		}

		return maxPos;

	}

	public boolean isTieGame() {
		return board[0][0] != null && board[0][1] != null && board[0][2] != null && board[0][3] != null
				&& board[0][4] != null && board[0][5] != null && board[0][6] != null;
	}

	@Override
	public boolean pieceEndsGame(int row, int column) {
		Color color = board[row][column].getColor();
		if (row < 0 || column < 0 || row > 5 || column > 6) {
			return false;
		}

		// Top left to bottom right diagonals
		if (pieceEndsGameHelper(row - 3, column - 3, color) && pieceEndsGameHelper(row - 2, column - 2, color)
				&& pieceEndsGameHelper(row - 1, column - 1, color)) {
			return true;
		} else if (pieceEndsGameHelper(row - 2, column - 2, color) && pieceEndsGameHelper(row - 1, column - 1, color)
				&& pieceEndsGameHelper(row + 1, column + 1, color)) {
			return true;
		} else if (pieceEndsGameHelper(row - 1, column - 1, color) && pieceEndsGameHelper(row + 1, column + 1, color)
				&& pieceEndsGameHelper(row + 2, column + 2, color)) {
			return true;
		} else if (pieceEndsGameHelper(row + 1, column + 1, color) && pieceEndsGameHelper(row + 2, column + 2, color)
				&& pieceEndsGameHelper(row + 3, column + 3, color)) {
			return true;
		}

		// Top Middle to Bottom Middle
		if (pieceEndsGameHelper(row - 3, column, color) && pieceEndsGameHelper(row - 2, column, color)
				&& pieceEndsGameHelper(row - 1, column, color)) {
			return true;
		} else if (pieceEndsGameHelper(row - 2, column, color) && pieceEndsGameHelper(row - 1, column, color)
				&& pieceEndsGameHelper(row + 1, column, color)) {
			return true;
		} else if (pieceEndsGameHelper(row - 1, column, color) && pieceEndsGameHelper(row + 1, column, color)
				&& pieceEndsGameHelper(row + 2, column, color)) {
			return true;
		} else if (pieceEndsGameHelper(row + 1, column, color) && pieceEndsGameHelper(row + 2, column, color)
				&& pieceEndsGameHelper(row + 3, column, color)) {
			return true;
		}

		// Right Middle to Left Middle
		if (pieceEndsGameHelper(row, column - 3, color) && pieceEndsGameHelper(row, column - 2, color)
				&& pieceEndsGameHelper(row, column - 1, color)) {
			return true;
		} else if (pieceEndsGameHelper(row, column - 2, color) && pieceEndsGameHelper(row, column - 1, color)
				&& pieceEndsGameHelper(row, column + 1, color)) {
			return true;
		} else if (pieceEndsGameHelper(row, column - 1, color) && pieceEndsGameHelper(row, column + 1, color)
				&& pieceEndsGameHelper(row, column + 2, color)) {
			return true;
		} else if (pieceEndsGameHelper(row, column + 1, color) && pieceEndsGameHelper(row, column + 2, color)
				&& pieceEndsGameHelper(row, column + 3, color)) {
			return true;
		}

		// Bottom Left to Top Right
		if (pieceEndsGameHelper(row + 3, column - 3, color) && pieceEndsGameHelper(row + 2, column - 2, color)
				&& pieceEndsGameHelper(row + 1, column - 1, color)) {
			return true;
		} else if (pieceEndsGameHelper(row + 2, column - 2, color) && pieceEndsGameHelper(row + 1, column - 1, color)
				&& pieceEndsGameHelper(row - 1, column + 1, color)) {
			return true;
		} else if (pieceEndsGameHelper(row + 1, column - 1, color) && pieceEndsGameHelper(row - 1, column + 1, color)
				&& pieceEndsGameHelper(row - 2, column + 2, color)) {
			return true;
		} else if (pieceEndsGameHelper(row - 1, column + 1, color) && pieceEndsGameHelper(row - 2, column + 2, color)
				&& pieceEndsGameHelper(row - 3, column + 3, color)) {
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
