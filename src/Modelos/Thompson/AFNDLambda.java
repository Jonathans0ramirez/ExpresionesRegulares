package Modelos.Thompson;

import java.util.Stack;

/**
 *
 * @author Jonathan Ramírez
 */
public class AFNDLambda {
    private NodeLambda start;
    private NodeLambda end;
    public int size;
    final String lambda = "λ";

    /* Constructor */
    public AFNDLambda() {
        NodeLambda inicio = new NodeLambda(1, null, null, null, null, null, null);
        start = inicio;
        end = start;
        size = 1;
    }
    
    public AFNDLambda(String r, String ope) {
        NodeLambda inicio = new NodeLambda(1, null, null, null, null, null, null);
        NodeLambda fin = new NodeLambda(0, null, null, null, null, null, null);
        NodeLambda n = new NodeLambda(0, null, null, null, null, null, null);
        NodeLambda q = new NodeLambda(0, null, null, null, null, null, null);        
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
    
    public AFNDLambda(String r, String s, String ope) {
        NodeLambda inicio = new NodeLambda(1, null, null, null, null, null, null); 
        NodeLambda fin = new NodeLambda(0, null, null, null, null, null, null);        
        NodeLambda n = new NodeLambda(0, null, null, null, null, null, null);
        NodeLambda q = new NodeLambda(0, null, null, null, null, null, null);      
        NodeLambda w = new NodeLambda(0, null, null, null, null, null, null);
        NodeLambda e = new NodeLambda(0, null, null, null, null, null, null);
        NodeLambda t = new NodeLambda(0, null, null, null, null, null, null);
        switch (ope){
            case "r.s":
                fin.setData(3);
                fin.setLinkPrevUp(n);
                n.setData(2);
                n.setLinkUp(fin);
                n.setLinkPrevUp(inicio);
                n.setTransUp(s);
                inicio.setLinkUp(n);
                inicio.setTransUp(r);
                start = inicio;
                end = fin;
                size = 3;
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
    
    public void addAtPos(AFNDLambda e, NodeLambda pos) {
        NodeLambda estr = e.getStart();
        NodeLambda efn = e.getEnd(); 
        NodeLambda prevUpPos;
        NodeLambda nextUpPos;
        NodeLambda prevDownPos; 
        NodeLambda nextDownPos;
        if (start == pos) {                                                                    //insert at start
            start = estr;
            prevUpPos = efn.getLinkPrevUp();
            prevUpPos.setLinkUp(pos);
            pos.setLinkPrevUp(prevUpPos);
            if (efn.getLinkPrevDown() != null) {
                nextDownPos = efn.getLinkPrevDown();
                nextDownPos.setLinkDown(pos);
                pos.setLinkPrevDown(nextDownPos);
            }
        } else if (!pos.isTheEnd() && e.getStart().isUnion() && pos.isPlus()){                  //r|s in r* or r⁺
            prevUpPos = pos.getLinkPrevUp();
            prevDownPos = pos.getLinkPrevDown();
            NodeLambda p = efn.getLinkPrevUp().getLinkPrevUp();
            NodeLambda q = efn.getLinkPrevUp().getLinkPrevDown();
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
        }  else if (!pos.isTheEnd() && pos.isPlus()){                                            //any in r* or r⁺
            prevUpPos = pos.getLinkPrevUp();
            efn.setLinkDown(estr);
            efn.setTransDown(lambda);
            estr.setLinkPrevDown(efn);
            if (pos.getLinkPrevDown() != null) { 
                prevDownPos = pos.getLinkPrevDown().getLinkUp();
                efn.setLinkUp(prevDownPos);
                efn.setTransUp(lambda);
                prevDownPos.setLinkPrevUp(efn);
            }
            prevUpPos.setLinkUp(estr);          
            estr.setLinkPrevUp(prevUpPos);
        } else if (!pos.isTheEnd()){                                                            //any in any
            prevUpPos = pos.getLinkPrevUp();
            nextUpPos = pos.getLinkUp();
            NodeLambda p;
            if (prevUpPos.getLinkUp() == pos) {
                prevUpPos.setLinkUp(estr);          
            } else if (prevUpPos.getLinkDown() == pos) {
                prevUpPos.setLinkDown(estr);               
            }
            estr.setLinkPrevUp(prevUpPos);
            if (pos.getLinkPrevDown() != null) { 
                prevDownPos = pos.getLinkPrevDown();
                prevDownPos.setLinkDown(estr);                
                prevDownPos.setLinkPrevDown(estr);
            }
            p = efn.getLinkPrevUp();                     
            if (estr.isStar()) {
                estr.setLinkDown(nextUpPos);
                nextUpPos.setLinkPrevDown(estr);
            }
            p.setLinkUp(nextUpPos);
            nextUpPos.setLinkPrevUp(p);  
            if (efn.getLinkPrevDown() != null) {
                NodeLambda x = efn.getLinkPrevDown();
                x.setLinkDown(nextUpPos);
                nextUpPos.setLinkPrevDown(x);              
            }
        } else if (pos.isTheEnd()){                                                             //insert at the End
            if (this.end.getLinkPrevDown() == null) {
                this.end.setLinkUp(e.getStart());
                this.end.setTransUp(lambda);
                e.getStart().setLinkPrevUp(this.end);               
            } else {
                NodeLambda p = this.end.getLinkPrevDown();
                NodeLambda q = this.end.getLinkPrevUp();
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
        NodeLambda init = start;
        NodeLambda p = init;
        NodeLambda z = null;
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
                z = (NodeLambda) nodes.pop();
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
    
    /*Function to search the node to replace*/
    public NodeLambda findNode () {
        Stack nodes = new Stack();
        NodeLambda str = this.getStart();
        NodeLambda p = str;
        NodeLambda z;
        while (!p.isTheEnd()) {
            if (p.getTransUp().equals("r") || (p.getTransUp().equals("s"))) {
                return p;
            }
            if (p.getLinkDown() != null && p.isUnion()) {
                nodes.push(p);
            }
            if (!nodes.empty() && p.getLinkUp().isTheEnd()) {
                z = (NodeLambda) nodes.pop();
                p = z.getLinkDown();
            } else {
                p = p.getLinkUp();
            }
        }
        return null;
    }

    public NodeLambda getStart() {
        return start;
    }

    public void setStart(NodeLambda start) {
        this.start = start;
    }

    public NodeLambda getEnd() {
        return end;
    }

    public void setEnd(NodeLambda end) {
        this.end = end;
    }
}
