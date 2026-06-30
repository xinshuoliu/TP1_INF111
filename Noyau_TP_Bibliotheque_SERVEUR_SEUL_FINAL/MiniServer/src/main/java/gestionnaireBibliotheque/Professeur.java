package gestionnaireBibliotheque;

public class Professeur extends Utilisateur {

    public Professeur(int id, String nom) {
        super(id, nom);
    }

    @Override
    public int nombreMaxEmprunts() {
        return 10;
    }

    @Override
    public double calculerPenalite(int joursRetard) {
        return joursRetard * 0.75;
    }

    @Override
    public TypeUtilisateur getTypeUtilisateur() {
        return TypeUtilisateur.PROFESSEUR;
    }
}
