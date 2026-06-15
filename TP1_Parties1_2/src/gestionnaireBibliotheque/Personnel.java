package gestionnaireBibliotheque;

public class Personnel extends Utilisateur {

    public Personnel(int id, String nom) {
        super(id, nom);
    }

    @Override
    public int nombreMaxEmprunts() {
        return 7;
    }

    @Override
    public double calculerPenalite(int joursRetard) {
        return joursRetard * 0.50;
    }

    @Override
    public TypeUtilisateur getTypeUtilisateur() {
        return TypeUtilisateur.PERSONNEL;
    }
}
