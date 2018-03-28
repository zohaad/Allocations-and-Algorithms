import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		String filename = "instance" + args[0] + ".gr";
		File file = new File(filename);
		Reader reader = new Reader(file);

		try {
			Integer[][] cost = reader.read_cost();
			ArrayList<Integer> terminals = reader.read_terminals();
			cost = Leaves.remove(cost, terminals);
			System.gc();
			
			// cost = Leaves.remove(cost, )

			// debugging
			// outerloop:
			// for (int i = 0; i < cost.length; i++) {
			// 	for (int j = 0; j < cost[i].length; j++) {
			// 		System.out.println("i: " + i + " j: " + j +  " c: " + cost[i][j]);
			// 		if (cost[i][j] != null) {
			// 			break outerloop;
			// 		}
			// 	}
				
			// }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}