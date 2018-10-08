package Modelos.Thompson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
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
    private final String[] thompsonDone = new String[27];
    private String[][] estadosThompson = new String[27][4];
    final String lambda = "λ";

    /*Constructor*/
    public Thompson(AFNDLambda e) {
        this.expresion = e;
    }
    
    /*Método encargado de construir el AFD*/
    public String[][] crearAFD() {
        conjuntosAlpha();
        crearAFDTable();
        estadosThompson = reconstruir(estadosThompson);
        //estadosThompson = simplificar(estadosThompson);
        return estadosThompson;
    }
    
    /*Determina si una hilera pertenece al autómata finito generado*/
    public boolean pertenece (String hilera) {
        if (hilera.isEmpty()) {
            return "1".equals(estadosThompson[0][3]); 
        }
        hilera = hilera.concat("¬");
        int i = 0;
        String ch = hilera.substring(i, i + 1);
        String aux = "";
        int index = 0;        
        while (!ch.equals("¬")) {
            
            ch = hilera.substring(i, i + 1);
            switch (ch) {
                case "0": 
                    aux = estadosThompson[index][1];
                    break;
                case "1":
                    aux = estadosThompson[index][2];
                    break;
                case "¬":
                    return "1".equals(estadosThompson[index][3]);
                default:
                    return false;
            }
            for (int n = 0; n < thompsonDone.length; n++) {
                if (aux.equals(thompsonDone[n])) {
                    index = n;
                    break;
                }
            }
            i++;            
        }
        return false;
    }
    
    /*Crea la representación del AFD en forma de matriz*/
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
        thompsonDone[0] = inicio.getDataNode();
        if (linkZero.getNodesLambda() == null) {
            estadosThompson[0][1] = "";
        } else {
            estadosThompson[0][1] = linkZero.getDataNode();
            colaThompson.offer(linkZero);
        }
        if (linkOne.getNodesLambda() == null) {
            estadosThompson[0][2] = "";
        } else {
            estadosThompson[0][2] = linkOne.getDataNode();
            colaThompson.offer(linkOne);
        }
        if (isAceptacion(inicio.getDataNode())) {
            estadosThompson[0][3] = "1";
        } else {
            estadosThompson[0][3] = "0";
        }
        int i = 1;
        while (!colaThompson.isEmpty()) {
            p = (NodeThompson) colaThompson.poll();
            if (!isDone(p)) {
                estadosThompson[i][0] = p.getDataNode();
                linkZero = new NodeThompson(cierreZero(p));
                linkOne = new NodeThompson(cierreOne(p));       
                p.setLinkZero(linkZero);
                if (linkZero.getNodesLambda() == null) {
                    estadosThompson[i][1] = "";
                } else {
                    if (!isDone(linkZero)) {
                        colaThompson.offer(linkZero);
                    }
                    estadosThompson[i][1] = linkZero.getDataNode();
                }
                p.setLinkOne(linkOne);
                if (linkOne.getNodesLambda() == null) {
                    estadosThompson[i][2] = "";
                } else {
                    if (!isDone(linkOne)) {
                        colaThompson.offer(linkOne);
                    }
                    estadosThompson[i][2] = linkOne.getDataNode();
                }
                if (isAceptacion(p.getDataNode())) {
                    estadosThompson[i][3] = "1";
                } else {
                    estadosThompson[i][3] = "0";
                }
                thompsonDone[i] = p.getDataNode();
                i++;
            }         
        }
    }
    
    /*Crea el cierre alpha de cada uno de los estados*/
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
    
    /*Concatena dos cierres alpha*/
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

    /*Retorna como arraylist el cierre alpha cuando la entrada es un cero, del nodo ingresado*/
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

    /*Retorna como arraylist el cierre alpha cuando la entrada es un uno, del nodo ingresado*/
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
    
    /*Determina si el cierre alpha ya ha sido calculado para el nodo ingresado*/
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

    /*Determina si el Nodo ya tiene asignado ambos cierres alpha*/
    private boolean isDone(NodeThompson e) {
        String th = e.getDataNode();
        boolean res = false;
        if (thompsonDone.length == 0) {
            return false;
        }
        for (int i = 0; i < 20; i++) {
                if (th.equals(thompsonDone[i])) {
                    res = thompsonDone[i] != null;
                    if (res == false){
                        return false;
                    }
                }
        }
        return res;
    }
    
    /*Le asigna valores a la construcción de Thompson, haciendolo más legible para el usuario*/
    private String[][] reconstruir (String[][] estThompson) {
        String[][] res = estThompson;
        String[][] nuevo = new String[estadosThompson.length][4];
        String[] letras = new String[27];
        letras[0] = "A";
        letras[1] = "B";
        letras[2] = "C";
        letras[3] = "D";
        letras[4] = "E";
        letras[5] = "F";
        letras[6] = "G";
        letras[7] = "H";
        letras[8] = "I";
        letras[9] = "J";
        letras[10] = "K";
        letras[11] = "L";
        letras[12] = "M";
        letras[13] = "N";
        letras[14] = "Ñ";
        letras[15] = "O";
        letras[16] = "P";
        letras[17] = "Q";
        letras[18] = "R";
        letras[19] = "S";
        letras[20] = "T";
        letras[21] = "U";
        letras[22] = "V";
        letras[23] = "W";
        letras[24] = "X";
        letras[25] = "Y";
        letras[26] = "Z";
        
        String aux;
        String thDone;
        for (int s = 0; s < thompsonDone.length; s++) {
            thDone = thompsonDone[s];
            if (thDone == null){
                return res;
            }
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 3; j++) {
                    aux = res[i][j];
                    if (thDone.equals(aux)){
                        res[i][j] = letras[s];
                    }
                }
            }
            thompsonDone[s] = letras[s];
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

    /*Determina si el Nodo es de aceptacióm, o rechazo*/
    private boolean isAceptacion(String data) {
        String end = "";
        int lastEstado = estados.size();
        if (data.length() < 2) {
            end = data.substring(data.length() - 1);
        } else {
            end = data.substring(data.length() - 2);
        }
        
        return (String.valueOf(lastEstado).contains(end));
    }
}
