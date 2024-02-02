package modele.jeux.ia;

import modele.jeux.interfaceJeu.Coup;
import modele.jeux.interfaceJeu.IA;
import modele.jeux.interfaceJeu.Plateau;

public interface Strategie {
    Coup jouerCoup(IA ia, Plateau plateau);
}
