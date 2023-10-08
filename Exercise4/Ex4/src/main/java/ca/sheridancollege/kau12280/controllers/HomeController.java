package ca.sheridancollege.kau12280.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ca.sheridancollege.kau12280.beans.School;
import ca.sheridancollege.kau12280.database.DatabaseAccess;

@Controller
public class HomeController {
    private DatabaseAccess da;

    public HomeController(DatabaseAccess da) {
        this.da = da;
    }

    @GetMapping("/addSchool")
    public String addSchoolForm() {
        return "addSchool";
    }

    @GetMapping("removeAll")
    public String removeAndGoHome() {
    	  da.getSchoolList().clear(); 
    	    return "redirect:/"; 
    }

    @GetMapping("/viewSchools")
    public String viewSchools(Model model) {
        model.addAttribute("schools", da.getSchoolList());
        return "listing";
    }

    @PostMapping("/addSchool")
    public String addSchool(
            @RequestParam String schoolName,
            @RequestParam String schoolAddress,
            @RequestParam int numStudents) {
        School school = new School();
        school.setSchoolName(schoolName);
        school.setSchoolAddress(schoolAddress);
        school.setNumStudents(numStudents);
        da.getSchoolList().add(school);
        return "index";
    }
}
