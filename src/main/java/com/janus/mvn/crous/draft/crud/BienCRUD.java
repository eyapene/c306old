/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.janus.mvn.crous.draft.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.janus.mvn.crous.draft.entites.Bien;
import com.janus.mvn.crous.draft.pontjdbc.PontJDBC;



/**
 *
 * @author k.atsou
 */
public class BienCRUD {
    
    public Boolean creerBien(int idBien){
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
    
    public Boolean creerBien(int idBien, String nature, int idProprietaire){
    	try {
            PreparedStatement ps = PontJDBC.getPreparedStatement("INSERT INTO bien VALUES (?,?,?)");
            ps.setInt(1, idBien);
            ps.setString(2, nature);
            ps.setInt(3, idProprietaire);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    	System.out.println("Le bien a été ajouté avec succès");
    	return true;
    }
    
    public Boolean creerBienAdresseAuto(String nature, int idProprietaire){
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
	
	public Boolean majBien(){
		
		return null;
	}
	
	public Bien getBienbyId(int idBien){
		ResultSet rs;
		Bien bien = new Bien();
		try {
            PreparedStatement ps = PontJDBC.getPreparedStatement("SELECT * FROM bien WHERE id_bien = ?");
            ps.setInt(1, idBien);
            rs = ps.executeQuery();
            while (rs.next()){
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
	
	public Bien getPremierBien(){
		ResultSet rs;
		Bien bien = new Bien();
		try {
            PreparedStatement ps = PontJDBC.getPreparedStatement("SELECT * FROM bien ORDER BY id_bien ASC LIMIT 1");
            rs = ps.executeQuery();
            while (rs.next()){
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
	
	public Bien getDernierBien(){
		ResultSet rs;
		Bien bien = new Bien();
		try {
            PreparedStatement ps = PontJDBC.getPreparedStatement("SELECT * FROM bien ORDER BY id_bien DESC LIMIT 1");
            rs = ps.executeQuery();
            while (rs.next()){
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
	
	public Boolean deleteBienById(int idBien){
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

	public int nombreTotalBiens() {
		ResultSet rs;
		int nbreBiens = 0;
		try {
            PreparedStatement ps = PontJDBC.getPreparedStatement("SELECT count(*) FROM `bien` ");
            rs = ps.executeQuery();
            // System.out.println(rs.toString());
            while (rs.next()){
            	nbreBiens = rs.getInt(1);
            }
            
		} catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PontJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
		return nbreBiens;
	}

	public void creerBien(Bien bien) {
		// TODO Auto-generated method stub
		
	}
  
    
}