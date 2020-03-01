import java.util.LinkedList;

class Vertex<T> {
    private T label;
    private LinkedList<T> neighbors;
    private boolean marked;

    public Vertex(T label) {
        setLabel(label);
        setNeighbors(new LinkedList<T>());
        marked = false;
    }

    public T getLabel() {
        return label;
    }

    public void setLabel(T label) {
        this.label = label;
    }

    public LinkedList<T> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(LinkedList<T> neighbors) {
        this.neighbors = neighbors;
    }

    public boolean isMarked(){
        return marked;
    }

    public void setMarked(boolean b){
        marked = b;
    }

}