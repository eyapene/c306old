package com.janus.mvn.crous.draft.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.janus.mvn.crous.draft.crud.BienCRUD;
import com.janus.mvn.crous.draft.entites.Bien;

public class BienServiceTest {

	@Test
	public void testAjoutBien() {
		// Compter le nombre de biens dans la base
		BienCRUD bienCRUD = new BienCRUD();
		Integer nbTotalBiensAvantAjout = bienCRUD.nombreTotalBiens();
		
		// Création d'un nouveau bien
		Bien bien = new Bien(1521);
		
		// Enregistrer le bien dans la base de données
		bienCRUD.creerBien(bien);
		
		// Compter à nouveau le nombre de biens dans la bdd
		Integer nbTotalBiensApresAjout = bienCRUD.nombreTotalBiens();
		
		assertEquals("Comptage", nbTotalBiensAvantAjout+1, nbTotalBiensApresAjout.intValue());
	}

}
