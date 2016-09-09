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

public class EightQueensUnoriginal	{
	private int[][] threatenedBoard;
	private int numSolutions;

	EightQueensUnoriginal(int w)	{
		threatenedBoard = new int[w][w];
		numSolutions = 0;
	}
	public static void main(String... pumpkins)	{
		// EightQueensUnoriginal eq = new EightQueensUnoriginal(Integer.parseInt(pumpkins[0]));
		// eq.run();
		EightQueensUnoriginalRunner eqr = new EightQueensUnoriginalRunner(Integer.parseInt(pumpkins[0]), Integer.parseInt(pumpkins[1]), pumpkins.length > 2);
		eqr.run();
	}
	public void run()	{
		double startTime = System.nanoTime();
		int i;
		for (i = 0; i < (int)(threatenedBoard.length / 2); i++)
			solveQueens(0, i, threatenedBoard);
		int tempSolutions = numSolutions;
		if (threatenedBoard.length % 2 == 1)
			solveQueens(0, i, threatenedBoard);
		numSolutions += tempSolutions;
		System.out.printf("%,d solutions in %,f seconds\n", numSolutions, (System.nanoTime()-startTime) / 1E9);
	}
	private void solveQueens(int x, int y, int[][] threatenedBoard)	{
		addQueen(x, y, threatenedBoard);
		if (x == threatenedBoard.length - 1) {
			numSolutions++;
			threatenedBoard[x][y] = -1;
		}	else {
			for (int i = 0; i < threatenedBoard.length; i++)
				if (threatenedBoard[x + 1][i] == -1)
					solveQueens(x + 1, i, threatenedBoard);
			removeQueen(x, y, threatenedBoard);
		}
	}
	private void addQueen(int x, int y, int[][] threatenedBoard) {
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

class EightQueensUnoriginalRunner {
	private EightQueensUnoriginalThread[] threads;
	private int numSolutions, oddSolutions;
	private int threadsCompleted;
	private double startTime;
	private int numThreads;
	private int width;
	private int depth;
	private boolean quites;

	public EightQueensUnoriginalRunner(int width, int depth, boolean quites) {
		this.width = width;
		this.depth = depth;
		this.quites = quites;
		numSolutions = oddSolutions = 0;
		threadsCompleted = 0;
		numThreads = (int)(width / 2f + 0.9) * depth;
		threads = new EightQueensUnoriginalThread[numThreads];
	}

	public void run() {
		startTime = System.nanoTime();
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new EightQueensUnoriginalThread(this, width, i, depth, quites);
			threads[i].start();
		}
	}

	public synchronized void addNumSolutions(int numSolutions, int threadNum) {
		if (width % 2 == 1 && threadNum >= numThreads - depth)
			oddSolutions += numSolutions;
		else this.numSolutions += numSolutions;
		threadsCompleted++;
		if (threadsCompleted == numThreads) {
			this.numSolutions = this.numSolutions * 2 + oddSolutions;
			System.out.printf("%,d solutions in %,f seconds\n", this.numSolutions, (System.nanoTime()-startTime) / 1E9);
		}
	}
}

class EightQueensUnoriginalThread extends Thread {

	private Thread thread;
	private int x, y;
	private String threadName;
	private EightQueensUnoriginalRunner runner;
	private int threadNum;
	private int numSolutions;
	private int depth;
	private boolean quites;
	private int[] queens;

	public EightQueensUnoriginalThread(EightQueensUnoriginalRunner runner, int width, int threadNum, int depth, boolean quites) {
		this.runner = runner;
		this.threadNum = threadNum;
		this.depth = depth;
		this.quites = quites;
		queens = new int[width];
		threadName = threadNum + " " + depth;
		numSolutions = 0;
	}

	public void run() {
		if (depth == 1) {
			queens[0] = threadNum;
			solveQueens(queens, 1);
		}
		else {
			// System.out.println(threadNum + " " + (int)(threadNum / 2) + " " + threadNum % 2);
			// addQueen(0, (int)(threadNum / 2), threatenedBoard);
			queens[0] = (int)(threadNum / 2);
			if (threadNum % 2 == 0) {
				for (int i = 0; i < (int)(queens.length / 2 + 0.6); i++) {
					queens[1] = i;
					if (isValid(queens, 1))
						solveQueens(queens, 2);
				}
			}
			else for (int i = (int)(queens.length / 2 + 0.6); i < queens.length; i++) {
				queens[1] = i;
				if (isValid(queens, 1))
					solveQueens(queens, 2);
			}
		}
		runner.addNumSolutions(numSolutions, threadNum);
	}

	private void solveQueens(int[] queens, int col) {
		int len = queens.length;
		if (col == queens.length)
			numSolutions++;
		else for (int i = 0; i < len; i++) {
			queens[col] = i;
			if (isValid(queens, col))
				solveQueens(queens, col + 1);
		}
	}
	// Unoriginal
	private boolean isValid(int[] queens, int col) {
		for (int i = 0; i < col; i++) {
			if (queens[i] == queens[col])           return false;   // same column
			if (queens[i] - queens[col] == col - i) return false;   // same major diagonal
			if (queens[col] - queens[i] == col - i) return false;   // same minor diagonal
		}
		return true;
	}

	private void addQueen(int x, int y, int[][] threatenedBoard) {
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
		if (quites) {
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
		}
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
		if (quites) {
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

	public void start() {
		if (thread == null) {
			thread = new Thread(this, threadName);
			thread.start();
		}
	}
}