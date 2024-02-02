package modele.jeux.othello;

import modele.jeux.interfaceJeu.*;
import modele.jeux.ia.Strategie;
import modele.jeux.ia.StrategieMinimax;
import modele.jeux.ia.StrategieNaif;

public class IAOthello extends JoueurOthello implements IA {
    private Strategie strategie;
    private Jeu jeu;
    public IAOthello(String nom, Jeu jeu) {
        super(nom);
        this.jeu = jeu;
        this.strategie = new StrategieNaif(jeu);
    }

    public IAOthello(String nom, Jeu jeu, Joueur joueur_adverse){
        super(nom);
        this.jeu = jeu;
        this.strategie = new StrategieMinimax(jeu, joueur_adverse, this);
    }

    public Coup coupIA(Plateau plateau){
        return strategie.jouerCoup(this,plateau);
    }
}