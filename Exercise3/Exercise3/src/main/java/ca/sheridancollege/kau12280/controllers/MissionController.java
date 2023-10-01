package ca.sheridancollege.kau12280.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ca.sheridancollege.kau12280.beans.Mission;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MissionController {

    private final List<Mission> missions = new ArrayList<>();

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/mission")
    public String createMission() {
        return "mission";
    }

    @GetMapping("/missions")
    public String viewMissions(Model model) {
        model.addAttribute("missions", missions);
        return "missions-list"; // Display the mission list on a separate page
    }

    @PostMapping("/missions")
    public String submitMission(
    		@RequestParam String missionTitle,
    		@RequestParam String gadgets, 
    		Model model) {
        Mission mission = new Mission(missionTitle, gadgets);
        missions.add(mission);
        model.addAttribute("mission", mission); // Pass the mission to the template
        return "mission-details"; // Redirect to the mission-details page
    }

}

