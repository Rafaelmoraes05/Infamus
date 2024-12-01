package com.devcaotics.infamus.model.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.devcaotics.infamus.model.entities.Estudante;
import com.devcaotics.infamus.model.entities.Professor;
import com.devcaotics.infamus.model.entities.Relato;

public class RepositoryFacade {
	
	private static RepositoryFacade myself = null;
	
	private Repository<Estudante, Integer> rEstudante;
	private Repository<Relato, Integer> rRelato;
	private Repository<Professor, Integer> rProfessor;
	
	private RepositoryFacade() {
		
		rEstudante = new EstudanteRepository();
		rRelato = new RelatoRepository();
		rProfessor = new ProfessorRepository();

	}
	
	public static RepositoryFacade getInstance() {
		
		if(myself == null)
			myself = new RepositoryFacade();
		
		return myself;
		
	}
	
	public Estudante readEstudante(int codigo) throws SQLException {
		return this.rEstudante.read(codigo);
	}

	public Professor readProfessorEmail(String email) throws SQLException {
		return this.rProfessor.readByEmail(email);
	}

	public Professor readProfessor(Integer codigo) throws SQLException {
		return this.rProfessor.read(codigo);
	}

	public Professor updateProfessor(Professor professor) throws SQLException {
		return this.rProfessor.update(professor);
	}
	
	public void createEstudante(Estudante estudante) throws SQLException {
		rEstudante.create(estudante);
	}
	
	public void createRelato(Relato relato) throws SQLException {
		rRelato.create(relato);
	}

	public void createProfessor(Professor professor) throws SQLException {
		rProfessor.create(professor);
	}
	
	public List<Estudante> readAllEstudantes(){
		try {
			return ((EstudanteRepository)this.rEstudante).readAll();
		} catch (SQLException e) {
			return new ArrayList<>();
		}
	}

	public List<Relato> filterRelatoByCodigoEstudante(int codigo) throws SQLException{
		return ((RelatoRepository)this.rRelato).filterByCodigoEstudante(codigo);
	}

	public List<Relato> filterByCodigoProfessor(int codigo) throws SQLException{
		return ((RelatoRepository)this.rRelato).filterByCodigoProfessor(codigo);
	}
}
