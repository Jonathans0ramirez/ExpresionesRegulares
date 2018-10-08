package Modelos.Thompson;

import java.util.ArrayList;

/**
 *
 * @author Jonathan Ramirez
 */
public class NodeThompson {    
    protected ArrayList nodesLambda;
    protected NodeThompson zero;
    protected NodeThompson one;
    protected String estado;
    
    public NodeThompson (ArrayList nodesLambda) {
        this.nodesLambda = nodesLambda;
        this.zero = null;
        this.one = null;
        this.estado = "Rechazo";
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
    
    /*Función que retornará como una cadena de caracteres los datos del ArrayList nodesLambda */
    public String getDataNode () {
        String res = "";
        String coma = ", ";
        ArrayList nodes = nodesLambda;
        int len = nodes.size();
        NodeLambda[] aux= new NodeLambda[nodes.size()];
        aux = (NodeLambda[]) nodes.toArray(aux);
        int estado = 1;
        String pool;
        while (estado <= len) {
            pool = Integer.toString(aux[estado - 1].getData());
            if (!res.isEmpty()) {
                res = res.concat(coma);
            }
            res = res.concat(pool);
            estado++;
        }
        return res;
    }
}
