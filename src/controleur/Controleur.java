package controleur;
import modele.enumerate.Difficulte;
import modele.enumerate.JeuChoisi;
import modele.enumerate.TypePartie;
import modele.jeux.awale.*;
import modele.jeux.interfaceJeu.*;
import modele.jeux.othello.JoueurOthello;
import modele.jeux.othello.CoupOthello;
import modele.jeux.othello.IAOthello;
import modele.jeux.othello.Othello;
import modele.jeux.othello.PlateauOthello;
import vue.Ihm;

import java.util.ArrayList;
import java.util.Arrays;

public class Controleur {
    private Ihm ihm;
    public Controleur(Ihm ihm){
        this.ihm = ihm;
    }

    /**
     * Méthode qui s'occupe du déroulement du jeu Othello
     */
    public void jouer(){
        Jeu jeu = choisirJeu();
        Joueur j1 = initialiserJoueurDebutJeu(jeu);
        TypePartie type = joueurouIA();
        Joueur j2 = initialiserPartie(type,jeu,j1);
        String rejouer = "o";
        while (rejouer.equals("o")){
            Plateau plateau = initialiserPlateau(jeu,j1,j2);
            ihm.afficherPlateau(plateau);
            jouerPartie(j1,j2,plateau,jeu,type);
            rejouer = finDeLaPartie(j1,j2,plateau);
        }
        finDuJeu(j1,j2,jeu);
    }

    private Joueur initialiserJoueurDebutJeu(Jeu jeu){
        String nom_j1 = ihm.demanderNom(1);
        if (jeu instanceof Othello){
            return new JoueurOthello(nom_j1);
        }
        else{
            return new JoueurAwale(nom_j1);
        }
    }

    private Plateau initialiserPlateau (Jeu jeu, Joueur j1, Joueur j2){
        if (jeu instanceof Othello){
            return new PlateauOthello(6);
        }
        else {
            return new PlateauAwale(6,new ArrayList<>(Arrays.asList(j1,j2)));
        }
    }

    private Jeu choisirJeu() {
        JeuChoisi jeu = ihm.demanderJeu();
        switch (jeu){
            case OTHELLO -> {
                return new Othello();
            }
            case AWALE -> {
                return new Awale();
            }
        }
        return null;
    }

    /**
     * méthode qui s'occupe de l'initialisation de la partie
     * @return Joueur[]
     */
    private Joueur initialiserPartie(TypePartie type, Jeu jeu, Joueur joueur){
        if (jeu instanceof Othello) {
            switch (type) {
                case MULTI -> {
                    String nom_j2 = ihm.demanderNom(2);
                    return new JoueurOthello(nom_j2);
                }
                case SOLO -> {
                    return choisirDifficulte(jeu, joueur);
                }
            }
        }
        else {
            switch (type){
                case SOLO -> {
                    return choisirDifficulte(jeu, joueur);
                }
                case MULTI -> {
                    String nom_j2 = ihm.demanderNom(2);
                    return new JoueurAwale(nom_j2);
                }
            }
        }
        return null;
    }

    private TypePartie joueurouIA(){
        TypePartie type = ihm.demanderJoueurouIA();
        return type;
    }

    private IA choisirDifficulte(Jeu jeu, Joueur joueur_adverse){
        Difficulte difficulte = ihm.demanderDifficulte();
        if (jeu instanceof Othello) {
            switch (difficulte) {
                case FACILE -> {
                    return new IAOthello("L'ordinateur", jeu);
                }
                case DIFFICILE -> {
                    return new IAOthello("L'ordinateur", jeu, joueur_adverse);
                }
            }
        }
        else {
            switch (difficulte) {
                case FACILE -> {
                    return new IAawale("L'ordinateur", jeu);
                }
                case DIFFICILE -> {
                    return new IAawale("L'ordinateur", jeu, joueur_adverse);
                }
            }
        }
        return null;
    }

    /**
     * Méthode qui s'occupe du déroulement du jeu
     * @param j1 joueur 1
     * @param j2
     * @param plateau
     */
    private void jouerPartie(Joueur j1, Joueur j2, Plateau plateau, Jeu jeu, TypePartie type){
        while (jeu.estTerminer(j1,j2,plateau)){
            jouerTour(j1,plateau,jeu);
            switch (type){
                case SOLO -> {
                    IAOthello ia = (IAOthello) j2;
                    jouerTourIa(ia, plateau, jeu);
                }
                case MULTI -> {
                    jouerTour(j2, plateau, jeu);
                }
            }
        }
    }

    /**
     * Méthode qui gère le tour du joueur
     * @param j
     * @param plateau
     */
    private void jouerTour(Joueur j, Plateau plateau, Jeu jeu){
        boolean tourJoueur = true;
        while (tourJoueur) {
            String coup = ihm.jouerUnTour(j.getNom());
            if (coup.equals("P") && jeu.coupPossible(j,plateau))
                ihm.afficherErreurCoup();
            else if (coup.equals("P"))
                tourJoueur = false;
            else {
                try {
                    Coup coupJ1 = creerCoup(coup,jeu);
                    if (jeu.coupPossible(j,plateau)) {
                        jeu.jouerCoup(coupJ1, j,plateau);
                        j.setCoupPrecedent(coupJ1);
                        ihm.afficherPlateau(plateau);
                        tourJoueur = false;
                    }
                } catch (IndexOutOfBoundsException | NullPointerException e) {
                    ihm.afficherErreurCoup();
                }
            }
        }
    }

    private Coup creerCoup(String coup, Jeu jeu){
        if (jeu instanceof Othello){
            return new CoupOthello(coup);
        }
        else{
            return new CoupAwale(coup);
        }
    }

    private void jouerTourIa(IA ia, Plateau plateau, Jeu jeu){
        boolean tour = true;
        while (tour){
            Coup coup = ia.coupIA(plateau);
            if (coup == null){
                tour = false;
            }
            else {
                jeu.jouerCoup(coup,ia,plateau);
                ihm.afficherPlateau(plateau);
                ihm.afficherCoupOrdi(coup.toString());
                tour = false;
            }
        }
    }

    /**
     * Méthode qui se charge de gérer la fin de la partie
     * @param j1
     * @param j2
     * @param plateau
     * @return String
     */
    private String finDeLaPartie(Joueur j1, Joueur j2, Plateau plateau){
        Joueur gagnant = plateau.recupererGagnant(j1, j2);
        try {
            gagnant.partieGagner();
            ihm.afficherGagnant(gagnant.getNom());
        } catch (NullPointerException e) {
            ihm.afficherGagnant();
        }
        return ihm.demanderDeRejouer();
    }

    /**
     * Méthode qui gère l'affichage du gagnant
     * @param j1
     * @param j2
     */
    private void finDuJeu(Joueur j1, Joueur j2, Jeu jeu){
        Joueur vainqueur = jeu.gagnant(j1,j2);
        if (vainqueur == null)
            ihm.afficherVainqueur();
        else
            ihm.afficherVainqueur(vainqueur.toString());
    }
}
