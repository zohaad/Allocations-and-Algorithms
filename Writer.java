import java.io.*;

public class Writer {
	private String instance;
	private Integer[][] matrix;
	private int two_opt_cost;

	public Writer(String instance, Integer[][] matrix, int two_opt_cost) {
		this.instance = instance;
		this.matrix = matrix;
		this.two_opt_cost = two_opt_cost;
	}

	public void write() throws IOException {
		FileWriter fw = new FileWriter("solution" + instance + ".txt");
		fw.write("VALUE " + two_opt_cost + "\n");
		for (int i = 0; i < this.matrix.length; i++) {
			if (this.matrix[i] != null) {
				for (int j = 0; j < this.matrix[i].length - 1; j++) {
					if (this.matrix[i][j] != null) {
						fw.write((i + 1) + " " + (j + 1) + "\n");
					}
				}
			}
		}
		fw.close();
	}
}