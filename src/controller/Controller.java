package controller;

import pieces.Pieces;
import pieces.Pieces.Color;

public interface Controller {
	/**
	 * Places a piece in the given column with the given color. Returns false if not
	 * valid (valid: 0-6)
	 * 
	 * @param column The column to drop the piece into. Starts at 0. Invalid
	 * @param color  The color of the piece being placed
	 * @return Whether or not the piece was placed. (Not placed if invalid move)
	 */
	public boolean placePiece(int column, Pieces.Color color);

	/**
	 * Chooses a column for the ai to place its piece.
	 * 
	 * @param color The color of the ai
	 * @return returns the column to place the piece for the ai.
	 */
	public int aiPlacePiece(Pieces.Color color);

	/**
	 * 
	 * @param row    The row of the piece 0-5
	 * @param column The column of the piece 0-6
	 * @return Whether the game is ended when the piece is placed
	 */
	public boolean pieceEndsGame(int row, int column);

	/**
	 * Increments the turns of the game
	 */
	public void incrementTurns();

	/**
	 * Gets the turn number of the game
	 * @return The turn number of the board.
	 */
	public int getTurns();

	/**
	 * Sets the color of the AI to the given color
	 * @param color The color to set the AI to
	 */
	public void setAIColor(Color color);

	/**
	 * Return the color of the game's AI
	 * @return the color of the board's AI
	 */
	public Color getAIColor();
}
