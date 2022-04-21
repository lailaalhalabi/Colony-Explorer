import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;
/**
 * @author Laila Alhalabi, ID #40106558. COMP352, ASSIGNMENT2, VERSION1. 
 *         The purpose of this recursive version is finding colonies using DFS (Depth-first search) by finding neighbors of each cell containing '1' and labeling them.
 */
public class ColonyExplorer1 {
	// Declaring variables
	static int rows = 5; 
	static int columns = 20;
	static int coloniesCounter; 
	static int cellsCounter; 
	static char label; 
	// Declaring "grid" to store the grid & "colonizedGrid" to store the grid with labels
	static int grid[][]; 
	static char colonizedGrid[][];
	/**
	 * A method that generates a grid of random rows and columns filled with 0's and 1's
	 * @return grid
	 */
	public static int[][] gridGenerator() {
		// Declaring a random object & generating random values between 5 and 20 for rows and columns
		Random random = new Random(); 
		rows = random.nextInt(20 - 5 + 1) + 5; 
		columns = random.nextInt(20 - 5 + 1) + 5; 
		grid = new int[rows][columns]; 
		// Iterating over rows & columns and filling them with random 0's and 1's
		for (int i = 0; i < rows; i++) { 
			for (int j = 0; j < columns; j++) { 
				grid[i][j] = random.nextInt(2); 
			}
		}
		return grid; // Returning the generated grid
	}
	/**
	 * A method that prints the grid, find colonies and then it prints the grid with labeled colonies. 
	 * The method finds the size of each colony inside the grid and the total number of colonies.
	 * @param grid array of integers to find the colonies in it
	 * @param writer a writing object
	 */
	public static void ColonyExplorer(int[][] grid, PrintStream writer) {
		// Initializing variables
		colonizedGrid = new char [rows][columns];
		coloniesCounter = 0;
		cellsCounter = 0;
		label = 'A';
		// Initializing "colonizedgrid" with dashes to label the colonies only
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				colonizedGrid[i][j] = '-';
			}
		}
		// Printing the grid that we want to find the colonies for
		System.out.println("The grid consists of " + rows + " rows and " + columns + " columns as follow:");
		writer.println("The grid consists of " + rows + " rows and " + columns + " columns as follow:");
		for (int i = 0; i < rows; i++) { 
			for (int j = 0; j < columns; j++) { 
				System.out.print(grid[i][j] + " ");
				writer.print(grid[i][j] + " ");
			} 
			System.out.println(); 
			writer.println();
		}
		// Looping through the grid to find colonies
		System.out.println("The colonies found inside the grid are: ");
		writer.println("The colonies found inside the grid are: ");
		for (int i = 0; i < rows; i++) { 
			for (int j = 0; j < columns; j++) { 
				// When the first 1 is found, ExploreAndLabelColony method will be called to ind the neighbors of the cell & labels them
				if (grid[i][j] == 1) { 
					ExploreAndLabelColony(grid, i, j);
					System.out.println((coloniesCounter+1) + ")  " + label + ": " + cellsCounter);
					writer.println((coloniesCounter+1) + ")  " + label + ": " + cellsCounter);
					// Incrementing to move to the next letter, incrementing times that the method was called & setting cellsCounter to zero to count the next colony size
					label++;
					coloniesCounter++; 
					cellsCounter = 0; 
				}
			} 
		}
		System.out.println("The total number of the found colonies is #" + coloniesCounter);
		writer.println("The total number of the found colonies is #" + coloniesCounter);
		// Printing the grid with labels
		System.out.println("Grid of colonies with the labels: ");
		for (int i = 0; i < rows; i++) { 
			for (int j = 0; j < columns; j++) { 
				System.out.print(colonizedGrid[i][j] + " ");
				writer.print(colonizedGrid[i][j] + " ");
			} 
			System.out.println(); 
			writer.println();
		}
	}
	/**
	 * A method that checks if the cell is valid (exists and not visited before). 
	 * If the cell is invalid then it returns. 
	 * If valid then it labels it, sets it to '0' to mark it as visited, increases the cellsCounter and stores the neighbors inside two arrays a[] and b[].
	 * After storing the neighbors, it iterates through the 8 directions to see what cell is valid and equals 1, so it calls itself to check the cell.
	 * In this version, tail recursion was used to eliminate auxiliary memory usage.
	 * @param grid grid array of integers to find the colonies in it
	 * @param ROW index at current row
	 * @param COLUMN index at current column
	 */
	public static void ExploreAndLabelColony(int[][] grid, int ROW, int COLUMN) { 
		// Checking if the cell doesn't exist or equals 0 so it returns
		if (ROW < 0 || ROW >= rows || COLUMN < 0 || COLUMN >= columns || grid[ROW][COLUMN] == 0) { 
			return;
			}
		// Labelling the visited cell, setting it to 0 so we don't check it again and incresing the counter
		colonizedGrid[ROW][COLUMN] = label;
		grid[ROW][COLUMN] = 0; 
		cellsCounter++;
		// Declaring a[] to store directions of x & b[] to store directions of y to find neighbors of the cells
		int[] a = { ROW - 1, ROW + 1, ROW, ROW, ROW - 1, ROW - 1, ROW + 1, ROW + 1 };
		int[] b = { COLUMN, COLUMN, COLUMN - 1, COLUMN + 1, COLUMN - 1, COLUMN + 1, COLUMN - 1, COLUMN + 1 };
		// Calling the method recursively eight times to check all its neighbors
		for (int k = 0; k < 8; k++) {
		   // Checking if the cells exists & equals 1 so it calls the method recursively
			if (a[k] < rows && a[k] >= 0 && b[k] < columns && b[k] >= 0 && grid[a[k]][b[k]] == 1) {
				ExploreAndLabelColony(grid, a[k], b[k]);
			}
		}		
	}
	public static void main(String[] args) {
		PrintStream write = null; // Initializing a PrintStream
		File file = new File("ColonyExplorer1.txt"); // Creating a file and declaring a PrintStream to output the rows
		try {
			write = new PrintStream(new BufferedOutputStream(new FileOutputStream(file))); // Writing to the file
			System.out.println("\n	# # # # COLONY EXPLORER # # # #\n");
			write.println("\n	# # # # COLONY EXPLORER # # # #\n");
			// Declaring a grid & finding colonies
			int gridToTest[][] = { { 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1 },{ 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 },{ 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
					         { 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0 },{ 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1 } };
			System.out.println("  	 * * * testing the grid * * *  \n");
			write.println("  	 * * * testing the grid * * *  \n");
			ColonyExplorer(gridToTest,write);
			// Generating grids & testing them
			System.out.println("\n\n\n     * * * testing generated grids * * *\n");
			write.println("\n\n\n     * * * testing generated grids * * *\n");
			for (int k = 1; k <= 20; k++) { // looping 
				System.out.println("\n  	      * * * TEST " + k + " * * *  \n");
				write.println("  	      * * * TEST " + k + " * * *  \n");
				gridGenerator();
				long start = System.nanoTime(); // Declaring the start time of the program
				ColonyExplorer(grid,write);
				long end = System.nanoTime(); // Declaring the end time of the program
				// Calculating the execution time, converting it to seconds and printing it
				long executionTime = (end - start);
				double seconds = (double) executionTime / 1000000000;
				System.out.println("\nFOR RUN# " + k + "\nThe program took " + seconds + " seconds to be executed.\n"
						         + "---------------------------------------------------------------------\n");
				write.print("\nFOR RUN# " + k + "\nThe program took " + seconds + " seconds to be executed.\n"
						+ "---------------------------------------------------------------------\n");
			} // Catching exceptions
		} catch(Exception e) { 
			e.getMessage();
		} // Closing the write object
		write.close();
	}
}
