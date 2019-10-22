package LinkedList;


/* SList.java */

public class SList {

    private SListNode head;
    private SListNode tail;
    private int size;


    public Object getHead() {
        return head.item;
    }


    public Object getTail() {
        return tail.item;
    }

    public SList() {
        size = 0;
        head = null;
        tail = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int length() {
        return size;
    }


    public void insertFront(Object obj) {
        head = new SListNode(obj, head);
        if (tail == null) {
            tail = head;
        }
        size++;
    }

    public void insertEnd(Object obj) {
        SListNode node = new SListNode(obj);
        if (head == null) {
            head = node;
            tail = head;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public void removeFront() {
        if (size == 0 || size == 1) {
            head = null;
            tail = null;
            size = 0;
        }
        else{
            head = head.next;
            size--;
        }
    }
}