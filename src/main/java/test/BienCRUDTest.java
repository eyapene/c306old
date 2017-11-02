package test;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import com.janus.mvn.crous.draft.crud.BienCRUD;
import com.janus.mvn.crous.draft.crud.PersonneCRUD;
import com.janus.mvn.crous.draft.crud.exception.ObjetDejaExistantDansLaTableException;
import com.janus.mvn.crous.draft.entites.Bien;
import com.janus.mvn.crous.draft.entites.Personne;
import com.janus.mvn.crous.draft.pontjdbc.PontJDBC;

public class BienCRUDTest {
	
	BienCRUD bienCRUD = new BienCRUD();
	PersonneCRUD personneCRUD = new PersonneCRUD();
	
	@Test
	public void testFlushBienTable() {

		System.out.println("==================== TEST PURGE TABLE BIEN ===============================");

		bienCRUD.flushBienTable();
		
		Personne proprietaire = new Personne("Nom Personne pour test Flush Bien", 
											"Prenom Personne pour test Flush Bien", 
											"Adresse Personne pour test Flush Bien");
		personneCRUD.flushPersonneTable();
		personneCRUD.ajouterObjetPersonne(proprietaire);
		
		try {
			PreparedStatement ps = PontJDBC
					.getPreparedStatement("INSERT INTO bien(nature, idProprietaire) VALUES (?,?)");
			ps.setString(1, "NATURE POUR TEST FLUSH BIEN");
			ps.setInt(2, personneCRUD.getDernierId());
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}

		ResultSet rs;
		int nbreBiens = 0;
		try {
			PreparedStatement ps = PontJDBC.getPreparedStatement("SELECT count(*) FROM `bien` ");
			rs = ps.executeQuery();
			// System.out.println(rs.toString());
			while (rs.next()) {
				nbreBiens = rs.getInt(1);
			}
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}

		assertEquals("Test Flush Table Bien. ", 1, nbreBiens);

	}
	
	@Test
	public void testGetDernierId() {
		System.out.println("==================== TEST RECUP DERNIER ID ===============================");
		int dernierIdAvantInsertion = bienCRUD.getDernierId();
		System.out.println("dernierIdAvantInsertion : " + dernierIdAvantInsertion);
		bienCRUD.ajouterBien("Nature test Get dernier Id", personneCRUD.getDernierId());
		int dernierIdApresInsertion = bienCRUD.getDernierId();
		System.out.println("dernierIdApresInsertion : " + dernierIdApresInsertion);
		assertEquals("Test get dernier id", dernierIdAvantInsertion + 1, dernierIdApresInsertion);
	}
	
	@Test
	public void testAjouterBien() {
		System.out.println("==================== TEST AJOUT BIEN ===============================");
		
		// Compter le nombre de biens dans la base
		int nbTotalBiensAvantAjout = bienCRUD.nombreTotalBiens();
		
		int dernierIdProprietaire = personneCRUD.getDernierId();
		System.out.println("Dernier id propriétaire : " + dernierIdProprietaire);

		// Enregistrer le bien dans la base de données
		bienCRUD.ajouterBien("Nature", dernierIdProprietaire);

		// Compter à nouveau le nombre de biens dans la bdd
		int nbTotalBiensApresAjout = bienCRUD.nombreTotalBiens();

		System.out.println("Avant : " + nbTotalBiensAvantAjout);

		System.out.println("Après : " + nbTotalBiensApresAjout);

		assertEquals("Comptage", nbTotalBiensAvantAjout + 1, nbTotalBiensApresAjout);
	}

	@Test
	public void testAjouterBienObjetSansFlush() {
		System.out.println("==================== TEST AJOUT BIEN OBJET SANS FLUSH ===============================");
		bienCRUD.flushBienTable();
		int nbTotalBiensAvantAjout = bienCRUD.nombreTotalBiens();
		System.out.println("Avant : " + nbTotalBiensAvantAjout);
		int lastInsertId = bienCRUD.getDernierId();
		Bien bien = new Bien("Nature Test Ajout Objet", personneCRUD.getDernierId());
		// Bien bien = new Bien("Nature", 59);
		try{
			bienCRUD.ajouterObjetBien(bien);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		int nbTotalBiensApresAjout = bienCRUD.nombreTotalBiens();
		System.out.println("Après : " + nbTotalBiensApresAjout);
		assertEquals("Comptage", nbTotalBiensAvantAjout + 1, nbTotalBiensApresAjout);
	}
	
	@Test
	public void testMajBien(){
		System.out.println("==================== TEST MAJ BIEN ===============================");
		Bien bienAvantModif = bienCRUD.getDernierBien();
		Bien bienModifie = new Bien(bienAvantModif.getAdresse(), bienAvantModif.getNature(), bienAvantModif.getProprietaire().getId());
		bienModifie.setNature(bienModifie.getNature() + " (modifiée pour test Mise à jour)");
		bienCRUD.majBien(bienModifie);
		Bien bienApresModifRecupere = bienCRUD.getBienbyId(bienAvantModif.getAdresse());
		System.out.println("bienAvantModif.getNature() 		   : " + bienAvantModif.getNature());
		System.out.println("bienApresModifRecupere.getNature() : " + bienApresModifRecupere.getNature());
		assertNotEquals(bienApresModifRecupere.getNature(), bienAvantModif.getNature());
	}
	
	@Test
	public void testGetBienById(){
		System.out.println("==================== TEST RECUPERATION BIEN BY ID ===============================");
		int dernierId = bienCRUD.getDernierId();
		System.out.println("Dernier Id : " + dernierId);
		//bienCRUD.flushBienTable();
		Personne personne = new Personne("Nom12212", "Prenom", "Adresse");
		personneCRUD.ajouterObjetPersonne(personne);
		bienCRUD.ajouterBien("Villa", personneCRUD.getDernierId());
		Bien bienRecup = bienCRUD.getBienbyId(dernierId+1);
		System.out.println("bienRecup.getAdresse() : " + bienRecup.getAdresse());
		assertEquals("Test récupération d'un bien.", dernierId + 1, bienRecup.getAdresse());
	}
	
	@Test
	public void testSupprimerBien(){
		System.out.println("==================== TEST SUPPRESSION BIEN ===============================");

		// Flush de la table bien
		bienCRUD.flushBienTable();

		Personne personne = new Personne("Nom", "Prenom", "Adresse");
		personneCRUD.flushPersonneTable();
		personneCRUD.ajouterObjetPersonne(personne);
		
		Bien bien = new Bien("NATURE POUR TEST DE SUPPRESSION", personneCRUD.getDernierId());
		try {
			bienCRUD.ajouterObjetBien(bien);
		} catch (ObjetDejaExistantDansLaTableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Suppression du 1er élément
		bienCRUD.deleteBien(bien);

		// Compter à nouveau le nombre de biens dans la bdd
		int nbTotalBiensApresSuppression = bienCRUD.nombreTotalBiens();

		assertNotEquals("Test Suppression bien.", 0, nbTotalBiensApresSuppression);
	}
	
	@Test
	public void testVerifierUnicite(){
		bienCRUD.flushBienTable();
		Personne personne = new Personne("Nom", "Prenom", "Adresse");
		personneCRUD.flushPersonneTable();
		personneCRUD.ajouterObjetPersonne(personne);
		Bien bien = new Bien("Nature", personneCRUD.getDernierId());
		try {
			bienCRUD.ajouterObjetBien(bien);
		} catch (ObjetDejaExistantDansLaTableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(true, bienCRUD.verifierUniciteBool(bien));
	}
	
	@Test
	public void testListeBiens() {

		System.out.println("==================== TEST LISTE BIENS ===============================");

		// Compter le nombre de biens dans la bdd
		int nbTotalBiensAvantRecup = bienCRUD.nombreTotalBiens();

		// Recupération de la liste
		List<Bien> listeBiensRecup = bienCRUD.recupererListeBiens();

		// Comptage du nombre de biens de la liste
		int nbBiensRecup = listeBiensRecup.size();

		assertEquals("Test Liste", nbTotalBiensAvantRecup, nbBiensRecup);
	}
		
//	@Test
//	public void testMiseAJourBien(){
//		System.out.println("==================== TEST MISE A JOUR BIEN ===============================");
//		Bien dernierBien = bienCRUD.getDernierBien();
//		int adresseBienRecup = dernierBien.getAdresse();
//	}
//	
//	 @Test
//	 public void testRecupererDernierBien(){
//		 // Ajout d'un bien dans la base
//		 bienCRUD.creerBien(1558);
//		 
//		 // Récupération du bien avec son ID
//		 Bien bienRecupere = bienCRUD.getBienbyId(1558);
//		 
//		 // Récupération du dernier bien
//		 Bien dernierBienRecupere = bienCRUD.getDernierBien();
//		 
//		 // Vérifier qu'il s'agit bien du dernier bien
//		 assertEquals("Test dernier bien", bienRecupere.getAdresse(), dernierBienRecupere.getAdresse());
//		 
//	 }
//	 
//	@Test
//	public void testRecupererDernierBien2(){
//		
//		// Récupération du dernier élement
//		Bien dernierBien = bienCRUD.getDernierBien();
//		
//		// Incrémentation de l'ID
//		dernierBien.setAdresse(dernierBien.getAdresse() + 1);
//		
//		// Enrégistrement du nouveau bien
//		bienCRUD.creerBien(dernierBien.getAdresse());
//		
//		// Récupération du bien
//		Bien dernierBienRecupere = bienCRUD.getDernierBien();
//		
//		// Vérification que le dernier bien récupéré (Adresse) correspond au premier dernier bien modifié (Adresse)
//		assertEquals(dernierBien.getAdresse(), dernierBienRecupere.getAdresse());
//		
//	}
//	
//	@Test
//	public void testRecupBien(){
//		System.out.println("==================== TEST RECUPERATION BIEN ===============================");
//		int idBienACreer = 999;
//		bienCRUD.creerBien(idBienACreer);
//		Bien bienRecup = bienCRUD.getBienbyId(idBienACreer);
//		assertEquals("Test récupération d'un bien", idBienACreer, bienRecup.getAdresse());
//	}
//	


}
