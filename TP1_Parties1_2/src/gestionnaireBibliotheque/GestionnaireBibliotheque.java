//Partie 2
package gestionnaireBibliotheque;
 
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

 // Classe principale de gestion de la bibliothèque.
 // Traite les commandes envoyées par les utilisateurs connectés et coordonne
 // les opération sur les livres, les emprunts et les commentaires.
public class GestionnaireBibliotheque {


    // Répertoire de tous les utilisateurs enregistrés dans le système
    private Map<Integer, Utilisateur> utilisateurs;
    
    // Utilisateurs actuellement connectés au système
    private Map<Integer, Utilisateur> utilisateursAuthentifies;

    // Liste de tous les livres disponibles dans la bibliothèque
    private ListeLivres livres;

    // File de la priorité globale contenant toutes les réservations
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

    // Méthodes

    public String traiterCommande(int idConnexion, String typeCommande, String argument) {
        if (typeCommande == null) {
            return "COMMAND_ERROR";
        }
        // Convertit le type de commande en majuscules pour accepter les 
        // commandes peu importe la casse (ex: "books", "Books" ou "BOOKS")
        switch (typeCommande.toUpperCase()) {
            case "REGISTER" :
                return traiterREGISTER(argument);
            case "ID" :
                return traiterID(idConnexion, argument);
            case "BOOKS" :
                return traiterBOOKS();
            case "BORROW" :
                return traiterBORROW(idConnexion, argument);
            case "RETURN" :
                return traiterRETURN(idConnexion, argument);
            case "RESERVE" :
                return traiterRESERVE(idConnexion, argument);
            case "INFO" :
                return traiterINFO(idConnexion, argument);
            case "PENALTY" :
                return traiterPENALTY(idConnexion, argument);
            case "EXIT" :
                return traiterEXIT(idConnexion);
            default : return "COMMAND_ERROR";
        }
    }

    // Exercice 1 : traitement de la commande ID  
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

    // Exercice 2 : traitement de la commande BOOKS
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

    // Exercice 3 : traitement de la commande BORROW
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

    // Exercice 4 : traitement de la commande RETURN
    public String traiterRETURN(int idConnexion, String argument) {

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
        int idEmprunt;
        int jourRetour;
        try {
            idEmprunt = Integer.parseInt(parties[0]);
            jourRetour = Integer.parseInt(parties[1]);
        } catch (NumberFormatException e) {
            return "BAD_ARGUMENT_ERROR";
        }

        Emprunt emprunt = utilisateur.rechercherEmpruntEnCours(idEmprunt);
        if (emprunt == null) {
            return "BAD_BORROW_ERROR";
        }
 
        emprunt.retourner(jourRetour);
        utilisateur.supprimerEmpruntEnCours(idEmprunt);
        utilisateur.ajouterEmpruntTermine(emprunt);
 
        Livre livre = emprunt.getLivre();
 
        Reservation reservation = reservationsEnAttente.retirerPourLivre(livre.getId());

        // Si aucune réservation en attente : le livre redevient DISPONIBLE
        if (reservation == null) {
            livre.setStatut(StatutLivre.DISPONIBLE);
            return "RETURN_OK " + emprunt.getId() + " " + livre.getId();

        // Réservation prioritaire trouvée. Le livre est attribué à l'utilisateur
        // prioritaire sans repasser par DISPONIBLE. Un nouvel emprunt est crée pour cet utilisateur
        } else {
            reservation.setStatut(StatutReservation.ATTRIBUEE);
            Utilisateur uPrioritaire = utilisateurs.get(reservation.getIdUtilisateur());
 
            Emprunt nouvelEmprunt = new Emprunt(livre, uPrioritaire.getId(), jourRetour);
            uPrioritaire.ajouterEmpruntEnCours(nouvelEmprunt);
            livre.setStatut(StatutLivre.EMPRUNTE);
 
            return "RESERVATION_ASSIGNED " + nouvelEmprunt.getId() + " " + livre.getId();
        }
    }

    // Exercice 5 : traitement de la commande RESERVE
    public String traiterRESERVE(int idConnexion, String argument) {

        Utilisateur utilisateur = utilisateursAuthentifies.get(idConnexion);
        if (utilisateur == null) {
            return "AUTHENTICATION_ERROR";
        }
        if (argument == null || argument.isEmpty()) {
            return "BAD_ARGUMENT_ERROR";
        }
        int idLivre;
        try {
            idLivre = Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            return "BAD_ARGUMENT_ERROR";
        }
 
        Livre livre = livres.rechercher(idLivre);
        if (livre == null) {
            return "BOOK_NOT_FOUND_ERROR";
        }
        if (livre.getStatut() == StatutLivre.DISPONIBLE) {
            return "BOOK_ALREADY_AVAILABLE_ERROR";
        }
        if (utilisateur.getReservations().taille() >= Utilisateur.MAX_RESERVATIONS) {
            return "RESERVATION_LIMIT_ERROR";
        }
        if (utilisateur.possedeReservationEnAttentePourLivre(idLivre)) {
            return "BAD_ARGUMENT_ERROR";
        }

        Reservation reservation = new Reservation(livre, utilisateur.getId(), utilisateur.getTypeUtilisateur());
        reservationsEnAttente.ajouter(reservation);
        utilisateur.ajouterReservation(reservation);
        livre.setStatut(StatutLivre.RESERVE);
 
        return "RESERVATION_OK " + reservation.getId() + " " + livre.getId();
    }

    // Exercice 6 : traitement de la commande INFO
    public String traiterINFO(int idConnexion, String argument) {

        Utilisateur utilisateur = utilisateursAuthentifies.get(idConnexion);
        if (utilisateur == null) {
            return "AUTHENTICATION_ERROR";
        }
 
        // INFO
        if (argument == null || argument.isEmpty()) {
            ListeEmprunts emprunts = utilisateur.getEmpruntsEnCours();
            StringBuilder reponse = new StringBuilder();
            reponse.append("INFO ").append(emprunts.taille());
            for (Emprunt e : emprunts) {
            reponse.append(" {")
            .append(e.getId()).append(" ")
            .append(e.getLivre().getId()).append(" ")
            .append(e.getJourEmprunt()).append(" ")
            .append(e.getJourRetourPrevu()).append(" ")
            .append(e.getStatut()).append("}");
            }
            return reponse.toString();
        }
        // INFO <id emprunt>
        int idEmprunt;
        try {
            idEmprunt = Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            return "BAD_ARGUMENT_ERROR";
        }
 
        Emprunt emprunt = utilisateur.rechercherEmpruntEnCours(idEmprunt);
        if (emprunt == null) {
            return "BAD_BORROW_ERROR";
        }
        return "INFO 1 {" + emprunt.getId() + " " + emprunt.getLivre().getId() + " " + emprunt.getJourEmprunt() + " " + emprunt.getJourRetourPrevu() + " " + emprunt.getStatut() + "}";
    }

    // Exercice 7 : traitement de la commande PENALTY
    public String traiterPENALTY(int idConnexion, String argument) {

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
        int idEmprunt;
        int jourActuel;
        try {
            idEmprunt = Integer.parseInt(parties[0]);
            jourActuel = Integer.parseInt(parties[1]);
        } catch (NumberFormatException e) {
            return "BAD_ARGUMENT_ERROR";
        }
        
        // Recherche l'emprunt autant dans les emprunts en cours que terminés, car
        // une pénalité peut être calculée même après que le livre ait été rendu.
        Emprunt emprunt = utilisateur.rechercherEmpruntEnCours(idEmprunt);
        if (emprunt == null) {
            emprunt = utilisateur.rechercherEmpruntTermine(idEmprunt);
        }
        if (emprunt == null) {
            return "BAD_BORROW_ERROR";
        }
        if (emprunt.estEnRetard(jourActuel) == false) {
            return "PENALTY 0.00 0";  
        }
 
        int joursRetard = emprunt.calculerJoursRetard(jourActuel);
        double montant = utilisateur.calculerPenalite(joursRetard);

        return "PENALTY " + String.format(Locale.US, "%.2f", montant) + " " + joursRetard;
    }

    // Exercice 8 : traitement de la commande REGISTER
    public String traiterREGISTER(String argument) {
        if (argument == null) {
            return "BAD_ARGUMENT_ERROR";
        }
        String[] parties = argument.split(" ");
        if (parties.length != 3) {
            return "BAD_ARGUMENT_ERROR";
        }
        int idUtilisateur;
        try {
            idUtilisateur = Integer.parseInt(parties[0]);
        } catch (NumberFormatException e) {
            return "BAD_ARGUMENT_ERROR";
        }
        String typeUtilisateur = parties[1].toUpperCase();
        String nomUtilisateur = parties[2];
 
        if (utilisateurs.containsKey(idUtilisateur)) {
            return "BAD_ARGUMENT_ERROR";
        }

        // Crée l'objet utilisateur correspondant au type demandé, chaque type ayant
        // ses propres règles (limite d'emprunts et taux de pénalité diffférent).
        Utilisateur utilisateur;
        switch (typeUtilisateur) {
            case "ETUDIANT" :
                utilisateur = new Etudiant(idUtilisateur, nomUtilisateur);
            break;
            case "PERSONNEL" :
                utilisateur = new Personnel(idUtilisateur, nomUtilisateur);
            break;
            case "PROFESSEUR" :
                utilisateur = new Professeur(idUtilisateur, nomUtilisateur);
            break;
            default : 
            return "BAD_ARGUMENT_ERROR";
        }
 
        utilisateurs.put(idUtilisateur, utilisateur);
        return "REGISTERED " + idUtilisateur + " " + nomUtilisateur;
    }
    // Exercice 9 : traitement de la commande EXIT
    public String traiterEXIT(int idConnexion) {

        if (utilisateursAuthentifies.containsKey(idConnexion)) {
            utilisateursAuthentifies.remove(idConnexion);
            return "END";
        }
        return "AUTHENTICATION_ERROR";
    }
}
