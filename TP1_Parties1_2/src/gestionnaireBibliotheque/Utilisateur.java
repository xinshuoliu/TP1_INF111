package gestionnaireBibliotheque;

// Classe abstraite représentant un utilisateur de la bibliothèque. 
// Gère les emprunts en cours, les emprunts terminés et les réservations.
// Les sous-classes Etudiant, Personnel et Professeur définissent les règles spécifiques à chaque type.
public abstract class Utilisateur {

    // Nombre maximal de réservations en attente autorisé pour tous les types d'utilisateurs. 
    // La limite est identique pour tous, d'où le static final partagé par toutes les sous-classes.
    public static final int MAX_RESERVATIONS = 10; 

    private int id;
    private String nom;
    private ListeEmprunts empruntsEnCours; // livres pas encore rendus
    private ListeEmprunts empruntsTermines; // historique emprunts rendus
    private FilePrioriteReservations reservations;

    public Utilisateur(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.empruntsEnCours = new ListeEmprunts();
        this.empruntsTermines = new ListeEmprunts();
        this.reservations = new FilePrioriteReservations();
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public ListeEmprunts getEmpruntsEnCours() {
        return empruntsEnCours;
    }

    public ListeEmprunts getEmpruntsTermines() {
        return empruntsTermines;
    }

    public FilePrioriteReservations getReservations() {
        return reservations;
    }

    public void ajouterEmpruntEnCours(Emprunt emprunt) {
        empruntsEnCours.ajouter(emprunt);
    }

    public void ajouterEmpruntTermine(Emprunt emprunt) {
        empruntsTermines.ajouter(emprunt);
    }

    public Emprunt supprimerEmpruntEnCours(int idEmprunt) {
        return empruntsEnCours.supprimer(idEmprunt);
    }

    public Emprunt rechercherEmpruntEnCours(int idEmprunt) {
        return empruntsEnCours.rechercher(idEmprunt);
    }

    public Emprunt rechercherEmpruntTermine(int idEmprunt) {
        return empruntsTermines.rechercher(idEmprunt);
    }

    public boolean aDesEmpruntsEnCours() {
        return empruntsEnCours.taille() > 0;
    }

    // Méthode abstraite retournant le nombre maximal d'emprunts selon le type d'utilisateur. 
    // Chaque sous-classe définit sa propre limite : 
    // 5 pour Etudiant, 7 pour Personnel et 10 pour Professeur.
    public abstract int nombreMaxEmprunts(); 

    // Retourne true si l'utilisateur n'a pas atteint sa limite d'emprunts. 
    // Utilise < car un utilisateur ayant exactement atteint sa limite 
    // (ex: 5 emprunts pour un étudiant) ne peut plus emprunter.
    public boolean peutEmprunter() {
        return empruntsEnCours.taille() < nombreMaxEmprunts();
    }
    // Vérifie si l'utilisateur possède déjà une réservation en attente pour un livre précis. 
    // Utilisé dans traiterRESERVE pour empêcher un utilisateur 
    // de réserver plusieurs fois le même livre.
    public boolean possedeReservationEnAttentePourLivre(int idLivre) {
        return reservations.aReservationPourLivre(idLivre);
    }

    public boolean ajouterReservation(Reservation reservation) {
        if (reservations.taille() >= MAX_RESERVATIONS) {
            return false; // file pleine, reservation refusee
        }
        return reservations.ajouter(reservation);
    }

    public abstract double calculerPenalite(int joursRetard); // taux different selon type d'utilisateur

    public abstract TypeUtilisateur getTypeUtilisateur();

    @Override
    public String toString() {
        return getTypeUtilisateur() + "{id=" + id
                + ", nom=" + nom
                + ", empruntsEnCours=" + empruntsEnCours.taille()
                + ", empruntsTermines=" + empruntsTermines.taille()
                + ", reservationsEnAttente=" + reservations.taille() + "}";
    }
}
