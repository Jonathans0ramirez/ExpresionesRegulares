package Modelos;

import java.util.Stack;

/**
 * @author Jonathan Ramírez
 */
public class ExpresionRegular {

    private String expresionRegular = "";
    private String secuenciaVacia = "Ø";
    private String finDeSecuencia = "¬";
    private String secuenciaNula = " ";

    public ExpresionRegular(String expresionRegular) {
        this.expresionRegular = expresionRegular;
    }

    private boolean esCorrecta(String expresionRegular) {
        expresionRegular += finDeSecuencia;
        boolean res = false;
        int len = expresionRegular.length();
        int i = 0;
        int sigValido = 0; //0 false. 1 true;
        Stack pila = null;
        String subEx = expresionRegular.substring(i, len - 1);
        do {
            switch (subEx) {
                case "0":
                    sigValido = (subEx.charAt(i + 1) != '0' || subEx.charAt(i + 1) != '1' ? 0 : 1);
                    break;
                case "1":
                    sigValido = (subEx.charAt(i + 1) != '0' || subEx.charAt(i + 1) != '1' ? 0 : 1);
                    break;
                case "(":
                    pila.push(subEx.charAt(i));
                    break;
                case ")":
                    if (pila.empty()) {
                        pila.pop();
                    } else {
                        return false;
                    }
                    break;
                case "|":
                    break;
                case "+":
                    break;
                case ".":
                    break;
                case "*":
                    break;
                default:
                    res = false;
                    return res;
            }
            if(sigValido == 1)
            i++;
            subEx = expresionRegular.substring(i, len);
        } while ((!finDeSecuencia(subEx)));
        return res;
    }

    private boolean finDeSecuencia(String aux) {
        return aux.charAt(0) == finDeSecuencia.charAt(0);
    }
}
