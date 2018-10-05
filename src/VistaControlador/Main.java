/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VistaControlador;

import Modelos.Thompson.AFNDLambda;
import Modelos.Thompson.NodeLambda;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author PC
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Principal.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //launch(args);
        /*ExpresionRegular e = new ExpresionRegular("(0|1.0*1)*0*");
        System.out.println(e.esCorrecta());*/
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
        
        System.out.println("Holi");
    }
    
}
