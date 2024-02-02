package modele.jeux.awale;

import modele.jeux.interfaceJeu.Coup;
import modele.jeux.interfaceJeu.Joueur;

public class CoupAwale implements Coup {
    private int slot;
    private JoueurAwale joueur;
    public CoupAwale (String coup){
        this.slot = Integer.parseInt(coup);
    }
    public CoupAwale(int slot){
        this.slot = slot;
    }
    public CoupAwale(int slot, Joueur joueur){
        this.slot = slot;
        this.joueur = (JoueurAwale) joueur;
    }

    public int getSlot(){
        return this.slot;
    }
}
