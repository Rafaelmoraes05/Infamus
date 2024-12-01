package com.devcaotics.infamus.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.devcaotics.infamus.model.entities.Professor;
import com.devcaotics.infamus.model.entities.Relato;
import com.devcaotics.infamus.model.repository.RepositoryFacade;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    @GetMapping("/cadastro")
    public String exibirPaginaCadastro(){
        return "cadastro";
    }

    @GetMapping("/perfil")
    public String exibirPerfil(Model m, HttpSession session, RedirectAttributes redirectAttributes){
        Professor professor = (Professor) session.getAttribute("professor");
        if (professor == null) {
            redirectAttributes.addFlashAttribute("erroRelato", "Faça login para visualizar o seu perfil");
            return "redirect:/login";
        } else {
            System.out.println("Professor logado: " + professor.getNome() + " codigo: " + professor.getCodigo());
        }
        m.addAttribute("professor", professor);
        return "perfil";
    }

    @GetMapping("/relatos")
    public String relato(Model m, HttpSession session, RedirectAttributes redirectAttributes) throws SQLException {
        
        Professor professor = (Professor) session.getAttribute("professor");
        if (professor == null) {
            redirectAttributes.addFlashAttribute("erroRelato", "Faça login para visualizar os relatos");
            return "redirect:/login";
        } else {
            System.out.println("Professor logado: " + professor.getNome() + " codigo: " + professor.getCodigo());
        }

        try {
            List<Relato> relatos = RepositoryFacade.getInstance().filterByCodigoProfessor(professor.getCodigo());
            m.addAttribute("relatos", relatos);
            System.out.println(relatos);
        } catch (SQLException e) {
            m.addAttribute("erro", "Erro ao carregar os relatos");
            return "relatosProfessor";
        }

        return "relatosProfessor";
    }

    @PostMapping("/cadastro")
    public String cadastrarProfessor (Model m, HttpServletRequest request, Professor p, HttpSession session) {

        try {
            RepositoryFacade.getInstance().createProfessor(p);
            session.setAttribute("msg", "Professor Cadastrado com Sucesso");

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            session.setAttribute("msg", "Erro ao cadastrar o professor");
        }

        return "redirect:/login";
    }

    @PostMapping("/atualizarSenha")
    public String atualizarProfessor (Model m, HttpServletRequest request, Professor p, HttpSession session) {
        String novaSenha = request.getParameter("novaSenha");

        Professor professor = (Professor) session.getAttribute("professor");
        if (professor == null) {
            session.setAttribute("msg", "Professor não encontrado");
            return "redirect:/perfil";
        }

        professor.setSenha(novaSenha);
        try {
            RepositoryFacade.getInstance().updateProfessor(professor);
            session.setAttribute("msg", "Senha alterada.");
        } catch (SQLException e1) {
            session.setAttribute("msg", "Erro ao alterar senha");
        }

        return "redirect:/perfil";
    }
}
