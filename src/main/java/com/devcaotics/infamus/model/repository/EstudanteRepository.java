package com.devcaotics.infamus.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.devcaotics.infamus.model.entities.Estudante;
import com.devcaotics.infamus.model.entities.Professor;

public final class EstudanteRepository implements Repository<Estudante, Integer>{

	protected EstudanteRepository() {
		
	}
	
	@Override
	public void create(Estudante c) throws SQLException {
		// TODO Auto-generated method stub
		
		String sql = "insert into estudante(matricula_estudante, nome_estudante"
				+ ", endereco_estudante, telefone_estudante, email_estudante, "
				+ "ano_entrada) values (?,?,?,?,?,?)";
		
		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
		
		pstm.setString(1, c.getMatricula());
		pstm.setString(2, c.getNome());
		pstm.setString(3, c.getEndereco());
		pstm.setString(4, c.getTelefone());
		pstm.setString(5, c.getEmail());
		pstm.setInt(6, c.getAnoEntrada());
		
		pstm.execute();
		
	}

	@Override
	public Professor update(Estudante c) throws SQLException {
		// TODO Auto-generated method stub
		
		String sql = "update estudante set matricula_estudante=?,"
				+ "nome_estudante=?, endereco_estudante = ?,"
				+ "telefone_estudante=?, email_estudante=?,"
				+ "ano_entrada=? where codigo_estudante=?";
		
		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
		
		pstm.setString(1, c.getMatricula());
		pstm.setString(2, c.getNome());
		pstm.setString(3, c.getEndereco());
		pstm.setString(4, c.getTelefone());
		pstm.setString(5, c.getEmail());
		pstm.setInt(6, c.getAnoEntrada());
		
		pstm.setInt(7, c.getCodigo());
		
		pstm.executeUpdate();

		return null;
	}

	@Override
	public Estudante read(Integer k) throws SQLException {
		// TODO Auto-generated method stub
		
		String sql = "select * from estudante where codigo_estudante = ?";
		
		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
		
		pstm.setInt(1, k);
		
		ResultSet result = pstm.executeQuery();
		
		if(result.next()) {
			
			Estudante e =  new Estudante();
			
			e.setCodigo(result.getInt("codigo_estudante"));
			e.setNome(result.getString("nome_estudante"));
			e.setMatricula(result.getString("matricula_estudante"));
			e.setEndereco(result.getString("endereco_estudante"));
			e.setTelefone(result.getString("telefone_estudante"));
			e.setEmail(result.getString("email_estudante"));
			e.setAnoEntrada(result.getInt("ano_entrada"));
			
			return e;
		}
		
		return null;
	}
	
	public List<Estudante> readAll() throws SQLException{
		
		String sql = "select * from estudante";
		
		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
		
		List<Estudante> estudantes = new ArrayList<>();
		
		ResultSet result = pstm.executeQuery();
		
		while(result.next()) {
			
			Estudante e =  new Estudante();
			
			e.setCodigo(result.getInt("codigo_estudante"));
			e.setNome(result.getString("nome_estudante"));
			e.setMatricula(result.getString("matricula_estudante"));
			e.setEndereco(result.getString("endereco_estudante"));
			e.setTelefone(result.getString("telefone_estudante"));
			e.setEmail(result.getString("email_estudante"));
			e.setAnoEntrada(result.getInt("ano_entrada"));
			
			estudantes.add(e);
		}
		
		return estudantes;
		
	}

	@Override
	public void delete(Integer k) throws SQLException {
		// TODO Auto-generated method stub
		
		String sql = "delete from estudante where codigo_estudante = "+k;
		
		ConnectionManager.getCurrentConnection().prepareStatement(sql).execute();
		
	}

	@Override
	public Estudante readByEmail(String email) throws SQLException {
		return null;
	}

	public static void main(String args[]) {

		Estudante e = new Estudante();
		e.setMatricula("2213312");
		e.setNome("Ártico Pereira Asiático");
		e.setAnoEntrada(2010);
		e.setEmail("americo_aziatico@gmail.com");
		e.setTelefone("22133123");
		e.setEndereco("rua capitão cabo sargento major, 23");
		e.setCodigo(1);

		try {
			new EstudanteRepository().create(e);
			//Estudante e = new EstudanteRepository().read(1);
			//System.out.println(e.getNome());
			new EstudanteRepository().delete(1);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
