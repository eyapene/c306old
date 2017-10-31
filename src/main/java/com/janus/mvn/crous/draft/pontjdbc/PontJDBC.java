package com.janus.mvn.crous.draft.pontjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PontJDBC {
	
	public static PreparedStatement getPreparedStatement(String sql) throws SQLException, ClassNotFoundException {
        
		PreparedStatement ps = null;
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/bd_crous_test";
        String user = "root";
        String pass = "";
        Connection con = DriverManager.getConnection(url, user, pass);
        // System.out.println(con.toString());
        ps = con.prepareStatement(sql);

        return ps;
    }

}
