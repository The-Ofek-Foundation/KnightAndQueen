public class EightQueensUnoriginal	{
	public static void main(String... pumpkins)	{
		EightQueensUnoriginalRunner eqr = new EightQueensUnoriginalRunner(Integer.parseInt(pumpkins[0]), Integer.parseInt(pumpkins[1]), pumpkins.length > 2);
		eqr.run();
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

	public void start() {
		if (thread == null) {
			thread = new Thread(this, threadName);
			thread.start();
		}
	}
}