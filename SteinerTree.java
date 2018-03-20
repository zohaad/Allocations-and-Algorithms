import java.util.*;
import java.io.*;

/*
	cost matrix is upper-triangluar
*/

public class SteinerTree {
	public static void main(String[] args) {
		// max int array is size 22516
		//int[][] cost = new int[22516][22516];

		String filename = "instance001.gr";
		File file = new File(filename);
		try {
			Scanner scan = new Scanner(file);
			int[] nodes_edges = amount_nodes_edges(scan);
			int[][] cost = read_matrix(scan, nodes_edges[0]);
			int[] terminals = read_terminals(scan);

			System.out.println(cost[1129][1254]);
			System.out.println(cost[1129][1253]);
			System.out.println(nodes_edges[0] + " " + nodes_edges[1]);

			// close scanner
			scan.close();

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

		int[][] cost = new int[nodes][nodes];
		int from;
		int to;
		int c;

		// make other edges cost very large
		int max = (int) (Integer.MAX_VALUE / 2 - 1);
		for (int j = 0; j < nodes; j++) {
			for (int i = 0; i < j; i++) {
				cost[i][j] = max;
			}
		}

		// method for edges
		String line = scan.nextLine();
		while (!line.equals("END")) {
			String[] line_array = line.split(" ");

			from =  Integer.parseInt(line_array[1]) - 1;
			to = Integer.parseInt(line_array[2]) - 1;
			c = Integer.parseInt(line_array[3]);
			cost[from][to] = c;
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