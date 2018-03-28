import java.io.*;
import java.util.*;

public class Reader {

	private Scanner scan;

	public Reader(File file){
		this.scan = new Scanner(file);
	}

	public int[] amount_nodes_edges() {
		// skip first line
		this.scan.nextLine();

		String[] line_array = this.scan.nextLine().split(" ");
		int nodes = Integer.parseInt(line_array[1]);

		line_array = this.scan.nextLine().split(" ");
		int edges = Integer.parseInt(line_array[1]);

		int[] amount_nodes_edges = {nodes, edges};
		return amount_nodes_edges;
	}



}