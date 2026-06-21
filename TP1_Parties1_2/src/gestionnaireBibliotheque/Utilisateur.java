package gestionnaireBibliotheque;

public abstract class Utilisateur {

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

    public abstract int nombreMaxEmprunts();

    public boolean peutEmprunter() {
        return empruntsEnCours.taille() < nombreMaxEmprunts();
    }
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
