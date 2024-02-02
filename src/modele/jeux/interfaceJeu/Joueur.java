package modele.jeux.interfaceJeu;

public interface Joueur<T> {
    void setCoupPrecedent(Coup coup);
    void partieGagner();
    String getNom();
    Coup getCoupPrecedent();
}
