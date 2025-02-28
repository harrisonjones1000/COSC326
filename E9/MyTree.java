package E9;

public class MyTree {
    private int target;
    private int[] sequence;
    private boolean normal; //true is normal, false is left to right
    private int height;
    private Node root = new Node("", null, 0);

    public MyTree(int target, int[] sequence, boolean normal){
        this.target = target;
        this.sequence=sequence;
        this.normal=normal;
        this.height=sequence.length;
    }

    public class Node{
        int value;
        String sequence;
        Node parent;
        int level;
    
        Node(String sequence, Node parent, int level){
            this.sequence = sequence;
            this.parent=parent;
            this.level=level;
        }
    }
}
