/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expresionesregulares;

import Modelos.*;
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
        linkedList p = new linkedList(1, "r", "r*");
        linkedList q = new linkedList(1, "r", "s", "r|s");
        
        Node r = p.getStart().getLinkUp(); 
        p.addAtPos(q, r);
        System.out.println("Holi");
    }
    
}
