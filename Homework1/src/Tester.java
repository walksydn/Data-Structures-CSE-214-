public class Tester {

    public static void main(String[] args) {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        list.addTail(2);
        list.addTail(2);
        list.addTail(5);
        list.addTail(7);
        list.addTail(7);
        list.addTail(8);
        list.addTail(8);
        list.addTail(8);
        list.addTail(9);
        list.addTail(20);
        list.addTail(20);
        System.out.println(list.toString());

        list.removeDuplicates();
        System.out.println(list.toString());
    }
}
