import java.util.*;
import java.io.*;

/*
	cost matrix is upper-triangluar
*/

public class SteinerTree {
    public static void main(String[] args) {
        // max int array is size 22516
        //int[][] cost = new int[22516][22516];

        //String filename = "instance" + args[0] + ".gr";
        String filename = "C:\\Users\\Mark\\Desktop\\heuristic\\public\\instance000.gr";
        File file = new File(filename);
        try {
            Scanner scan = new Scanner(file);
            int[] nodes_edges = amount_nodes_edges(scan);
            int[][] cost = read_matrix(scan, nodes_edges[0]+1);
            int[] terminals = read_terminals(scan);

            // close scanner
            scan.close();

            System.out.println("cost 7-6:" +cost[7][6]);
            System.out.println("cost 5-1:" +cost[5][1]);
            System.out.println(nodes_edges[0] + " " + nodes_edges[1]);

            MST mst = new MST(cost, terminals);

            //System.out.println("FW:");
            mst.Floyd_Warschall_Matrix();
            mst.delete_leaves();
            mst.print_Matrix();
            for (int i = nodes_edges[0]; i>1; i--){
                for (int j = i-1; j>0; j--){
                    mst.getPath(i,j);
                    mst.print_Pre();
                }
            }

            mst.Kruskal(nodes_edges[0]);
            /*mst.getPath(7,1);
            mst.print_Pre();
            mst.getPath(6,4);
            mst.print_Pre();*/

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
        for (int i = 1; i < nodes; i++) {
            cost[i] = new int[i+1];
        }

        int col;
        int row;
        int c;

        // make other edges cost very large
        int max = (int) (Integer.MAX_VALUE / 2 - 1);
        for (int i = 1; i < nodes; i++) {
            for (int j = 1; j <= i; j++) {
                if(i==j) cost[i][j]=0;
                else cost[i][j] = max;
            }
        }

        // method for edges
        String line = scan.nextLine();
        while (!line.equals("END")) {
            String[] line_array = line.split(" ");

            col =  Integer.parseInt(line_array[1]);
            row = Integer.parseInt(line_array[2]);
            c = Integer.parseInt(line_array[3]);

            // to prevent 009 issues, swap if col is bigger
            if (col < row) {
                cost[row][col] = c;
            }
            else{
                cost[col][row] =c;
            }
            // enable this if complete matrix is needed (instead of triangular)
            // cost[row][col] = cost[col][row] = c;
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
            terminals[i] = Integer.parseInt(scan.nextLine().split(" ")[1])-1;
        }

        /* terminal boolean array
        int[] terminal = new int[nodes+1];
            String line = scan.nextLine();
            line = scan.nextLine();
            line = scan.nextLine();
            while (!line.equals("END")) {
                String[] line_array = line.split(" ");
                terminal[Integer.parseInt(line_array[1])]=1;
                line = scan.nextLine();
            }
         */
        return terminals;
    }
}