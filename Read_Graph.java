import java.util.*;
import java.io.*;

public class Read_Graph {

    public static void main(String[] args) {
        // max int array is size 22516
        //int[][] cost = new int[22516][22516];

        String filename = "C:\\Users\\Mark\\Desktop\\heuristic\\public\\instance001.gr";
        File file = new File(filename);
        try {
            Scanner scan = new Scanner(file);
            int[] nodes_edges = nodes_edges(scan);
            int[][] cost = read_matrix(scan, nodes_edges[0]);
            int[] terminal = read_terminals(scan, nodes_edges[0]);
            //System.out.println(cost[1129][1254]);
            System.out.println(nodes_edges[0] + " " + nodes_edges[1]);
            System.out.println(cost[1][2]);
            int[] degree = new int[nodes_edges[0]];
            calculate_Degree(nodes_edges[0], degree, cost);
            filter_degree(nodes_edges[0], degree, cost, terminal);


            /*for(int i=0;i<terminal.length; i++){
                System.out.println(terminal[i]);
            }*/

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int[] nodes_edges(Scanner scan) {

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

    public static int[] read_terminals(Scanner scan, int nodes)
        throws java.io.FileNotFoundException {
            int[] terminal = new int[nodes+1];
            String line = scan.nextLine();
            line = scan.nextLine();
            line = scan.nextLine();

            while (!line.equals("END")) {
                String[] line_array = line.split(" ");
                terminal[Integer.parseInt(line_array[1])]=1;
                line = scan.nextLine();
            }

            return terminal;
    }

    public static void calculate_Degree(int n, int[] degree, int[][] cost){
        for (int i=0;i<n; i++){
            for (int j =0; j<n; j++){
                if (cost[i][j] > 0) {
                    degree[i]++;

                }
            }
        }
        int total_degree = 0;
        //System.out.println("Unfiltered: ");
        for (int i=0; i<n;i++){
            total_degree += degree[i];
            //System.out.println(degree[i]);
        }
        System.out.println("Total Unfiltered: " + total_degree);
    }

    public static void filter_degree(int n, int[] degree, int[][] cost, int[] terminal){
        int count;

        do{
            count =0;
            for (int i=0; i<n; i++){
                if (degree[i]==1 && terminal[i]==0){
                    for (int j=0; j<n; j++){
                        if(cost[i][j]>0){
                            cost[i][j] = cost[j][i] =0;
                            degree[i]--;
                            degree[j]--;
                        }
                    }
                    count++;
                }
            }
        }while(count!=0);
        //System.out.println("Filtered:");
        int total_degree=0;
        for (int i=0; i<n;i++){
            total_degree += degree[i];
            //System.out.println(degree[i]);
        }
        System.out.println("Total Filtered:" + total_degree);
    }

    public static int find_cost(int[][] cost, int i, int j){
        return cost[i][j];
    }

}
