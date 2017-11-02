/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.janus.mvn.crous.draft.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.janus.mvn.crous.draft.crud.exception.ObjetDejaExistantDansLaTableException;
import com.janus.mvn.crous.draft.entites.Bien;
import com.janus.mvn.crous.draft.entites.Personne;
import com.janus.mvn.crous.draft.pontjdbc.PontJDBC;

/**
 *
 * @author k.atsou
 */
public class BienCRUD {

	PersonneCRUD personneCRUD = new PersonneCRUD();

	public Boolean creerBien(int idBien) {
		try {
			PreparedStatement ps = PontJDBC.getPreparedStatement("INSERT INTO bien VALUES (?)");
			ps.setInt(1, idBien);
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		System.out.println("Le bien a été ajouté avec succès");
		return true;
	}

	public Boolean creerBienAdresseAuto(String nature, int idProprietaire) {
		try {
			PreparedStatement ps = PontJDBC.getPreparedStatement("INSERT INTO bien VALUES (?,?)");
			ps.setString(1, nature);
			ps.setInt(2, idProprietaire);
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		System.out.println("Le bien a été ajouté avec succès");
		return true;
	}

	public Boolean majBien() {

		return null;
	}

	public Bien getBienbyId(int idBien) {
		ResultSet rs;
		Bien bien = new Bien();
		try {
			PreparedStatement ps = PontJDBC.getPreparedStatement("SELECT * FROM bien WHERE adresse = ?");
			ps.setInt(1, idBien);
			rs = ps.executeQuery();
			while (rs.next()) {
				int adresse = rs.getInt(1);
				String nature = rs.getString(2);
				int idProprietaire = rs.getInt(3);
				bien.setAdresse(adresse);
				bien.setNature(nature);
				bien.setProprietaire(personneCRUD.getPersonneById(idProprietaire));
			}
		} catch (SQLException | ClassNotFoundException ex) {
			System.out.println("Erreur de récupération du bien");
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("Le bien a été bien récupéré");
		return bien;
	}

	public Bien getPremierBien() {
		ResultSet rs;
		Bien bien = new Bien();
		try {
			PreparedStatement ps = PontJDBC.getPreparedStatement("SELECT * FROM bien ORDER BY id_bien ASC LIMIT 1");
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				bien.setAdresse(id);
			}
		} catch (SQLException | ClassNotFoundException ex) {
			System.out.println("Erreur de récupération du bien");
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("Le bien a été bien récupéré");
		return bien;
	}

	public Bien getDernierBien() {
		ResultSet rs;
		Bien bien = new Bien();
		try {
			PreparedStatement ps = PontJDBC.getPreparedStatement("SELECT * FROM bien ORDER BY adresse DESC LIMIT 1");
			rs = ps.executeQuery();
			while (rs.next()) {
				int adresse = rs.getInt(1);
				String nature = rs.getString(2);
				int idProprietaire = rs.getInt(3);
				bien.setAdresse(adresse);
				bien.setNature(nature);
				bien.setProprietaire(personneCRUD.getPersonneById(idProprietaire));
			}
		} catch (SQLException | ClassNotFoundException ex) {
			System.out.println("Erreur de récupération du bien");
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("Le bien a été bien récupéré");
		return bien;
	}

	public Boolean deleteBienById(int idBien) {
		try {
			PreparedStatement ps = PontJDBC.getPreparedStatement("DELETE FROM bien WHERE id_bien = ?");
			ps.setInt(1, idBien);
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException ex) {
			System.out.println("Erreur de suppression");
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		System.out.println("Le bien a été bien supprimé");
		return true;
	}

	public void deleteBien(Bien bien) {
		try {
			PreparedStatement ps = PontJDBC.getPreparedStatement("DELETE FROM bien WHERE adresse = ?");
			ps.setInt(1, bien.getAdresse());
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException ex) {
			System.out.println("Erreur de suppression");
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("Le bien a été bien supprimé");
	}

	public int nombreTotalBiens() {
		ResultSet rs;
		int nbreBiens = 0;
		try {
			PreparedStatement ps = PontJDBC.getPreparedStatement("SELECT count(*) FROM `bien` ");
			rs = ps.executeQuery();
			// System.out.println(rs.toString());
			while (rs.next()) {
				nbreBiens = rs.getInt(1);
			}

		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
		return nbreBiens;
	}

	public void flushBienTable() {
		try {
			PreparedStatement ps = PontJDBC.getPreparedStatement("DELETE FROM bien");
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("Flush de la table bien : la liste des biens a été bien supprimée de la bdd");
	}

	public void creerBien(Bien bien) {
		// TODO Auto-generated method stub

	}

	public void ajouterBien(String nature, int idProprietaire) {
		try {
			PreparedStatement ps = PontJDBC
					.getPreparedStatement("INSERT INTO bien(nature, idProprietaire) VALUES (?,?)");
			ps.setString(1, nature);
			ps.setInt(2, idProprietaire);
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("Le bien a été ajouté avec succès");
	}

	public int getDernierId() {
		ResultSet rs;
		int dernierId = 0;
		try {
			PreparedStatement ps = PontJDBC.getPreparedStatement("SELECT * FROM bien ORDER BY adresse DESC LIMIT 1");
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

	public void ajouterObjetBien(Bien bien) throws ObjetDejaExistantDansLaTableException {
		int idBienACreer = bien.getAdresse();
		String nature = bien.getNature();
		int idProprietaire = bien.getProprietaire().getId();
		try {
			PreparedStatement ps = PontJDBC.getPreparedStatement("INSERT INTO bien VALUES (?,?,?)");
			ps.setInt(1, idBienACreer);
			ps.setString(2, nature);
			ps.setInt(3, idProprietaire);
			if (!verifierUniciteBool(bien)) {
				ps.executeUpdate();
			} else {
				throw new ObjetDejaExistantDansLaTableException("Le bien a ajouter existe deja dans la base");
			}
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("Le bien a été ajouté avec succès");
	}

	public void majBien(Bien bienModifie) {
		String nature = bienModifie.getNature();
		int idProprietaire = bienModifie.getProprietaire().getId();
		try {
			PreparedStatement ps = PontJDBC
					.getPreparedStatement("UPDATE bien SET nature = ?, idProprietaire = ? WHERE adresse = ?");
			ps.setString(1, nature);
			ps.setInt(2, idProprietaire);
			ps.setInt(3, bienModifie.getAdresse());
			// if (!verifierUniciteBool(personne)){
			ps.executeUpdate();
			// }else{
			// throw new RuntimeException("La personne a ajouter existe deja
			// dans la base");
			// }
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("Le bien a été modifié avec succès");
	}

	public boolean verifierUniciteBool(Bien bien) {
		boolean retour = true;
		List<Bien> listeBiensRecup = recupererListeBiens();
		if (listeBiensRecup.size() == 0) {
			return false;
		}
		for (Bien b : listeBiensRecup) {
			Bien bienTemoin = new Bien(b.getNature(), b.getProprietaire().getId());

			if (bienTemoin.comparer(bien)) {
				retour = true;
			} else {
				retour = false;
			}
		}
		return retour;
	}

	public List<Bien> recupererListeBiens() {
		ResultSet rs;
		List<Bien> listeBiens = new ArrayList<Bien>();
		try {
			PreparedStatement ps = PontJDBC.getPreparedStatement("SELECT * FROM bien");
			rs = ps.executeQuery();
			while (rs.next()) {
				Bien bien = new Bien();
				bien.setAdresse(rs.getInt(1));
				bien.setNature(rs.getString(2));
				bien.setProprietaire(personneCRUD.getPersonneById(rs.getInt(3)));
				listeBiens.add(bien);
			}
		} catch (SQLException | ClassNotFoundException ex) {
			System.out.println("Erreur de récupération de la liste des biens");
			Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("La liste des biens a été bien récupérée");
		return listeBiens;
	}

}
