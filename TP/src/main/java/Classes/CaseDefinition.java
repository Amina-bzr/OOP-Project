package Classes;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.util.Scanner ;
public class CaseDefinition extends CaseQuestion
{
    public final String color="blue" ; //private or not?
    private String mot ;
    private String definition ;
    public static int qstpos=0;


    public CaseDefinition( int num)
    {
        this.num=num;
        qstpos = (qstpos + 1) % 8;
        String keys[] = new String[]{"walk", "eat", "teacher", "cat", "sing", "study", "puppy", "run"};

        this.mot=keys[qstpos];
        this.definition= Jeu.mots_def.get(mot);

    }

    public void  PointeCase ( Joueur j) {

        Stage popupwindow = new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Fenêtre de Définition");

        //affichage d'explication
        Text explication = new Text();
        explication.setText("Entrer la mot dont la définition est la suivante :");


        //affichage de la definition
        Text definition = new Text();
        definition.setText(this.definition);

        //
        TextField rep = new TextField();

        rep.setPromptText("Entrer votre réponse");
        //rep.setPrefColumnCount((Jeu.mots_def.get("Walk")).length());// !!!!!!!!!!!!!!!!

        Text resultat = new Text("La réponse contient "+(this.mot).length()+" lettres!");
        Button valid = new Button("valider");
        Button close = new Button("Sortir");
        valid.setOnAction(new EventHandler<ActionEvent>() {
            /**
             *
             */
            @Override
            public void handle(ActionEvent event) {
                String reponse = rep.getText();
                valid.setDisable(true);
                if (tester(reponse) == true) {
                    resultat.setText("reponse correcte");
                    resultat.setFill(Color.GREEN);
                    j.deplacer(4);
                    j.modifScore(20);

                } else {
                    resultat.setText("reponse incorrecte");
                    resultat.setFill(Color.RED);
                    j.modifScore(-10);
                }

            }
        });
        /** */

        close.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                popupwindow.close();
            }
        });

        HBox hbox = new HBox(rep, valid);
        VBox container = new VBox(explication, definition, hbox, resultat, close);
        hbox.setAlignment(Pos.CENTER);
        container.setSpacing(10);
        container.setAlignment(Pos.CENTER);


        Scene sceneDef= new Scene(container, 600, 300);
        popupwindow.setScene(sceneDef);

        popupwindow.showAndWait();

    }

        public boolean tester(String reponse) {
        if ((reponse.toLowerCase()).compareTo(this.mot)==0){
            return true ;
        }else {
            return false ;
        }
    }

        @Override
        public String getColor() {
        return this.color;
    }


    public int getNum ()
    {
        return this.num;
    }
    }