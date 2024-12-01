package com.devcaotics.infamus.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.devcaotics.infamus.model.entities.Professor;

public class ProfessorRepository implements Repository<Professor, Integer> {

    protected ProfessorRepository() {}

    @Override
    public void create(Professor professor) throws SQLException {
        String sql = "INSERT INTO professor(nome_professor, email_professor, senha_professor) VALUES (?,?,?)";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        pstm.setString(1, professor.getNome());
        pstm.setString(2, professor.getEmail());
        pstm.setString(3, professor.getSenha());

        pstm.executeUpdate();

        ResultSet generatedKeys = pstm.getGeneratedKeys();
        if (generatedKeys.next()) {
            professor.setCodigo(generatedKeys.getInt(1));
        }
    }

    @Override
    public Professor update(Professor professor) throws SQLException {
        String sql = "UPDATE professor SET nome_professor=?, email_professor=?, senha_professor=? WHERE codigo_professor=?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setString(1, professor.getNome());
        pstm.setString(2, professor.getEmail());
        pstm.setString(3, professor.getSenha());
        pstm.setInt(4, professor.getCodigo());

        pstm.executeUpdate();
        return professor;
    }

    @Override
    public Professor read(Integer codigo) throws SQLException {
        String sql = "SELECT * FROM professor WHERE codigo_professor = ?";
        PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        stmt.setInt(1, codigo);

        ResultSet result = stmt.executeQuery();

        if (result.next()) {
            Professor professor = new Professor();
            professor.setCodigo(result.getInt("codigo_professor"));
            professor.setNome(result.getString("nome_professor"));
            professor.setEmail(result.getString("email_professor"));
            professor.setSenha(result.getString("senha_professor"));
            return professor;
        }

        return null;
    }

    public Professor readByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM professor WHERE email_professor = ?";
        PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        stmt.setString(1, email);

        ResultSet result = stmt.executeQuery();

        if (result.next()) {
            Professor professor = new Professor();
            professor.setCodigo(result.getInt("codigo_professor"));
            professor.setNome(result.getString("nome_professor"));
            professor.setEmail(result.getString("email_professor"));
            professor.setSenha(result.getString("senha_professor"));
            return professor;
        }

        return null;
    }


    public List<Professor> readAll() throws SQLException {
        String sql = "SELECT * FROM professor";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        List<Professor> professores = new ArrayList<>();

        ResultSet result = pstm.executeQuery();
        while (result.next()) {
            Professor p = new Professor();
            p.setCodigo(result.getInt("codigo_professor"));
            p.setNome(result.getString("nome_professor"));
            p.setEmail(result.getString("email_professor"));
            p.setSenha(result.getString("senha_professor"));
            professores.add(p);
        }

        return professores;
    }

    @Override
    public void delete(Integer codigo) throws SQLException {
        String sql = "DELETE FROM professor WHERE codigo_professor = ?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setInt(1, codigo);
        pstm.executeUpdate();
    }
}