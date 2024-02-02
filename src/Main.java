import vue.Ihm;
import controleur.Controleur;

public class Main {
    public static void main(String[] args){
        Ihm ihm = new Ihm();
        Controleur controleur = new Controleur(ihm);
        controleur.jouer();
    }
}
