package com.devcaotics.infamus.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.devcaotics.infamus.model.entities.Estudante;
import com.devcaotics.infamus.model.entities.Professor;
import com.devcaotics.infamus.model.entities.Relato;
import com.devcaotics.infamus.model.repository.RepositoryFacade;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/relatos")
public class RelatorController {

	@Autowired
	private HttpSession session;

	@GetMapping("/{codigoEstudante}")
	public String viewRelatos(Model m, @PathVariable("codigoEstudante") int codigo, RedirectAttributes redirectAttributes) {
		Professor professor = (Professor) session.getAttribute("professor");
		if (professor == null) {
			redirectAttributes.addFlashAttribute("erroRelato", "Você precisa estar logado para visualizar os relatos ou relatar um estudante.");
			return "redirect:/login";
		}

		try {
			Estudante e = RepositoryFacade.getInstance().readEstudante(codigo);
			List<Relato> relatos = RepositoryFacade.getInstance().filterRelatoByCodigoEstudante(codigo);

			if (e != null) {
				m.addAttribute("relatos", relatos);
				m.addAttribute("estudante", e);
				m.addAttribute("relato", new Relato());
			}

			return "relatos";
		} catch (SQLException e) {
			session.setAttribute("msg", "O estudante com o código " + codigo + " não está cadastrado!");
			return "relatos";
		}
	}

	@RequestMapping({"/relatos/*"})
	public String relato(Model m, HttpSession session, RedirectAttributes redirectAttributes) throws SQLException {
		
		Professor professor = (Professor) session.getAttribute("professor");

		if (professor == null) {
			redirectAttributes.addFlashAttribute("erroRelato", "Você precisa estar logado para visualizar os relatos.");
			return "redirect:/login";
		}

		List<Relato> relatos = RepositoryFacade.getInstance().filterByCodigoProfessor(professor.getCodigo());
		m.addAttribute("relatos", relatos);

		return "relatos";
	}

	@PostMapping("/save")
	public String saveRelato(Model m, Relato r, @RequestParam("codigoEstudante") int codigo) {
		try {
			Estudante e = RepositoryFacade.getInstance().readEstudante(codigo);
			r.setEstudante(e);

			Professor professor = (Professor) session.getAttribute("professor");
			r.setProfessor(professor);

			RepositoryFacade.getInstance().createRelato(r);
			session.setAttribute("msg", "Relato cadastrado com sucesso!");

			return viewRelatos(m, codigo, null);
		} catch (SQLException exp) {
			session.setAttribute("msg", "Erro ao cadastrar o relato!");

			return viewRelatos(m, codigo, null);
		}
	}

	@PostMapping("/save2")
	public String saveRelato2(Model m, Relato r) {
		try {
			RepositoryFacade.getInstance().createRelato(r);
			session.setAttribute("msg", "Relato cadastrado com sucesso!");

			return viewRelatos(m, r.getEstudante().getCodigo(), null);
		} catch (SQLException exp) {
			session.setAttribute("msg", "Erro ao cadastrar o relato!");

			return viewRelatos(m, r.getEstudante().getCodigo(), null);
		}
	}
}