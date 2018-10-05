package Modelos.Thompson;

import java.util.ArrayList;

/**
 *
 * @author Jonathan Ramirez
 */
public class NodeThompson {
    protected int transZero;
    protected int transOne;    
    protected ArrayList nodesLambda;
    protected NodeThompson zero;
    protected NodeThompson one;
    protected String estado;

    public NodeThompson(int transZero, int transOne, ArrayList nodesLambda, NodeThompson zero, NodeThompson one, String estado) {
        this.transZero = transZero;
        this.transOne = transOne;
        this.nodesLambda = nodesLambda;
        this.zero = zero;
        this.one = one;
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getTransZero() {
        return transZero;
    }

    public void setTransZero(int transZero) {
        this.transZero = transZero;
    }

    public int getTransOne() {
        return transOne;
    }

    public void setTransOne(int transOne) {
        this.transOne = transOne;
    }

    public ArrayList getNodesLambda() {
        return nodesLambda;
    }

    public void setNodesLambda(ArrayList nodesLambda) {
        this.nodesLambda = nodesLambda;
    }

    public NodeThompson getLinkZero() {
        return zero;
    }

    public void setLinkZero(NodeThompson zero) {
        this.zero = zero;
    }

    public NodeThompson getLinkOne() {
        return one;
    }

    public void setLinkOne(NodeThompson one) {
        this.one = one;
    }  
}
