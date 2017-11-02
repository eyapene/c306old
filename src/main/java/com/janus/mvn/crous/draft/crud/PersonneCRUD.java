package com.janus.mvn.crous.draft.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.janus.mvn.crous.draft.entites.Bien;
import com.janus.mvn.crous.draft.entites.Personne;
import com.janus.mvn.crous.draft.pontjdbc.PontJDBC;

public class PersonneCRUD {

	public Personne getPersonneById(int idProprietaire) {
		ResultSet rs;
		Personne perso = new Personne();
		try {
			PreparedStatement ps = PontJDBC.getPreparedStatement("SELECT * FROM personne WHERE idPersonne = ?");
			ps.setInt(1, idProprietaire);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String nom = rs.getString(2);
				String prenom = rs.getString(3);
				String adresse = rs.getString(4);
				perso.setId(id);
				perso.setNom(nom);
				perso.setPrenom(prenom);
				perso.setAdresse(adresse);
			}
		} catch (SQLException | ClassNotFoundException ex) {
			System.out.println("Erreur de récupération de la personne");
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("La personne a été bien récupérée");
		return perso;
	}

	public Boolean ajouterPersonne(int idPersonneACreer, String nom, String prenom, String adresse) {
		try {
			PreparedStatement ps = PontJDBC.getPreparedStatement("INSERT INTO personne VALUES (?,?,?,?)");
			ps.setInt(1, idPersonneACreer);
			ps.setString(2, nom);
			ps.setString(3, prenom);
			ps.setString(4, adresse);
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		System.out.println("La personne a été ajoutée avec succès");
		return true;
	}

	public Boolean ajouterPersonneIdAuto(String nom, String prenom, String adresse) {
		try {
			PreparedStatement ps = PontJDBC
					.getPreparedStatement("INSERT INTO personne(nom, prenom, adresse) VALUES (?,?,?)");
			ps.setString(1, nom);
			ps.setString(2, prenom);
			ps.setString(3, adresse);
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		System.out.println("La personne a été ajoutée avec succès");
		return true;
	}

	public int nombreTotalPersonnes() {
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
		return nbrePersonnes;
	}

	public void flushPersonneTable() {
		try {
			PreparedStatement ps = PontJDBC.getPreparedStatement("DELETE FROM personne");
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("Flush de la table personne : la liste des personnes a été bien supprimée de la bdd");
	}

	public int getDernierId() {

		ResultSet rs;
		int dernierId = 0;
		try {
			PreparedStatement ps = PontJDBC
					.getPreparedStatement("SELECT * FROM personne ORDER BY idPersonne DESC LIMIT 1");
			rs = ps.executeQuery();
			while (rs.next()) {
				dernierId = rs.getInt(1);
			}
		} catch (SQLException | ClassNotFoundException ex) {
			System.out.println("Erreur de récupération du dernier Id");
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("Le dernier Id a été bien récupéré");
		return dernierId;
	}

	public void ajouterObjetPersonne(Personne personne) throws RuntimeException {
		int idPersonneACreer = personne.getId();
		String nom = personne.getNom();
		String prenom = personne.getPrenom();
		String adresse = personne.getAdresse();
		try {
			PreparedStatement ps = PontJDBC.getPreparedStatement("INSERT INTO personne VALUES (?,?,?,?)");
			ps.setInt(1, idPersonneACreer);
			ps.setString(2, nom);
			ps.setString(3, prenom);
			ps.setString(4, adresse);
			if (!verifierUniciteBool(personne)) {
				ps.executeUpdate();
			} else {
				throw new RuntimeException("La personne a ajouter existe deja dans la base");
			}
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("La personne a été ajoutée avec succès");
	}

	public void ajouterObjetPersonneIdAuto(Personne personne) {
		String nom = personne.getNom();
		String prenom = personne.getPrenom();
		String adresse = personne.getAdresse();
		try {
			PreparedStatement ps = PontJDBC
					.getPreparedStatement("INSERT INTO personne(nom, prenom, adresse) VALUES (?,?,?)");
			ps.setString(1, nom);
			ps.setString(2, prenom);
			ps.setString(3, adresse);
			if (!verifierUniciteBool(personne)) {
				ps.executeUpdate();
			} else {
				throw new RuntimeException("La personne a ajouter existe deja dans la base");
			}
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("La personne a été ajoutée avec succès");
	}

	public void verifierUnicite(Personne personne) {
		Personne personne1 = new Personne("NOM PERSONNE OBJET SANS FLUSH", "PrenomPersonneObjetSansFlushadresse",
				"AdressePersonneObjetSansFlush");

		Personne personne2 = new Personne("NOM PERSONNE OBJET SANS FLUSH", "PrenomPersonneObjetSansFlushadresse",
				"AdressePersonneObjetSansFlush");
		System.out.println("Test comparaison : " + personne1.comparer(personne2));
		List<Personne> listePersonneRecup = recupererListePersonnes();
		for (Personne p : listePersonneRecup) {
			String nomPerso = p.getNom(), prenomPerso = p.getPrenom(), adressePerso = p.getAdresse();
			Personne perso = new Personne(nomPerso, prenomPerso, adressePerso);
			System.out.println("Comparaison ...");
			System.out.println("Resultat de la comparaison : " + perso.comparer(personne));
			System.out.println("Comparaison de " + personne.toString());
			System.out.println(" et ");
			System.out.println(perso.toString());
			System.out.println("Résultat : " + perso.comparer(personne));
			if (perso.comparer(personne)) {
				throw new RuntimeException("La personne a ajouter existe deja dans la base");
			}
		}
	}

	public boolean verifierUniciteBool(Personne personne) {
		boolean retour = true;
		Personne personne1 = new Personne("NOM PERSONNE OBJET SANS FLUSH", "PrenomPersonneObjetSansFlushadresse",
				"AdressePersonneObjetSansFlush");

		Personne personne2 = new Personne("NOM PERSONNE OBJET SANS FLUSH", "PrenomPersonneObjetSansFlushadresse",
				"AdressePersonneObjetSansFlush");
		System.out.println("Test comparaison : " + personne1.comparer(personne2));
		List<Personne> listePersonneRecup = recupererListePersonnes();
		if (listePersonneRecup.size() == 0) {
			return false;
		}
		for (Personne p : listePersonneRecup) {
			String nomPerso = p.getNom(), prenomPerso = p.getPrenom(), adressePerso = p.getAdresse();
			Personne perso = new Personne(nomPerso, prenomPerso, adressePerso);
			System.out.println("Comparaison ...");
			System.out.println("Resultat de la comparaison : " + perso.comparer(personne));
			System.out.println("Comparaison de " + personne.toString());
			System.out.println(" et ");
			System.out.println(perso.toString());
			System.out.println("Résultat : " + perso.comparer(personne));
			if (perso.comparer(personne)) {
				retour = true;
			} else {
				retour = false;
			}
		}
		System.out.println("bjr");
		return retour;
	}

	public void deleteObjetPersonne(Personne personne) {
		try {
			PreparedStatement ps = PontJDBC.getPreparedStatement("DELETE FROM personne WHERE idPersonne=?");
			ps.setInt(1, personne.getId());
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("La personne a été bien supprimée");
	}

	public List<Personne> recupererListePersonnes() {
		ResultSet rs;
		List<Personne> listePersonnes = new ArrayList<Personne>();
		try {
			PreparedStatement ps = PontJDBC.getPreparedStatement("SELECT * FROM personne");
			rs = ps.executeQuery();
			while (rs.next()) {
				Personne perso = new Personne();
				int id = rs.getInt(1);
				String nom = rs.getString(2);
				String prenom = rs.getString(3);
				String adresse = rs.getString(4);
				perso.setId(id);
				perso.setNom(nom);
				perso.setPrenom(prenom);
				perso.setAdresse(adresse);
				listePersonnes.add(perso);
			}
		} catch (SQLException | ClassNotFoundException ex) {
			System.out.println("Erreur de récupération de la liste des personnes");
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("La liste des personnes a été bien récupérée");
		return listePersonnes;
	}

	public Personne getDernierePersonne() {
		ResultSet rs;
		Personne personne = new Personne();
		try {
			PreparedStatement ps = PontJDBC
					.getPreparedStatement("SELECT * FROM personne ORDER BY idPersonne DESC LIMIT 1");
			rs = ps.executeQuery();
			while (rs.next()) {
				personne.setId(rs.getInt(1));
				personne.setNom(rs.getString(2));
				personne.setPrenom(rs.getString(3));
				personne.setAdresse(rs.getString(4));
			}
		} catch (SQLException | ClassNotFoundException ex) {
			System.out.println("Erreur de récupération de la dernière personne");
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("La dernière personne été bien récupéré");
		return personne;
	}

	public void update(Personne personneModifiee) {
		
			try{
				PreparedStatement ps = PontJDBC.getPreparedStatement("UPDATE personne SET nom = ?, "
						+ "prenom = ?, adresse = ? WHERE idPersonne = ?");
					ps.setString(1, personneModifiee.getNom());
					ps.setString(2, personneModifiee.getPrenom());
					ps.setString(3, personneModifiee.getAdresse());
					ps.setInt(4, personneModifiee.getId());
					ps.executeUpdate();
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e){
				e.printStackTrace();
			}
			
//			if (!verifierUniciteBool(personne)){
				
//			}else{
//				throw new RuntimeException("La personne a ajouter existe deja dans la base");
//			}
		
		System.out.println("La personne a été modifiée avec succès");
	}
}
