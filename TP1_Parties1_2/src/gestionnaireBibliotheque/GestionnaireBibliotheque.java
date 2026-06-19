//Partie 2
package gestionnaireBibliotheque;
 
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class GestionnaireBibliotheque {

    private Map<Integer, Utilisateur> utilisateurs;
    private Map<Integer, Utilisateur> utilisateursAuthentifies;
    private ListeLivres livres;
    private FilePrioriteReservations reservationsEnAttente;

    public GestionnaireBibliotheque() {
        utilisateurs = new HashMap<>();
        utilisateursAuthentifies = new HashMap<>();
        livres = new ListeLivres();
        reservationsEnAttente = new FilePrioriteReservations();
 
        // Quelques livres de test
        livres.ajouter(new Livre("Java", "Deitel", "Informatique"));
        livres.ajouter(new Livre("CleanCode", "Martin", "Informatique"));
        livres.ajouter(new Livre("Algorithmes", "Cormen", "Informatique"));
 
        // Quelques utilisateurs de test
        Utilisateur amine = new Etudiant(101, "Amine");
        Utilisateur sara = new Personnel(102, "Sara");
        Utilisateur karim = new Professeur(103, "Karim");
        utilisateurs.put(amine.getId(), amine);
        utilisateurs.put(sara.getId(), sara);
        utilisateurs.put(karim.getId(), karim);
    }


    //Méthodes
    //À compléter
    public String traiterCommande(int idConnexion, String typeCommande, String argument) {
        
        if (typeCommande == "REGISTER") {
            return
        }
    }
    //Exercice 1 (à vérifier) 
    public String traiterID(int idConnexion, String argument) {
        if (argument == null || argument.trim().isEmpty()) {
            return "BAD_ARGUMENT_ERROR";
        }
        int idUtilisateur;
        try {
            idUtilisateur = Integer.parseInt(argument.trim());
        } catch (NumberFormatException e) {
            return "BAD_ARGUMENT_ERROR";
        }
 
        Utilisateur utilisateur = utilisateurs.get(idUtilisateur);
        if (utilisateur == null) {
            return "BAD_ARGUMENT_ERROR";
        }
        if (utilisateursAuthentifies.containsKey(idConnexion)) {
            return "ALREADY_AUTHENTIFIED_ERROR";
        }
        if (utilisateursAuthentifies.containsValue(utilisateur)) {
            return "TOO_MANY_CONNECTIONS_ERROR";
        }
 
        utilisateursAuthentifies.put(idConnexion, utilisateur);
        return "AUTHORIZED " + utilisateur.getId() + " " + utilisateur.getNom();
    }

    //Exercice 2
    public String traiterBOOKS() {
        StringBuilder reponse = new StringBuilder();
        reponse.append("BOOKS ").append(livres.taille());
        for (Livre livre : livres) {
            reponse.append(" {")
            .append(livre.getId()).append(" ")
            .append(livre.getTitre()).append(" ")
            .append(livre.getAuteur()).append(" ")
            .append(livre.getCategorie()).append(" ")
            .append(livre.getStatut()).append("}");
        }
        return reponse.toString();
    }
    //Exercice 3
    public String traiterBORROW(int idConnexion, String argument) {
        Utilisateur utilisateur = utilisateursAuthentifies.get(idConnexion);
        if (utilisateur == null) {
            return "AUTHENTICATION_ERROR";
        }
        if (argument == null) {
            return "BAD_ARGUMENT_ERROR";
        }
        String[] parties = argument.split(" ");
        if (parties.length != 2) {
            return "BAD_ARGUMENT_ERROR";
        }
        
        int idLivre;
        int jour;
        try {
            idLivre = Integer.parseInt(parties[0]);
            jour = Integer.parseInt(parties[1]);
        } catch (NumberFormatException e) {
            return "BAD_ARGUMENT_ERROR";
        }

        Livre livre = livres.rechercher(idLivre);
        if (livre == null) {
            return "BOOK_NOT_FOUND_ERROR";
        }
        if (livre.getStatut() != StatutLivre.DISPONIBLE) {
            return "BOOK_NOT_AVAILABLE_ERROR";
        }
        if (utilisateur.peutEmprunter() == false) {
            return "BORROW_LIMIT_ERROR";
        }

        Emprunt emprunt = new Emprunt(livre, utilisateur.getId(), jour);
        utilisateur.ajouterEmpruntEnCours(emprunt);
        livre.setStatut(StatutLivre.EMPRUNTE);
 
        return "BORROW_OK " + emprunt.getId() + " " + livre.getId() + " " + emprunt.getJourRetourPrevu();
    }
}
