package SmartHome;

public class NewLinkedList {
    Node headPtr;

    class Node {
        Node next;
        Node prev;
        Object data;        // Choose any datatype

        Node(Object data) {
            this.data = data;
            prev = null;
            next = null;
        }

        public Node getNext() { return next;}
        public Node getPrev(){return prev;}
        public Object getData() { return data;}

        public void setNext(Node next) { this.next = next; }
        public void setPrev(Node prev){this.prev=prev;}
        public void setData(Object data) { this.data = data;}
    }

    public NewLinkedList() {
        headPtr = null;
    }

    public void traverse() {
        Node p = headPtr;

        while (p != null) {
//
            p = p.getNext();
        }
    }

    public void insertFirst(Object data) {
        Node newNode = new Node(data);
        newNode.setNext(headPtr);
        headPtr.setPrev(newNode);
//        DOUBLE LINKED LIST
        headPtr = newNode;
    }

    public void insertLast(Object data) {
        Node newNode = new Node(data);
        if(headPtr == null) { headPtr = newNode; }
        else {
            Node p = headPtr;
            while (p.getNext() != null) {p = p.getNext(); }
            p.setNext(newNode);
            newNode.setPrev(p);
//            DOUBLE LINKED LIST
        }
    }

    public void deleteFirst() {
        if(headPtr != null) headPtr = headPtr.getNext();
    }

    public void deleteLast() {
        if(headPtr == null) return;
        if(headPtr.getNext() == null) {
            headPtr = null;
            return;
        }
        Node p = headPtr;
        while (p.getNext().getNext() != null) p = p.getNext();
        p.setNext(null);
    }
}
