/**
 * @author molax
 * @date 2023/2/10 12:54
 */
public class Question {

    public static void main(String[] args) {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        nodeA.next = nodeB;
        nodeB.next = new Node("C");
        print(nodeA);
        print(reverse(nodeA));
    }

    public static Node reverse(Node node){
        if(node == null || node.next == null){
            return node;
        }
        Node reverse = reverse(node.next);
        node.next.next = node;
        node.next = null;
        return reverse;
    }

    public static void print(Node node){
        while(node != null){
            System.out.print(" "+node.data);
            node = node.next;
        }
        System.out.println("");
    }
}

class Node{
    final String data;

    Node next;

    public Node(String data){
        this.data = data;
    }
}