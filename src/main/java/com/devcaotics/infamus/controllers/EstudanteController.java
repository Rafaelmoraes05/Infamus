package com.devcaotics.infamus.controllers;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devcaotics.infamus.model.entities.Estudante;
import com.devcaotics.infamus.model.repository.RepositoryFacade;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/estudante")
public class EstudanteController {

	@PostMapping("/cadastro")
	public String inserir(Model m, HttpServletRequest request, Estudante e, HttpSession session) {
		
		/*
		 * o request é uma variável de contexto que o spring prepara para nós
		 * */
		
		//String nome = request.getParameter("nome");
		//String matricula = request.getParameter("matricula");
		
		try {
			RepositoryFacade.getInstance().createEstudante(e);
			
			session.setAttribute("msg","Estudante Cadastrado com Sucesso!");
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			session.setAttribute("msg","Erro ao cadastrar o estudante!");
		}
		
		return "redirect:/";
		
		
	}
	
}
