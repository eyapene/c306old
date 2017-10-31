package test;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import com.janus.mvn.crous.draft.crud.PersonneCRUD;
import com.janus.mvn.crous.draft.entites.Bien;
import com.janus.mvn.crous.draft.entites.Personne;
import com.janus.mvn.crous.draft.pontjdbc.PontJDBC;

public class PersonneCRUDTest {

	PersonneCRUD personneCRUD = new PersonneCRUD();

	@Test
	public void testAjouterPersonneIdAuto() {
		System.out.println("==================== TEST AJOUT PERSONNE ID AUTO ===============================");
		// Compter le nombre de personnes dans la base

		int nbTotalPersonnesAvantAjout = personneCRUD.nombreTotalPersonnes();

		// Création d'un nouveau bien
		// Bien bien = new Bien(1521, "Villa", 1);

		// Enregistrer le bien dans la base de données
		personneCRUD.ajouterPersonneIdAuto("NOM PERSONNE AJOUT ID AUTO", "Prenom", "Adresse");

		// Compter à nouveau le nombre de biens dans la bdd
		int nbTotalPersonnesApresAjout = personneCRUD.nombreTotalPersonnes();

		System.out.println("Avant : " + nbTotalPersonnesAvantAjout);

		System.out.println("Après : " + nbTotalPersonnesApresAjout);

		assertEquals("Comptage", nbTotalPersonnesAvantAjout + 1, nbTotalPersonnesApresAjout);
	}

	@Test
	public void testGetPersonneById() {
		System.out.println("==================== TEST RECUPERATION PERSONNE BY ID ===============================");
		int dernierId = personneCRUD.getDernierId();
		Boolean creationPersonne = personneCRUD.ajouterPersonneIdAuto("NOM PERSONNE RECUP BY ID", "Prenom", "Adresse");
		Personne personneRecup = personneCRUD.getPersonneById(dernierId + 1);
		assertEquals("Test récupération d'une personne.", dernierId + 1, personneRecup.getId());
	}

	@Test
	public void testGetPersonneByIdInsertionIdManuelle() {
		System.out.println("==================== TEST RECUPERATION PERSONNE BY ID ===============================");
		int dernierId = personneCRUD.getDernierId();
		// Personne personne = new Personne(dernierId+1, "Babia", "Joseph", "Rue
		// des Bougainvilliers");
		String nom = "NOM PERSONNE AJOUT ID MANUEL";
		String prenom = "Prenom";
		String adresse = "Adresse";
		personneCRUD.ajouterPersonne(dernierId + 1, nom, prenom, adresse);
		Personne personneRecup = personneCRUD.getPersonneById(dernierId + 1);
		assertEquals("Test récupération d'une personne - Vérification Id.", dernierId + 1, personneRecup.getId());
		assertEquals("Test récupération d'une personne - Vérification nom.", nom, personneRecup.getNom());
		assertEquals("Test récupération d'une personne - Vérification prenom.", prenom, personneRecup.getPrenom());
		assertEquals("Test récupération d'une personne - Vérification adresse.", adresse, personneRecup.getAdresse());
	}

	@Test
	public void testAjouterPersonneSansFlush() {
		System.out.println("==================== TEST AJOUT PERSONNE SANS FLUSH ===============================");
		// Compter le nombre de biens dans la base

		int nbTotalPersonnesAvantAjout = personneCRUD.nombreTotalPersonnes();
		System.out.println("Avant : " + nbTotalPersonnesAvantAjout);

		// Création d'un nouveau bien
		// Bien bien = new Bien(1521, "Villa", 1);

		// Récupérer le last insert id --- A implémenter
		int lastInsertId = personneCRUD.getDernierId();
		// Enregistrer le bien dans la base de données
		personneCRUD.ajouterPersonne(lastInsertId + 1, "NOM PERSONNE AJOUT SANS FLUSH ID MANUEL", "Prenom", "Adresse");

		// Compter à nouveau le nombre de biens dans la bdd
		Integer nbTotalPersonnesApresAjout = personneCRUD.nombreTotalPersonnes();

		System.out.println("Après : " + nbTotalPersonnesApresAjout);

		assertEquals("Comptage", nbTotalPersonnesAvantAjout + 1, nbTotalPersonnesApresAjout.intValue());
	}

	@Test
	public void testAjouterPersonneAvecFlush() {
		System.out.println("==================== TEST AJOUT PERSONNE AVEC FLUSH ===============================");
		personneCRUD.flushPersonneTable();
		personneCRUD.ajouterPersonneIdAuto("NOM PERSONNE AJOUT SANS FLUSH ID AUTO", "Prenom", "Adresse");
		assertEquals(1, personneCRUD.nombreTotalPersonnes());
	}

	@Test
	public void testNombreTotalPersonnes() {
		System.out.println("==================== TEST COMPTAGE PERSONNES ===============================");
		personneCRUD.flushPersonneTable();
		personneCRUD.ajouterPersonneIdAuto("NOM PERSONNE AJOUT ID AUTO TEST COMPTAGE", "Prenom", "Adresse");
		assertEquals(1, personneCRUD.nombreTotalPersonnes());
	}

	@Test
	public void testFlushPersonneTable() {

		System.out.println("==================== TEST PURGE TABLE PERSONNE ===============================");

		personneCRUD.flushPersonneTable();

		try {
			PreparedStatement ps = PontJDBC
					.getPreparedStatement("INSERT INTO personne(nom, prenom, adresse) VALUES (?,?,?)");
			ps.setString(1, "NOM PERSONNE TEST JDBC/MYSQL");
			ps.setString(2, "Prenom");
			ps.setString(3, "Adresse");
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}

		ResultSet rs;
		int nbrePersonnes = 0;
		try {
			PreparedStatement ps = PontJDBC.getPreparedStatement("SELECT count(*) FROM `personne` ");
			rs = ps.executeQuery();
			// System.out.println(rs.toString());
			while (rs.next()) {
				nbrePersonnes = rs.getInt(1);
			}
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}

		assertEquals(1, nbrePersonnes);

	}

	@Test
	public void testGetDernierId() {
		System.out.println("==================== TEST RECUP DERNIER ID ===============================");
		int dernierIdAvantInsertion = personneCRUD.getDernierId();
		personneCRUD.ajouterPersonneIdAuto("NOM PERSONNE TEST RECUP DERNIER ID", "Prenom", "Adresse");
		int dernierIdApresInsertion = personneCRUD.getDernierId();
		assertEquals(dernierIdAvantInsertion + 1, dernierIdApresInsertion);
	}

	@Test
	public void testAjouterPersonneObjetSansFlush() {
		System.out.println("==================== TEST AJOUT PERSONNE OBJET SANS FLUSH ===============================");
		personneCRUD.flushPersonneTable();
		int nbTotalPersonnesAvantAjout = personneCRUD.nombreTotalPersonnes();
		System.out.println("Avant : " + nbTotalPersonnesAvantAjout);
		int lastInsertId = personneCRUD.getDernierId();
		Personne personne = new Personne(lastInsertId + 1, "NOM PERSONNE OBJET SANS FLUSH",
				"PrenomPersonneObjetSansFlush", "AdressePersonneObjetSansFlush");
		personneCRUD.ajouterObjetPersonne(personne);
		int nbTotalPersonnesApresAjout = personneCRUD.nombreTotalPersonnes();
		System.out.println("Après : " + nbTotalPersonnesApresAjout);
		assertEquals("Comptage", nbTotalPersonnesAvantAjout + 1, nbTotalPersonnesApresAjout);
	}

	@Test
	public void testAjouterPersonneObjetSansFlushAuto() {
		System.out.println("==================== TEST AJOUT PERSONNE OBJET SANS FLUSH ===============================");
		personneCRUD.flushPersonneTable();
		int nbTotalPersonnesAvantAjout = personneCRUD.nombreTotalPersonnes();
		System.out.println("Avant : " + nbTotalPersonnesAvantAjout);
		int lastInsertId = personneCRUD.getDernierId();
		Personne personne = new Personne(lastInsertId + 1, "NOM PERSONNE OBJET SANS FLUSH",
				"PrenomPersonneObjetSansFlush", "AdressePersonneObjetSansFlush");
		personneCRUD.ajouterObjetPersonneIdAuto(personne);
		int nbTotalPersonnesApresAjout = personneCRUD.nombreTotalPersonnes();
		System.out.println("Après : " + nbTotalPersonnesApresAjout);
		assertEquals("Comptage", nbTotalPersonnesAvantAjout + 1, nbTotalPersonnesApresAjout);
	}

	@Test
	public void testCreerPersonneObjetAvecFlush() {
		System.out.println("==================== TEST AJOUT PERSONNE OBJET AVEC FLUSH ===============================");
		personneCRUD.flushPersonneTable();
		Personne personne = new Personne("NOM PERSONNE OBJET SANS FLUSH", "PrenomPersonneObjetSansFlush",
				"AdressePersonneObjetSansFlush");
		personneCRUD.ajouterObjetPersonneIdAuto(personne);
		assertEquals(1, personneCRUD.nombreTotalPersonnes());
	}

	@Test
	public void testSuppressionPersonne() {

		System.out.println("==================== TEST SUPPRESSION PERSONNE ===============================");

		// Flush de la table personne
		personneCRUD.flushPersonneTable();

		// Ajout d'une personne à la table personne
		Personne personne = new Personne("NOM PERSONNE POUR TEST DE SUPPRESSION", "PrenomPersonnePourTestSuppression",
				"AdressePersonnePourTestSuppression");
		personneCRUD.ajouterObjetPersonne(personne);

		// Suppression du 1er élément
		personneCRUD.deleteObjetPersonne(personne);

		// Compter à nouveau le nombre de biens dans la bdd
		int nbTotalPersonnesApresSuppression = personneCRUD.nombreTotalPersonnes();

		assertNotEquals("Test Suppression", 0, nbTotalPersonnesApresSuppression);

	}

	@Test
	public void testListePersonne() {

		System.out.println("==================== TEST LISTE PERSONNE ===============================");

		// Ajout d'une personne à la talbe personne
		Personne personne = new Personne("NOM PERSONNE POUR LISTE PERSONNE", "PrenomPersonnePourListePersonne",
				"AdressePersonnePourListePersonne");
		personneCRUD.ajouterObjetPersonne(personne);

		// Compter le nombre de biens dans la bdd
		int nbTotalPersonnesAvantRecup = personneCRUD.nombreTotalPersonnes();

		// Recupération de la liste
		List<Personne> listePersonneRecup = personneCRUD.recupererListePersonnes();

		// Comptage du nombre de bien de la liste
		int nbPersonnesRecup = listePersonneRecup.size();

		assertEquals("Test Liste", nbTotalPersonnesAvantRecup, nbPersonnesRecup);
	}

	@Test
	public void testUnicitePersonneSansAjout() {
		System.out.println("==================== TEST UNICITE PERSONNE ===============================");

		// Flush de la table personne
		personneCRUD.flushPersonneTable();

		// Ajout d'une personne 1
		Personne personne1 = new Personne("NOM PERSONNE TEST UNICITE", "PrenomPersonneTestUnicite",
				"AdressePersonneTestUnicite");
		personneCRUD.ajouterObjetPersonne(personne1);

		// Ajout d'une personne 2 identique à 1
		Personne personne2 = new Personne("NOM PERSONNE TEST UNICITE", "PrenomPersonneTestUnicite",
				"AdressePersonneTestUnicite");
		
		try{
			personneCRUD.ajouterObjetPersonneIdAuto(personne2);
			fail("Une exception aurait du etre générée");
		}catch(RuntimeException e){
			
		}
		
		// assertEquals("Test unicité.", true, personne1.comparer(personne2));

	}
	
	@Test
	public void testUnicitePersonneAvecAjout() {
		System.out.println("==================== TEST UNICITE PERSONNE AVEC AJOUT ===============================");

		// Flush de la table personne
		personneCRUD.flushPersonneTable();

		// Ajout d'une personne 1
		Personne personne1 = new Personne("NOM PERSONNE TEST UNICITE", "PrenomPersonneTestUnicite",
				"AdressePersonneTestUnicite");
		personneCRUD.ajouterObjetPersonne(personne1);
		
		int nbTotalPersonnesAvantRecup = personneCRUD.nombreTotalPersonnes();

		// Ajout d'une personne 2 identique à 1
		Personne personne2 = new Personne("NOM PERSONNE TEST UNICITE", "PrenomPersonneTestUnicite2",
				"AdressePersonneTestUnicite");
		
		try{
			personneCRUD.ajouterObjetPersonneIdAuto(personne2);
		}catch(RuntimeException e){
			
		}
		
		int nbTotalPersonnesApresRecup = personneCRUD.nombreTotalPersonnes();
		
		assertEquals("Test unicité avec ajout.", nbTotalPersonnesAvantRecup+1, nbTotalPersonnesApresRecup);
	}
	
	@Test
	public void testVerifierUnicite(){
		personneCRUD.flushPersonneTable();
		Personne personne = new Personne("NOM PERSONNE OBJET SANS FLUSH", 
				"PrenomPersonneObjetSansFlush", "AdressePersonneObjetSansFlush");
		personneCRUD.ajouterObjetPersonne(personne);
		assertEquals(true, personneCRUD.verifierUniciteBool(personne));
	}

}
