package modele.jeux.ia.arbre;

import modele.jeux.interfaceJeu.Coup;
import java.util.ArrayList;

public class Noeud {
    private int value;
    private Coup coup;
    private ArrayList<Noeud> successeur;

    public Noeud(int value, Coup coup, ArrayList<Noeud> successeur){
        this.value = value;
        this.coup = coup;
        this.successeur = successeur;
    }

    public void setSuccesseur(ArrayList<Noeud> successeur){
        this.successeur = successeur;
    }

    public Coup getCoup() { return this.coup; }

    public int getValue() { return this.value; }

    public ArrayList<Noeud> getSuccesseur() { return this.successeur; }

}
