package modele.jeux.awale;

import modele.jeux.interfaceJeu.Jeu;
import modele.jeux.interfaceJeu.Joueur;
import modele.jeux.interfaceJeu.Plateau;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PlateauAwale implements Plateau {
    protected HashMap<String, ArrayList<Integer>> plateau;
    protected int taillePlateau;

    public PlateauAwale(int taille, ArrayList<Joueur> joueurs){
        this.plateau = new HashMap<>();
        for (Joueur j : joueurs){
            JoueurAwale joueurAwale = (JoueurAwale) j;
            ArrayList<Integer> uneLigne = new ArrayList<>();
            uneLigne.add(0);
            for (int i = 1 ; i <= taille ; i++){
                uneLigne.add(4);
            }
            plateau.put(joueurAwale.getId(), uneLigne);
        }
        this.taillePlateau = taille;
    }

    private PlateauAwale(HashMap<String,ArrayList<Integer>> copy){
        this.plateau = copy;
        this.taillePlateau = copy.get("joueur").size() - 1;
    }

    public String toString(){
        String s = "\n";
        s += "     ";
        for (int i = 1 ; i <= taillePlateau ; i++){
            s += "| " + i + " ";
        }
        s += "|     \n";
        String debutFin = "|----";
        for (int i = 1 ; i <= taillePlateau ; i++){
            debutFin += "|---";
        }
        debutFin += "|----|\n";
        s += debutFin;
        s += "| B1 |";
        for (int i = 1 ; i <= taillePlateau ; i++){
            s += " " + plateau.get("adversaire").get(i) + " |";
        }
        s += " B2 |\n";
        s += "|    ";
        for (int i = 1 ; i <= taillePlateau ; i++){
            s += "|---";
        }
        s += "|    |\n";
        s += "|  " + plateau.get("joueur").get(0) + " |";
        for (int i = 1 ; i <= taillePlateau ; i++){
            s += " " + plateau.get("joueur").get(i) + " |";
        }
        s += "  " + plateau.get("adversaire").get(0) + " |\n";
        s += debutFin;
        return s;
    }
    public Plateau copiePlateau(){
        HashMap<String,ArrayList<Integer>> copy = new HashMap<>();
        ArrayList<String> ids = new ArrayList<>(Arrays.asList("joueur","adversaire"));
        for (String id : ids){
            ArrayList<Integer> nouvelle_colonne = (ArrayList<Integer>) plateau.get(id).clone();
            copy.put(id,nouvelle_colonne);
        }
        return new PlateauAwale(copy);
    }

    public HashMap<String,ArrayList<Integer>> getPlateau(){
        return this.plateau;
    }

    public int recuperValeurCoup (Jeu jeu, Plateau p2){
        // TODO: 14/04/2023
        return 0;
    }

    public Joueur recupererGagnant(Joueur j1, Joueur j2){
        JoueurAwale joueurAwale1 = (JoueurAwale) j1;
        JoueurAwale joueurAwale2 = (JoueurAwale) j2;
        if (plateau.get(joueurAwale1.getId()).get(0) > plateau.get(joueurAwale2.getId()).get(0)){
            return joueurAwale1;
        }
        else if (plateau.get(joueurAwale1.getId()).get(0) < plateau.get(joueurAwale2.getId()).get(0)){
            return joueurAwale2;
        }else{
            return null;
        }
    }
}
