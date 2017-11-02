package com.janus.mvn.crous.draft.crud.exception;

public class ObjetDejaExistantDansLaTableException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1893423978612279006L;

	public ObjetDejaExistantDansLaTableException(String string) {
		super(string);
	}

	public String getMessage(){
		// return "L'objet existe déjà dans la table";
		return super.getMessage();
	}
	
}
