package com.devcaotics.infamus.model.repository;

import java.sql.SQLException;

import com.devcaotics.infamus.model.entities.Professor;

public interface Repository <C, KEY>{

	public void create(C c) throws SQLException;
	public Professor update(C c) throws SQLException;
	public C read(KEY k) throws SQLException;
	public void delete(KEY k) throws SQLException;

	C readByEmail(String email) throws SQLException;
}
