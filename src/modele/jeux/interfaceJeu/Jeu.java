package modele.jeux.interfaceJeu;

import java.util.List;

public interface Jeu {
    boolean estTerminer(Joueur j1, Joueur j2, Plateau plateau);
    boolean coupPossible(Joueur joueur, Plateau plateau);
    void jouerCoup(Coup coup, Joueur joueur, Plateau plateau);
    Joueur gagnant(Joueur j1, Joueur j2);
    List<Coup> listeCoupPossible(IA ia, Plateau plateau);
    int recupererScorePlacement(Coup coup,Plateau plateau);

}
