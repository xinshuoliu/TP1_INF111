package gestionnaireBibliotheque;

public class Etudiant extends Utilisateur {

    public Etudiant(int id, String nom) {
        super(id, nom); // super appelle constructeur parent Utilisateur
    }

    @Override
    public int nombreMaxEmprunts() {
        return 5; 
    }

    @Override
    public double calculerPenalite(int joursRetard) {
        return joursRetard * 0.25; // taux le plus bas
    }

    @Override
    public TypeUtilisateur getTypeUtilisateur() {
        return TypeUtilisateur.ETUDIANT;
    }
}
