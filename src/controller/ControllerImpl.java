package controller;

import board.AbstractBoard;
import board.BoardImpl;
import pieces.Pieces.Color;

public class ControllerImpl implements Controller {
	AbstractBoard board = new BoardImpl();

	@Override
	public int placePiece(int column, Color color) {
		return board.placePiece(column, color);
	}

	@Override
	public int aiPlacePiece(Color color) {
		return board.aiPlacePiece(color);
	}

	@Override
	public boolean pieceEndsGame(int row, int column) {
		return board.pieceEndsGame(row, column);
	}

	@Override
	public void incrementTurns() {
		board.setTurn(board.getTurn() + 1);
	}

	@Override
	public int getTurns() {
		return board.getTurn();
	}

	@Override
	public void setAIColor(Color color) {
		board.setAiColor(color);
	}

	@Override
	public Color getAIColor() {
		return board.getAiColor();
	}

	@Override
	public boolean isTieGame() {
		return board.isTieGame();
	}
}
