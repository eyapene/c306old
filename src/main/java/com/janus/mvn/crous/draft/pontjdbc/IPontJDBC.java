package com.janus.mvn.crous.draft.pontjdbc;

import java.sql.PreparedStatement;
import java.util.List;

import com.janus.mvn.crous.draft.entites.Bien;

public interface IPontJDBC {

	PreparedStatement connectAndGetPreparedStatement(String sql);
	
}
