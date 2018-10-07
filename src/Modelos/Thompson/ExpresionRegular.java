package Modelos.Thompson;

import java.util.ArrayList;
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

    public ExpresionRegular(String expresionRegular) {
        this.expresionRegular = expresionRegular + finDeSecuencia;
    }

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
                    preValido = (subEx.charAt(i - 1) == '0' || subEx.charAt(i - 1) == '1' ? 1 : 0);
                    sigValido = preValido;
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
    
    public String[][] crearAFD (ExpresionRegular expres) {
        AFNDLambda p = new AFNDLambda(1, "r", "r*");
        AFNDLambda q = new AFNDLambda(1, "0", "s", "r|s");       
        
        NodeLambda r = p.getStart().getLinkUp(); 
        p.addAtPos(q, r);
        p.asignId();
         
        r = p.getEnd();
        q = new AFNDLambda(1, "0", "r*");
        p.addAtPos(q, r);
        p.asignId();
        
        r = p.findNode();
        q = new AFNDLambda(1, "1", "s", "r.s");
        p.addAtPos(q, r);
        p.asignId();
        
        r = p.findNode();
        q = new AFNDLambda(1, "r", "s", "r.s");
        p.addAtPos(q, r);
        p.asignId();
        
        r = p.findNode();
        q = new AFNDLambda(1, "0", "r*");
        p.addAtPos(q, r);
        p.asignId();
        
        r = p.findNode();
        q = new AFNDLambda(1, "1", "r");
        p.addAtPos(q, r);
        p.asignId();
        
        
        th = new Thompson(p);
        String[][] exp = th.crearAFD();
        return exp;
    }
    
    public boolean pertenece (String expres) {
        boolean exp = th.pertenece(expres);
        return exp;
    }

    public Thompson getTh() {
        return th;
    }
}