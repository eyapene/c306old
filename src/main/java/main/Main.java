package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.janus.mvn.crous.draft.crud.BienCRUD;
import com.janus.mvn.crous.draft.entites.Bien;

public class Main {

	public static void main(String[] args) {
		// //System.out.println("Hello World!!");
		//
		// // Compter le nombre de biens dans la base
		// BienCRUD bienCRUD = new BienCRUD();
		// Integer nbTotalBiensAvantAjout = bienCRUD.nombreTotalBiens();
		//
		// // Création d'un nouveau bien
		// Bien bien = new Bien(1521);
		//
		// // Enregistrer le bien dans la base de données
		// bienCRUD.creerBien(bien);
		//
		// // Compter à nouveau le nombre de biens dans la bdd
		// Integer nbTotalBiensApresAjout = bienCRUD.nombreTotalBiens();
		//
		// System.out.println("Nombre avant ajout : " + nbTotalBiensAvantAjout
		// + ". Nombre après ajout : " + nbTotalBiensApresAjout);

		// Création d'un nouveau bien

		BienCRUD bienCRUD = new BienCRUD();
		// Bien bien = new Bien(1521);

		// Enregistrer le bien dans la base de données
		bienCRUD.creerBien(1521);

	}

}
