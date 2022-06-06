package Classes;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class CaseImage extends CaseQuestion
{
    // instance variables - replace the example below with your own
    public static final String color="pink"  ;
      public String mot  ;
      public String images[] = new String[4]  ; //tableau de chemains vers les images
      public String  imgCorr  ;
       static int qstpos=0;


    /**
     * Constructor for objects of class CaseImage
     */
    public CaseImage(int num)
    {
        this.num=num;
        String keys[] = new String[]{"Walk", "Eat", "Teacher", "Cat", "Sing", "Study", "Puppy", "Run"};

        this.qstpos=(this.qstpos+1)%8;
        this.mot=keys[qstpos];
        this.imgCorr=Jeu.mots_imgs.get(this.mot);
        this.images[qstpos%4]=this.imgCorr;
        this.images[(qstpos+1)%4]=Jeu.mots_imgs.get(keys[(qstpos+1)%8]);
        this.images[(qstpos+2)%4]=Jeu.mots_imgs.get(keys[(qstpos+2)%8]);
        this.images[(qstpos+3)%4]=Jeu.mots_imgs.get(keys[(qstpos+3)%8]);

    }

       public void  PointeCase ( Joueur j)

       { Stage popupwindow=new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Selectionnez la bonne image!");
        GridPane btns_grid= new GridPane();
        btns_grid.setGridLinesVisible(true);
        Text resultat = new Text();
        //container of btns + title
        VBox container = new VBox(new Text( this.mot),btns_grid,resultat);
        container.setSpacing(10);
        container.setStyle("-fx-padding: 35px;");
        container.getStyleClass().add("fenetre_case_image");
        container.setAlignment(Pos.CENTER);
        //loop to initialize the grid of btns with proper images and give event handlers to imaged-btns
           //in the event handler we check whether the player introduced the correct answer
        int cpt =0;
           for (int i=0; i<=1; i++) {
            for (int k=0; k<=1; k++) {
                String img=this.images[cpt];
                Image imgt = new Image("file:"+img);


                Button btn= new Button("",new ImageView(imgt));
                btn.setStyle("-fx-padding:0;");
                btns_grid.add(btn, i, k);


                //event handler on selecting an image
                btn.setOnAction(new EventHandler<ActionEvent>(){
                    public void handle(ActionEvent actionEvent) {
                        for ( int i=0; i<4; i++ ) {
                            btns_grid.getChildren().get(i).setDisable(true);
                        }
                        if (tester(img)==true){
                            j.deplacer(4);
                            j.modifScore(20);
                            resultat.setText("Bonne réponse!");
                            resultat.setFill(Color.GREEN);
                        }else{
                            j.modifScore(-10);
                            resultat.setText("réponse incorrecte..");
                            System.out.println("Lancez le dé");
                            resultat.setFill(Color.RED);
                        }

                    }});
                cpt++;
            }
        }


        btns_grid.setAlignment(Pos.CENTER);
        btns_grid.setCenterShape(true);
        Scene scene1= new Scene(container, 600, 600);
        popupwindow.setScene(scene1);
        scene1.getStylesheets().add("file:src/main/java/css/style.css");
        popupwindow.showAndWait();


    }

    public boolean  tester(String reponse){
        if (reponse == imgCorr ){
            return true ;
        }else return false ;
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
