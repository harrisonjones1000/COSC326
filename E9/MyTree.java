package E9;

public class MyTree {
    private int target;
    private int[] sequence; // the order of the numbers
    private boolean normal; // true is normal, false is left to right
    private int height; // length of sequence
    private Node root;

    public MyTree(int target, int[] sequence, boolean normal){
        this.target = target;
        this.sequence = sequence;
        this.normal = normal;
        this.height = sequence.length - 1;
        this.root = new Node(new boolean[height], null, 0);
    }

    public boolean[] search(){
        boolean[] order = root.order;

        if(!normal) root.value=sequence[0];

        order[0]=true;
        boolean[] left_subtree = search(new Node(order, root, 1), true);
        if(left_subtree!=null) return left_subtree;
        
        order[0]=false;
        boolean[] right_subtree = search(new Node(order, root, 1), false);
        if(right_subtree!=null) return right_subtree;
        return null;    
    }

    private boolean[] search(Node n, boolean left){
        if(!normal){ //if left to right
            if(left){
                n.value = n.parent.value + sequence[n.level]; //if plus
            }else{
                n.value = n.parent.value * sequence[n.level]; //if multiply
            }

        }else if(n.level==height){ //if normal and leaf node, calc value
            n.value = normalValue(n.order);
        }

        if(n.level==height){ //if leaf node
            if(n.value==target){
                return n.order; //leaf node order produces target
            }else{
                return null; //lead node not target
            }
        }else{ //non leaf node, continue search
            boolean[] order = n.order;
            
            //left subtree search
            order[n.level]=true;
            boolean[] result_l = search(new Node(order, n, n.level+1), true);
            if(result_l!=null) return result_l;
            
            //right subtree
            order[n.level]=false; 
            boolean[] result_r = search(new Node(order, n, n.level+1), false); 
            if(result_r!=null) return result_r;

            return null; //if both subtrees dont produce target, return null
        }
    }

    private int normalValue(boolean[] order){
        int current_consec=0;
        int value=0;

        for(int i=0; i<order.length; i++){
            if(order[i]){ //plus
                if(current_consec!=0){ //last operation was multiply
                    current_consec=current_consec*sequence[i];
                    value+=current_consec;
                    current_consec=0;
                }else{
                    value+=sequence[i];
                }
            }else{ //multiply
                if(current_consec==0){
                    current_consec=sequence[i];
                }else{
                    current_consec=current_consec*sequence[i];
                }
            }
        }

        if(order[order.length-1]){ //final plus
            if(current_consec!=0){ //second last operation was multiplication
                current_consec=current_consec*sequence[order.length-1];
                value+=current_consec;
            }
                value+=sequence[order.length];

        }else{ //final multiply
            current_consec=current_consec*sequence[order.length];
            value+=current_consec;
        }

        return value;
    }

    public static class Node{
        int value;
        boolean[] order; //the 
        Node parent;
        int level;
    
        Node(boolean[] order, Node parent, int level){
            this.order = order;
            this.parent=parent;
            this.level=level;
        }
    }
}
