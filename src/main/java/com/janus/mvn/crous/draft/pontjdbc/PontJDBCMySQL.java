package com.janus.mvn.crous.draft.pontjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.janus.mvn.crous.draft.entites.Bien;

public class PontJDBCMySQL implements IPontJDBC {

	private String driver;
	private String url;
	private String user;
	private String pwd;
	
	public PontJDBCMySQL(String driver, String url, String user, String pwd) {
		super();
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.pwd = pwd;
	}

	
	@Override
	public PreparedStatement connectAndGetPreparedStatement(String sql) {
		// super.url ="jdbc:mysql://"+super.host+"/"+this.db;
		try {
			this.getPreparedStatement(sql);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public PreparedStatement getPreparedStatement(String sql) throws SQLException, ClassNotFoundException {
        PreparedStatement ps = null;

        Class.forName(this.driver);
//        String url = "jdbc:sqlserver://localhost:1433;databaseName=CRUD_News";
//        String user = "sa";
//        String pass = "TogoBB2016";
        Connection con = DriverManager.getConnection(this.url, this.user, this.pwd);
        ps = con.prepareStatement(sql);

        return ps;
    }

}
