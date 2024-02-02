package modele.jeux.ia;

import modele.jeux.ia.arbre.Arbre;
import modele.jeux.ia.arbre.Noeud;
import modele.jeux.interfaceJeu.*;
import java.util.ArrayList;
import java.util.Iterator;

public class StrategieMinimax implements Strategie {
    private Jeu jeu;
    private Joueur joueur_adverse;
    private IA ia;

    public StrategieMinimax(Jeu jeu, Joueur joueur_adverse, IA ia){
        this.jeu = jeu;
        this.joueur_adverse = joueur_adverse;
        this.ia = ia;

    }
    @Override
    public Coup jouerCoup(IA ia, Plateau plateau) {
        Arbre arbre = new Arbre(jeu, ia, joueur_adverse);
        arbre.construireArbre(plateau,joueur_adverse.getCoupPrecedent());
        Noeud n = minimax(arbre.getRacine(), 4);
        Noeud noeud = getCoupOriginal(n, arbre.getRacine(), 4);
        return noeud.getCoup();
    }

    private Noeud minimax(Noeud n, int profondeur) {
        Noeud noeud = n;
        if (profondeur == 0) {
            return noeud;
        } else if (profondeur % 2 == 1) {
            ArrayList<Noeud> s = n.getSuccesseur();
            if (s == null){
                return n;
            }
            Iterator<Noeud> it = s.iterator();
            ArrayList<Noeud> succ = new ArrayList<>();
            while (it.hasNext()) {
                Noeud fils = minimax(it.next(), profondeur - 1);
                succ.add(fils);
            }
            noeud = NMax(succ);
        } else {
            ArrayList<Noeud> s = n.getSuccesseur();
            Iterator<Noeud> it = s.iterator();
            ArrayList<Noeud> succ = new ArrayList<>();
            while (it.hasNext()) {
                Noeud fils = minimax(it.next(), profondeur - 1);
                succ.add(fils);
            }
            noeud = NMin(succ);
        }
        return noeud;
    }

    private Noeud NMin(ArrayList<Noeud> liste) {
        if (liste.size() == 0)
            return null;
        Noeud min = null;
        Iterator<Noeud> it_noeud = liste.iterator();
        min = it_noeud.next();
        while (it_noeud.hasNext()) {
            Noeud temp = it_noeud.next();
            System.out.println(temp == null);
            try {
                if (temp.getValue() < min.getValue()) {
                    min = temp;
                }
            } catch (NullPointerException e){
                if (temp == null && min == null)
                    continue;
                else if (temp == null)
                    continue;
                else
                    min = temp;
            }
        }
        return min;
    }

    private Noeud NMax(ArrayList<Noeud> liste) {
        if (liste.size() == 0)
            return null;
        Noeud max = null;
        Iterator<Noeud> it_noeud = liste.iterator();
        max = it_noeud.next();
        while (it_noeud.hasNext()) {
            Noeud temp = it_noeud.next();
            try {
                if (temp.getValue() > max.getValue()) {
                    max = temp;
                }
            } catch (NullPointerException e){
                if (temp != null) {
                    max = temp;
                }
            }
        }
        return max;
    }

    private Noeud getCoupOriginal(Noeud feuille,Noeud noeud,int profondeur){
        Noeud n = null;
        ArrayList<Noeud> succ = noeud.getSuccesseur();
        Iterator<Noeud> it = succ.iterator();
        while (it.hasNext()){
            n = it.next();
            if (inSuccesseur(feuille,n,profondeur)){
                return n;
            }
        }
        return n;
    }

    private boolean inSuccesseur(Noeud feuille, Noeud noeud, int profondeur) {
        ArrayList<Noeud> succ = noeud.getSuccesseur();
        if (succ == null){
            return false;
        }
        Iterator<Noeud> it = succ.iterator();
        if(profondeur-1==0){
            return noeud.equals(feuille);
        }
        if (profondeur-1== 1) {
            while (it.hasNext()) {
                Noeud n = it.next();
                if (n.equals(feuille)) {
                    return true;
                }
            }
        } else {
            while (it.hasNext()) {
                boolean n = inSuccesseur(feuille, it.next(), profondeur - 1);
                if(n){
                    return true;
                }
            }
        }
        return false;
    }
}
