package gestionnaireBibliotheque;

public class Reservation implements Comparable<Reservation> {

    private static int compteurID = 0;
    private static int compteurOrdre = 0;

    private int id;
    private Livre livre;
    private int idUtilisateur;
    private TypeUtilisateur typeUtilisateur;
    private int ordreReservation;
    private StatutReservation statut;

    public static int prochainID() {
        compteurID++;
        return compteurID;
    }

    public static int prochainOrdreReservation() {
        compteurOrdre++;
        return compteurOrdre;
    }

    public Reservation(Livre livre, int idUtilisateur, TypeUtilisateur typeUtilisateur) {
        this.id = prochainID();
        this.livre = livre;
        this.idUtilisateur = idUtilisateur;
        this.typeUtilisateur = typeUtilisateur;
        this.ordreReservation = prochainOrdreReservation();
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

    private int getPriorite() {
        if (typeUtilisateur == TypeUtilisateur.PROFESSEUR) return 0;
        if (typeUtilisateur == TypeUtilisateur.PERSONNEL) return 1;
        return 2;
    }

    @Override
    public int compareTo(Reservation autre) {
        int maPriorite = this.getPriorite();
        int autrePriorite = autre.getPriorite();

        if (maPriorite < autrePriorite) return -1;
        if (maPriorite > autrePriorite) return 1;

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
