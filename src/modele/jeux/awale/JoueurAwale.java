package modele.jeux.awale;

import modele.jeux.interfaceJeu.Coup;
import modele.jeux.interfaceJeu.Joueur;

public class JoueurAwale implements Joueur {
    private String nom;
    private int nbPartieGagne;
    private String id;
    private static int nbInit;
    private Coup coupPrecedent;

    public JoueurAwale (String nom){
        this.nom = nom;
        if (nbInit % 2 == 0){
            this.id = "joueur";
        }
        else {
            this.id = "adversaire";
        }
        nbInit++;
    }

    public String getId(){
        return this.id;
    }

    @Override
    public void setCoupPrecedent(Coup coup) {
        this.coupPrecedent = coup;
    }

    @Override
    public void partieGagner() {
        this.nbPartieGagne++;
    }

    public Coup getCoupPrecedent(){
        return this.coupPrecedent;
    }

    public int getPartieGagner(){
        return this.nbPartieGagne;
    }

    public String getNom(){
        return this.nom;
    }
}
