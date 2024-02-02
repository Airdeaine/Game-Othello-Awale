package modele.jeux.ia;

import modele.jeux.interfaceJeu.Coup;
import modele.jeux.interfaceJeu.IA;
import modele.jeux.interfaceJeu.Jeu;
import modele.jeux.interfaceJeu.Plateau;
import java.util.List;
import java.util.Random;

public class StrategieNaif implements Strategie {
    private Jeu jeu;
    public StrategieNaif(Jeu jeu){
        this.jeu = jeu;
    }
    @Override
    public Coup jouerCoup(IA ia, Plateau plateau) {
        List<Coup> liste = jeu.listeCoupPossible(ia,plateau);
        int taille = liste.size();
        if (taille>0){
            Random r = new Random();
            int n = r.nextInt(taille);
            return liste.get(n);
        }
        else{
            return null;
        }

    }
}
