/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.util.Stack;

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
                inicio.setTransUp(r);
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
                n.setTransUp(lambda);
                n.setTransDown(lambda);
                q.setData(2);
                q.setLinkUp(n);
                q.setLinkPrevUp(inicio);
                q.setLinkPrevDown(n);
                q.setTransUp(r);
                inicio.setLinkUp(q);
                inicio.setLinkDown(fin);
                inicio.setTransUp(lambda);
                inicio.setTransDown(lambda);
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
                n.setTransUp(lambda);
                n.setTransDown(lambda); 
                q.setData(2);
                q.setLinkUp(n);
                q.setLinkPrevUp(inicio);
                q.setLinkPrevDown(n);
                q.setTransUp(r);
                inicio.setLinkUp(q);
                inicio.setTransUp(lambda);
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
                n.setTransUp(s);
                q.setData(2);
                q.setLinkUp(n);
                q.setLinkPrevUp(inicio);
                q.setTransUp(lambda);
                inicio.setLinkUp(q);
                inicio.setTransUp(r);
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
                w.setTransUp(lambda);
                e.setData(5);
                e.setLinkUp(w);
                e.setLinkPrevUp(n);
                e.setTransUp(lambda);
                t.setData(3);
                t.setLinkUp(w);
                t.setLinkPrevUp(q);
                t.setTransUp(lambda);
                n.setData(4);
                n.setLinkUp(e);
                n.setLinkPrevUp(inicio);
                n.setTransUp(s);
                q.setData(2);
                q.setLinkUp(t);
                q.setLinkPrevUp(inicio);
                q.setTransUp(r);
                inicio.setLinkUp(q);
                inicio.setLinkDown(n);
                inicio.setTransUp(lambda);
                inicio.setTransDown(lambda);
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
    
    public void addAtPos(linkedList e, Node pos) {
        Node estr = e.getStart();
        Node efn = e.getEnd();        
        if (start == null) {
            start = estr;
            end = efn;       
        } else if (pos.getData() != this.end.getData() && e.getStart().isUnion()){                  //r|s in r* or r⁺
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
            p.setTransUp(lambda);
            prevDownPos.setLinkPrevUp(p);
            p = p.getLinkUp();
            q.setLinkUp(p);
            q.setTransUp(lambda);
            p.setLinkPrevDown(q);
            p = p.getLinkUp();
            p.setLinkPrevDown(prevUpPos);         
        } else if (pos.getData() != this.end.getData() && e.getStart().isStar()){                   //r* in any
            Node prevUpPos = pos.getLinkPrevUp();
            Node nextUpPos = pos.getLinkUp();
            Node p = efn.getLinkPrevUp();
            prevUpPos.setLinkUp(estr);
            estr.setLinkPrevUp(prevUpPos);
            estr.setLinkDown(nextUpPos);
            nextUpPos.setLinkPrevDown(estr);
            p.setLinkUp(nextUpPos);
            nextUpPos.setLinkPrevUp(p);
        } else if (pos.getData() != this.end.getData()){                                            //any in r* or r⁺
            Node prevUpPos = pos.getLinkPrevUp();
            efn.setLinkDown(estr);
            efn.setTransDown(lambda);
            estr.setLinkPrevDown(efn);
            if (pos.getLinkPrevDown() != null) { 
                Node prevDownPos = pos.getLinkPrevDown().getLinkUp();
                efn.setLinkUp(prevDownPos);
                efn.setTransUp(lambda);
                prevDownPos.setLinkPrevUp(efn);
            }
            prevUpPos.setLinkUp(estr);
            estr.setLinkPrevUp(prevUpPos);
        } else {        
            if (this.end.getLinkPrevDown() == null) {
                this.end.setLinkUp(e.getStart());
                this.end.setTransUp(lambda);
                e.getStart().setLinkPrevUp(this.end);               
            } else {
                Node p = this.end.getLinkPrevDown();
                Node q = this.end.getLinkPrevUp();
                p.setLinkDown(e.getStart());
                e.getStart().setLinkPrevDown(p);
                q.setLinkUp(e.getStart());
                e.getStart().setLinkPrevUp(q);
            }
        }    
    }
    
    /*Function to rename the id of Nodes*/
    public void asignId() {
        Stack nodes = new Stack();
        Node init = start;
        Node p = init;
        Node z = null;
        int value = 1;
        while (!p.isTheEnd()) {
            if (p.getLinkDown() != null) {
                if (p.isUnion()) {
                    nodes.push(p);
                    nodes.push(p.getUnionNode());
                }
            }
            if (!nodes.empty() && p.getLinkUp() == nodes.peek()) {
                nodes.pop();
                p.setData(value);
                value++;
                z = (Node) nodes.pop();
                p = z.getLinkDown();
            }
            p.setData(value);
            p = p.getLinkUp();
            value++;
        }
        p.setData(value);
        end = p;
        size = value;
    }
    
//    /* Function to delete node at position */
//    public void deleteAtPos(int pos) {
//        /*if (pos == 1) {
//            if (size == 1) {
//                start = null;
//                end = null;
//                size = 0;
//                return;
//            }
//            start = start.getLinkUp();
//            start.setLinkDown(null);
//            size--;
//            return;
//        }
//        if (pos == size) {
//            end.setLinkUp(null);
//            size--;
//        }*/
//        Node ptr = start.getLinkUp();
//        Node aux = ptr;
//        
//        for (int i = 1; i <= size; i++){
//            if(aux.getLinkUp()==end){
//                
//            }
//            if(aux.getLinkDown()==end){
//                
//            }
//        }
//        Node p = ptr.getLinkUp();
//        for (int i = 2; i <= size; i++) {
//            if (i == pos) {
//                p = ptr.getLinkDown();
//                Node n = ptr.getLinkUp();
//                p.setLinkUp(n);
//                n.setLinkDown(p);
//                size--;
//                return;
//            }
//            ptr = ptr.getLinkUp();
//        }
//    }

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
