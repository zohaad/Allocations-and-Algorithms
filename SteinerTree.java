import java.util.*;
import java.io.*;

public class SteinerTree {
	public static void main(String[] args) {
		// max int array is size 22516
		//int[][] cost = new int[22516][22516];
		try {
			int[][] cost = read_matrix("instance001.gr"); // change to args[0] later
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//System.out.println(cost[0][0]);
	}

	public static int[][] read_matrix(String filename)
	throws java.io.FileNotFoundException {

		File file = new File(filename);
		Scanner scan = new Scanner(file);

		scan.nextLine(); // 1st line is useless

		String line = scan.nextLine();
		int nodes = Integer.parseInt(line.split(" ")[1]);

		line = scan.nextLine();
		int edges = Integer.parseInt(line.split(" ")[1]);
		
		System.out.println(edges);


		int[][] cost = new int[nodes][nodes];
		int from;
		int to;
		int c;
		// method for edges
		line = scan.nextLine();
		while (line != "END") {
			String[] line_array = line.split(" ");
			System.out.println(line);
			from = Integer.parseInt(line_array[1]);
			from--;
			to = Integer.parseInt(line_array[2]);
			to--;
			c = Integer.parseInt(line_array[3]);
			line = scan.nextLine();
			System.out.println(line == "END");
		}

		return cost;
	} 
}