import java.util.Arrays;

/* 
https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
These rules, which compare the behavior of the automaton to real life, can be condensed into the following:

Any live cell with two or three live neighbours survives.
Any dead cell with three live neighbours becomes a live cell.
All other live cells die in the next generation. Similarly, all other dead cells stay dead.
*/

public class GameOfLife {
	int[][] board; // 2D array
	int numRows;
	int numCols;
	int step;

	// Constructor method for GameOfLife (constructors have the name of the class)
	public GameOfLife(int rows, int cols) {
		board = new int[rows][cols]; // initialises 10x10 matrix of 0's.
		numRows = rows;
		numCols = cols;
	}

	public boolean outOfBounds(int x, int y) {
		// Returns true if a given coordinate is out of bounds.
		if (x < 0 || y < 0 || x >= numRows || y >= numCols) {
			return true;
		}
		return false;
	}

	public int numAdjacent(int row, int col) {
		// Returns True if a given position has a live state adjacent (omnidirectional)
		// to it.
		int count = 0;
		int[][] d8 = { { -1, -1 }, { -1, 0 }, { -1, 1 },
				{ 0, -1 }, { 0, 0 }, { 0, 1 },
				{ 1, -1 }, { 1, 0 }, { 1, 1 } };

		for (int i = 0; i < 9; i++) {
			int[] row_checked = d8[i];
			int new_row = row + row_checked[0];
			int new_col = col + row_checked[1];

			// Skip to next iteration if out of bounds
			if (outOfBounds(new_row, new_col))
				continue;
			// could use ternary operator outOfBounds(new_row, new_col) ? continue: pass;

			int cell = this.board[new_row][new_col];
			if (cell == 1)
				count++;
		}
		return count;
	}

	public void update() {
		// Updates the game state to the next tick.

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				// Execute rules for each cell (unoptimised right now)
				int numAdjLives = numAdjacent(i, j);
				int curCell = board[i][j];

				curCell = rule1(numAdjLives, curCell); // Sets the curCell to either 1/0 depending if it passes rule one
														// (similar for next 2 rules, unsure if this is best way to do
														// it.)
				curCell = rule2(numAdjLives, curCell);

			}
		}



		step++; // Just so we can keep track of the step count.

		// Updates the game state to the next tick.
		// rule1();
		// rule2();
		// rule3();
	}

	private int rule1(int adjLives, int curCell) {
		// Any live cell with two or three alive neighbours survives.
		if ((adjLives == 2 || adjLives == 3) && curCell == 1) {
			return 1;
		}
		return 0;
	}

	private int rule2(int adjLives, int curCell) {
		// Any dead cell with three live neighbours becomes a live cell.
		if (adjLives == 3 && curCell == 0) {
			return 1;
		}
		return 0;
	}

	private int rule3(int adjLives, int curCell) {
		// All other live cells die in the next generation. Similarly, all other dead cells stay dead.
		if (adjLives == 3 && curCell == 0) {
			return 1;
		}
		return 0;
	}
	public static void main(String[] args) {
		GameOfLife gLife = new GameOfLife(10, 10);

		gLife.board[4][4] = 1; // to test adjacent
		gLife.board[3][2] = 1; // to test adjacent

		System.out.println(Arrays.deepToString(gLife.board));

		System.out.println(gLife.numAdjacent(3, 3));
		System.out.println("Hey  this is what I see at 4,4: " + gLife.board[4][4]);

	}
}

/*
 * Implementation of rules.
 * Rules are given a cell position and check the adjacent cell pos' if occupied,
 * and then do the rule.
 */
// public rule1() {

// }

// You can have some methods here that quickdraw certain patterns (like the
// block, glider, etc.) based off a given cell position.

// For the rule checking:
// https://stackoverflow.com/questions/49096988/checking-adjacent-tiles-in-2-dimensional-array
// (checks if a tile is 'occupied') and would handle outside boundary stuff (in
// this case, if it was outside it'd return false -- we could just count this as
// 'dead')