package gestionnaireBibliotheque;

import java.util.PriorityQueue;

public class FilePrioriteReservations {

    private PriorityQueue<Reservation> file; // trie automatiquement par priorite

    public FilePrioriteReservations() {
        file = new PriorityQueue<>();
    }

    public boolean ajouter(Reservation reservation) {
        return file.add(reservation);
    }

    public Reservation retirer() {
        if (file.isEmpty()) {
            return null;
        }
        return file.poll(); // retire et retourne le plus prioritaire
    }

    public Reservation consulter() {
        if (file.isEmpty()) {
            return null;
        }
        return file.peek(); // lit le plus prioritaire sans retirer
    }

    public boolean estVide() {
        return file.isEmpty();
    }

    public int taille() {
        return file.size();
    }

    public Reservation retirerPourLivre(int idLivre) {
        Reservation prioritaire = null;

        for (Reservation r : file) {
            if (r.getLivre().getId() == idLivre) {
                if (prioritaire == null || r.compareTo(prioritaire) < 0) { // compareTo < 0 signifie plus prioritaire
                    prioritaire = r;
                }
            }
        }

        if (prioritaire != null) {
            file.remove(prioritaire);
        }

        return prioritaire;
    }
    public boolean aReservationPourLivre(int idLivre) {
        for (Reservation r : file) {
            if (r.getLivre().getId() == idLivre && r.getStatut() == StatutReservation.EN_ATTENTE) 
                { 
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String resultat = "FilePrioriteReservations{\n";
        for (Reservation r : file) {
            resultat += "  " + r.toString() + "\n";
        }
        resultat += "}";
        return resultat;
    }
}
