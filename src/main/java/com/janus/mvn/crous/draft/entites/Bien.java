/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.janus.mvn.crous.draft.entites;

import java.util.HashSet;

import com.janus.mvn.crous.draft.crud.PersonneCRUD;

/**
 *
 * @author k.atsou
 */
public class Bien {
    
    private int adresse;
    
    private Personne proprietaire;
    
    private String nature;

    public Bien(int adresse, Personne proprietaire, String nature) {
        this.adresse = adresse;
        this.proprietaire = proprietaire;
        this.nature = nature;
    }

    public Bien() {
    }

    public Bien(int adresse) {
		this.adresse = adresse;
	}

	public Bien(int adresse, String nature, int idProprietaire) {
		// Personne proprio = new Personne(idProprietaire, "Dupont", "Jean", "Rue du Lac");
		PersonneCRUD personneCRUD = new PersonneCRUD();
		Personne proprio = personneCRUD.getPersonneById(idProprietaire);
		this.adresse = adresse;
		this.proprietaire = proprio;
		this.nature = nature;
	}

	public int getAdresse() {
        return this.adresse;
    }

    public void setAdresse(int adresse) {
        this.adresse = adresse;
    }

    public Personne getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Personne proprietaire) {
        this.proprietaire = proprietaire;
    }
    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }
    
    public String toString(){
    	int addr = this.adresse;
		return "Bien à l'adresse : " + addr;
    	
    }
    
}
