package ca.sheridancollege.kau12280.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
@Controller
public class HomeController {
	  @GetMapping("/")
	    public String index(HttpSession session, Model model) {
	        List<String> messages = getSessionMessages(session);
	        model.addAttribute("messages", messages);
	        model.addAttribute("sessionId", session.getId());
	        return "index";
	    }

	    @PostMapping("/saveMessage")
	    public String saveMessage(@RequestParam String message, HttpSession session, Model model) {
	        List<String> messages = getSessionMessages(session);
	        messages.add(message);
	        session.setAttribute("messages", messages);
	        model.addAttribute("messages", messages);
	        model.addAttribute("sessionId", session.getId());
	        return "index";
	    }

	    @PostMapping("/destroySession")
	    public String destroySession(HttpSession session) {
	        session.invalidate();
	        return "redirect:/";
	    }

	    private List<String> getSessionMessages(HttpSession session) {
	        List<String> messages = (List<String>) session.getAttribute("messages");
	        if (messages == null) {
	            messages = new ArrayList<>();
	            session.setAttribute("messages", messages);
	        }
	        return messages;
	    }
}


