package com.devcaotics.infamus.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devcaotics.infamus.model.entities.Estudante;
import com.devcaotics.infamus.model.entities.Professor;
import com.devcaotics.infamus.model.repository.RepositoryFacade;

import jakarta.servlet.http.HttpSession;


@Controller
public class MainController {

	@RequestMapping({"/*","/estudante/*"})
	public String inicial(Model m, HttpSession session) {
		List<Estudante> estudantes = RepositoryFacade.getInstance().readAllEstudantes();
		m.addAttribute("estudantes", estudantes);

		Professor professor = (Professor) session.getAttribute("professor");
		m.addAttribute("professor", professor);

		return "index";
	}


}
