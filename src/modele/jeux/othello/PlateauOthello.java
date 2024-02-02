package modele.jeux.othello;

import modele.jeux.interfaceJeu.Coup;
import modele.jeux.interfaceJeu.Jeu;
import modele.jeux.interfaceJeu.Joueur;
import modele.jeux.interfaceJeu.Plateau;

import java.util.*;

public class PlateauOthello implements Plateau {
    protected HashMap<Character,ArrayList<String>> plateau;
    protected final int taille;
    protected final char derniereColonne;
    protected final String pionNoir = "\u26AB";
    protected final String pionBlanc = "\u26AA";
    protected final String caseVerte = "\uD83D\uDFE9";
    private PlateauOthello(HashMap<Character,ArrayList<String>> map){
        this.plateau = map;
        this.derniereColonne = derniereColonne(map);
        this.taille = map.get('A').size();
    }
    public PlateauOthello(){
        this.plateau = new HashMap<>();
        this.taille = 8;
        this.derniereColonne = 'H';
        for (char c = 'A' ; c <= 'H'; c++){
            ArrayList<String> tab = new ArrayList<>();
            if (c =='D') {
                for (int i = 1; i <= 8; i++) {
                    if (i == 4) {
                        tab.add(pionNoir);
                    } else if (i == 5) {
                        tab.add(pionBlanc);
                    } else {
                        tab.add(caseVerte);
                    }
                }
            } else if (c == 'E') {
                for (int i = 1; i <= 8; i++) {
                    if (i == 4) {
                        tab.add(pionBlanc);
                    } else if (i == 5) {
                        tab.add(pionNoir);
                    } else {
                        tab.add(caseVerte);
                    }
                }
            }
            else {
                for (int i = 1; i <= 8 ; i++) {
                    tab.add(caseVerte);
                }
            }
            plateau.put(c,tab);
        }
    }
    public PlateauOthello(int taille){
        this.plateau = new HashMap<>();
        this.taille = taille;
        int nbColonne = 0;
        char colonne = 'A';
        while (nbColonne < taille){
            ArrayList<String> uneColonne = new ArrayList<>();
            int nbCase = 0;
            if (nbColonne == (taille / 2) - 1){
                while (nbCase < taille){
                    if (nbCase == (taille / 2) - 1)
                        uneColonne.add(pionBlanc);
                    else if (nbCase == (taille / 2))
                        uneColonne.add(pionNoir);
                    else
                        uneColonne.add(caseVerte);
                    nbCase++;
                }
            }
            else if (nbColonne == (taille / 2)){
                while (nbCase < taille){
                    if (nbCase == (taille / 2) - 1)
                        uneColonne.add(pionNoir);
                    else if (nbCase == (taille / 2))
                        uneColonne.add(pionBlanc);
                    else
                        uneColonne.add(caseVerte);
                    nbCase++;
                }
            }
            else{
                while (nbCase < taille){
                    uneColonne.add(caseVerte);
                    nbCase++;
                }
            }
            plateau.put(colonne,uneColonne);
            colonne++;
            nbColonne++;
        }
        this.derniereColonne = derniereColonne(plateau);
    }

    /**
     * Méthode qui récupère la dernière colonne du plateau
     * @param p
     * @return
     */
    private char derniereColonne(HashMap<Character,ArrayList<String>> p){
        Set<Character> listeColonne = p.keySet();
        Iterator<Character> it = listeColonne.iterator();
        char dernierElement = 'A';
        while(it.hasNext())
            dernierElement = it.next();
        return dernierElement;
    }

    /**
     * Verifie si les deux joueurs ont encore des coups à jouer, sinon le jeu se termine.
     * @param j1
     * @param j2
     * @return boolean
     */
    public Joueur recupererGagnant(Joueur j1, Joueur j2){
        JoueurOthello joueurOthello1 = (JoueurOthello) j1;
        JoueurOthello joueurOthello2 = (JoueurOthello) j2;
        int nbPionNoir = 0;
        int nbPionBlanc = 0;
        Iterator<Character> lesColonnes = plateau.keySet().iterator();
        while (lesColonnes.hasNext()){
            Iterator<String> uneCase = plateau.get(lesColonnes.next()).iterator();
            while (uneCase.hasNext()){
                if (uneCase.next().equals(pionNoir))
                    nbPionNoir++;
                if (uneCase.next().equals(pionBlanc))
                    nbPionBlanc++;
            }
        }
        if (nbPionNoir > nbPionBlanc)
            return joueurOthello1;
        else if (nbPionNoir < nbPionBlanc)
            return joueurOthello2;
        else
            return null;
    }

    public int getTaille() { return this.taille; }

    public char getDerniereColonne() { return this.derniereColonne; }

    public HashMap<Character, ArrayList<String>> getPlateau() {
        return plateau;
    }

    /**
     * Retourne le plateau sous la forme d'une chaine de caractère
     * @return String
     */
    public String toString(){
        String s = "\\  ";
        Set<Character> key = plateau.keySet();
        for (Character c : key)
            s += c + "   ";
        s += "\n";
        for (int i = 0 ; i < taille ; i++){
            s += (i+1) + "  ";
            for (Character c : key){
                s += plateau.get(c).get(i) + "  ";
            }
            s += "\n";
        }
        return s;
    }

    public PlateauOthello copiePlateau(){
        HashMap<Character,ArrayList<String>> copy = new HashMap<>();
        for (char c = 'A' ; c <= derniereColonne ; c++ ){
            ArrayList<String> nouvelle_colonne = (ArrayList<String>) plateau.get(c).clone();
            copy.put(c,nouvelle_colonne);
        }
        return new PlateauOthello(copy);
    }

    public int recuperValeurCoup (Jeu jeu, Plateau p1){
        PlateauOthello plateauOthello1 = this;
        PlateauOthello plateauOthello2 = (PlateauOthello) p1;
        int value = 0;
        HashMap<Character,ArrayList<String>> plateau_init = plateauOthello1.getPlateau();
        HashMap<Character,ArrayList<String>> plateau_suivant = plateauOthello2.getPlateau();
        int taille = plateauOthello1.getTaille();
        char derniereColonne = plateauOthello1.getDerniereColonne();
        for (char c = 'A' ; c <= derniereColonne ; c++){
            for (int i = 0 ; i < taille ; i++){
                String case_init = plateau_init.get(c).get(i);
                String case_suivant  = plateau_suivant.get(c).get(i);
                if (!case_init.equals(case_suivant)){
                    Coup coup = new CoupOthello(c + " " + i);
                    value += jeu.recupererScorePlacement(coup,plateauOthello2);
                }
            }
        }
        return value;
    }
}
