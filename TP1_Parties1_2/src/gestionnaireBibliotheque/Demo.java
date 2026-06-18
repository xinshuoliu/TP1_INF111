package gestionnaireBibliotheque;

public class Demo {

    public static void main(String[] args) {

        // Section 1 - Livre

        Livre livre1 = new Livre("Mathematiques", "Bernard", "Sciences");
        Livre livre2 = new Livre("Physique", "Leroy", "Sciences");
        Livre livre3 = new Livre("Histoire", "Simon", "Sociales");

        System.out.println(livre1);
        System.out.println(livre2);
        System.out.println(livre3);


        // Section 2 - Emprunt

        Etudiant etudiant = new Etudiant(101, "Lucas");

        Emprunt emprunt = new Emprunt(livre1, etudiant.getId(), 5);
        System.out.println(emprunt);
        System.out.println("Jours de retard : " + emprunt.calculerJoursRetard(50));

        // jour retour prevu doit etre 45
        System.out.println("Jour retour prevu : " + emprunt.getJourRetourPrevu());

        System.out.println("En retard au jour 44 : " + emprunt.estEnRetard(44));
        System.out.println("En retard au jour 46 : " + emprunt.estEnRetard(46));

        Emprunt empruntAtemps = new Emprunt(livre2, etudiant.getId(), 5);
        empruntAtemps.retourner(44);
        System.out.println("Statut retour a temps : " + empruntAtemps.getStatut());

        Emprunt empruntRetard = new Emprunt(livre3, etudiant.getId(), 5);
        empruntRetard.retourner(50);
        System.out.println("Statut retour en retard : " + empruntRetard.getStatut());


        // Section 3 - Penalites

        Personnel personnel = new Personnel(102, "Marie");
        Professeur professeur = new Professeur(103, "Jean");

        System.out.println("Penalite etudiant 10j : " + etudiant.calculerPenalite(10));
        System.out.println("Penalite personnel 10j : " + personnel.calculerPenalite(10));
        System.out.println("Penalite professeur 10j : " + professeur.calculerPenalite(10));


        // Section 4 - Reservations et priorite

        Livre livreReserve = new Livre("Biologie", "Moreau", "Sciences");
        livreReserve.setStatut(StatutLivre.EMPRUNTE);

        // etudiant arrive en premier mais le professeur est prioritaire
        Reservation resEtudiant = new Reservation(livreReserve, etudiant.getId(), TypeUtilisateur.ETUDIANT);
        Reservation resProfesseur = new Reservation(livreReserve, professeur.getId(), TypeUtilisateur.PROFESSEUR);
        Reservation resPersonnel = new Reservation(livreReserve, personnel.getId(), TypeUtilisateur.PERSONNEL);

        FilePrioriteReservations file = new FilePrioriteReservations();
        file.ajouter(resEtudiant);
        file.ajouter(resProfesseur);
        file.ajouter(resPersonnel);

        // ordre attendu : PROFESSEUR, PERSONNEL, ETUDIANT
        System.out.println("Premier : " + file.consulter());
        System.out.println("Retire 1 : " + file.retirer());
        System.out.println("Retire 2 : " + file.retirer());
        System.out.println("Retire 3 : " + file.retirer());


        // Section 5 - Listes

        ListeLivres listeLivres = new ListeLivres();
        listeLivres.ajouter(livre1);
        listeLivres.ajouter(livre2);
        listeLivres.ajouter(livre3);

        System.out.println("Taille : " + listeLivres.taille());
        System.out.println("Contient id " + livre1.getId() + " : " + listeLivres.contient(livre1.getId()));
        listeLivres.supprimer(livre3.getId());
        System.out.println("Taille apres suppression : " + listeLivres.taille());
        System.out.println(listeLivres);

        ListeEmprunts listeEmprunts = new ListeEmprunts();
        listeEmprunts.ajouter(emprunt);
        listeEmprunts.ajouter(empruntAtemps);

        System.out.println("Taille : " + listeEmprunts.taille());
        System.out.println(listeEmprunts);
    }
}
