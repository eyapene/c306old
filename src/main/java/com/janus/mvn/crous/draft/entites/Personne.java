/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.janus.mvn.crous.draft.entites;

import java.util.HashSet;

/**
 *
 * @author k.atsou
 */
public class Personne {

	private int id;

	private String nom;

	private String prenom;

	private String adresse;

	private HashSet<Bien> biens;

	public Personne() {
	}

	public Personne(int id, String nom, String prenom, String adresse) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
	}

	public Personne(String nom, String prenom, String adresse) {
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
	}

	public int getId() {
		return id;
	}

	public void setId(int numero) {
		this.id = numero;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public HashSet<Bien> getBiens() {
		return biens;
	}

	public void setBiens(HashSet<Bien> biens) {
		this.biens = biens;
	}

	public Boolean comparer(Personne personneCiblePourComparaison) {
//		if (this.nom == personneCiblePourComparaison.getNom() && this.prenom == personneCiblePourComparaison.getPrenom()
//				&& this.adresse == personneCiblePourComparaison.getAdresse()) {
//			return true;
//		}else{
//			return false;
//		}
		if (this.nom.equalsIgnoreCase(personneCiblePourComparaison.getNom()) &&
			this.prenom.equalsIgnoreCase(personneCiblePourComparaison.getPrenom()) &&
			this.adresse.equalsIgnoreCase(personneCiblePourComparaison.getAdresse())) {
			return true;
		}else{
			return false;
		}
	}

	public String toString() {

		return "Personne : nom = " + nom + ", prenom = " + prenom + ", adresse = " + adresse;

	}

}
