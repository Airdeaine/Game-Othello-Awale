package modele.jeux.ia.arbre;

import modele.jeux.interfaceJeu.*;
import java.util.*;

public class Arbre {
    private Noeud racine;
    private final IA ia;
    private final Joueur joueur;
    private final Jeu jeu;

    public Arbre(Jeu jeu, IA ia, Joueur joueur){
        this.ia = ia;
        this.joueur = joueur;
        this.jeu = jeu;
        this.racine = null;
    }

    public void construireArbre (Plateau plateau, Coup coup){
        this.racine = new Noeud(0,coup,null); // La valeur de la racine est inutile car elle ne sera pas utiliser par l'algo
        int hauteur = 2;
        aux_construireArbreRec(plateau, hauteur, racine);
    }

    private void aux_construireArbreRec (Plateau plateau, int hauteur, Noeud pere) {
        Joueur j;
        if (hauteur % 2 == 0){
            j = ia;
        }
        else {
            j = joueur;
        }
        List<Coup> list_coup_possible_suivant = jeu.listeCoupPossible(ia,plateau);
        Iterator<Coup> it_coup = list_coup_possible_suivant.iterator();
        ArrayList<Noeud> succ = new ArrayList<>();
        while (it_coup.hasNext()){
            Coup c = it_coup.next();
            Plateau plateau_tour_suivant = plateau.copiePlateau();
            jeu.jouerCoup(c,j,plateau_tour_suivant);
            int value = plateau.recuperValeurCoup(jeu,plateau_tour_suivant);
            succ.add(new Noeud(value,c,null));
        }
        pere.setSuccesseur(succ);
        if (hauteur <= 4 && pere.getSuccesseur() != null){
            Iterator<Noeud> it_noeud = pere.getSuccesseur().iterator();
            while (it_noeud.hasNext()){
                Noeud fils = it_noeud.next();
                Plateau plateau_tour_suivant = plateau.copiePlateau();
                try {
                    jeu.jouerCoup(fils.getCoup(), j, plateau_tour_suivant);
                    aux_construireArbreRec(plateau_tour_suivant,hauteur + 1, fils);
                }
                catch (IndexOutOfBoundsException e){
                    continue;
                }
            }
        }
    }

    /*private int recuperValeurCoup (Plateau p1, Plateau p2){
        int value = 0;
        HashMap<Character,ArrayList<String>> plateau_init = p1.getPlateau();
        HashMap<Character,ArrayList<String>> plateau_suivant = p2.getPlateau();
        int taille = p1.getTaille();
        char derniereColonne = p1.getDerniereColonne();
        for (char c = 'A' ; c <= derniereColonne ; c++){
            for (int i = 0 ; i < taille ; i++){
                String case_init = plateau_init.get(c).get(i);
                String case_suivant  = plateau_suivant.get(c).get(i);
                if (!case_init.equals(case_suivant)){
                    CoupOthello coup = new CoupOthello(c + " " + i);
                    value += othello.recupererScorePlacement(coup,p2);
                }
            }
        }
        return value;
    }*/

    public ArrayList<Noeud> recupererNoeudArbre(Noeud racine, int hauteur){
        if (hauteur == 0){
            ArrayList<Noeud> noeuds = new ArrayList<>();
            noeuds.add(racine);
            return noeuds;
        }
        ArrayList<Noeud> noeuds = new ArrayList<>();
        for (Noeud fils : racine.getSuccesseur()){
            noeuds.addAll(recupererNoeudArbre(fils,hauteur - 1));
        }
        return noeuds;
    }

    public Noeud getRacine() { return this.racine; }
}
