package Classes;

import java.io.Serializable;

/**
 * Abstract class Case - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Case implements Serializable
{

    public abstract void PointeCase(Joueur j) ;
    public abstract String getColor() ;
    public abstract int getNum() ;
    public int num;
}
