class TreeElement<K extends Comparable<K>, V> {
    private K key;
    private V value;
    private int treeLocation;

    public TreeElement(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public TreeElement(K key, V value, int treeLocation) {
        this.key = key;
        this.value = value;
        this.treeLocation = treeLocation;
    }

    @Override
    public String toString() {
        return "TreeElement [key=" + key + ", treeLocation=" + treeLocation + ", value=" + value + "]";
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public int getTreeLocation() {
        return treeLocation;
    }

    public void setTreeLocation(int treeLocation) {
        this.treeLocation = treeLocation;
    }
}