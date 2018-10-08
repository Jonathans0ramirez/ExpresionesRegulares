package Modelos.Thompson;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Jonathan Ramírez
 */
public class SeparadorExpresion {
    private final Queue colaOperando;
    private String expresionOperando;
    final private String finDeSecuencia = "¬";

    /*Constructor*/
    public SeparadorExpresion() {
        this.colaOperando = new LinkedList();
    }
    
    /*Función que retornará el String asociado a la primera parte de la expresión regular si esta consta de solo valores*/
    public String operandosNumFirst (String expresion) {
        String auxCola = "";
        String ch = "";
        int i = 0;
        boolean inProcess = false;
        boolean buscarSuma = sePuedeBuscarSuma(expresion);
        ch = expresion.substring(i, i + 1);
        do {            
            switch (ch) {
                case "|":
                    if (inProcess == true && !auxCola.isEmpty() && buscarSuma == true) {
                        inProcess = false;
                    } else {
                        auxCola = auxCola.concat(ch);
                    }
                    break;
                case ".":
                    if (inProcess == true && !auxCola.isEmpty() && buscarSuma == true) {
                        auxCola = auxCola.concat(ch);
                    } else {
                        inProcess = false;
                    }
                    break;
                case "*":
                    if (inProcess == true) {
                        auxCola = auxCola.concat(ch);
                    }
                    break;
                case "+":
                    if (inProcess == true) {
                        auxCola = auxCola.concat(ch);
                    }
                    break;
                case "0":
                    if (inProcess == false) {
                        inProcess = true;
                        auxCola = auxCola.concat(ch);
                    }
                    break;
                case "1":
                    if (inProcess == false) {
                        inProcess = true;
                        auxCola = auxCola.concat(ch);
                    }
                    break;
            }
            i++;
            ch = expresion.substring(i, i + 1);
        } while (!ch.equals(finDeSecuencia) && inProcess == true);
        return auxCola;
    }
    
    /*Función que retornará el String asociado a la primera parte de la expresión regular, si esta tiene como primer carácter un parentesis*/
    public String operandoParentFirst (String expresion) {
        String auxCola = "";
        String ch = "";
        Stack parentesis = new Stack();
        int i = 0;
        boolean inParent = false; 
        ch = expresion.substring(i, i + 1);
        do {            
            switch (ch) {
                case "(":
                    if (inParent == false) {
                        inParent = true;
                    } else {
                        parentesis.push(ch);
                        auxCola = auxCola.concat(ch);
                    }                        
                    break;
                case ")":
                    if (!parentesis.empty()) {
                        parentesis.pop();
                    } else {
                        inParent = false;
                        if (expresion.substring(i + 1, i + 2).equals("+") || expresion.substring(i + 1, i + 2).equals("*")){
                            auxCola = "(" + auxCola + ")" + expresion.substring(i + 1, i + 2);
                        }
                    }
                    break;
                default:
                    if (inParent == true) {
                        auxCola = auxCola.concat(ch);
                    }
                    break;
            }
            i++;
            ch = expresion.substring(i, i + 1);
        } while (!ch.equals(finDeSecuencia) && inParent == true);
        return auxCola;
    }
    
    /*Método principal que separa la expresión regular y la retorna como una Cola*/
    public Queue getOperandos (String expresion) {
        String chInit = expresion.substring(0, 1);
        String aux = "";
        if (chInit.equals("(")) {
            aux = operandoParentFirst(expresion);
        } else if (chInit.equals("0") || chInit.equals("1")){
            aux = operandosNumFirst(expresion);
        }
        int leng = aux.length();               
        if (leng != expresion.length() - 1) {
            colaOperando.offer(aux);
            String oper = expresion.substring(leng, leng + 1);
            colaOperando.offer(oper);
            if (chInit.equals("(")) {
                aux = expresion.substring(aux.length());
            } else if (chInit.equals("0") || chInit.equals("1")) {
                aux = expresion.substring(aux.length());
            }
            colaOperando.offer(aux);
        } else {
            String p = aux.substring(leng - 1);
            aux = aux.substring(0, leng - 1);
            colaOperando.offer(aux);
            colaOperando.offer(p);
        }
        return colaOperando;
    }

    /*Determina si en la expresión se puede separar mediante el operando de unión*/
    private boolean sePuedeBuscarSuma(String expresion) {
        boolean outParent = true;
        int i = 0;
        String ch = "";
        Stack parent = null;       
        do {
            ch = expresion.substring(i, i + 1);
            switch (ch) {
                case "(":
                    parent.push(ch);
                    outParent = false;
                    break;
                case ")":
                    if (!parent.empty()) {
                        parent.pop();
                    }
                    break;
                case "|":
                    if (outParent == true) {
                        return true;
                    }
                    break;
                default:
                    break;
            }
            if (parent.empty()) {
                outParent = true;
            }
            i++;
        } while (!ch.equals(finDeSecuencia));
        
        return false;
    }
}
