package Classes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Plateau {
    public void Plateau() {

    }

    public static StackPane PLateauGraphique(Partie partie ) {

        Text score = new Text();
        Text position = new Text("Position actuelle: "+partie.getJoueur().getPosition());
        Text destination = new Text("Lancez les dés!");
        score.setText("score: "+partie.getJoueur().getScore());
        BorderPane main = new BorderPane();
        Button susp=new Button("suspendre");
        VBox entete = new VBox(new HBox (new ImageView(new Image("file:src/main/java/assets/joueur.jpg")),new Text("  "),new Text(partie.getJoueur().getNom())), score, position, destination,susp);
        entete.getStyleClass().add("entete");
        main.setRight(entete);

        Case  plateau_code[] = partie.plateau; //le plateau encodé dans un tableau 1d
        StackPane stack = new StackPane(); //stackpane qui va contenir le plateau
        GridPane plateau = new GridPane(); //l'interface graphique du plateau

        plateau.setHgap(10);
        plateau.setVgap(10);

        //LANCER LES DÉS
        //--- DE1 et DE2 -------

        ImageView de1 = new ImageView();
        ImageView de2 = new ImageView();
        HBox des = new HBox(de1, de2);

        des.getStyleClass().add("hbox");
        //---- button LANCER ------
        Button lancer = new Button("Lancer");
        lancer.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                int valeur_de1= De.lancerDe();
                int valeur_de2= De.lancerDe();
                de1.setImage(new Image("file:src/main/java/assets/"+valeur_de1+".png"));
                de2.setImage(new Image("file:src/main/java/assets/"+valeur_de2+".png"));
                position.setText("Position actuelle: " + partie.getJoueur().getPosition());
                partie.getJoueur().setPosition((partie.getJoueur().deplacer(valeur_de1+ valeur_de2)));
                destination.setText("Partez vers la case: " + partie.getJoueur().getPosition());
                System.out.println(partie.getJoueur().getPosition());
                lancer.setDisable(true);
            }
        });
        //REGROUPER LES DÉS + LE BOUTTON LANCER DANS UN VBOX
        VBox des_lancer = new VBox(des, lancer);

        //--FIN----
        // suspendre le jeu

        susp.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent actionEvent) {

                Partie p =partie ;
                try {
                    FileOutputStream fileOut =  new FileOutputStream(partie.getJoueur().getNom()+".txt");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(p);
                    out.close();
                    fileOut.close();
                    Stage popupwindow=new Stage();
                    popupwindow.initModality(Modality.APPLICATION_MODAL);
                    popupwindow.setTitle("Sauvegarde réussie!");
                    HBox erreur = new HBox(new Text("Votre partie a été enregistré avec succés!"));
                    erreur.getStyleClass().add("poppup");
                    Scene scene1= new Scene(erreur, 500, 150);
                    popupwindow.setScene(scene1);
                    scene1.getStylesheets().add("file:src/main/java/css/style.css");
                    popupwindow.showAndWait();
                    System.out.printf("Serialized data is saved ");
                } catch (IOException i) {
                    i.printStackTrace();
                }
            }});
        //Remplissage du borderpane
        main.setCenter(plateau);
        main.setLeft(des_lancer);
        main.setMargin(plateau, new Insets(10,0,0,10));
        main.setMargin(des_lancer, new Insets(10,10,0,0));

        //---


        plateau.setGridLinesVisible(false); //bien definier les bornes du plateau

        //remplissage du plateau (grid) avec les btns + définition des évenements
        int count = 0;
        int a = 0;
        int b = 9;
        int col;
        int l;
        ImageView cercle = new ImageView("file:src/main/java/assets/cercle.png");
        //Logique de création du plateau
        while (a <= 4 && b >= 5) {
            for (l = a; l <= b; l++) {

                col = a;
                System.out.println("la ligne est :" + Integer.toString(l));
                System.out.println("la colone  est :" + Integer.toString(col));

                Case c = plateau_code[count];
                Button btn = new Button(Integer.toString(count));
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent actionEvent) {
                        try {
                            System.out.println(c.getNum());
                            if ((partie.getJoueur().getPosition() == c.getNum()) || (c.getColor()!="white")) {
                                // lancer.setDisable(false);
                                position.setText("Position actuelle: " + partie.getJoueur().getPosition());
                                c.PointeCase(partie.getJoueur());
                                if (partie.getJoueur().getPosition() == c.getNum()) {
                                    lancer.setDisable(false);
                                    destination.setText("Lancez les dés! ");}

                                /*if (c.getColor() == "white")  {    //si c une case de parcours on demande au joueur de lancer les dés
                                    destination.setText("Lancez le dé! ");
                                }*/
                                else if (c.getNum()==99) { //case fin
                                    try {
                                        partie.TerminerPartie();}
                                    catch (Exception e) {
                                        System.out.println("fichier scores_joueurs n'existe pas");
                                    }
                                } else { //on affiche la pos auquelle il doit deplacer sinon
                                    destination.setText("Partez vers la case: " + partie.getJoueur().getPosition());
                                }
                                plateau.getChildren().removeAll(cercle);
                                /*for ( int u=0; u<99; u++ ) {
                                    (plateau.getChildren().get(u)).;
                                }*/
                                btn.setGraphic(cercle); //set Icon
                                btn.setGraphicTextGap(3);

                                score.setText("score: "+partie.getJoueur().getScore());

                            } else {

                                throw new CaseErroneeException();
                            }
                        } catch (CaseErroneeException e) {
                            Stage popupwindow = new Stage();
                            popupwindow.initModality(Modality.APPLICATION_MODAL);
                            popupwindow.setTitle("Selectionnez la bonne Case!");
                            HBox container = new HBox(new Text("Veuillez selectionner la bonne case"));
                            Scene scene1 = new Scene(container, 400, 150);
                            popupwindow.setScene(scene1);
                            popupwindow.showAndWait();
                        }
                        // lancer.setDisable(false);
                    }
                });
                btn.setStyle("-fx-background-color: " + plateau_code[count].getColor() + ";" + "-fx-padding:20px;");
                plateau.add(btn, col, l);
                count++;

            }

            //2eme boucle
            for (col = a + 1; col <= b; col++) {
                l = b;
                System.out.println("la ligne est :" + Integer.toString(l));
                System.out.println("la colone  est :" + Integer.toString(col));
                Case c = plateau_code[count];
                Button btn = new Button(Integer.toString(count));
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent actionEvent) {
                        try {
                            System.out.println(c.getNum());
                            if ((partie.getJoueur().getPosition() == c.getNum()) || (c.getColor()!="white")) {
                                // lancer.setDisable(false);
                                position.setText("Position actuelle: " + partie.getJoueur().getPosition());

                                c.PointeCase(partie.getJoueur());
                                if (partie.getJoueur().getPosition() == c.getNum()) {
                                    lancer.setDisable(false);
                                    destination.setText("Lancez les dés! ");}

                                /*if (c.getColor() == "white")  {    //si c une case de parcours on demande au joueur de lancer les dés
                                    destination.setText("Lancez le dé! ");
                                }*/
                                else if (c.getNum()==99) { //case fin
                                    System.out.println(99);
                                    try {
                                        partie.TerminerPartie();}
                                    catch (Exception e) {
                                        System.out.println("fichier scores_joueurs n'existe pas");
                                    }
                                } else { //on affiche la pos auquelle il doit deplacer sinon
                                    destination.setText("Partez vers la case: " + partie.getJoueur().getPosition());
                                }
                                plateau.getChildren().removeAll(cercle);
                                btn.setGraphic(cercle); //set Icon
                                btn.setGraphicTextGap(1);
                                score.setText("score: "+partie.getJoueur().getScore());

                            } else {
                                throw new CaseErroneeException();
                            }
                        } catch (CaseErroneeException e) {
                            Stage popupwindow = new Stage();
                            popupwindow.initModality(Modality.APPLICATION_MODAL);
                            popupwindow.setTitle("Selectionnez la bonne Case!");
                            HBox container = new HBox(new Text("Veuillez selectionner la bonne case"));
                            Scene scene1 = new Scene(container, 400, 150);
                            popupwindow.setScene(scene1);
                            popupwindow.showAndWait();
                        }
                        // lancer.setDisable(false);
                    }
                });
                btn.setStyle("-fx-background-color: " + plateau_code[count].getColor() + ";" + "-fx-padding:20px;");
                plateau.add(btn, col, l);
                count++;

            }


            //3ème boucle
            for (l = b - 1; l >= a; l--) {
                System.out.println("la ligne est :" + Integer.toString(l));
                System.out.println("la colone  est :" + Integer.toString(col));
                col = b;
                Case c = plateau_code[count];
                Button btn = new Button(Integer.toString(count));
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent actionEvent) {
                        try {
                            System.out.println(c.getNum());
                            if ((partie.getJoueur().getPosition() == c.getNum()) || (c.getColor()!="white")) {
                                //lancer.setDisable(false);
                                position.setText("Position actuelle: " + partie.getJoueur().getPosition());
                                c.PointeCase(partie.getJoueur());
                                if (partie.getJoueur().getPosition() == c.getNum()) {
                                    lancer.setDisable(false);
                                    destination.setText("Lancez les dés! ");}

                                /*if (c.getColor() == "white")  {    //si c une case de parcours on demande au joueur de lancer les dés
                                    destination.setText("Lancez le dé! ");
                                }*/
                                else if (c.getNum()==99) { //case fin
                                    System.out.println(99);
                                    try {
                                        partie.TerminerPartie();}
                                    catch (Exception e) {
                                        System.out.println("fichier scores_joueurs n'existe pas");
                                    }
                                } else { //on affiche la pos auquelle il doit deplacer sinon
                                    destination.setText("Partez vers la case: " + partie.getJoueur().getPosition());
                                }
                                plateau.getChildren().removeAll(cercle);
                                btn.setGraphic(cercle); //set Iconcon
                                btn.setGraphicTextGap(3);
                                score.setText("score: "+partie.getJoueur().getScore());

                            } else {
                                throw new CaseErroneeException();
                            }
                        } catch (CaseErroneeException e) {
                            Stage popupwindow = new Stage();
                            popupwindow.initModality(Modality.APPLICATION_MODAL);
                            popupwindow.setTitle("Selectionnez la bonne Case!");
                            HBox container = new HBox(new Text("Veuillez selectionner la bonne case"));
                            Scene scene1 = new Scene(container, 400, 150);
                            popupwindow.setScene(scene1);
                            popupwindow.showAndWait();
                        }
                        // lancer.setDisable(false);
                    }
                });
                btn.setStyle("-fx-background-color: " + plateau_code[count].getColor() + ";" + "-fx-padding:20px;");
                plateau.add(btn, col, l);
                count++;

            }


            //4ème boucle
            for (col = b - 1; col >= a + 1; col--) {
                System.out.println("la ligne est :" + Integer.toString(l));
                System.out.println("la colone  est :" + Integer.toString(col));
                l = a;
                Case c = plateau_code[count];
                Button btn = new Button(Integer.toString(count));
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent actionEvent) {
                        try {
                            System.out.println(c.getNum());
                            if ((partie.getJoueur().getPosition() == c.getNum()) || (c.getColor()!="white")) {

                                position.setText("Position actuelle: " + partie.getJoueur().getPosition());
                                c.PointeCase(partie.getJoueur());
                                if (partie.getJoueur().getPosition() == c.getNum()) {
                                    lancer.setDisable(false);
                                    destination.setText("Lancez les dés! ");}

                                /*if (c.getColor() == "white")  {    //si c une case de parcours on demande au joueur de lancer les dés
                                    destination.setText("Lancez le dé! ");
                                }*/
                                else if (c.getNum()==99) { //case fin
                                    System.out.println(99);
                                    try {
                                        partie.TerminerPartie();}
                                    catch (Exception e) {
                                        System.out.println("fichier scores_joueurs n'existe pas");
                                    }
                                } else { //on affiche la pos auquelle il doit deplacer sinon
                                    destination.setText("Partez vers la case: " + partie.getJoueur().getPosition());
                                }

                                plateau.getChildren().removeAll(cercle);
                                btn.setGraphic(cercle); //set Icon
                                btn.setGraphicTextGap(3);
                                score.setText("score: "+partie.getJoueur().getScore());

                            } else {
                                throw new CaseErroneeException();
                            }
                        } catch (CaseErroneeException e) {
                            Stage popupwindow = new Stage();
                            popupwindow.initModality(Modality.APPLICATION_MODAL);
                            popupwindow.setTitle("Selectionnez la bonne Case!");
                            HBox container = new HBox(new Text("Veuillez selectionner la bonne case"));
                            Scene scene1 = new Scene(container, 400, 150);
                            popupwindow.setScene(scene1);
                            popupwindow.showAndWait();
                        }
                        // lancer.setDisable(false);
                    }
                });
                btn.setStyle("-fx-background-color: " + plateau_code[count].getColor() + ";" + "-fx-padding:20px;");
                plateau.add(btn, col, l);
                count++;

            }

            a++;
            b--;


        }
        stack.getChildren().add(main);


        return stack;

    }
}
