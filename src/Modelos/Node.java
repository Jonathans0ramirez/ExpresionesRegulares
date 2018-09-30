package Modelos;

/**
 *
 * @author PC
 */
public class Node {

    protected String transUp, transDown;
    protected int data;
    protected Node up, down, prevUp, prevDown;

    /* Constructor */
    public Node() {
        up = null;
        down = null;
        prevUp = null;
        prevDown = null;
        data = 0;
        transUp = null;
        transDown = null;
    }

    /* Constructor */
    public Node(int v, Node u, Node d, Node pu, Node pd, String tn, String tp) {
        data = v;
        up = u;
        down = d;
        prevUp = pu;
        prevDown = pd;
        transUp = tn;
        transDown = tp;
    }
    
    public boolean isTheEnd() {
        return up == null && down == null;
    }
    
    public boolean isUnion(){
        Node p = this; 
        Node q = p.getLinkDown();
        boolean res = false;
        while (!p.isTheEnd()){
            if (p == q){
                return false;
            }
            p = p.getLinkUp();
        }
        res = p != q;
        if (res == false) return false;
        p = this.getLinkUp();
        while (!q.isTheEnd()){
            if (q == p){
                return false;
            }
            q = q.getLinkUp();
        }
        res = p != q;
        return res;
    }
    
    public Node getUnionNode() {
        Node p = this.getLinkUp().getLinkUp().getLinkUp();
        Node q = this.getLinkDown().getLinkUp().getLinkUp();
        while (!q.isTheEnd()) {
            if (q == p) {
                return q;
            }
            q = q.getLinkUp();
        }
        return null;
    }
    
//    public Node buscarNext(Node x) {
//        if (vacia()) {
//            return null;
//        }
//        if (this.equals(x)) {
//            return this;
//        }
//        up.buscarNext(x);
//        return this;
//    }
//    
//    public Node buscarPrev(Node x) {
//        if (vacia()) {
//            return null;
//        }
//        if (this.equals(x)) {
//            return this;
//        }
//        up.buscarPrev(x);
//        return this;
//    }

    /* Function to set link to up node */
    public void setLinkUp(Node u) {
        up = u;
    }

    /* Function to set link to down node */
    public void setLinkDown(Node d) {
        down = d;
    }
    
    /* Function to set link to prevUp node */
    public void setLinkPrevUp(Node pu) {
        prevUp = pu;
    }
    
    /* Function to set link to prevDown node */
    public void setLinkPrevDown(Node pd) {
        prevDown = pd;
    }

    /* Funtion to get link to prevDown node */
    public Node getLinkPrevDown() {
        return prevDown;
    }

    /* Funtion to get link to prevUp node */
    public Node getLinkPrevUp() {
        return prevUp;
    }

    /* Funtion to get link to up node */
    public Node getLinkUp() {
        return up;
    }

    /* Function to get link to down node */
    public Node getLinkDown() {
        return down;
    }

    /* Function to set data to node */
    public void setData(int d) {
        data = d;
    }

    /* Function to get data from node */
    public int getData() {
        return data;
    }

    public String getTransUp() {
        return transUp;
    }

    public void setTransUp(String transUp) {
        this.transUp = transUp;
    }

    public String getTransDown() {
        return transDown;
    }

    public void setTransDown(String transDown) {
        this.transDown = transDown;
    }
}