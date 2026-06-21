package gestionnaireBibliotheque;

import java.util.ArrayList;
import java.util.Iterator;

public class ListeEmprunts implements IListeEmprunts, Iterable<Emprunt> {

    private ArrayList<Emprunt> liste;

    public ListeEmprunts() {
        liste = new ArrayList<>();
    }

    @Override
    public boolean ajouter(Emprunt emprunt) {
        return liste.add(emprunt);
    }

    @Override
    public Emprunt supprimer(int idEmprunt) {
        for (int i = 0; i < liste.size(); i++) {
            if (liste.get(i).getId() == idEmprunt) {
                return liste.remove(i); // retire par index
            }
        }
        return null; //introuvable
    }

    @Override
    public Emprunt rechercher(int idEmprunt) {
        for (Emprunt e : liste) {
            if (e.getId() == idEmprunt) {
                return e;
            }
        }
        return null; //introuvable
    }

    @Override
    public boolean contient(int idEmprunt) {
        return rechercher(idEmprunt) != null; // reutilise rechercher au lieu de dupliquer la boucle
    }

    @Override
    public int taille() {
        return liste.size();
    }

    @Override
    public boolean estVide() {
        return liste.isEmpty();
    }

    @Override
    public Iterator<Emprunt> iterator() {
        return liste.iterator();
    }

    @Override
    public String toString() {
        String resultat = "ListeEmprunts{\n";
        for (Emprunt e : liste) {
            resultat += "  " + e.toString() + "\n";
        }
        resultat += "}";
        return resultat;
    }
}
