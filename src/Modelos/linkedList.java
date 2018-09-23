/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

/**
 *
 * @author PC
 */
public class linkedList {
    private Node start;
    private Node end;
    public int size;
    final String lambda = "λ";

    /* Constructor */
    public linkedList() {
        Node inicio = new Node(0, null, null, null, null);
        start = inicio;
        end = start;
        size = 1;
    }
    
    public linkedList(int value, String r, String ope) {
        Node inicio = new Node(0, null, null, null, null);
        Node fin = new Node(0, null, null, null, null);
        Node n = new Node(0, null, null, null, null);
        Node q = new Node(0, null, null, null, null);        
        switch (ope){
            case "r":
                inicio.setTransNext(r);
                start = inicio;
                end = fin;
                size = 2;
                break;            
            case "r*":
                n.setLinkNext(fin);
                n.setLinkPrev(q);
                n.setTransNext(lambda);
                n.setTransPrev(lambda);
                q.setLinkNext(n);
                q.setTransNext(r);
                inicio.setLinkNext(q);
                inicio.setLinkPrev(fin);
                inicio.setTransNext(lambda);
                inicio.setTransPrev(lambda);
                start = inicio;
                end = fin;
                size = 4;
                break;
            case "r+":
                n.setLinkPrev(q);
                n.setLinkNext(fin);
                n.setTransNext(lambda);
                n.setTransPrev(lambda);                
                q.setLinkNext(n);
                q.setTransNext(r);
                inicio.setLinkNext(q);
                inicio.setTransNext(lambda);
                start = inicio;
                end = fin;
                size = 4;
                break;
        }
    }
    
    public linkedList(int value, String r, String s, String ope) {
        Node inicio = new Node(0, null, null, null, null); 
        Node fin = new Node(0, null, null, null, null);        
        Node n = new Node(0, null, null, null, null);
        Node q = new Node(0, null, null, null, null);      
        Node w = new Node(0, null, null, null, null);
        Node e = new Node(0, null, null, null, null);
        Node t = new Node(0, null, null, null, null);
        switch (ope){
            case "r.s":
                n.setLinkNext(fin);
                n.setTransNext(s);
                q.setLinkNext(n);
                q.setTransNext(lambda);
                inicio.setLinkNext(q);
                inicio.setTransNext(r);
                start = inicio;
                end = fin;
                size = 4;
                break;
            case "r|s":
                w.setTransNext(lambda);
                e.setLinkPrev(w);
                e.setTransPrev(lambda);
                t.setLinkNext(w);
                t.setTransNext(lambda);
                n.setLinkPrev(e);
                n.setTransPrev(s);
                q.setLinkNext(t);
                q.setTransNext(r);
                inicio.setLinkNext(q);
                inicio.setLinkPrev(n);
                inicio.setTransNext(lambda);
                inicio.setTransPrev(lambda);
                start = inicio;
                end = fin;
                size = 7;
                break;
        }
    }

    /* Function to check if list is empty */
    public boolean isEmpty() {
        return start == null;
    }

    /* Function to get size of list */
    public int getSize() {
        return size;
    }

    /* Function to insert element at end */
    public void insertAtEnd(int val, String tn, String tp) {
        Node nptr = new Node(val, null, null, tn, tp);
        if (start == null) {
            start = nptr;
            end = start;
        } else {
            nptr.setLinkPrev(end);
            end.setLinkNext(nptr);
            end = nptr;
        }
        size++;
    }

    /* Function to insert element at position */
    public void insertAtPos(int val, int pos, String tn, String tp) {
        Node nptr = new Node(val, null, null, null, null);
        if (pos == 1) {
            insertAtEnd(val, tn, tp);
            return;
        }
        Node ptr = start;
        for (int i = 2; i <= size; i++) {
            if (i == pos) {
                Node tmp = ptr.getLinkNext();
                ptr.setLinkNext(nptr);
                nptr.setLinkPrev(ptr);
                nptr.setLinkNext(tmp);
                tmp.setLinkPrev(nptr);
            }
            ptr = ptr.getLinkNext();
        }
        size++;
    }
    
    public void addAll(linkedList e) {
        Node estr = e.getStart();
        Node efn = e.getEnd();        
        if (start == null) {
            start = estr;
            end = efn;
        } else {
            end.setLinkNext(estr);
            end.setTransNext(lambda);
            deleteAtPos(size);
            end = efn;
        }
        size+= e.getSize();
    }

    /* Function to delete node at position */
    public void deleteAtPos(int pos) {
        /*if (pos == 1) {
            if (size == 1) {
                start = null;
                end = null;
                size = 0;
                return;
            }
            start = start.getLinkNext();
            start.setLinkPrev(null);
            size--;
            return;
        }
        if (pos == size) {
            end.setLinkNext(null);
            size--;
        }*/
        Node ptr = start.getLinkNext();
        Node aux = ptr;
        
        for (int i = 1; i <= size; i++){
            if(aux.getLinkNext()==end){
                
            }
            if(aux.getLinkPrev()==end){
                
            }
        }
        Node p = ptr.getLinkNext();
        for (int i = 2; i <= size; i++) {
            if (i == pos) {
                p = ptr.getLinkPrev();
                Node n = ptr.getLinkNext();
                p.setLinkNext(n);
                n.setLinkPrev(p);
                size--;
                return;
            }
            ptr = ptr.getLinkNext();
        }
    }

    /* Function to display status of list */
    public void display() {
        System.out.print("\nDoubly Linked List = ");
        if (size == 0) {
            System.out.print("empty\n");
            return;
        }
        if (start.getLinkNext() == null) {
            System.out.println(start.getData());
            return;
        }
        Node ptr = start;
        System.out.print(start.getData() + " <-> ");
        ptr = start.getLinkNext();
        while (ptr.getLinkNext() != null) {
            System.out.print(ptr.getData() + " <-> ");
            ptr = ptr.getLinkNext();
        }
        System.out.print(ptr.getData() + "\n");
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public Node getEnd() {
        return end;
    }

    public void setEnd(Node end) {
        this.end = end;
    }
}
