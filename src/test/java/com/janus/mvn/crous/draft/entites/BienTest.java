package com.janus.mvn.crous.draft.entites;

import static org.junit.Assert.*;

import org.junit.Test;

import com.janus.mvn.crous.draft.entites.Bien;

public class BienTest {

	@Test
	public void testBienInteger() {
		int idValeur = 1521;
		Bien bien = new Bien(idValeur);
		int idObjet = bien.getAdresse();
		System.out.println(bien.toString());
		assertEquals(idValeur, idObjet);
	}

}
