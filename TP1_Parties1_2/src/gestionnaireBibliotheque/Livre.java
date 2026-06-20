package gestionnaireBibliotheque;

public class Livre {

    public static final int DUREE_MAX_EMPRUNT = 40; // utilise par Emprunt pour calculer date limite

    private static int compteur = 0; 

    private int id;
    private String titre;
    private String auteur;
    private String categorie;
    private StatutLivre statut;

    public static int prochainID() {
        compteur++;
        return compteur;
    }

    public Livre(String titre, String auteur, String categorie) {
        this.id = prochainID();
        this.titre = titre;
        this.auteur = auteur;
        this.categorie = categorie;
        this.statut = StatutLivre.DISPONIBLE; // tout livre commence dispo
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getCategorie() {
        return categorie;
    }

    public StatutLivre getStatut() {
        return statut;
    }

    public void setStatut(StatutLivre statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Livre{id=" + id
                + ", titre=" + titre
                + ", auteur=" + auteur
                + ", categorie=" + categorie
                + ", statut=" + statut + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // meme reference en memoire
        if (!(obj instanceof Livre)) return false; // pas un livre, impossible d'etre egal
        Livre autre = (Livre) obj;
        return this.id == autre.id; // deux livres sont egaux si meme id
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }


}
