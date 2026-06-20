package gestionnaireBibliotheque;


// Réprésente une réservation d'un livre non disponible par un utilisateur.
// Gère la priorité des réservations selon le type d'utilisateur 
// (professeur, personnel, étudiant) et l'ordre d'arrivée.
public class Reservation implements Comparable<Reservation> {

    private static int compteurID = 0;
    private static int compteurOrdre = 0; // ordre d'arrivee 

    private int id;
    private Livre livre;
    private int idUtilisateur;
    private TypeUtilisateur typeUtilisateur;
    private int ordreReservation; //tiebreak si deux usagers ont meme priorite
    private StatutReservation statut;

    public static int prochainID() {
        compteurID++;
        return compteurID;
    }
    // Génère l'ordre d'arrivée de la réservation. Utilisé comme critère de
    // départage entre deux utilisateurs du même type : la réservation la 
    // plus ancienne est prioritaire.
    public static int prochainOrdreReservation() {
        compteurOrdre++;
        return compteurOrdre;
    }

    public Reservation(Livre livre, int idUtilisateur, TypeUtilisateur typeUtilisateur) {
        this.id = prochainID();
        this.livre = livre;
        this.idUtilisateur = idUtilisateur;
        this.typeUtilisateur = typeUtilisateur;
        this.ordreReservation = prochainOrdreReservation(); // capture l'ordre d'arrivee au moment de la reservation
        this.statut = StatutReservation.EN_ATTENTE;
    }

    public int getId() {
        return id;
    }

    public Livre getLivre() {
        return livre;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public TypeUtilisateur getTypeUtilisateur() {
        return typeUtilisateur;
    }

    public int getOrdreReservation() {
        return ordreReservation;
    }

    public StatutReservation getStatut() {
        return statut;
    }

    public void setStatut(StatutReservation statut) {
        this.statut = statut;
    }
    
    // Retourne un niveau de priorité numérique selon le type d'utilisateur
    // : 0 (PROFESSEUR) étant le plus prioritaire, 1 (PERSONNEL) ensuite,
    // et 2 (ETUDIANT) le moins prioritaire.
    private int getPriorite() {
        if (typeUtilisateur == TypeUtilisateur.PROFESSEUR) return 0; // 0 = le plus prioritaire
        if (typeUtilisateur == TypeUtilisateur.PERSONNEL) return 1;
        return 2; // ETUDIANT, le moins prioritaire
    }

    @Override

    // Compare deux réservations selon deux critères : le type d'utilisateur
    // d'abord (professeur > personnel > étudiant), puis l'ordre d'arrivée 
    // en cas d'égalité. Retourne -1 si prioritaire, 0 si équivalent, 1 si moins prioritaire.
    public int compareTo(Reservation autre) {
        int maPriorite = this.getPriorite();
        int autrePriorite = autre.getPriorite();

        if (maPriorite < autrePriorite) return -1; //passe avant autre
        if (maPriorite > autrePriorite) return 1;  // passe apres autre

        // si meme type d'utilisateur, celui arrive en premier gagne
        if (this.ordreReservation < autre.ordreReservation) return -1;
        if (this.ordreReservation > autre.ordreReservation) return 1;

        return 0;
    }

    @Override
    public String toString() {
        return "Reservation{id=" + id
                + ", idLivre=" + livre.getId()
                + ", idUtilisateur=" + idUtilisateur
                + ", typeUtilisateur=" + typeUtilisateur
                + ", ordreReservation=" + ordreReservation
                + ", statut=" + statut + "}";
    }
}
