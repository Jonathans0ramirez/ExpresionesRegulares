package Modelos;

/**
 *
 * @author PC
 */
public class Node {

    protected String transNext, transPrev;
    protected int data;
    protected Node next, prev;

    /* Constructor */
    public Node() {
        next = null;
        prev = null;
        data = 0;
        transNext = null;
        transPrev = null;
    }

    /* Constructor */
    public Node(int d, Node n, Node p, String tn, String tp) {
        data = d;
        next = n;
        prev = p;
        transNext = tn;
        transPrev = tp;
    }
    
    public boolean vacia() {
        return this == null && next == null && prev == null;
    }
    
    public Node buscarNext(Node x) {
        if (vacia()) {
            return null;
        }
        if (this.equals(x)) {
            return this;
        }
        next.buscarNext(x);
        return this;
    }
    
    public Node buscarPrev(Node x) {
        if (vacia()) {
            return null;
        }
        if (this.equals(x)) {
            return this;
        }
        next.buscarPrev(x);
        return this;
    }

    /* Function to set link to next node */
    public void setLinkNext(Node n) {
        next = n;
    }

    /* Function to set link to previous node */
    public void setLinkPrev(Node p) {
        prev = p;
    }

    /* Funtion to get link to next node */
    public Node getLinkNext() {
        return next;
    }

    /* Function to get link to previous node */
    public Node getLinkPrev() {
        return prev;
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