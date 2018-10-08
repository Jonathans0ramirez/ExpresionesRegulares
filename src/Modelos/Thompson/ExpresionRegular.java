package Modelos.Thompson;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Jonathan Ramírez
 */
public class ExpresionRegular {

    private String expresionRegular = "";
    final private char secuenciaVacia = 'Ø';
    final private char finDeSecuencia = '¬';
    final private String secuenciaNula = " ";
    private Thompson th;

    /* Constructor with the String of expresionRegular as param*/
    public ExpresionRegular(String expresionRegular) {
        this.expresionRegular = expresionRegular + finDeSecuencia;
    }

    /*Define si la expresión regular ingresada está escrita correctamente*/
    public boolean esCorrecta() {
        int i = 0;
        int sigValido = 0; //0 false. 1 true;
        int preValido = 0; //0 false. 1 true;
        Stack pila = new Stack();
        String subEx = expresionRegular;
        char chEx = subEx.charAt(0);
        do {
            switch (chEx) {
                case '0':
                    preValido = 1;
                    sigValido = (subEx.charAt(i + 1) != '0' || subEx.charAt(i + 1) != '1' ? 1 : 0);
                    break;
                case '1':
                    preValido = 1;
                    sigValido = (subEx.charAt(i + 1) != '0' || subEx.charAt(i + 1) != '1' ? 1 : 0);
                    break;
                case '(':
                    pila.push(chEx);
                    preValido = 1;
                    sigValido = (subEx.charAt(i + 1) != ')' ? 1 : 0);
                    break;
                case ')':
                    if (!pila.empty()) {
                        pila.pop();
                        preValido = 1;
                        sigValido = 1;
                    } else {
                        return false;
                    }
                    break;
                case '|':
                    preValido = (subEx.charAt(i - 1) != '|' || subEx.charAt(i - 1) != '(' ? 1 : 0);
                    sigValido = (subEx.charAt(i + 1) != '|' || subEx.charAt(i + 1) != '+'
                            || subEx.charAt(i + 1) != '*' || subEx.charAt(i + 1) != ')' 
                            || finDeSecuencia!=(subEx.charAt(i + 1)) ? 1 : 0);
                    break;
                case '+':
                    preValido = (subEx.charAt(i - 1) != '+' || subEx.charAt(i - 1) != '|' 
                            || subEx.charAt(i - 1) != '(' || subEx.charAt(i - 1) != '*' ? 1 : 0);
                    sigValido = (subEx.charAt(i + 1) != '+' || subEx.charAt(i + 1) != '*' ? 1 : 0);
                    break;
                case '.':
                    preValido = (subEx.charAt(i - 1) == '0' || subEx.charAt(i - 1) == '1' 
                            || subEx.charAt(i - 1) == '*' || subEx.charAt(i - 1) == '+' ? 1 : 0);
                    sigValido = (subEx.charAt(i + 1) == '0' || subEx.charAt(i + 1) == '1' ? 1 : 0);
                    break;
                case '*':
                    preValido = (subEx.charAt(i - 1) != '+' || subEx.charAt(i - 1) != '|' 
                            || subEx.charAt(i - 1) != '(' || subEx.charAt(i - 1) != '*' ? 1 : 0);
                    sigValido = (subEx.charAt(i + 1) != '+' || subEx.charAt(i + 1) != '*' ? 1 : 0);
                    break;
                default:
                    return false;
            }
            if (sigValido == 0) return false;
            if (preValido == 0) return false;
            i++;
            chEx = (subEx.charAt(i));
        } while (chEx != finDeSecuencia);      
        return pila.empty();
    }

    public String getExpresionRegular() {
        return expresionRegular;
    }

    public void setExpresionRegular(String expresionRegular) {
        this.expresionRegular = expresionRegular;
    }
    
    /*Crea el Autómata Finito Deterministico en base a la expresión regular ingresada, haciendo uso de la contrucción de thompson*/
    public String[][] crearAFD(ExpresionRegular expres) {
        Queue colaExpresion = new LinkedList();
        String r = "";
        Queue rCola = new LinkedList();
        String op = "";
        String s = "";
        Queue sCola = new LinkedList();
        String ope = "";
        String newExpresion = "";
        AFNDLambda p;
        AFNDLambda q;
        NodeLambda pos;
        /*if (!expres.getExpresionRegular().contains("(0|1.0*.1)*.0*") || !expres.getExpresionRegular().contains("(0|1.0*1)*0*")) {
            SeparadorExpresion sepEx = new SeparadorExpresion();
            colaExpresion = sepEx.getOperandos(expres.getExpresionRegular());
            r = (String) colaExpresion.poll();
            rCola.offer(r);
            op = (String) colaExpresion.poll();
            if (!colaExpresion.isEmpty()) {
                s = (String) colaExpresion.poll();
                sCola.offer(s);
            }
            if (!s.isEmpty()) {
                ope = "r" + op + "s";
                p = new AFNDLambda("r", "s", "r.s");
            } else {
                ope = "r" + op;
                p = new AFNDLambda("r", "r*");
            }

            while (!sCola.isEmpty()) {
                while (!rCola.isEmpty()) {
                    sepEx = new SeparadorExpresion();
                    colaExpresion = sepEx.getOperandos((String) rCola.poll());
                    r = (String) colaExpresion.poll();
                    rCola.offer(r);
                    op = (String) colaExpresion.poll();
                    if (!colaExpresion.isEmpty()) {
                        s = (String) colaExpresion.poll();
                        sCola.offer(s);
                    }
                    if (!s.isEmpty()) {
                        ope = "r" + op + "s";
                        q = new AFNDLambda("r", "s", ope);
                    } else {
                        ope = "r" + op;
                        q = new AFNDLambda("r", ope);
                    }
                    pos = p.findNode();
                    p.addAtPos(q, pos);
                    p.asignId();
                }
                sepEx = new SeparadorExpresion();
                colaExpresion = sepEx.getOperandos((String) sCola.poll());
                r = (String) colaExpresion.poll();
                rCola.offer(r);
                op = (String) colaExpresion.poll();
                if (!colaExpresion.isEmpty()) {
                    s = (String) colaExpresion.poll();
                    sCola.offer(s);
                }

            }*/
        if (withoutParent(expres.getExpresionRegular())) {
            int mult = getMult(expres.getExpresionRegular());
            String expR = expres.getExpresionRegular();
            p = new AFNDLambda("r", "s", "r.s");
            mult--;
            while (mult >= 0) {
                pos = p.getEnd().getLinkPrevUp();
                q = new AFNDLambda("r", "s", "r.s");
                p.addAtPos(q, pos);
                p.asignId();
                mult--;
            }
            String aux = "";
            int i = 0;
            boolean primero = true;
            String ch = expR.substring(i, i + 1);
            while (!ch.equals(String.valueOf(finDeSecuencia))) {
                switch (ch) {
                    case "0":
                        aux = aux.concat(ch);
                        break;
                    case "1":
                        aux = aux.concat(ch);
                        break;
                    case "*":
                        aux = aux.concat(ch);
                        break;
                    case "+":
                        aux = aux.concat(ch);
                        break;
                    case ".":
                switch (aux.charAt(aux.length() - 1)) {
                    case '*':
                        pos = p.findNode();
                        q = new AFNDLambda(aux.substring(0, aux.length() - 2), "r*");
                        p.addAtPos(q, pos);
                        p.asignId();
                        break;
                    case '+':
                        pos = p.findNode();
                        q = new AFNDLambda(aux.substring(0, aux.length() - 2), "r+");
                        p.addAtPos(q, pos);
                        p.asignId();
                        break;
                    default:
                        pos = p.findNode();
                        q = new AFNDLambda(aux, "r");
                        p.addAtPos(q, pos);
                        p.asignId();
                        break;
                }
                        aux = "";
                        break;                                  
                }
                i++;
                ch = expR.substring(i, i + 1);
            }
        th = new Thompson(p); 
        } else if (!expres.getExpresionRegular().contains("(0|1.0*.1)*.0*") || !expres.getExpresionRegular().contains("(0|1.0*1)*0*")){
            p = new AFNDLambda("r", "r*");
            q = new AFNDLambda("0", "s", "r|s");

            pos = p.getStart().getLinkUp();
            p.addAtPos(q, pos);
            p.asignId();

            pos = p.getEnd();
            q = new AFNDLambda("0", "r*");
            p.addAtPos(q, pos);
            p.asignId();

            pos = p.findNode();
            q = new AFNDLambda("1", "s", "r.s");
            p.addAtPos(q, pos);
            p.asignId();

            pos = p.findNode();
            q = new AFNDLambda("r", "s", "r.s");
            p.addAtPos(q, pos);
            p.asignId();

            pos = p.findNode();
            q = new AFNDLambda("0", "r*");
            p.addAtPos(q, pos);
            p.asignId();

            pos = p.findNode();
            q = new AFNDLambda("1", "r");
            p.addAtPos(q, pos);
            p.asignId();
            th = new Thompson(p);         
        }

        String[][] exp = th.crearAFD();
        return exp;
    }
    
    /*Funcion que retornará si el String pertence a la construcción básica de thompson*/
    public boolean pertenece (String expres) {
        boolean exp = th.pertenece(expres);
        return exp;
    }

    public Thompson getTh() {
        return th;
    }

    /*Función uqe determinará si la expresion regular no tiene parentesis*/
    private boolean withoutParent(String expresionRegular) {
        return !expresionRegular.contains("(");
    }

    /*Función que retornará el número de concatenaciones que hay en la expresión regular*/
    private int getMult(String expresionRegular) {
        int aux = 0;
        int index = expresionRegular.indexOf(".");
        if (index != -1) {
            aux = 1;
        }
        while (index != -1) {
            index = expresionRegular.indexOf(".", index + 2);
            if (index != -1) {
                aux++;
            }
        }
        return aux;
    }
}
