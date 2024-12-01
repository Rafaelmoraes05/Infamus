package com.devcaotics.infamus.model.repository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.devcaotics.infamus.model.entities.Estudante;
import com.devcaotics.infamus.model.entities.Professor;
import com.devcaotics.infamus.model.entities.Relato;

public final class RelatoRepository implements Repository<Relato, Integer> {

	protected RelatoRepository() {

	}

	@Override
	public void create(Relato c) throws SQLException {

		String sql = "insert into relato(data_relato, descricao, codigo_fk_estudante, codigo_fk_professor) values (?, ?, ?, ?)";

		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);

		pstm.setDate(1, new java.sql.Date(c.getData().getTime()));
		pstm.setBytes(2, c.getDescricao().getBytes());
		pstm.setInt(3, c.getEstudante().getCodigo());
		pstm.setInt(4, c.getProfessor().getCodigo());

		pstm.execute();
	}

	@Override
	public Professor update(Relato c) {

		return null;
	}

	@Override
	public Relato read(Integer k) throws SQLException {

		Relato r = null;

		String sql = "select * from relato as r join estudante as e on(r.codigo_fk_estudante = e.codigo_estudante) where r.codigo_relato = "
				+ k;

		ResultSet result = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();

		if (result.next()) {

			r = new Relato();

			r.setCodigo(k);
			r.setData(new Date(result.getDate("data_relato").getTime()));
			r.setDescricao(new String(result.getBytes("descricao")));

			Estudante e = new Estudante();
			e.setCodigo(result.getInt("codigo_estudante"));
			e.setNome(result.getString("nome_estudante"));
			e.setMatricula(result.getString("matricula_estudante"));
			e.setEndereco(result.getString("endereco_estudante"));
			e.setEmail(result.getString("email_estudante"));
			e.setTelefone(result.getString("telefone_estudante"));
			e.setAnoEntrada(result.getInt("ano_entrada"));

			r.setEstudante(e);
		}

		return r;
	}

	@Override
	public void delete(Integer k) {

	}

	@Override
	public Relato readByEmail(String email) throws SQLException {
		return null;
	}

	public List<Relato> filterByCodigoEstudante(int codigoEstudante) throws SQLException {

		String sql = "select * from relato as r join estudante as e on(r.codigo_fk_estudante = e.codigo_estudante) where r.codigo_fk_estudante = "
				+ codigoEstudante;

		ResultSet result = ConnectionManager.getCurrentConnection().prepareStatement(sql).executeQuery();

		List<Relato> relatos = new ArrayList<Relato>();

		Estudante e = null;

		while (result.next()) {

			if (e == null) {
				e = new Estudante();
				e.setCodigo(result.getInt("codigo_estudante"));
				e.setNome(result.getString("nome_estudante"));
				e.setMatricula(result.getString("matricula_estudante"));
				e.setEndereco(result.getString("endereco_estudante"));
				e.setEmail(result.getString("email_estudante"));
				e.setTelefone(result.getString("telefone_estudante"));
				e.setAnoEntrada(result.getInt("ano_entrada"));

			}

			Relato r = new Relato();

			r.setCodigo(result.getInt("codigo_relato"));
			r.setData(new Date(result.getDate("data_relato").getTime()));
			r.setDescricao(new String(result.getBytes("descricao")));
			
			r.setEstudante(e);
			
			relatos.add(r);

		}
		return relatos;

	}

	public List<Relato> filterByCodigoProfessor(int codigoProfessor) throws SQLException {

		String sql = "SELECT * FROM relato AS r JOIN professor AS p ON r.codigo_fk_professor = p.codigo_professor WHERE r.codigo_fk_professor = ?";

		Connection conn = ConnectionManager.getCurrentConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, codigoProfessor);

		ResultSet result = stmt.executeQuery();

		List<Relato> relatos = new ArrayList<>();

		Professor p = null;

		if (!result.isBeforeFirst()) {
			System.out.println("Nenhum relato encontrado para o professor com código: " + codigoProfessor);
		} else {
			while (result.next()) {
				System.out.println("Entrou no while"); 

				if (p == null) {
					p = new Professor();
					p.setNome(result.getString("nome_professor"));
					p.setEmail(result.getString("email_professor"));
					p.setSenha(result.getString("senha_professor"));
					p.setCodigo(result.getInt("codigo_professor"));
				}

				Relato r = new Relato();
				r.setCodigo(result.getInt("codigo_relato"));
				r.setData(new Date(result.getDate("data_relato").getTime()));
				r.setDescricao(result.getString("descricao"));
				r.setProfessor(p);

				relatos.add(r);
			}
		}

		return relatos;
	}




	public static void main(String args[]) {
		
		Relato r = new Relato();
		r.setData(new Date());
		r.setDescricao("Me fez raiva o dia todo");
		Estudante e =  new Estudante();
		e.setCodigo(2);
		
		r.setEstudante(e);
		
		RelatoRepository rr = new RelatoRepository();
		
		try {
			rr.create(r);
			
			r = new Relato();
			r.setData(new Date());
			r.setDescricao("Me fez raiva o dia todo e a noite também");
			r.setEstudante(e);
			
			rr.create(r);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
