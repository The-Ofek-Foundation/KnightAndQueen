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

public class Quites	{
	int[][] threatenedBoard;
	long numSolutions;

	Quites(int w)	{
		threatenedBoard = new int[w][w];
		for (int i = 0; i < threatenedBoard.length; i++)
			for (int a = 0; a < threatenedBoard[i].length; a++)
				threatenedBoard[i][a] = -1;
		numSolutions = 0;
	}
	public static void main(String... pumpkins)	{
		Quites eq = new Quites(Integer.parseInt(pumpkins[0]));
		eq.run();
	}
	public void run()	{
		double startTime = System.nanoTime();
		int i;
		for (i = 0; i < (int)(threatenedBoard.length / 2); i++)
			solveQueens(0, i, threatenedBoard);
		long tempSolutions = numSolutions;
		if (threatenedBoard.length % 2 == 1)
			solveQueens(0, i, threatenedBoard);
		numSolutions += tempSolutions;
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
		if (x < threatenedBoard.length - 1) {
			if (y > 1 && threatenedBoard[x+1][y-2] == -1)
				threatenedBoard[x+1][y-2] = x;
			if (y < threatenedBoard[x+1].length - 2 && threatenedBoard[x+1][y+2] == -1)
				threatenedBoard[x+1][y+2] = x;
		}
		if (x < threatenedBoard.length - 2) {
			if (y > 0 && threatenedBoard[x+2][y-1] == -1)
				threatenedBoard[x+2][y-1] = x;
			if (y < threatenedBoard[x+2].length - 1 && threatenedBoard[x+2][y+1] == -1)
				threatenedBoard[x+2][y+1] = x;
		}
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
		if (x < threatenedBoard.length - 1) {
			if (y > 1 && threatenedBoard[x+1][y-2] == x)
				threatenedBoard[x+1][y-2] = -1;
			if (y < threatenedBoard[x+1].length - 2 && threatenedBoard[x+1][y+2] == x)
				threatenedBoard[x+1][y+2] = -1;
		}
		if (x < threatenedBoard.length - 2) {
			if (y > 0 && threatenedBoard[x+2][y-1] == x)
				threatenedBoard[x+2][y-1] = -1;
			if (y < threatenedBoard[x+2].length - 1 && threatenedBoard[x+2][y+1] == x)
				threatenedBoard[x+2][y+1] = -1;
		}
	}
}
