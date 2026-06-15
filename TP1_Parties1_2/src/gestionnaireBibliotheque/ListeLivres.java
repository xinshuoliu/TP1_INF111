package gestionnaireBibliotheque;

import java.util.ArrayList;
import java.util.Iterator;

public class ListeLivres implements IListeLivres, Iterable<Livre> {

    private ArrayList<Livre> liste;

    public ListeLivres() {
        liste = new ArrayList<>();
    }

    @Override
    public boolean ajouter(Livre livre) {
        return liste.add(livre);
    }

    @Override
    public Livre supprimer(int idLivre) {
        for (int i = 0; i < liste.size(); i++) {
            if (liste.get(i).getId() == idLivre) {
                return liste.remove(i);
            }
        }
        return null;
    }

    @Override
    public Livre rechercher(int idLivre) {
        for (Livre l : liste) {
            if (l.getId() == idLivre) {
                return l;
            }
        }
        return null;
    }

    @Override
    public boolean contient(int idLivre) {
        return rechercher(idLivre) != null;
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
    public Iterator<Livre> iterator() {
        return liste.iterator();
    }

    @Override
    public String toString() {
        String resultat = "ListeLivres{\n";
        for (Livre l : liste) {
            resultat += "  " + l.toString() + "\n";
        }
        resultat += "}";
        return resultat;
    }
}
