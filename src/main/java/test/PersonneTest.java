package test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.janus.mvn.crous.draft.entites.Personne;

public class PersonneTest {

	@Test
	public void testComparer() {
		//Personne personne = new Personne("NOM PERSONNE 1", "PrenomPersonne1", "AdressePersonne1");
		
		//Personne personne2 = new Personne("NOM PERSONNE 1", "PrenomPersonne1", "AdressePersonne1");
		
		Personne personne = new Personne("NOM PERSONNE OBJET SANS FLUSH", "PrenomPersonneObjetSansFlushadresse", "AdressePersonneObjetSansFlush");
		
		Personne personne2 = new Personne("NOM PERSONNE OBJET SANS FLUSH", "PrenomPersonneObjetSansFlushadresse", "AdressePersonneObjetSansFlush");
		
		boolean comparaisonAvantModif = personne.comparer(personne2);
		
		System.out.println(comparaisonAvantModif);
		
		personne2.setAdresse("Nouvelle adresse");
		
		boolean comparaisonApresModif = personne.comparer(personne2);
		
		System.out.println(comparaisonApresModif);
		assertNotEquals("Test comparaison", comparaisonAvantModif, comparaisonApresModif);
		
	}

}
