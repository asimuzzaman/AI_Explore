//package dijkstra;

import java.io.*;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Graph g = new Graph();

        // The name of the file to open.
        input.next(); //input collector eg. "find_route"
        String fileName = input.next();
        String source = input.next();
        String dest = input.next();
        // reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fileReader);

            while((line = br.readLine()) != null) {
                String[] words = line.split(" "); //splitting words by whitespace
                if(words[0].equals("END")) break; //END OF INPUT

                if(!g.VertexExists(words[0])) g.AddVertex(words[0]);
                if(!g.VertexExists(words[1])) g.AddVertex(words[1]);
            }
            br.close();

            fileReader = new FileReader(fileName);
            br = new BufferedReader(fileReader);
            while((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                if(words[0].equals("END")) break; //END OF INPUT

                g.AddEdge(words[0],words[1],Integer.parseInt(words[2]));
            }
            br.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        g.SearchPath(source,dest); //showing the shortest path
    }
}
