package modele.jeux.othello;

import modele.enumerate.Position;
import modele.jeux.awale.JoueurAwale;
import modele.jeux.interfaceJeu.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Othello implements Jeu {
    public Othello(){}

    public boolean estTerminer (Joueur j1, Joueur j2, Plateau p){
        return coupPossible(j1,p) && coupPossible(j2,p);
    }


    /**
     * Permet de vérifier si il reste un coup à jouer pour le joueur donné en paramètre
     * @param j
     * @return boolean
     */
    public boolean coupPossible (Joueur j, Plateau p){
        JoueurOthello joueurOthello = (JoueurOthello) j;
        PlateauOthello plateauOthello = (PlateauOthello) p;
        boolean peutJouer= false;
        String pion = joueurOthello.recupererPionJoueur();
        for (char c = 'A' ; c <= plateauOthello.derniereColonne ; c++ ){
            for (int i = 1 ; i <= plateauOthello.taille ; i++){
                if (plateauOthello.plateau.get(c).get(i-1).equals(plateauOthello.caseVerte)) {
                    peutJouer = coupPossibleDiagonalHautDroit(c, i, pion,plateauOthello) || coupPossibleDiagonalHautGauche(c, i, pion,plateauOthello) ||
                            coupPossibleDiagonalBasDroit(c, i, pion,plateauOthello) || coupPossibleDiagonalBasGauche(c, i, pion,plateauOthello) ||
                            coupPossibleHorizontalGauche(c, i, pion,plateauOthello) || coupPossibleHorizontalDroit(c, i, pion,plateauOthello) ||
                            coupPossibleVerticalHaut(c, i, pion,plateauOthello) || coupPossibleVerticalBas(c, i, pion,plateauOthello);
                }
                if (peutJouer)
                    return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si il y a un coup possible sur la diagonal haut droit
     * @param colonne
     * @param ligne
     * @param pion
     * @return boolean
     */
    private boolean coupPossibleDiagonalHautDroit(char colonne,int ligne, String pion, PlateauOthello p){
        if (colonne == p.derniereColonne || ligne <= 1)
            return false;
        if (p.plateau.get(incrementerCaractere(colonne)).get(ligne - 2).equals(pion))
            return false;
        char c = incrementerCaractere(colonne);
        int i = ligne - 2;
        int nbPion = 0;
        while (c <= p.derniereColonne && i >= 0){
            if (p.plateau.get(c).get(i).equals(pion))
                nbPion++;
            if (p.plateau.get(c).get(i).equals(p.caseVerte) && nbPion == 0)
                return false;
            i--;
            c = incrementerCaractere(c);
        }
        return nbPion > 0;
    }

    /**
     * Vérifie si il y a un coup possible sur la diagonal haut gauche
     * @param colonne
     * @param ligne
     * @param pion
     * @return boolean
     */
    private boolean coupPossibleDiagonalHautGauche(char colonne,int ligne,String pion, PlateauOthello p){
        if (colonne == 'A' || ligne <= 1)
            return false;
        if (p.plateau.get(decrementerCaractere(colonne)).get(ligne - 2).equals(pion))
            return false;
        char c = decrementerCaractere(colonne);
        int i = ligne - 2;
        int nbPion = 0;
        while (c >= 'A' && i >= 0){
            if (p.plateau.get(c).get(i).equals(pion))
                nbPion++;
            if (p.plateau.get(c).get(i).equals(p.caseVerte) && nbPion == 0)
                return false;
            i--;
            c = decrementerCaractere(c);
        }
        return nbPion > 0;
    }


    /**
     * Vérifie si il y a un coup possible sur la diagonal bas gauche
     * @param colonne
     * @param ligne
     * @param pion
     * @return boolean
     */
    private boolean coupPossibleDiagonalBasGauche (char colonne,int ligne,String pion, PlateauOthello p){
        if (colonne == 'A' || ligne == p.taille)
            return false;
        if (p.plateau.get(decrementerCaractere(colonne)).get(ligne).equals(pion))
            return false;
        char c = decrementerCaractere(colonne);
        int i = ligne;
        int nbPion = 0;
        while (c >= 'A' && i < p.taille){
            if (p.plateau.get(c).get(i).equals(pion))
                nbPion++;
            if (p.plateau.get(c).get(i).equals(p.caseVerte) && nbPion == 0)
                return false;
            i++;
            c = decrementerCaractere(c);
        }
        return nbPion > 0;
    }

    /**
     * Vérifie si il y a un coup possible sur la ligne horizontal gauche
     * @param colonne
     * @param ligne
     * @param pion
     * @return boolean
     */
    private boolean coupPossibleHorizontalGauche (char colonne,int ligne,String pion, PlateauOthello p){
        if (colonne == 'A' || ligne == 0)
            return false;
        char c = decrementerCaractere(colonne);
        if (p.plateau.get(c).get(ligne - 1).equals(pion))
            return false;
        c = decrementerCaractere(colonne);
        int nbPion = 0;
        while (c >= 'A'){
            if (p.plateau.get(c).get(ligne - 1).equals(pion))
                nbPion++;
            if (p.plateau.get(c).get(ligne - 1).equals(p.caseVerte) && nbPion == 0)
                return false;
            c = decrementerCaractere(c);
        }
        return nbPion > 0;
    }

    /**
     * Vérifie si il y a un coup possible sur la ligne horizontal droit
     * @param colonne
     * @param ligne
     * @param pion
     * @return boolean
     */
    private boolean coupPossibleHorizontalDroit (char colonne,int ligne,String pion, PlateauOthello p){
        if (colonne == p.derniereColonne || ligne == 0)
            return false;
        char c = incrementerCaractere(colonne);
        if (p.plateau.get(c).get(ligne - 1).equals(pion))
            return false;
        c = incrementerCaractere(colonne);
        int nbPion = 0;
        while (c <= p.derniereColonne){
            if (p.plateau.get(c).get(ligne - 1).equals(pion))
                nbPion++;
            if (p.plateau.get(c).get(ligne - 1).equals(p.caseVerte) && nbPion == 0)
                return false;
            c = incrementerCaractere(c);
        }
        return nbPion > 0;
    }

    /**
     * Vérifie si il y a un coup possible sur la ligne vertical haut
     * @param colonne
     * @param ligne
     * @param pion
     * @return boolean
     */
    private boolean coupPossibleVerticalHaut (char colonne,int ligne,String pion, PlateauOthello p){
        if (ligne <= 1)
            return false;
        if (p.plateau.get(colonne).get(ligne - 2).equals(pion))
            return false;
        int i = ligne - 2;
        int nbPion = 0;
        while (i >= 0){
            if (p.plateau.get(colonne).get(i).equals(pion))
                nbPion++;
            if (p.plateau.get(colonne).get(i).equals(p.caseVerte) && nbPion == 0)
                return false;
            i--;
        }
        return nbPion > 0;
    }

    /**
     * Vérifie si il y a un coup possible sur la ligne vertical bas
     * @param colonne
     * @param ligne
     * @param pion
     * @return boolean
     */
    private boolean coupPossibleVerticalBas (char colonne,int ligne,String pion, PlateauOthello p){
        if (ligne == p.taille)
            return false;
        if (p.plateau.get(colonne).get(ligne).equals(pion))
            return false;
        int i = ligne;
        int nbPion = 0;
        while (i < p.taille){
            if (p.plateau.get(colonne).get(i).equals(pion))
                nbPion++;
            if (p.plateau.get(colonne).get(i).equals(p.caseVerte) && nbPion == 0)
                return false;
            i++;
        }
        return nbPion > 0;
    }

    /**
     * Vérifie si un pion est adjacent avec un autre pion d'une couleur différente
     * @param colonne
     * @param ligne
     * @param pion
     * @return boolean
     */
    private boolean verificationAdjacence (char colonne, int ligne, String pion, PlateauOthello p){
        return ((! p.plateau.get(colonne).get(ligne).equals(pion)) && (! p.plateau.get(colonne).get(ligne).equals(p.caseVerte)));
    }

    /**
     * Permet de placer un pion sur le plateau
     * @param coup
     * @param j
     */
    private void placerPion(CoupOthello coup, JoueurOthello j, PlateauOthello p){
        char colonne = coup.getColonne();
        int ligne = coup.getLigne()-1;
        ArrayList<String> modif = p.plateau.get(colonne);
        modif.set(ligne,j.recupererPionJoueur());
        p.plateau.replace(colonne,modif);
    }


    /**
     * Vérifie si il y a un coup possible sur la diagonal bas droit
     * @param colonne
     * @param ligne
     * @param pion
     * @return boolean
     */
    private boolean coupPossibleDiagonalBasDroit (char colonne,int ligne,String pion, PlateauOthello p){
        if (colonne == p.derniereColonne || ligne == p.taille)
            return false;
        if (p.plateau.get(incrementerCaractere(colonne)).get(ligne).equals(pion))
            return false;
        char c = incrementerCaractere(colonne);
        int i = ligne;
        int nbPion = 0;
        while (c <= p.derniereColonne && i < p.taille){
            if (p.plateau.get(c).get(i).equals(pion))
                nbPion++;
            if (p.plateau.get(c).get(i).equals(p.caseVerte) && nbPion == 0)
                return false;
            i++;
            c = incrementerCaractere(c);
        }
        return nbPion > 0;
    }

    /**
     * Méthode qui gère le placement des pions
     * @param coup
     * @param j
     * @exception IndexOutOfBoundsException
     */
    public void jouerCoup(Coup coup, Joueur j , Plateau p) {
        CoupOthello coupOthello = (CoupOthello) coup;
        JoueurOthello joueurOthello = (JoueurOthello) j;
        PlateauOthello plateauOthello = (PlateauOthello) p;
        final char colonne = coupOthello.getColonne();
        final int ligne = coupOthello.getLigne();
        final String pionJoueur = joueurOthello.recupererPionJoueur();
        int compteurPlacement = 0; // Si ce compteur vaut 0 alors une exception est levé pour redemander au joueur de rejouer
        if (plateauOthello.plateau.get(colonne).get(ligne - 1).equals(plateauOthello.caseVerte)){
            compteurPlacement += placerVerticalBas(colonne, ligne, pionJoueur,plateauOthello);
            compteurPlacement += placerVerticalHaut(colonne, ligne, pionJoueur,plateauOthello);
            compteurPlacement += placerHorizontalDroit(colonne, ligne, pionJoueur,plateauOthello);
            compteurPlacement += placerHorizontalGauche(colonne, ligne, pionJoueur,plateauOthello);
            compteurPlacement += placerDiagonalHautGauche(colonne, ligne, pionJoueur,plateauOthello);
            compteurPlacement += placerDiagonalHautDroit(colonne, ligne, pionJoueur,plateauOthello);
            compteurPlacement += placerDiagonalBasGauche(colonne, ligne, pionJoueur,plateauOthello);
            compteurPlacement += placerDiagonalBasDroit(colonne, ligne, pionJoueur,plateauOthello);
        }
        if (compteurPlacement == 0)
            throw new IndexOutOfBoundsException();
        else
            placerPion(coupOthello,joueurOthello,plateauOthello);
    }





    /**
     * Méthode qui s'occupe de retourner les pions adverse sur la diagonal haut gauche
     * @param colonne
     * @param ligne
     * @param pionJoueur
     * @return int
     */
    private int placerDiagonalHautGauche(char colonne, int ligne, String pionJoueur, PlateauOthello p){
        int i = ligne - 2;
        char c = decrementerCaractere(colonne);
        if (coupPossibleDiagonalHautGauche(colonne, ligne, pionJoueur,p) && verificationAdjacence(c, i, pionJoueur,p)) {
            while (c >= 'A' && i >= 0) {
                if (p.plateau.get(c).get(i).equals(pionJoueur) || p.plateau.get(c).get(i).equals(p.caseVerte))
                    break;
                else
                    p.plateau.get(c).set(i, pionJoueur);
                c = decrementerCaractere(c);
                i--;
            }
            return 1;
        }
        return 0;
    }

    /**
     * Méthode qui s'occupe de retourner les pions adverse sur la diagonal haut droit
     * @param colonne
     * @param ligne
     * @param pionJoueur
     * @return int
     */
    private int placerDiagonalHautDroit(char colonne, int ligne, String pionJoueur, PlateauOthello p){
        int i = ligne - 2;
        char c = incrementerCaractere(colonne);
        if (coupPossibleDiagonalHautDroit(colonne, ligne, pionJoueur,p) && verificationAdjacence(c, i, pionJoueur,p)) {
            while (c <= p.derniereColonne && i >= 0) {
                if (p.plateau.get(c).get(i).equals(pionJoueur) || p.plateau.get(c).get(i).equals(p.caseVerte))
                    break;
                else
                    p.plateau.get(c).set(i, pionJoueur);
                c = incrementerCaractere(c);
                i--;
            }
            return 1;
        }
        return 0;
    }

    /**
     * Méthode qui s'occupe de retourner les pions adverse sur la diagonal bas gauche
     * @param colonne
     * @param ligne
     * @param pionJoueur
     * @return int
     */
    private int placerDiagonalBasGauche(char colonne, int ligne, String pionJoueur, PlateauOthello p){
        int i = ligne;
        char c = decrementerCaractere(colonne);
        if (coupPossibleDiagonalBasGauche(colonne, ligne, pionJoueur,p) && verificationAdjacence(c, i, pionJoueur,p)) {
            while (c >= 'A' && i < p.taille) {
                if (p.plateau.get(c).get(i).equals(pionJoueur) || p.plateau.get(c).get(i).equals(p.caseVerte))
                    break;
                else
                    p.plateau.get(c).set(i, pionJoueur);
                c = decrementerCaractere(c);
                i++;
            }
            return 1;
        }
        return 0;
    }

    /**
     * Méthode qui s'occupe de retourner les pions adverse sur la diagonal bas droit
     * @param colonne
     * @param ligne
     * @param pionJoueur
     * @return int
     */
    private int placerDiagonalBasDroit(char colonne, int ligne, String pionJoueur, PlateauOthello p){
        int i = ligne;
        char c = incrementerCaractere(colonne);
        if (coupPossibleDiagonalBasDroit(colonne, ligne, pionJoueur,p) && verificationAdjacence(c, i, pionJoueur,p)) {
            while (c <= p.derniereColonne && i < p.taille) {
                if (p.plateau.get(c).get(i).equals(pionJoueur)|| p.plateau.get(c).get(i).equals(p.caseVerte))
                    break;
                else
                    p.plateau.get(c).set(i, pionJoueur);
                c = incrementerCaractere(c);
                i++;
            }
            return 1;
        }
        return 0;
    }

    /**
     * Méthode qui s'occupe de retourner les pions adverse sur la verticale haute
     * @param colonne
     * @param ligne
     * @param pionJoueur
     * @return int
     */
    private int placerVerticalHaut(char colonne, int ligne, String pionJoueur, PlateauOthello p){
        int i = ligne - 2;
        if (coupPossibleVerticalHaut(colonne, ligne, pionJoueur,p) && verificationAdjacence(colonne, i, pionJoueur,p)) {
            while (i >= 0) {
                if (p.plateau.get(colonne).get(i).equals(pionJoueur) || p.plateau.get(colonne).get(i).equals(p.caseVerte))
                    break;
                else
                    p.plateau.get(colonne).set(i, pionJoueur);
                i--;
            }
            return 1;
        }
        return 0;
    }

    /**
     * Méthode qui s'occupe de retourner les pions adverse sur la verticale basse
     * @param colonne
     * @param ligne
     * @param pionJoueur
     * @return int
     */
    private int placerVerticalBas(char colonne, int ligne, String pionJoueur, PlateauOthello p){
        int i = ligne;
        if (coupPossibleVerticalBas(colonne, ligne, pionJoueur,p) && verificationAdjacence(colonne, i, pionJoueur,p)) {
            while (i < p.taille){
                if (p.plateau.get(colonne).get(i).equals(pionJoueur)|| p.plateau.get(colonne).get(i).equals(p.caseVerte))
                    break;
                else
                    p.plateau.get(colonne).set(i, pionJoueur);
                i++;
            }
            return 1;
        }
        return 0;
    }

    /**
     * Méthode qui s'occupe de retourner les pions adverse sur l'horizontal gauche
     * @param colonne
     * @param ligne
     * @param pionJoueur
     * @return int
     */
    private int placerHorizontalGauche(char colonne, int ligne, String pionJoueur, PlateauOthello p){
        char c = decrementerCaractere(colonne);
        if (coupPossibleHorizontalGauche(colonne, ligne, pionJoueur,p) && verificationAdjacence(c, ligne - 1, pionJoueur,p)) {
            while (c >= 'A') {
                if (p.plateau.get(c).get(ligne - 1).equals(pionJoueur) || p.plateau.get(c).get(ligne-1).equals(p.caseVerte))
                    break;
                else
                    p.plateau.get(c).set(ligne - 1, pionJoueur);
                c = decrementerCaractere(c);
            }
            return 1;
        }
        return 0;
    }

    /**
     * Méthode qui s'occupe de retourner les pions adverse sur l'horizontal droit
     * @param colonne
     * @param ligne
     * @param pionJoueur
     * @return int
     */
    private int placerHorizontalDroit(char colonne, int ligne, String pionJoueur, PlateauOthello p){
        char c = incrementerCaractere(colonne);
        if (coupPossibleHorizontalDroit(colonne, ligne, pionJoueur,p) && verificationAdjacence(c, ligne - 1, pionJoueur,p)) {
            while (c <= p.derniereColonne) {
                if (p.plateau.get(c).get(ligne - 1).equals(pionJoueur)|| p.plateau.get(c).get(ligne-1).equals(p.caseVerte))
                    break;
                else
                    p.plateau.get(c).set(ligne - 1, pionJoueur);
                c = incrementerCaractere(c);
            }
            return 1;
        }
        return 0;
    }

    /**
     * Méthode qui permet de décrémenter un Character ( 'B' => 'A')
     * @param c
     * @return char
     */
    private char decrementerCaractere (char c){
        return Character.toChars(c - 1)[0];
    }

    /**
     * Méthode qui permet de décrémenter un Character ( 'A' => 'B')
     * @param c
     * @return char
     */
    private char incrementerCaractere (char c){
        return Character.toChars(c + 1)[0];
    }

    /**
     * Retourne le gagnant du jeu
     * @param j1
     * @param j2
     * @return Joueur
     */
    public Joueur gagnant(Joueur j1, Joueur j2){
        JoueurOthello joueurOthello1 = (JoueurOthello) j1;
        JoueurOthello joueurOthello2 = (JoueurOthello) j2;
        if (joueurOthello1.nbPartieGagne > joueurOthello2.nbPartieGagne)
            return j1;
        else if (joueurOthello1.nbPartieGagne < joueurOthello2.nbPartieGagne)
            return j2;
        else
            return null;
    }


/**
 * Méthode qui permet de recuperer la liste de coups possible
 * @param ia : ia
 * @param p : plateau de jeu
 * @return List : liste des coup possibles
 */
    public List<Coup> listeCoupPossible(IA ia, Plateau p){
        IAOthello iaOthello = (IAOthello) ia;
        PlateauOthello plateauOthello = (PlateauOthello) p;
        String pion = iaOthello.recupererPionJoueur();
        HashSet<Coup> liste = new HashSet<>(); //creer un hashset pour eviter les doublons
        for (char c = 'A' ; c <= plateauOthello.derniereColonne ; c++  ) {
            for (int i = 1 ; i <= plateauOthello.taille ; i++) {
                if (plateauOthello.plateau.get(c).get(i-1).equals(plateauOthello.caseVerte)) {
                    if (coupPossibleDiagonalHautDroit(c, i, pion,plateauOthello)) {
                        liste.add(new CoupOthello(c + " " + i));
                    }
                    if (coupPossibleDiagonalHautGauche(c, i, pion,plateauOthello)) {
                        liste.add( new CoupOthello(c + " " + i));
                    }
                    if (coupPossibleDiagonalBasDroit(c, i, pion,plateauOthello)) {
                        liste.add(new CoupOthello(c + " " + i));
                    }
                    if (coupPossibleDiagonalBasGauche(c, i, pion,plateauOthello)) {
                        liste.add(new CoupOthello(c + " " + i));
                    }
                    if (coupPossibleHorizontalDroit(c, i, pion,plateauOthello)) {
                        liste.add(new CoupOthello(c + " " + i));
                    }
                    if (coupPossibleHorizontalGauche(c, i, pion,plateauOthello)) {
                        liste.add(new CoupOthello(c + " " + i));
                    }
                    if (coupPossibleVerticalHaut(c, i,pion,plateauOthello)) {
                        liste.add(new CoupOthello(c + " " + i));
                    }
                    if (coupPossibleVerticalBas(c, i, pion,plateauOthello)) {
                        liste.add(new CoupOthello(c + " " + i));
                    }
                }
            }
        }
        List<Coup> listecoup = new ArrayList<>(liste);
        return listecoup;
    }

    /**
     * Perme
     * @param coup
     * @param plateau
     * @return
     */
    private static Position recupererPosition(Coup coup, Plateau plateau){
        PlateauOthello plateauOthello = (PlateauOthello) plateau;
        CoupOthello coupOthello = (CoupOthello) coup;
        int ligne = coupOthello.getLigne();
        int colonne = coupOthello.getColonne();
        if ((ligne == 1 && colonne == plateauOthello.derniereColonne) || (ligne == 1 && colonne == 'A') || (ligne == plateauOthello.taille && colonne == 'A') || (ligne == plateauOthello.taille && colonne == plateauOthello.derniereColonne))
            return Position.COIN;
        else if (ligne == 1 || ligne == plateauOthello.taille || colonne == 'A' || colonne == plateauOthello.derniereColonne)
            return Position.COTE;
        else
            return Position.CENTRALE;
    }

    /**
     * Permet de récupérer le score du placement d'un pion : 11 point si c'est un coin ; 6 points si c'est un côté ; 1 point dans le reste du temps
     * @param coup
     * @param plateau
     * @return int : score du placement
     */
    public int recupererScorePlacement(Coup coup, Plateau plateau){
        Position position = recupererPosition(coup,plateau);
        switch (position){
            case COIN:
                return 11;
            case COTE:
                return 6;
            case CENTRALE:
                return 1;
            default:
                return 0;
        }
    }
}



