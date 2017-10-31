package test;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import com.janus.mvn.crous.draft.crud.BienCRUD;
import com.janus.mvn.crous.draft.crud.PersonneCRUD;
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

		try {
			PreparedStatement ps = PontJDBC
					.getPreparedStatement("INSERT INTO bien(nature, idProprietaire) VALUES (?,?)");
			ps.setString(1, "NATURE");
			ps.setInt(2, 388);
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
		bienCRUD.ajouterBien("Nature test Get dernier Id", personneCRUD.getDernierId());
		int dernierIdApresInsertion = bienCRUD.getDernierId();
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
//		int lastInsertId = bienCRUD.getDernierId();
		Bien bien = new Bien("Nature Test Ajout Objet", personneCRUD.getDernierId());
		bienCRUD.ajouterObjetBien(bien);
		int nbTotalBiensApresAjout = bienCRUD.nombreTotalBiens();
		System.out.println("Après : " + nbTotalBiensApresAjout);
		assertEquals("Comptage", nbTotalBiensAvantAjout + 1, nbTotalBiensApresAjout);
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
//	@Test
//	public void testSuppressionBien(){
//		
//		System.out.println("==================== TEST SUPPRESSION BIEN ===============================");
//		
//		// Comptage
//		int nbTotalAvantSuppression = bienCRUD.nombreTotalBiens();
//		
//		// Suppression du 1er élément
//		int idPremierElement = bienCRUD.getPremierBien().getAdresse();
//		Boolean suppression = bienCRUD.deleteBienById(idPremierElement);
//		
//		// Recomptage
//		int nbTotalApresSuppression = bienCRUD.nombreTotalBiens();
//		
//		assertNotEquals("Test Suppression", nbTotalApresSuppression, nbTotalAvantSuppression);
//		
//		
//	}

}
