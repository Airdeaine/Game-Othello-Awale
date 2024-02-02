package vue;

import modele.enumerate.Difficulte;
import modele.enumerate.JeuChoisi;
import modele.enumerate.TypePartie;
import modele.jeux.interfaceJeu.Plateau;
import modele.jeux.othello.PlateauOthello;

import java.util.Scanner;

public class Ihm {
    /**
     * Demande à un utilisateur de rentrer son nom. Le message est personnalisable en fonction de l'index i renseigné
     * @param i
     * @return String nom
     */
    public String demanderNom(int i){
        System.out.print("Entrez le nom du joueur "+ i + " : ");
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }

    /**
     * Affiche le plateau de jeu
     * @param plateau
     */
    public void afficherPlateau(Plateau plateau){
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(plateau);
    }

    /**
     * Demande à l'utilisateur la position où il souhaite placer son pion ou alors s'il souhaite passer son tours
     * @return String coup
     */
    public String jouerUnTour(String joueur){
        System.out.print(joueur  + " : entrez le coup à jouer, soit en tappant de la forme \"H 3\" ou \"P\" pour passer le tour : ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /**
     * Affiche si un coup n'est pas valide
     */
    public void afficherErreurCoup(){
        System.out.println("Ce coup n'est pas valide, réessayer");
    }

    /**
     * Affiche le gagnant de la partie
     * @param j
     */
    public void afficherGagnant(String j){
        System.out.println( j + " a gagné la partie !");
    }

    /**
     * Affichage dans le cas où il n'y a pas de gagnant
     */
    public void afficherGagnant(){
        System.out.println("Il n'y a pas de gagnant");
    }

    /**
     * demande aux joueurs de rejouer la partie
     * @return String
     */
    public String demanderDeRejouer(){
        System.out.print("Souhaitez-vous rejouer une partie ? (o ou n) : ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /**
     * Affiche le vainqueur du jeu
     * @param j
     */
    public void afficherVainqueur(String j){
        System.out.println("Le vainqueur du jeu est " + j);
    }

    /**
     * Affichage dans le cas où il n'y a pas de vainqueurs
     */
    public void afficherVainqueur(){
        System.out.println("Il n'y a pas de vainqueurs");
    }

    /**Affiche le coup joué par l'Ordi */
    public void afficherCoupOrdi(String coup){
        System.out.println("L'ordinateur a joué "+coup);
    }

    public TypePartie demanderJoueurouIA(){
        int choix = 0;
        while (true) {
            TypePartie type = null;
            System.out.print("Voulez-vous jouer en solo ou en Multijoueur? tapez 1 pour solo , tapez 2 pour multijoueur : ");
            Scanner sc = new Scanner(System.in);
            choix = sc.nextInt();
            switch (choix) {
                case 1 -> {
                    type = TypePartie.SOLO;
                    return type;
                }
                case 2 -> {
                    type = TypePartie.MULTI;
                    return type;
                }
                default -> {
                    System.out.println("Veuillez entrez le nombre 1 ou 2 !");
                }
            }
        }
    }

    public Difficulte demanderDifficulte(){
        Difficulte difficulte = null;
        int choix = 0;
        while (true){
            System.out.print("Quel difficulté souhaitez-vous ? Tapez 1 pour facile et 2 pour difficile : ");
            Scanner sc = new Scanner(System.in);
            choix = sc.nextInt();
            switch (choix){
                case 1 -> {
                    difficulte = Difficulte.FACILE;
                    return difficulte;
                }
                case 2 -> {
                    difficulte = Difficulte.DIFFICILE;
                    return difficulte;
                }
                default -> {
                    System.out.println("Veuillez entrez un nombre entre 1 ou 2 !");
                }
            }
        }
    }

    public JeuChoisi demanderJeu(){
        JeuChoisi jeu = null;
        int choix = 0;
        while (true) {
            System.out.print("Quel jeu souhaitez-vous jouer ? Tapez 1 pour Othello et 2 pour Awale : ");
            Scanner sc = new Scanner(System.in);
            choix = sc.nextInt();
            switch (choix){
                case 1 -> {
                    jeu = JeuChoisi.OTHELLO;
                }
                case 2 -> {
                    jeu = JeuChoisi.AWALE;
                }
                default -> {
                    System.out.println("Veuillez entrer un choix valide");
                }
            }
            return jeu;
        }
    }
}
