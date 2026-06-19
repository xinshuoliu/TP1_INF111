package gestionnaireBibliotheque;

public class Emprunt {

    private static int compteur = 0; // partage entre tous les emprunts

    private int id;
    private Livre livre;
    private int idUtilisateur;
    private int jourEmprunt;
    private int jourRetourPrevu;
    private int jourRetour;
    private StatutEmprunt statut;

    public static int prochainID() {
        compteur++;
        return compteur;
    }

    public Emprunt(Livre livre, int idUtilisateur, int jourEmprunt) {
        this.id = prochainID();
        this.livre = livre;
        this.idUtilisateur = idUtilisateur;
        this.jourEmprunt = jourEmprunt;
        this.jourRetourPrevu = jourEmprunt + Livre.DUREE_MAX_EMPRUNT; // date limite = debut + duree max
        this.jourRetour = 0; // 0 = pas encore rendu
        this.statut = StatutEmprunt.EN_COURS;
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

    public int getJourEmprunt() {
        return jourEmprunt;
    }

    public int getJourRetourPrevu() {
        return jourRetourPrevu;
    }

    public int getJourRetour() {
        return jourRetour;
    }

    public StatutEmprunt getStatut() {
        return statut;
    }

    public void setStatut(StatutEmprunt statut) {
        this.statut = statut;
    }

    public boolean estEnRetard(int jourActuel) {
        return jourActuel > jourRetourPrevu;   // le meme jour est correct
    }

    public int calculerJoursRetard(int jourActuel) {
        if (!estEnRetard(jourActuel)) {
            return 0; // pas retard
        }
        return jourActuel - jourRetourPrevu;
    }

    public void retourner(int jourRetour) {
        this.jourRetour = jourRetour;
        if (estEnRetard(jourRetour)) {
            this.statut = StatutEmprunt.EN_RETARD;
        } else {
            this.statut = StatutEmprunt.RETOURNE; // rendu a temps
        }
    }

    @Override
    public String toString() {
        return "Emprunt{id=" + id
                + ", idLivre=" + livre.getId()
                + ", idUtilisateur=" + idUtilisateur
                + ", jourEmprunt=" + jourEmprunt
                + ", jourRetourPrevu=" + jourRetourPrevu
                + ", jourRetour=" + jourRetour
                + ", statut=" + statut + "}";
    }
}
