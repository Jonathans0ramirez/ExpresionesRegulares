package Modelos;

/**
 *
 * @author PC
 */
public class Node {

    protected String transNext, transPrev;
    protected int data;
    protected Node up, down, prevUp, prevDown;

    /* Constructor */
    public Node() {
        up = null;
        down = null;
        prevUp = null;
        prevDown = null;
        data = 0;
        transNext = null;
        transPrev = null;
    }

    /* Constructor */
    public Node(int v, Node u, Node d, Node pu, Node pd, String tn, String tp) {
        data = v;
        up = u;
        down = d;
        prevUp = pu;
        prevDown = pd;
        transNext = tn;
        transPrev = tp;
    }
    
    public boolean vacia() {
        return this == null && up == null && down == null;
    }
    
    public Node buscarNext(Node x) {
        if (vacia()) {
            return null;
        }
        if (this.equals(x)) {
            return this;
        }
        up.buscarNext(x);
        return this;
    }
    
    public Node buscarPrev(Node x) {
        if (vacia()) {
            return null;
        }
        if (this.equals(x)) {
            return this;
        }
        up.buscarPrev(x);
        return this;
    }

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

    public String getTransNext() {
        return transNext;
    }

    public void setTransNext(String transNext) {
        this.transNext = transNext;
    }

    public String getTransPrev() {
        return transPrev;
    }

    public void setTransPrev(String transPrev) {
        this.transPrev = transPrev;
    }
}