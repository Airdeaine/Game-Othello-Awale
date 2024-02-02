package modele.jeux.awale;

import modele.jeux.interfaceJeu.*;

import java.util.ArrayList;
import java.util.List;

public class Awale implements Jeu {
    public Awale() {}

    public boolean estTerminer (Joueur j1, Joueur j2, Plateau plateau){
        return coupPossible(j1,plateau) && coupPossible(j2,plateau);
    }

    public boolean coupPossible(Joueur j, Plateau plateau){
        PlateauAwale plateauAwale = (PlateauAwale) plateau;
        JoueurAwale joueurAwale = (JoueurAwale) j;
        String id = joueurAwale.getId();
        boolean peutJoueur = true;
        switch (id){
            case "joueur" -> {
                for (int i = plateauAwale.taillePlateau; i >= 1; i--) {
                    CoupAwale coup = new CoupAwale(i);
                    if (recupererValueCase(id, i, plateauAwale) == 0 || peutInfamer(coup, joueurAwale, plateauAwale)) {
                        peutJoueur = false;
                    } else {
                        peutJoueur = true;
                        break;
                    }
                }
            }
            case "advresaire" -> {
                for (int i = 1; i <= plateauAwale.taillePlateau; i++) {
                    CoupAwale coup = new CoupAwale(i);
                    if (recupererValueCase(id, i, plateauAwale) == 0 || peutInfamer(coup, joueurAwale, plateauAwale)) {
                        peutJoueur = false;
                    } else {
                        peutJoueur = true;
                        break;
                    }
                }
            }
        }
        return peutJoueur;
    }

    public boolean coupPossible(Joueur j, Plateau p, int slot){
        JoueurAwale joueurAwale = (JoueurAwale) j;
        PlateauAwale plateauAwale = (PlateauAwale) p;
        return !(recupererValueCase(joueurAwale.getId(), slot, plateauAwale) == 0 || peutInfamer(new CoupAwale(slot),joueurAwale,plateauAwale));
    }

    public void jouerCoup(Coup coup, Joueur joueur, Plateau plateau){
        CoupAwale coupAwale = (CoupAwale) coup;
        JoueurAwale joueurAwale = (JoueurAwale) joueur;
        PlateauAwale plateauAwale = (PlateauAwale) plateau;
        int slot = coupAwale.getSlot();
        int nbGraine = plateauAwale.plateau.get(joueurAwale.getId()).get(slot);
        if (nbGraine == 0){
            throw new RuntimeException(); // a revoir : cas ou on prend les graines dans un slot vide
        }
        String id = joueurAwale.getId();
        switch (id) {
            case "joueur" -> {
                setValueCase("joueur", slot, -1, plateauAwale);
                nbGraine = jouerTourJoueur(joueurAwale, slot - 1, nbGraine, plateauAwale);
                while (nbGraine > 0) {
                    nbGraine = jouerTourAdversaire(joueurAwale, 1, nbGraine, plateauAwale);
                    nbGraine = jouerTourJoueur(joueurAwale, plateauAwale.taillePlateau, nbGraine, plateauAwale);
                }
                finDeTour(joueurAwale, plateauAwale);
            }
            case "adversaire" -> {
                setValueCase("adversaire", slot, -1, plateauAwale);
                nbGraine = jouerTourAdversaire(joueurAwale, slot + 1, nbGraine, plateauAwale);
                while (nbGraine > 0) {
                    nbGraine = jouerTourJoueur(joueurAwale, plateauAwale.taillePlateau, nbGraine, plateauAwale);
                    nbGraine = jouerTourAdversaire(joueurAwale, 1, nbGraine, plateauAwale);
                }
                finDeTour(joueurAwale, plateauAwale);
            }
        }
    }



    private int jouerTourJoueur(JoueurAwale j, int slot, int nbGraine, PlateauAwale plateau){
        if (nbGraine != 0){
            for (int i = slot ; i >= 1; i--){
                if (nbGraine > 0 && (recupererValueCase(j.getId(),i,plateau) != -1 || j.getId().equals("adversaire"))){
                    modifierCase("joueur",i,1,plateau);
                    nbGraine--;
                    if (nbGraine == 0 && j.getId().equals("adversaire")){
                        rafle("joueur",plateau,i);
                    }
                }
            }
        }
        return nbGraine;
    }

    private int jouerTourAdversaire(JoueurAwale j, int slot, int nbGraine, PlateauAwale plateau){
        if (nbGraine != 0){
            for (int i = slot ; i <= plateau.taillePlateau; i++){
                if (nbGraine > 0 && (recupererValueCase(j.getId(),i,plateau) != -1 || j.getId().equals("joueur"))){
                    modifierCase("adversaire",i,1,plateau);
                    nbGraine--;
                    if (nbGraine == 0 && j.getId().equals("joueur")){
                        rafle("adversaire",plateau,i);
                    }
                }
            }
        }
        return nbGraine;
    }

    private void rafle(String id, PlateauAwale plateau, int dernierSlotJouer){
        if (recupererValueCase(id,dernierSlotJouer,plateau) <= 3 && recupererValueCase(id,dernierSlotJouer,plateau) > 1){
            int nbGraine = 0;
            switch (id){
                case "joueur" -> {
                    for (int i = dernierSlotJouer; i <= plateau.taillePlateau; i++) {
                        if (recupererValueCase(id, i, plateau) <= 3 && recupererValueCase(id, i, plateau) > 1) {
                            nbGraine += recupererValueCase(id, i, plateau);
                            setValueCase(id, i, 0, plateau);
                        } else {
                            break;
                        }
                    }
                }
                case "adversaire" -> {
                    for (int i = dernierSlotJouer; i >= 1; i--) {
                        if (recupererValueCase(id, i, plateau) <= 3 && recupererValueCase(id, i, plateau) > 1) {
                            nbGraine += recupererValueCase(id, i, plateau);
                            setValueCase(id, i, 0, plateau);
                        } else {
                            break;
                        }
                    }
                }
            }
            setValueCase(id,0,recupererValueCase(id,0,plateau) + nbGraine,plateau);
        }
    }

    private void finDeTour(JoueurAwale joueur, PlateauAwale plateau){
        String id = joueur.getId();
        for (int i = 1 ; i <= plateau.taillePlateau ; i++){
            if (recupererValueCase(id,i,plateau) == -1){
                setValueCase(id,i,0,plateau);
            }
        }
    }

    private boolean peutInfamer(CoupAwale coup, JoueurAwale j, PlateauAwale plateau){
        PlateauAwale copyPlateau = (PlateauAwale) plateau.copiePlateau();
        jouerCoup(coup,j,copyPlateau);
        int cpt = 0;
        switch (j.getId()){
            case "joueur" :
                for (int i = 1 ; i <= copyPlateau.taillePlateau ; i++){
                    if (recupererValueCase("adversaire",i,copyPlateau) == 0){
                        cpt++;
                    }
                }
                if (cpt == (copyPlateau.taillePlateau - 1) ){
                    return true;
                }
                break;
            case "adversaire" :
                for (int i = 1 ; i <= copyPlateau.taillePlateau ; i++){
                    if (recupererValueCase("joueur",i,copyPlateau) == 0){
                        cpt++;
                    }
                }
                if (cpt == (copyPlateau.taillePlateau - 1) ){
                    return true;
                }
                break;
        }
        return false;
    }

    public Joueur gagnant(Joueur j1, Joueur j2){
        JoueurAwale joueurAwale1 = (JoueurAwale) j1;
        JoueurAwale joueurAwale2 = (JoueurAwale) j2;
        if (joueurAwale1.getPartieGagner() > joueurAwale2.getPartieGagner()){
            return joueurAwale1;
        }
        else if (joueurAwale1.getPartieGagner() < joueurAwale2.getPartieGagner()){
            return joueurAwale2;
        }
        else {
            return null;
        }
    }


    public List<Coup> listeCoupPossible(IA ia, Plateau plateau) {
        IAawale iaAwale = (IAawale) ia;
        PlateauAwale plateauAwale = (PlateauAwale) plateau;
        List<Coup> listeCoup = new ArrayList<>();
        for (int i = 1 ; i <= plateauAwale.taillePlateau ; i++){
            if (coupPossible(iaAwale,plateau,i)){
                listeCoup.add(new CoupAwale(i));
            }
        }
        return listeCoup;
    }

    private int recupererValueCase(String id, int index, PlateauAwale plateau){
        return plateau.plateau.get(id).get(index);
    }
    private void modifierCase(String id, int index, int value, PlateauAwale plateau){
        plateau.plateau.get(id).set(index,plateau.plateau.get(id).get(index)+value);
    }

    private void setValueCase(String id, int index, int value, PlateauAwale plateau){
        plateau.plateau.get(id).set(index,value);
    }

    public int recupererScorePlacement (Coup coup, Plateau plateau){
        // TODO: 15/04/2023 problème pour récupérer la valeur du coup 
        return 0;
    }
}