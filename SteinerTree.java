import java.util.*;
import java.io.*;

/*
	cost matrix is upper-triangluar
*/

public class SteinerTree {
	public static void main(String[] args) {
		// max int array is size 22516
		//int[][] cost = new int[22516][22516];

		String filename = "instance" + args[0] + ".gr";
		File file = new File(filename);
		try {
			Scanner scan = new Scanner(file);
			int[] nodes_edges = amount_nodes_edges(scan);
			int[][] cost = read_matrix(scan, nodes_edges[0]);
			int[] terminals = read_terminals(scan);
			// close scanner
			scan.close();

			// System.out.println(cost[1254][1129]);
			// System.out.println(cost[1253][1129]);
			// System.out.println(nodes_edges[0] + " " + nodes_edges[1]);

			MST mst = new MST(cost, terminals);
			mst.delete_leaves();


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public static int[] amount_nodes_edges(Scanner scan) {

		scan.nextLine(); 

		String line = scan.nextLine();
		int nodes = Integer.parseInt(line.split(" ")[1]);

		line = scan.nextLine();
		int edges = Integer.parseInt(line.split(" ")[1]);

		int[] nodes_edges = {nodes, edges};
		return nodes_edges;
	}

	public static int[][] read_matrix(Scanner scan, int nodes)
	throws java.io.FileNotFoundException {

		int[][] cost = new int[nodes][];

		// make triangular matrix
		for (int i = 0; i < nodes; i++) {
			cost[i] = new int[i + 1];
		}

		int col;
		int row;
		int c;

		// make other edges cost very large
		int max = (int) (Integer.MAX_VALUE / 2 - 1);
		for (int i = 0; i < nodes; i++) {
			for (int j = 0; j < i; j++) {
				cost[i][j] = max;
			}
		}

		// method for edges
		String line = scan.nextLine();
		while (!line.equals("END")) {
			String[] line_array = line.split(" ");

			col =  Integer.parseInt(line_array[1]) - 1;
			row = Integer.parseInt(line_array[2]) - 1;
			c = Integer.parseInt(line_array[3]);

			// to prevent 009 issues, swap if col is bigger
			if (col > row) {
				int save = col;
				col = row;
				row = save;
			}

			cost[row][col] = c;
			// enable this if complete matrix is needed (instead of triangular)
			// cost[to][from] = c;
			line = scan.nextLine();

		}

		return cost;
	} 

	public static int[] read_terminals(Scanner scan) {
		scan.nextLine();
		scan.nextLine();
		int amount = Integer.parseInt(scan.nextLine().split(" ")[1]);

		int[] terminals = new int[amount];
		for (int i = 0; i < amount; i++) {
			terminals[i] = Integer.parseInt(scan.nextLine().split(" ")[1]);
		}
		return terminals;
	}
}