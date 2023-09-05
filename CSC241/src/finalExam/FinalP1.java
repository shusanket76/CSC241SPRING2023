package finalExam;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class FinalP1 {

    public static void main(String[] args ) {
        Queue<String> queue = new LinkedList<String>();
        queue.offer("10");
        queue.offer("20");
        queue.offer("30");
        queue.offer("40");
        System.out.println(queue.poll());
        System.out.println(queue);
    }
}