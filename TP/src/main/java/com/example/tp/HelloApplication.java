package com.example.tp;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Classes.*;

import java.io.*;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        //construction de la scene
        boolean clique;
        Jeu jeu = new Jeu(); // on lance le jeu


        //construction de la scene du formulaire
        Text t1 = new Text("Bienveue sur Spirale!");
        Text t2 = new Text("Entrez votre nom!");
        TextField nom_input = new TextField("Entrez votre nom..");
        Button valider = new Button("Jouer!");
        Button desir =new Button("desirialisation") ;
        VBox panneau = new VBox(t1,t2,nom_input,valider ,desir);

        panneau.getStyleClass().add("form_login");


        Scene scene = new Scene(panneau,600,300);

        /*  */

        boolean btnclicked ;
        desir.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent actionEvent) {
                Partie p = null;

                try {
                    FileInputStream fileIn = new FileInputStream("serialistion.txt");
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    p = (Partie) in.readObject();
                    in.close();
                    fileIn.close();
                    StackPane stack = Plateau.PLateauGraphique(p);
                    Scene scene = new Scene(stack, 1000, 1000); //la scene contenant tout le contenu
                    scene.getStylesheets().add("file:src/main/java/css/style.css");
                    stage.setTitle("Jeu Spirale");
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException i) {
                    i.printStackTrace();
                    return;
                } catch (ClassNotFoundException c) {
                    System.out.println(" class not found");
                    c.printStackTrace();
                    return;
                }

                System.out.println("Deserialized joueur..");
                System.out.println("Name: " +p.getJoueur().getNom());
                System.out.println("SCore : " +p.getJoueur().getScore());
                System.out.println("Position: " +p.getJoueur().getPosition());
                for (int i=0 ;i<p.plateau.length ;i++){
                    System.out.print(i);
                    System.out.println(p.plateau[i].getColor())  ;
                }


            }});
        /******************************************/

        valider.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent actionEvent) {
                    try {
                    jeu.entrerJoueur(nom_input.getText()); }
                    catch (Exception e) {
                        System.out.println("e");
                    }
                    finally {
                        Partie partie = jeu.LancerPartie(nom_input.getText()); //on lance une nvelle partie contenant le plateau
                        StackPane stack = Plateau.PLateauGraphique(partie);


                        Scene scene = new Scene(stack, 1000, 1000); //la scene contenant tout le contenu
                        scene.getStylesheets().add("file:src/main/java/css/style.css");
                        stage.setTitle("Jeu Spirale");
                        stage.setScene(scene);
                        stage.show();


                        //Donner un nom au Stage (facultatif)


                        //ajouter la scène au Stage
                    }
        } });

        scene.getStylesheets().add("file:src/main/java/css/style.css");
        stage.setTitle("Jeu Spirale");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}
