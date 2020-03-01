import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

class RemoveDuplicates {

    /**
     * Removes the repeated values in the given LinkedList through
     * iterating through the list, adding the value at that index to
     * a HashMash if it is not a repeat, and removing it if it is
     * ListIterator is used to avoid the O(n) remove() function
     * @param data the LinkedList being checked for doubles
     */
    public static void removeDuplicates(LinkedList<Integer> data) {
        HashMap<Integer, Integer> hash = new HashMap<>();
        hash.put(data.get(0), data.get(0));
        ListIterator<Integer> iter = data.listIterator(1);
        while (iter.hasNext()){
            int temp = iter.next();
            if(hash.containsKey(temp)){
                iter.remove();
            } else {
                hash.put(temp, temp);
            }
        }
    }

    public static void main(String[] args) {
        // EXAMPLE TEST
        LinkedList<Integer> list = new LinkedList<>();
        list.add(6);
        list.add(8);
        list.add(3);
        list.add(5);
        list.add(7);
        list.add(32);
        list.add(6);
        list.add(8);
        list.add(3);
        list.add(2);
        list.add(3);
        list.add(6);
        list.add(1);
        list.add(32);
        list.add(32);
        removeDuplicates(list);
        // arr is now {6, 8, 3, 5, 7, 32, 2, 1}
        for (int x : list)
            System.out.println(x);
    }
}