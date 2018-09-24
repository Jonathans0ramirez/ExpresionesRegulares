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
        Node inicio = new Node(1, null, null, null, null, null, null);
        start = inicio;
        end = start;
        size = 1;
    }
    
    public linkedList(int value, String r, String ope) {
        Node inicio = new Node(1, null, null, null, null, null, null);
        Node fin = new Node(0, null, null, null, null, null, null);
        Node n = new Node(0, null, null, null, null, null, null);
        Node q = new Node(0, null, null, null, null, null, null);        
        switch (ope){
            case "r":
                inicio.setTransNext(r);
                inicio.setLinkUp(fin);
                fin.setData(2);
                fin.setLinkPrevUp(inicio);
                start = inicio;
                end = fin;
                size = 2;
                break;            
            case "r*":
                fin.setData(4);
                fin.setLinkPrevUp(n);
                fin.setLinkPrevDown(inicio);
                n.setData(3);
                n.setLinkUp(fin);
                n.setLinkPrevUp(q);
                n.setLinkDown(q);
                n.setTransNext(lambda);
                n.setTransPrev(lambda);
                q.setData(2);
                q.setLinkUp(n);
                q.setLinkPrevUp(inicio);
                q.setLinkPrevDown(n);
                q.setTransNext(r);
                inicio.setLinkUp(q);
                inicio.setLinkDown(fin);
                inicio.setTransNext(lambda);
                inicio.setTransPrev(lambda);
                start = inicio;
                end = fin;
                size = 4;
                break;
            case "r+":
                fin.setData(4);
                fin.setLinkPrevUp(n);
                n.setData(3);
                n.setLinkDown(q);
                n.setLinkUp(fin);
                n.setLinkPrevUp(q);
                n.setTransNext(lambda);
                n.setTransPrev(lambda); 
                q.setData(2);
                q.setLinkUp(n);
                q.setLinkPrevUp(inicio);
                q.setLinkPrevDown(n);
                q.setTransNext(r);
                inicio.setLinkUp(q);
                inicio.setTransNext(lambda);
                start = inicio;
                end = fin;
                size = 4;
                break;
        }
    }
    
    public linkedList(int value, String r, String s, String ope) {
        Node inicio = new Node(1, null, null, null, null, null, null); 
        Node fin = new Node(0, null, null, null, null, null, null);        
        Node n = new Node(0, null, null, null, null, null, null);
        Node q = new Node(0, null, null, null, null, null, null);      
        Node w = new Node(0, null, null, null, null, null, null);
        Node e = new Node(0, null, null, null, null, null, null);
        Node t = new Node(0, null, null, null, null, null, null);
        switch (ope){
            case "r.s":
                fin.setData(4);
                fin.setLinkPrevUp(n);
                n.setData(3);
                n.setLinkUp(fin);
                n.setLinkPrevUp(q);
                n.setTransNext(s);
                q.setData(2);
                q.setLinkUp(n);
                q.setLinkPrevUp(inicio);
                q.setTransNext(lambda);
                inicio.setLinkUp(q);
                inicio.setTransNext(r);
                start = inicio;
                end = fin;
                size = 4;
                break;
            case "r|s":
                fin.setData(7);
                fin.setLinkPrevUp(w);
                w.setData(6);
                w.setLinkUp(fin);
                w.setLinkPrevUp(t);
                w.setLinkPrevDown(e);
                w.setTransNext(lambda);
                e.setData(5);
                e.setLinkDown(w);
                e.setLinkPrevDown(n);
                e.setTransPrev(lambda);
                t.setData(3);
                t.setLinkUp(w);
                t.setLinkPrevUp(q);
                t.setTransNext(lambda);
                n.setData(4);
                n.setLinkDown(e);
                n.setLinkPrevDown(inicio);
                n.setTransPrev(s);
                q.setData(2);
                q.setLinkUp(t);
                q.setLinkPrevUp(inicio);
                q.setTransNext(r);
                inicio.setLinkUp(q);
                inicio.setLinkDown(n);
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
        Node nptr = new Node(val, null, null, null, null, tn, tp);
        if (start == null) {
            start = nptr;
            end = start;
        } else {
            nptr.setLinkPrevUp(end);
            end.setLinkUp(nptr);
            end = nptr;
        }
        size++;
    }

    /* Function to insert element at position */
    public void insertAtPos(int val, int pos, String tn, String tp) {
        Node nptr = new Node(val, null, null, null, null, null, null);
        if (pos == 1) {
            insertAtEnd(val, tn, tp);
            return;
        }
        Node ptr = start;
        for (int i = 2; i <= size; i++) {
            if (i == pos) {
                Node tmp = ptr.getLinkUp();
                ptr.setLinkUp(nptr);
                nptr.setLinkDown(ptr);
                nptr.setLinkUp(tmp);
                tmp.setLinkDown(nptr);
            }
            ptr = ptr.getLinkUp();
        }
        size++;
    }
    
    public void addAtPos(linkedList e, Node pos) {
        Node estr = e.getStart();
        Node efn = e.getEnd();        
        if (start == null) {
            start = estr;
            end = efn;
            
        } else if (pos.getData() != this.end.getData()){                        //r/s in r*
            Node prevUpPos = pos.getLinkPrevUp();
            Node prevDownPos = pos.getLinkPrevDown();
            Node p = efn.getLinkPrevUp().getLinkPrevUp();
            Node q = efn.getLinkPrevUp().getLinkPrevDown();
            prevDownPos.setLinkDown(estr);
            estr.setLinkPrevDown(prevDownPos);            
            prevUpPos.setLinkUp(estr);
            estr.setLinkPrevUp(prevUpPos);
            pos.setLinkUp(null);
            p.setLinkUp(prevDownPos);
            prevDownPos.setLinkPrevUp(p);
            p = p.getLinkUp();
            q.setLinkDown(p);
            p.setLinkPrevDown(q);
            p = p.getLinkUp();
            p.setLinkPrevDown(prevUpPos);         
        }
        size+= e.getSize() - 3;
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
            start = start.getLinkUp();
            start.setLinkDown(null);
            size--;
            return;
        }
        if (pos == size) {
            end.setLinkUp(null);
            size--;
        }*/
        Node ptr = start.getLinkUp();
        Node aux = ptr;
        
        for (int i = 1; i <= size; i++){
            if(aux.getLinkUp()==end){
                
            }
            if(aux.getLinkDown()==end){
                
            }
        }
        Node p = ptr.getLinkUp();
        for (int i = 2; i <= size; i++) {
            if (i == pos) {
                p = ptr.getLinkDown();
                Node n = ptr.getLinkUp();
                p.setLinkUp(n);
                n.setLinkDown(p);
                size--;
                return;
            }
            ptr = ptr.getLinkUp();
        }
    }

    /* Function to display status of list */
    public void display() {
        System.out.print("\nDoubly Linked List = ");
        if (size == 0) {
            System.out.print("empty\n");
            return;
        }
        if (start.getLinkUp() == null) {
            System.out.println(start.getData());
            return;
        }
        Node ptr = start;
        System.out.print(start.getData() + " <-> ");
        ptr = start.getLinkUp();
        while (ptr.getLinkUp() != null) {
            System.out.print(ptr.getData() + " <-> ");
            ptr = ptr.getLinkUp();
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
