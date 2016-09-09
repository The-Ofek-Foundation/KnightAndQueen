/*	Ofek Gila
	November 18th, 2014
	KnightTour.java
	This solves the knight problem with an x by y sided threatenedBoard.
*/
/**
 * September 8th, 2016 Update
 *
 * @author Ofek Gila
 * @version September 8th, 2016
 * @since September 8th, 2016
 */

public class EightQueens	{
	int[][] threatenedBoard;
	long numSolutions;

	EightQueens(int w)	{
		threatenedBoard = new int[w][w];
		for (int i = 0; i < threatenedBoard.length; i++)
			for (int a = 0; a < threatenedBoard[i].length; a++)
				threatenedBoard[i][a] = -1;
		numSolutions = 0;
	}
	public static void main(String... pumpkins)	{
		EightQueens eq = new EightQueens(Integer.parseInt(pumpkins[0]));
		eq.run();
	}
	public void run()	{
		double startTime = System.nanoTime();
		for (int i = 0; i < threatenedBoard.length; i++)
			solveQueens(0, i, threatenedBoard);
		System.out.printf("%,d solutions in %,f seconds\n", numSolutions, (System.nanoTime()-startTime) / 1E9);
	}
	private void solveQueens(int x, int y, int[][] threatenedBoard)	{
		if (addQueen(x, y, threatenedBoard)) {
			if (x == threatenedBoard.length - 1) {
				numSolutions++;
				threatenedBoard[x][y] = -1;
			}	else {
				for (int i = 0; i < threatenedBoard.length; i++)
					solveQueens(x + 1, i, threatenedBoard);
				removeQueen(x, y, threatenedBoard);
			}
		}
	}
	private boolean addQueen(int x, int y, int[][] threatenedBoard) {
		if (threatenedBoard[x][y] != -1)
			return false;
		int i, a;
		for (i = x; i < threatenedBoard.length; i++)
			if (threatenedBoard[i][y] == -1)
				threatenedBoard[i][y] = x;
		for (i = x + 1, a = y + 1; i < threatenedBoard.length && a < threatenedBoard[i].length; i++, a++)
			if (threatenedBoard[i][a] == -1)
				threatenedBoard[i][a] = x;
		for (i = x + 1, a = y - 1; i < threatenedBoard.length && a >= 0; i++, a--)
			if (threatenedBoard[i][a] == -1)
				threatenedBoard[i][a] = x;
		return true;
	}
	private void removeQueen(int x, int y, int[][] threatenedBoard) {
		int i, a;
		for (i = x; i < threatenedBoard.length; i++)
			if (threatenedBoard[i][y] == x)
				threatenedBoard[i][y] = -1;
		for (i = x + 1, a = y + 1; i < threatenedBoard.length && a < threatenedBoard[i].length; i++, a++)
			if (threatenedBoard[i][a] == x)
				threatenedBoard[i][a] = -1;
		for (i = x + 1, a = y - 1; i < threatenedBoard.length && a >= 0; i++, a--)
			if (threatenedBoard[i][a] == x)
				threatenedBoard[i][a] = -1;
	}
}
