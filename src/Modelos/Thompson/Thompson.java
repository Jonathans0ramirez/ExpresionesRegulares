package Modelos.Thompson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
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
    private final String[] thompsonDone = new String[20];
    private final String[][] estadosThompson = new String[20][3];
    final String lambda = "λ";
    
    

    public Thompson(AFNDLambda e) {
        this.expresion = e;
    }
    
    public void crearAFD() {
        conjuntosAlpha();
        crearAFDTable();
    }
    
    public void crearAFDTable() {
        NodeThompson inicio = new NodeThompson(estados.get(0));
        NodeThompson linkZero = new NodeThompson(cierreZero(inicio));
        NodeThompson linkOne = new NodeThompson(cierreOne(inicio));
        NodeThompson p;
        estadosThompson[0][0] = inicio.getDataNode();
        inicio.setLinkZero(linkZero);
        start = inicio;
        inicio.setLinkOne(linkOne);
        Queue<NodeThompson> colaThompson = new LinkedList();
        colaThompson.offer(linkZero);
        colaThompson.offer(linkOne);
        thompsonDone[0] = inicio.getDataNode();
        estadosThompson[0][1] = linkZero.getDataNode();
        estadosThompson[0][2] = linkOne.getDataNode();
        int i = 1;
        while (!colaThompson.isEmpty()) {
            p = (NodeThompson) colaThompson.poll();
            if (!isDone(p)) {
                estadosThompson[i][0] = p.getDataNode();
                linkZero = new NodeThompson(cierreZero(p));
                linkOne = new NodeThompson(cierreOne(p));
                if (!isDone(linkZero)) {
                    colaThompson.offer(linkZero);
                }
                if (!isDone(linkOne)) {
                    colaThompson.offer(linkOne);
                }
                p.setLinkZero(linkZero);
                estadosThompson[i][1] = linkZero.getDataNode();
                p.setLinkOne(linkOne);
                estadosThompson[i][2] = linkOne.getDataNode();
                thompsonDone[i] = p.getDataNode();
                i++;
            }         
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
    
    private boolean isYet(NodeThompson e) {
        String th = e.getDataNode();
        if (estadosThompson.length == 0) {
            return false;
        }
        for (int i = 0; i < 20; i++) {
            if (th.equals(estadosThompson[i][0])) {
                return true;
            }
        }
        return false;
    }

    private boolean isDone(NodeThompson e) {
        String th = e.getDataNode();
        boolean res = false;
        if (thompsonDone.length == 0) {
            return false;
        }
        for (int i = 0; i < 20; i++) {
            //for (int j = 0; i < 3; i++) {
                if (th.equals(thompsonDone[i])) {
                    res = thompsonDone[i] != null;
                    if (res == false){
                        return false;
                    }
                }
            //}
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

    public void initTable() {
        
    }
}
