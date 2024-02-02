package modele.jeux.interfaceJeu;

import java.util.HashMap;

public interface Plateau {
    HashMap getPlateau();
    Plateau  copiePlateau();
    int recuperValeurCoup (Jeu jeu, Plateau p2);
    Joueur recupererGagnant(Joueur j1, Joueur j2);
}
