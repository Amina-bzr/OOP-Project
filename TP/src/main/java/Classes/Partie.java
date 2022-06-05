package Classes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Array;
import  java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;

/**
 * Write a description of class Partie here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Partie  implements Serializable
{
    // instance variables - replace the example below with your own
    public Case plateau[] = new Case[100];
    private Joueur joueur; //le seul qui joue à cette partie

    /**
     * Constructor for objects of class Partie
     */
    public Partie(Joueur joueur)
    {
        this.joueur=joueur;
        this.plateau[0]= new CaseDepart(0);
        this.plateau[99]= new CaseFin(99);

        int indices []= new int [25];

        for(int i=0;i<25;i++){
            boolean trouv= true;
            Arrays.sort(indices);
            while (trouv==true) {

                int randnum=ThreadLocalRandom.current().nextInt(1,98);
                int res= Arrays.binarySearch(indices, randnum);
                trouv = res >= 0 ? true : false;
                if (trouv==false) {
                    indices[0]=randnum;
                }
            }


            if (i<5) {
                //creer une case et lattribuer à plateau [randnum]
                System.out.println("CAse Bonus"+i);
                this.plateau[indices[0]]= new CaseBonus(indices[0]);

            } else if (5<=i && i<10) {
                System.out.println("CAse Malus");
                this.plateau[indices[0]]= new CaseMalus(indices[0]);

            }  else if (10<=i && i<15) {
                this.plateau[indices[0]]= new CaseImage(indices[0]);


            } else if (15<=i && i<20) {
                this.plateau[indices[0]]= new CaseDefinition(indices[0]);


            } else if (20<=i && i<25) {
            this.plateau[indices[0]]= new CaseSaut(indices[0]);
            }
        }
        Arrays.sort(indices);

        for (int j=1;j<=98;j++) {

            int res= Arrays.binarySearch(indices, j);
            boolean trouv = res >= 0 ? true : false;
            if (trouv==false) {
                this.plateau[j]=new CaseParcours(j);

            }
        }
    }
    
    public Joueur getJoueur() {
        return this.joueur;
    }
    
    public void setJoueur(Joueur joueur) {
        this.joueur=joueur;
    }

    
    public void TerminerPartie() throws Exception {
        // comparer le score actuelle du joueur avec son meilleur score

        // 1- checrcher le nom du jueur dans le fichier et obtenir son meilleur score :
        // ----1/ LES BUFFERS
        FileReader fr = new FileReader("joueurs_meilleur_score.txt");
        BufferedReader bf = new BufferedReader(fr);

        //---2/ VARIABLES UTILES
        String s;
        String[] mailleur_joueur= {"0","0"}; //[nom_joueur , meilleur_score] ce tab contient le meilleur joueur/score dans le jeu
        String fichier=""; //contient le contenu du nouveau fichier
        String[] mots; //contient joueur + meilleur score lu a partir de l'anien fichier

        //AFFICHAGE À LA FIN DE LA PARTIE
        Stage popupwindow=new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Fin de partie!");
        VBox record_personnel=null;

        boolean trouv=false;

        while ((s=bf.readLine())!=null) {

            mots=s.split(",");
            if (Integer.parseInt(mots[1]) > Integer.parseInt(mailleur_joueur[1])) {
                mailleur_joueur[0]=mots[0];
                mailleur_joueur[1]=mots[1];
            } //sauvegarde du meilleur joueur ~~~~FIN

            if (mots[0].compareTo(joueur.getNom())==0) {
                System.out.println("on va chenger le score ");
                if (joueur.getScore()> Integer.parseInt(mots[1])) {
                    mots[1]=Integer.toString(joueur.getScore());
                    record_personnel = new VBox(new Text("Félicitations!! vous avez dépasser votre meilleur score!"),new Text("Votre meilleur score: "+mots[1]),new Text("votre score actuelle: "+joueur.getScore()));
                     } else {
                    record_personnel = new VBox(new Text("Votre meilleur score: "+mots[1]),new Text("votre score actuelle: "+joueur.getScore()));
                }
            }
            fichier=fichier+mots[0]+","+mots[1]+"\n";
        }

        fr.close();
        Text meilleur= new Text("");
        Text resultat= new Text();
        VBox abattu = new VBox(resultat,meilleur);
        if ((mailleur_joueur[0].compareTo(joueur.getNom()))==0){
            resultat.setText("u're the king bruh!");
        }
        meilleur.setText("the king is: "+mailleur_joueur[0]+"\n score: "+mailleur_joueur[1]);
        FileWriter f = new FileWriter("joueurs_meilleur_score.txt",false);
        f.write(fichier);
        f.close();
        VBox container = new VBox(record_personnel,abattu);
        Scene scene1= new Scene(container, 600, 600);
        popupwindow.setScene(scene1);

        popupwindow.showAndWait();


        //if (joueur.getScore()>joueur.meilleurScore) {
        //    Jeu.joueurs_meilleur_score.put(joueur.getNom(), joueur.getScore());
        //}
    }
    
    public void PauserPartie(int y)
    {
        // enregistrer position, score, nom du joueur
        // enregistrer le plateau
    }
}
