package com.devcaotics.infamus.model.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Relato {
	
	private int codigo;
	private Date data;
	private String descricao;
	
	private Estudante estudante;
	private Professor professor;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Estudante getEstudante() {
		return estudante;
	}

	public void setEstudante(Estudante estudante) {
		this.estudante = estudante;
	}
	
	public String getDataFormatada() {
		if(this.data != null)
			return new SimpleDateFormat("dd/MM/yyyy").format(this.data);
		
		return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}
	
	public void setDataFormatada(String dataString) {
		
		try {
			this.data = new SimpleDateFormat("dd/MM/yyyy").parse(dataString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Professor getProfessor(){
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
}
