package Modelos.Thompson;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Jonathan Ramírez
 */
public class Thompson { 
    protected AFNDLambda expresion;
    protected ArrayList<NodeLambda> cierreAlpha = new ArrayList<>();
    protected ArrayList<ArrayList> estados = new ArrayList<>();
    final String lambda = "λ";
    
    

    public Thompson(AFNDLambda e) {
        this.expresion = e;
    }
    
    public void crearAFD () {
        conjuntosAlpha();
        
    }
    
    public void conjuntosAlpha () {
        Stack nodes = new Stack();
        Stack lambdaAux = new Stack();
        NodeLambda start = expresion.getStart();
        NodeLambda p = start;
        NodeLambda q;
        NodeLambda z = null;
        int estado = 1;
        while (!p.isTheEnd() && estado <= expresion.getSize()) {
            cierreAlpha.add(start);
            q = p;
            while ((p.getTransUp().equals(lambda) || p.getTransDown().equals(lambda)) && !p.isTheEnd()) {
                if (p.getTransDown().equals(lambda)) {
                    lambdaAux.push(p);
                }
                p = p.getLinkUp();
                cierreAlpha.add(q);              
            }
            while (!cierreAlpha.isEmpty()) {
                q = (NodeLambda) lambdaAux.pop();
                p = q;
                while ((p.getTransUp().equals(lambda) || p.getTransDown().equals(lambda)) && !p.isTheEnd()) {
                    if (p.getTransDown().equals(lambda)) {
                        lambdaAux.push(p);
                    }
                    p = p.getLinkUp();
                    cierreAlpha.add(p);
                }
            }
            estados.add(cierreAlpha);
            cierreAlpha.clear();
            
            if (p.getLinkDown() != null) {
                if (p.isUnion()) {
                    nodes.push(p);
                    nodes.push(p.getUnionNode());
                }
            } if (!nodes.empty() && p.getLinkUp() == nodes.peek()) {
                nodes.pop();
                z = (NodeLambda) nodes.pop();
                p = z.getLinkDown();
            } else {p = p.getLinkUp();}
            estado++;
        }  
    }
}
