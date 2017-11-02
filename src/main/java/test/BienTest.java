package test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.janus.mvn.crous.draft.crud.PersonneCRUD;
import com.janus.mvn.crous.draft.entites.Bien;
import com.janus.mvn.crous.draft.entites.Personne;

public class BienTest {
	
	PersonneCRUD personneCRUD = new PersonneCRUD();

	@Test
	public void testComparer() {
		
		Personne personne = new Personne("Nom", "Prenom", "Adresse");
		personneCRUD.flushPersonneTable();
		personneCRUD.ajouterObjetPersonne(personne);
		
		Bien bien = new Bien("Nature test comparaison", 
				personneCRUD.getDernierePersonne().getId());
		
		Bien bien2 = new Bien("Nature test comparaison", 
				personneCRUD.getDernierePersonne().getId());
		
		boolean comparaisonAvantModif = bien.comparer(bien2);
		
		System.out.println(comparaisonAvantModif);
		
		bien2.setNature("Nouvelle nature");
		
		boolean comparaisonApresModif = bien.comparer(bien2);
		
		System.out.println(comparaisonApresModif);
		assertNotEquals("Test comparaison", comparaisonAvantModif, comparaisonApresModif);
		
	}

}
