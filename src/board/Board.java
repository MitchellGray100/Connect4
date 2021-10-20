package board;

import pieces.Pieces;

public interface Board {

	/**
	 * Places a piece in the given column with the given color. Returns false if not
	 * valid (valid: 0-6)
	 * 
	 * @param column The column to drop the piece into. Starts at 0. Invalid
	 * @param color  The color of the piece being placed
	 * @return returns an int indicating the row placed. -1 if not valid (Not placed
	 *         if invalid move)
	 */
	public int placePiece(int column, Pieces.Color color);

	/**
	 * Chooses a column for the ai to place its piece.
	 * 
	 * @param color The color of the ai
	 * @return returns the column to place the piece for the ai.
	 */
	public int aiPlacePiece(Pieces.Color color);

	/**
	 * @return returns whether or not the game is a tie
	 */
	public boolean isTieGame();

	/**
	 * 
	 * @param row    The row of the piece 0-5
	 * @param column The column of the piece 0-6
	 * @return Whether the game is ended when the piece is placed
	 */
	public boolean pieceEndsGame(int row, int column);

	/**
	 * Determines if the piece at the location is the same as the given color
	 * 
	 * @param shiftedRow    The row of the piece to check
	 * @param shiftedColumn The column of the piece to check
	 * @param color         the color to compare the piece's color to
	 * @return true if same color. Return false if not
	 */
	public boolean pieceEndsGameHelper(int shiftedRow, int shiftedColumn, Pieces.Color color);

	/**
	 * Returns the row a piece would land if it was dropped there (valid: 0-6)
	 * 
	 * @param column The column to drop the piece into. Starts at 0.
	 * @return returns an int indicating the row it would be placed. -1 if not valid
	 *         (Not placed if invalid move)
	 */
	int dropAir(int column);
}
