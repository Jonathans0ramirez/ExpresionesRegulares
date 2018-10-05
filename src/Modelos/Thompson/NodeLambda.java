package Modelos.Thompson;

/**
 *
 * @author Jonathan Ramírez
 */
public class NodeLambda {

    protected String transUp, transDown;
    protected int data;
    protected NodeLambda up, down, prevUp, prevDown;

    /* Constructor */
    public NodeLambda() {
        up = null;
        down = null;
        prevUp = null;
        prevDown = null;
        data = 0;
        transUp = null;
        transDown = null;
    }

    /* Constructor */
    public NodeLambda(int v, NodeLambda u, NodeLambda d, NodeLambda pu, NodeLambda pd, String tn, String tp) {
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
    
    public boolean isUnion() {
        NodeLambda p = this;
        if (p.getLinkDown() == null) { 
            return false;
        }
        NodeLambda q = p.getLinkDown();
        boolean res = false;
        while (!p.isTheEnd()){
            if (p == q){
                return false;
            }
            p = p.getLinkUp();
        }
        res = p != q;
        if (res == false) { 
            return false;
        }
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
    
    public NodeLambda getUnionNode() {
        NodeLambda p = this.getLinkUp();
        NodeLambda q = this.getLinkDown();
        NodeLambda s = q;
        while (!p.isTheEnd()) {
            while (!q.isTheEnd()) {
                if (q == p) {
                    return q;
                }
                q = q.getLinkUp();
            }
            if (q == p) {
                return q;
            }
            p = p.getLinkUp();
            q = s;
        }
        return null;
    }
    
    public boolean isPlus() {
        NodeLambda p = this; 
        if (p.getLinkPrevDown() == null) {
            return false;
        }
        NodeLambda q = p.getLinkPrevDown();
        while (!p.isTheEnd()){
            if (p == q){
                return true;
            }
            p = p.getLinkUp();
        }
        return false;
    }
    
    public boolean isStar() {
        NodeLambda p = this;
        if (p.getLinkDown() == null) {
            return false;
        }
        NodeLambda q = p.getLinkDown();
        while (!p.isTheEnd()) {
            if (p == q) {
                return true;
            }
            p = p.getLinkUp();
        }
        return p == q;
    }
    
//    public NodeLambda buscarNext(NodeLambda x) {
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
//    public NodeLambda buscarPrev(NodeLambda x) {
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
    public void setLinkUp(NodeLambda u) {
        up = u;
    }

    /* Function to set link to down node */
    public void setLinkDown(NodeLambda d) {
        down = d;
    }
    
    /* Function to set link to prevUp node */
    public void setLinkPrevUp(NodeLambda pu) {
        prevUp = pu;
    }
    
    /* Function to set link to prevDown node */
    public void setLinkPrevDown(NodeLambda pd) {
        prevDown = pd;
    }

    /* Funtion to get link to prevDown node */
    public NodeLambda getLinkPrevDown() {
        return prevDown;
    }

    /* Funtion to get link to prevUp node */
    public NodeLambda getLinkPrevUp() {
        return prevUp;
    }

    /* Funtion to get link to up node */
    public NodeLambda getLinkUp() {
        return up;
    }

    /* Function to get link to down node */
    public NodeLambda getLinkDown() {
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