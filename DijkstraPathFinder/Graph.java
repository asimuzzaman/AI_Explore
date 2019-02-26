//package dijkstra;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Vector;

public class Graph {
    private Vector<String> vertices;
    private int[][] edges;
    private boolean[] marks;
    private boolean edgeInitialized = false;
    private Vector<Path> shortPaths = new Vector(); //

    public Graph () {
        vertices = new Vector();
    }

    public void AddVertex (String vertex) {
        vertices.addElement(vertex);
    }

    public boolean VertexExists(String s) {
        if(vertices.indexOf(s)== -1) return false;
        return true;
    }

    private void InitializeEdge () {
        int size = vertices.size();
        edges = new int[size][size];

        marks = new boolean[vertices.size()];

        edgeInitialized = true;
    }

    public void AddEdge (String from, String to, int weight) {
        if(!edgeInitialized)
            InitializeEdge();

        int row = vertices.indexOf(from);
        int col = vertices.indexOf(to);
        //undirected edge implementation
        edges[row][col] = weight;
        edges[col][row] = weight;
    }

    private void ClearMarks () {
        int size = vertices.size();
        for (int i=0;i<size;i++)
            marks[i] = false;
    }

    private void MarkVertex (String v) {
        int index = vertices.indexOf(v);
        marks[index] = true;
    }

    private boolean IsMarked (String v) {
        return marks[vertices.indexOf(v)];
    }

    private int GetWeight (String from, String to) {
        int row = vertices.indexOf(from);
        int col = vertices.indexOf(to);
        //returns 0 for unreachable path
        return edges[row][col];
    }

    private void GetReachables (String v,Vector<String> nodes) {
        int size = vertices.size();
        nodes.clear();
        int from = vertices.indexOf(v);

        for (int i=0;i<size;i++) {
            if(edges[from][i]!=0 && !IsMarked(vertices.get(i))) {
                nodes.addElement(vertices.get(i));
            }
        }
    }
    //implementation of Dijkstra's algorithm
    public void SearchPath (String start, String end) {
        Comparator<Path> comparator = new PathComparator();
        PriorityQueue<Path> queue = new PriorityQueue<>(50,comparator);
        boolean found = false;
        Vector<String> reachables = new Vector<>();
        boolean[] tempMark;
        Vector<Path> toBeInserted = new Vector<>();
        Vector<Path> result = new Vector<>();
        Stack<Path> finalPath = new Stack<>();

        ClearMarks();
        Path p = new Path();
        p.from = start;
        p.to = start;
        p.weight = 0;
        queue.add(p);

        while(!queue.isEmpty()) {
            Path current = queue.remove();
            result.addElement(current); //storing the shortest routes

            GetReachables(current.to,reachables);
            MarkVertex(current.to);
            tempMark = new boolean[vertices.size()];
            toBeInserted.clear();

            while (!queue.isEmpty()) {
                Path temp = queue.remove();
                Path newPath = new Path();
                tempMark[vertices.indexOf(temp.to)]= true;

                int weight = GetWeight(current.to,temp.to) + current.weight;
                int oldWeight = temp.weight;
                if (weight < oldWeight && (weight-current.weight)!=0) {
                    newPath.from = current.to;
                    newPath.to = temp.to;
                    newPath.weight = weight;
                    toBeInserted.addElement(newPath); //shorter path
                } else toBeInserted.addElement(temp); //no change
            }
            for (int i=0;i<reachables.size();i++) {
                Path newPath = new Path();
                if(!tempMark[vertices.indexOf(reachables.get(i))]) { //avoiding already visited ones
                    newPath.from = current.to;
                    newPath.to = reachables.get(i);
                    newPath.weight = GetWeight(newPath.from, newPath.to) + current.weight;

                    toBeInserted.addElement(newPath);
                }
            }
            for (int i=0;i<toBeInserted.size();i++) { //inserting into PriorityQ
                queue.add(toBeInserted.get(i));
            }
        }

        String dest = end;
        int distance=0;

        for (int i=0;i<result.size();i++) {
            Path t = result.get(i);
            if(t.to.equals(dest)) {
                t.weight = GetWeight(t.from, t.to);
                distance += t.weight;
                dest = t.from;
                i = 0; //resetting to the beginning
                finalPath.addElement(t);

                if (t.from.equals(start)) {
                    found = true;
                    break;
                }
            }
        }

        System.out.print("distance: ");
        if(found) {
            System.out.print(distance+" km");
            System.out.println("\nroute: ");
            while (!finalPath.isEmpty()) {
                Path t = finalPath.pop();
                System.out.println(t.from+" to "+t.to+", "+t.weight+" km");
            }
        } else {
            System.out.println("infinity");
            System.out.println("route: none");
        }
    }
}
