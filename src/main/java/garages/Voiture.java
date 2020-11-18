package garages;

import java.io.PrintStream;
import java.util.*;

public class Voiture {

    private final String immatriculation;
    private final List<Stationnement> myStationnements = new LinkedList<>();

    public Voiture(String i) {
        if (null == i) {
            throw new IllegalArgumentException("Une voiture doit avoir une immatriculation");
        }

        immatriculation = i;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    /**
     * Fait rentrer la voiture au garage Précondition : la voiture ne doit pas
     * être déjà dans un garage
     *
     * @param g le garage où la voiture va stationner
     * @throws java.lang.Exception Si déjà dans un garage
     */
    public void entreAuGarage(Garage g) throws Exception {
        // Et si la voiture est déjà dans un garage ?
        if (this.estDansUnGarage()) {
            throw new IllegalArgumentException("La voiture est déjà dans un garage");
        }

        //on crée un stationnement et on l'ajoute dans la linkedlist
        Stationnement s = new Stationnement(this, g);
        myStationnements.add(s);

    }

    /**
     * Fait sortir la voiture du garage Précondition : la voiture doit être dans
     * un garage
     *
     * @throws java.lang.Exception si la voiture n'est pas dans un garage
     */
    public void sortDuGarage() throws Exception {
        //Si la voiture n'est pas dans un garage 
        if (!this.estDansUnGarage()) {
            throw new IllegalArgumentException("La voiture doit être dans un garage pour pouvoir en sortir");
        }

        // vérifier si la voiture est bien dans un garage
        if (this.estDansUnGarage()) {
            Stationnement dernierSta = myStationnements.get(myStationnements.size() - 1); // Trouver le dernier stationnement de la voiture
            dernierSta.terminer(); // Terminer ce stationnement
        }
    }

    /**
     * @return l'ensemble des garages visités par cette voiture
     */
    public Set<Garage> garagesVisites() {
        Set<Garage> garagesVisites = new HashSet<Garage>();
        //On doit ajouter les garages visités par la voiture
        for (Stationnement s : myStationnements) {
            garagesVisites.add(s.getGarage());
        }
        return garagesVisites;

    }

    /**
     * @return vrai si la voiture est dans un garage, faux sinon
     */
    public boolean estDansUnGarage() {
        //Tester si le dernier stationnement est en cours
        if (myStationnements.size() - 1 == -1) {
            return false;
        }
        Stationnement dernierSta = myStationnements.get(myStationnements.size() - 1); //on récupère le dernier stationnement de la liste
        // Vrai si le dernier stationnement est en cours
        if (dernierSta.estEnCours()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Pour chaque garage visité, imprime le nom de ce garage suivi de la liste
     * des dates d'entrée / sortie dans ce garage
     * <br>Exemple :
     * <pre>
     * Garage Castres:
     *		Stationnement{ entree=28/01/2019, sortie=28/01/2019 }
     *		Stationnement{ entree=28/01/2019, en cours }
     *  Garage Albi:
     *		Stationnement{ entree=28/01/2019, sortie=28/01/2019 }
     * </pre>
     *
     * @param out l'endroit où imprimer (ex: System.out)
     */
    public void imprimeStationnements(PrintStream out) {
        //On crée une liste locale de stationnements 
        List<Stationnement> stationnements = new LinkedList<>(myStationnements);

        //On parcourt notre liste 
        for (int i = 0; i < stationnements.size(); i++) {
            //On crée une variable de type string pour nos garages 
            String garage = stationnements.get(i).getGarage().toString();
            //on ajoute le nom des garages à la sortie out ainsi que les stationnements 
            out.append(garage + "\n");
            out.append(stationnements.get(i).toString() + "\n");

            for (int j = i + 1; j < stationnements.size(); j++) {
                //Si on a 2 fois le meme garage il ne doit pas être réaffiché à l'écran 
                //donc on le supprime de notre liste
                if (stationnements.get(j).getGarage() == stationnements.get(i).getGarage()) {
                    out.append(stationnements.get(j).toString() + "\n");
                    stationnements.remove(stationnements.get(j));
                }

            }

        }

    }
}
