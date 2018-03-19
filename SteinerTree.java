import java.util.*;
import java.io.*;

public class SteinerTree {
	public static void main(String[] args) {
		// max int array is size 22516
		//int[][] cost = new int[22516][22516];
		try {
			read_matrix(args[0]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void read_matrix(String filename)
	throws java.io.FileNotFoundException {

		File file = new File(filename);
		Scanner scan = new Scanner(file);

		String s = scan.nextLine();
		System.out.println(s);
	} 
}