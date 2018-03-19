import java.util.*;
import java.io.*;


public class SteinerTree {
	public static void main(String[] args) {
		// max int array is size 22516
		//int[][] cost = new int[22516][22516];

		String filename = "instance001.gr";
		File file = new File(filename);
		try {
			Scanner scan = new Scanner(file);
			int[] nodes_edges = nodes_edges(scan);
			int[][] cost = read_matrix(scan, nodes_edges[0]);

			System.out.println(cost[1129][1254]);
			System.out.println(nodes_edges[0] + " " + nodes_edges[1]);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public static int[] nodes_edges(Scanner scan) {

		scan.nextLine(); // 1st line is useless

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
		// method for edges
		String line = scan.nextLine();
		while (!line.equals("END")) {
			String[] line_array = line.split(" ");

			from =  Integer.parseInt(line_array[1]) - 1;
			to = Integer.parseInt(line_array[2]) - 1;
			c = Integer.parseInt(line_array[3]);
			cost[from][to] = c;
			cost[to][from] = c;
			line = scan.nextLine();

		}
		return cost;
	} 

	public static void terminals(Scanner scan) {
		
	}
}