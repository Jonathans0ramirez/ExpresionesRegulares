package Modelos.Thompson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

/**
 *
 * @author Jonathan Ramírez
 */
public class Thompson { 
    protected AFNDLambda expresion;
    protected ArrayList<NodeLambda> cierreAlpha = new ArrayList<>();
    protected ArrayList<ArrayList> estados = new ArrayList<>();
    protected NodeThompson start;
    protected NodeThompson end;
    private Stack estadosThompson;
    final String lambda = "λ";
    
    

    public Thompson(AFNDLambda e) {
        this.expresion = e;
    }
    
    public void crearAFD () {
        conjuntosAlpha();
        NodeThompson inicio = new NodeThompson(estados.get(0));
        NodeThompson linkZero = new NodeThompson(cierreZero(inicio));
        NodeThompson linkOne = new NodeThompson(cierreOne(inicio));
        NodeThompson p, q;
        inicio.setLinkZero(linkZero);
        start = inicio;
        inicio.setLinkOne(linkOne);
        Stack pilaThompson = new Stack();
        pilaThompson.push(linkZero);
        pilaThompson.push(linkOne);
        String chido = linkZero.getDataNode();
        estadosThompson.push(chido);
        chido = linkOne.getDataNode();
        estadosThompson.push(chido);
        
        
        //ListIterator<NodeThompson> iterator;
        //int i = 1;
        while (!pilaThompson.empty()) {         
            p = (NodeThompson) pilaThompson.pop();
            linkZero = new NodeThompson(cierreZero(p));
            linkOne = new NodeThompson(cierreOne(p));
//            if (linkZero.isYet()) {
//                pilaThompson.push(linkZero);
//                pilaThompson.push(linkOne);
//            }
            p.setLinkZero(linkZero);
            p.setLinkOne(linkOne);
        }
    }
    
    public void conjuntosAlpha () {
        Stack nodes = new Stack();
        Stack lambdaAux = new Stack();
        NodeLambda x;
        NodeLambda p = expresion.getStart();
        NodeLambda q;
        NodeLambda z = null;
        int estado = 1;
        while (!p.isTheEnd() && estado <= expresion.getSize()) {
            cierreAlpha.add(p);
            x = p;
            while (!p.isTheEnd() && p.getTransUp().equals(lambda)) {
                if (p.getTransDown() != null && p.getTransDown().equals(lambda)) {
                    lambdaAux.push(p);
                }
                p = p.getLinkUp();
                cierreAlpha.add(p);              
            }
            while (!lambdaAux.isEmpty()) {
                q = (NodeLambda) lambdaAux.pop();
                p = q.getLinkDown();
                cierreAlpha.add(p);
                while (!p.isTheEnd() && p.getTransUp().equals(lambda)) {
                    if (p.getTransDown() != null && p.getTransDown().equals(lambda)) {
                        lambdaAux.push(p);
                    }
                    p = p.getLinkUp();
                    cierreAlpha.add(p);
                }
            }
            Collections.sort(cierreAlpha);
            estados.add(cierreAlpha);            
            cierreAlpha = new ArrayList<>();
            
            p = x;
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
        cierreAlpha.add(p);
        Collections.sort(cierreAlpha);
        estados.add(cierreAlpha);
        cierreAlpha = new ArrayList<>();
    }
    
    public ArrayList concAlpha (ArrayList f, ArrayList e) {
        ArrayList<NodeLambda> list = new ArrayList<>();
        NodeLambda p = null;
        NodeLambda t = null;
        if (f == null){
          f = e;  
        } else {
            f.addAll(e);
        }
        Collections.sort(f);

        ListIterator<NodeLambda> iterator = f.listIterator();
        p = iterator.next();
        if (iterator.hasNext()) {
            t = iterator.next();
        } else {
            list.add(p);
            return list;
        }
        while (iterator.hasNext()) {
            if (p.equals(t)) {
                iterator.remove();
            }
            p = t;
            t = iterator.next();            
        }
        if (p.equals(t)) {
            iterator.remove();
        }
        iterator = f.listIterator();
        while (iterator.hasNext()) {
            t = iterator.next();
            list.add(t);
        }
        return list;
    }

    public AFNDLambda getExpresion() {
        return expresion;
    }

    public void setExpresion(AFNDLambda expresion) {
        this.expresion = expresion;
    }

    public ArrayList<NodeLambda> getCierreAlpha() {
        return cierreAlpha;
    }

    public void setCierreAlpha(ArrayList<NodeLambda> cierreAlpha) {
        this.cierreAlpha = cierreAlpha;
    }

    public ArrayList<ArrayList> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<ArrayList> estados) {
        this.estados = estados;
    }

    private ArrayList cierreZero(NodeThompson index) {
        //ArrayList main = estados.get(index);
        ArrayList main = index.getNodesLambda();
        ArrayList<NodeLambda> list = new ArrayList<>();
        ArrayList res = null;
        ListIterator<NodeLambda> iterator;
        int estado = 1;
        for (iterator = main.listIterator(); iterator.hasNext();) {
            NodeLambda node = iterator.next();
            if (!node.isTheEnd() && node.getTransUp().equals("0")) { 
                estado = node.getData();
                list = estados.get(estado);
                res = concAlpha(res, list);
            }
        }
        return res;
    }

    private ArrayList cierreOne(NodeThompson index) {
        ArrayList main = index.getNodesLambda();
        ArrayList<NodeLambda> list = new ArrayList<>();
        ArrayList res = null;
        ListIterator<NodeLambda> iterator;
        int estado = 1;
        for (iterator = main.listIterator(); iterator.hasNext();) {
            NodeLambda node = iterator.next();
            if (!node.isTheEnd() && node.getTransUp().equals("1")) { 
                estado = node.getData();
                list = estados.get(estado);
                res = concAlpha(res, list);
            }
        }
        return res;
    }

    public NodeThompson getStart() {
        return start;
    }

    public void setStart(NodeThompson start) {
        this.start = start;
    }

    public NodeThompson getEnd() {
        return end;
    }

    public void setEnd(NodeThompson end) {
        this.end = end;
    }
}
