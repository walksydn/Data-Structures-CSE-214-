import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;

class Graph<T> {

    private HashMap<T, Vertex<T>> vertexes;
    private int numEdges;

    /**
     * Constructor for the Graph class that initializes the HashMap and
     * sets the number of edges to zero
     */
    public Graph() {
        vertexes = new HashMap<>();
        numEdges = 0;
    }

    /**
     * Contructor for the Graph class that:
     *  Initializes the HashMap
     *  Sets the number of edges to zero
     *  Reads in the text in the file found at filename line-by-line, converting it into
     *    a graph according to graph description language (can only be used on directed graphs)
     * @param filename name of the file being read
     * @throws FileNotFoundException thrown when the file given as the parameter does not existd
     */
    public Graph(String filename) throws FileNotFoundException{
        vertexes = new HashMap<>();
        numEdges = 0;
        File f = new File("src/" + filename);
        Scanner input = new Scanner(f);
        // skips the first line that has no data
        if(input.hasNext()){
            input.nextLine();
        }
        while(input.hasNext()){
            String line = input.nextLine();
            // prevents reading of the last line, which is just "}"
            if (input.hasNext()){
                // split string into the first and second vertex of the edge
                String[] v = line.split("->");
                // add the edge, removing the random spaces before the first vertex
                addEdge((T)v[0].trim(), (T)v[1].trim());
            }
        }
    }

    /**
     * Adds an edge between the two vertexes with the values x and y
     * Edge is added through accessing the LinkedList associated
     *  with the vertex x through the HashMap and adding y to it
     * @param x the first vertex (edge is from this vertex)
     * @param y the second vertex (edge is to this vertex)
     * @throws KeyAlreadyExistsException thrown when an edge between x and y already exists
     */
    public void addEdge(T x, T y) throws KeyAlreadyExistsException {
        // create a new vertex in the HashMap if x or y are already not within it
        if(!vertexes.containsKey(x)){
            vertexes.put(x, new Vertex(x));
        }
        if(!vertexes.containsKey(y)){
            vertexes.put(y, new Vertex(y));
        }
        // check if the edge already exists and add it if it does not
        if(!vertexes.get(x).getNeighbors().contains(y)){
            vertexes.get(x).getNeighbors().add(y);
            numEdges++;
        } else {
            throw new KeyAlreadyExistsException();
        }
    }

    /**
     * Removes the edge from x to y of the graph through removing it from the neighbors list
     *  associated with the vertex mapped to x in the HashMap
     * @param x the first vertex (edge is from this vertex)
     * @param y the second vertex (edge is to this vertex)
     * @throws NoSuchElementException thrown when either of the vertexes do not exist or the edge does not exist
     */
    public void removeEdge(T x, T y) throws NoSuchElementException {
        // make sure both vertexes and the edge are existent
        if(!vertexes.containsKey(x) || !vertexes.containsKey(y) || !vertexes.get(x).getNeighbors().contains(y)){
            throw new NoSuchElementException();
        } else {
            // remove the value of y from the neighbors list of the vertex of value x
            vertexes.get(x).getNeighbors().remove(y);
            numEdges--;
        }
    }

    /**
     * Checks if the edge between x and y exists through searching through
     * the neighbors list associated with the the vertex with value x
     * @param x the first vertex (edge is from this vertex)
     * @param y the second vertex (edge is to this vertex)
     * @return true if the edge exists, false otherwise
     * @throws NoSuchElementException thrown if either vertex does not exist
     */
    public boolean isEdge(T x, T y) throws NoSuchElementException{
        // check if the vertexes both exist
        if(!vertexes.containsKey(x) || !vertexes.containsKey(y)){
            throw new NoSuchElementException();
        } else {
            // check if the neighbors list of the vertex with value x contains y
            // aka: if there is an edge from x to y
            if(vertexes.get(x).getNeighbors().contains(y)){
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Returns the neighbors of the vertex associated with the element x
     * @param x the value of the vertex being checked for neighbors
     * @return the neighbors of the vertex with value x
     * @throws NoSuchElementException thrown when a vertex with value x does not exist
     */
    public LinkedList<T> neighbors(T x) throws NoSuchElementException{
        // check if the vertex with value x exists
        if(vertexes.containsKey(x)){
            return vertexes.get(x).getNeighbors();
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Finds the number of vertexes through getting the size of the HashMap
     * storing the vertexes
     * @return the number of vertexes in the graph
     */
    public int numVertices() {
        return vertexes.size();
    }

    /**
     * Finds the number of edges
     *  The value of numEdges is incremented each time an edge is added
     *  and decremented each time an edge is removed
     * @return the number of edges in the graph
     */
    public int numEdges() {
        return numEdges;
    }

    /**
     * Removes a vertex from the HashMap and removes all of the edges to that vertex
     * @param x the value of the vertex being removed
     * @throws NoSuchElementException thrown when the vertex associated with x does not exist
     */
    public void removeVertex(T x) throws NoSuchElementException{
        // check if the vertex exists
        if(!vertexes.containsKey(x)){
            throw new NoSuchElementException();
        } else {
            numEdges -= vertexes.get(x).getNeighbors().size();
            vertexes.remove(x);
            vertexes.forEach((k, v) -> {
                if(v.getNeighbors().contains(x)){
                    v.getNeighbors().remove(x);
                    numEdges--;
                }
            });
        }
    }

    /**
     * Converts the graph currently stored within vertexes to a string and stores
     * it as a file of the name filename (the parameter)
     * Conversion is done through iterating through the HashMap and printing the edges found
     *  in each neighbors list in the format (first vertex)->(second vertex)
     * @param filename the name of the file being generated/written to
     */
    public void toDotFile(String filename) {
        // initiate the string and adds the header of the text file
        final String[] output = {"Digraph { \n"};
        // iterate through the each vertex and add all of its edges to output
        vertexes.forEach((k, v) -> {
            for(int i = 0; i < v.getNeighbors().size(); i++){
                output[0] += "    " + (String)k + "->" + (String)v.getNeighbors().get(i) + "\n";
            }
        });
        // add the last line of output
        output[0] += "}";
        // write output to the file of the name entered into the method
        try{
            FileWriter file = new FileWriter(filename);
            file.write(output[0]);
            file.close();
        } catch (Exception e){
            System.out.println("That file cannot be written to");
        }
    }

    /**
     * Sets all the "marked" variables of each vertex in the HashMap to false
     */
    private void resetMarked(){
        vertexes.forEach((k, v) -> {
            v.setMarked(false);
        });
    }

    /**
     * Does a depth first search beginning at the specific vertex using
     * the dfsHelper method
     * @param x the value of the starting vertex
     */
    public void depthFirstSearch(T x) {
        resetMarked(); // sets all vertex markers to false
        dfsHelper(vertexes, vertexes.get(x));
    }

    /**
     * Recursively prints the value of each vertex on the graph through checking each neighbor of
     *  the current vertex (goes all the way down the path of one neighbor before moving onto the next)
     * @param vertex the list of vertexes
     * @param v the current vertex being checked
     */
    public void dfsHelper(HashMap<T, Vertex<T>> vertex, Vertex<T> v) {
        // store the neighbors of vertex
        LinkedList<T> connections = v.getNeighbors();
        int i;
        Vertex<T> nextNeighbor;
        // set the current vertex as visited
        v.setMarked(true);
        System.out.println(v.getLabel());
        // call dfs helper on all of the neighbors in order
        for (i = 0; i < connections.size(); i++) {
            nextNeighbor = vertex.get(connections.get(i));
            if (!nextNeighbor.isMarked())
                dfsHelper(vertex, nextNeighbor);
        }
    }

    /**
     * Does a breadth first search on the current graph:
     *  Iterate through the graph beginning at the entered vertex, placing each of its
     *  neighbors in a queue and popping them as their neighbors are checked
     * @param x the value of the starting vertex
     */
    public void breadthFirstSearch(T x) {
        resetMarked(); // sets all vertex markers to false
        LinkedList<T> connections;
        int i;
        T vertex, nextNeighbor;
        Queue<T> q = new Queue();
        q.enqueue(x); // add the starting vertex to the queue
        while (!q.isEmpty()) {
            // remove the current vertex from the queue
            vertex = q.dequeue();
            System.out.println(vertex);
            // get all neighbors of the current vertex
            connections = vertexes.get(vertex).getNeighbors();
            // add the neighbors of the current vertex to the queue if they have not been visited yet
            for (i = 0; i < connections.size(); i++) {
                nextNeighbor = connections.get(i);
                if (!vertexes.get(nextNeighbor).isMarked()) {
                    vertexes.get(nextNeighbor).setMarked(true);
                    q.enqueue(nextNeighbor);
                }
            }
        }
    }

    /**
     * Does a depth first search on the current graph:
     *  Iterate through the graph beginning at the entered vertex, placing each of its
     *  neighbors in a stack and popping them as their neighbors are checked
     * @param x the value of the starting vertex
     */
    public void depthFirstSearchIterative(T x) {
        resetMarked(); // sets all vertex markers to false
        LinkedList<T> connections;
        int i;
        T vertex, nextNeighbor;
        Stack<T> s = new Stack();
        s.push(x); // add the starting vertex to the stack
        while (!s.isEmpty()) {
            // remove the current vertex from the stack
            vertex = s.pop();
            System.out.println(vertex);
            // get all neighbors of the current vertex
            connections = vertexes.get(vertex).getNeighbors();
            // add the neighbors of the current vertex to the stack if they have not been visited yet
            for (i = 0; i < connections.size(); i++) {
                nextNeighbor = connections.get(i);
                if (!vertexes.get(nextNeighbor).isMarked()) {
                    vertexes.get(nextNeighbor).setMarked(true);
                    s.push(nextNeighbor);
                }
            }
        }
    }

    /**
     * Used for testing through creating the string formatted version of the graph
     * @return the string formatted version of the graph
     */
    public String toString(){
        final String[] output = {""};
        vertexes.forEach((k, v) -> {
            for(int i = 0; i < v.getNeighbors().size(); i++){
                output[0] += (String)k + "->" + (String)v.getNeighbors().get(i) + "\n";
            }
        });
        return output[0];
    }

    public static void main(String[] args) {

    }
}