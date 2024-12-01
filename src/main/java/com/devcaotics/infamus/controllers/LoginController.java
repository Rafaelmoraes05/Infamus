package com.devcaotics.infamus.controllers;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.devcaotics.infamus.model.entities.Professor;
import com.devcaotics.infamus.model.repository.RepositoryFacade;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("senha") String senha,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) throws SQLException {
        
        Professor professor = RepositoryFacade.getInstance().readProfessorEmail(email);

        if (professor != null && professor.getSenha().equals(senha)) {

            session.setAttribute("professor", professor);

            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "Credenciais inválidas");
            return "redirect:/login";
        }
    }

        @GetMapping("/logout")
        public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
            redirectAttributes.addFlashAttribute("msg", "Você foi desconectado.");
            session.invalidate();
            return "redirect:/";
        }

}
