package Classes;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
/**
 * Write a description of class Jeu here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Jeu
{
    // instance variables - replace the example below with your own
    public static final Map <String, String> mots_def = new HashMap<String, String>();
    public static final Map <String, String> mots_imgs = new HashMap<String, String>();
    public static Map <String, Integer> joueurs_meilleur_score = new HashMap<String, Integer>();



    /**
     * Constructor for objects of class Jeu
     */
    public Jeu()
    {
        //init img_mots
        mots_imgs.put("Walk", "src/main/java/assets/walk.jpg");
        mots_imgs.put("Eat", "src/main/java/assets/eat.jpg");
        mots_imgs.put("Teacher", "src/main/java/assets/teacher.jpg");
        mots_imgs.put("Cat", "src/main/java/assets/cat.jpg");
        mots_imgs.put("Sing", "src/main/java/assets/sing.png");
        mots_imgs.put("Study", "src/main/java/assets/study.jpg");
        mots_imgs.put("Puppy", "src/main/java/assets/puppy.jpg");
        mots_imgs.put("Run", "src/main/java/assets/run.jpg");
        //init def_mots
        mots_def.put("walk", "marcher");
        mots_def.put("eat", "manger");
        mots_def.put("teacher", "enseignant");
        mots_def.put("cat", "chat");
        mots_def.put("sing", "chanter");
        mots_def.put("study", "etudier");
        mots_def.put("puppy", "chien");
        mots_def.put("run", "courrir");
    }

    
    
    public Partie LancerPartie(String nom)
    {
        // put your code here
        // la variable j: represente le joueur authentifié
        Joueur j= new Joueur(nom, 0,0);
        Partie p= new Partie(j);
        return p;
        //Case c= p.plateau[0];
    }
    
    public void entrerJoueur (String nom) throws  Exception {
        FileReader fr = new FileReader("joueurs_meilleur_score.txt");
        BufferedReader bf = new BufferedReader(fr);
        String s;
        String[] mots;

        boolean trouv=false;

        while ((s=bf.readLine())!=null && (trouv==false)) {

            mots=s.split(",");
            for (String mot : mots) {
                System.out.println(mot);
                if (mot.compareTo(nom)==0) {
                    trouv=true;
                }
            }
        }

        fr.close();

        if (trouv==false) {
            FileWriter f = new FileWriter("joueurs_meilleur_score.txt",true);
            f.write(nom+","+"0"+"\n");
            f.close();
        }
    }
}
