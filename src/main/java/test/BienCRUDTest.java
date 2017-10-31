package test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.janus.mvn.crous.draft.crud.BienCRUD;
import com.janus.mvn.crous.draft.entites.Bien;

public class BienCRUDTest {
	
	BienCRUD bienCRUD = new BienCRUD();
	
	@Test
	public void testAjoutBienById() {
		
		System.out.println("==================== TEST AJOUT BIEN BY ID ===============================");
		// Compter le nombre de biens dans la base
		
		Integer nbTotalBiensAvantAjout = bienCRUD.nombreTotalBiens();
		
		// Création d'un nouveau bien
		Bien bien = new Bien(1521);
		
		// Enregistrer le bien dans la base de données
		bienCRUD.creerBien(1521);
		
		// Compter à nouveau le nombre de biens dans la bdd
		Integer nbTotalBiensApresAjout = bienCRUD.nombreTotalBiens();
		
		System.out.println("Avant : " + nbTotalBiensAvantAjout);
		
		System.out.println("Après : " + nbTotalBiensApresAjout);
		
		assertEquals("Comptage", nbTotalBiensAvantAjout+1, nbTotalBiensApresAjout.intValue());
	}
	
	@Test
	public void testAjoutBien() {
		
		System.out.println("==================== TEST AJOUT BIEN ===============================");
		// Compter le nombre de biens dans la base
		
		Integer nbTotalBiensAvantAjout = bienCRUD.nombreTotalBiens();
		
		// Création d'un nouveau bien
		// Bien bien = new Bien(1521, "Villa", 1);
		
		// Enregistrer le bien dans la base de données
		bienCRUD.creerBien(1521, "Villa", 1);
		
		// Compter à nouveau le nombre de biens dans la bdd
		int nbTotalBiensApresAjout = bienCRUD.nombreTotalBiens();
		
		System.out.println("Avant : " + nbTotalBiensAvantAjout);
		
		System.out.println("Après : " + nbTotalBiensApresAjout);
		
		assertEquals("Comptage", nbTotalBiensAvantAjout+1, nbTotalBiensApresAjout);
	}
	
	@Test
	public void testMiseAJourBien(){
		System.out.println("==================== TEST MISE A JOUR BIEN ===============================");
		Bien dernierBien = bienCRUD.getDernierBien();
		int adresseBienRecup = dernierBien.getAdresse();
	}
	
	 @Test
	 public void testRecupererDernierBien(){
		 // Ajout d'un bien dans la base
		 bienCRUD.creerBien(1558);
		 
		 // Récupération du bien avec son ID
		 Bien bienRecupere = bienCRUD.getBienbyId(1558);
		 
		 // Récupération du dernier bien
		 Bien dernierBienRecupere = bienCRUD.getDernierBien();
		 
		 // Vérifier qu'il s'agit bien du dernier bien
		 assertEquals("Test dernier bien", bienRecupere.getAdresse(), dernierBienRecupere.getAdresse());
		 
	 }
	 
	@Test
	public void testRecupererDernierBien2(){
		
		// Récupération du dernier élement
		Bien dernierBien = bienCRUD.getDernierBien();
		
		// Incrémentation de l'ID
		dernierBien.setAdresse(dernierBien.getAdresse() + 1);
		
		// Enrégistrement du nouveau bien
		bienCRUD.creerBien(dernierBien.getAdresse());
		
		// Récupération du bien
		Bien dernierBienRecupere = bienCRUD.getDernierBien();
		
		// Vérification que le dernier bien récupéré (Adresse) correspond au premier dernier bien modifié (Adresse)
		assertEquals(dernierBien.getAdresse(), dernierBienRecupere.getAdresse());
		
	}
	
	@Test
	public void testRecupBien(){
		System.out.println("==================== TEST RECUPERATION BIEN ===============================");
		int idBienACreer = 999;
		bienCRUD.creerBien(idBienACreer);
		Bien bienRecup = bienCRUD.getBienbyId(idBienACreer);
		assertEquals("Test récupération d'un bien", idBienACreer, bienRecup.getAdresse());
	}
	
	@Test
	public void testSuppressionBien(){
		
		System.out.println("==================== TEST SUPPRESSION BIEN ===============================");
		
		// Comptage
		int nbTotalAvantSuppression = bienCRUD.nombreTotalBiens();
		
		// Suppression du 1er élément
		int idPremierElement = bienCRUD.getPremierBien().getAdresse();
		Boolean suppression = bienCRUD.deleteBienById(idPremierElement);
		
		// Recomptage
		int nbTotalApresSuppression = bienCRUD.nombreTotalBiens();
		
		assertNotEquals("Test Suppression", nbTotalApresSuppression, nbTotalAvantSuppression);
		
		
	}

}
