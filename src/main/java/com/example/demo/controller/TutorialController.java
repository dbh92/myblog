package com.example.demo.controller;

import com.example.demo.model.Tutorial;
import com.example.demo.repository.TutorialRepository;
import com.example.demo.service.tutorialService.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class TutorialController {
    @Autowired
    TutorialRepository tutorialRepository;

    @Autowired
    TutorialService tutorialService;

    @GetMapping("/tutorials")
    public String getAllTutorials(@ModelAttribute Tutorial tutorial, Model model) throws Exception {

        List<Tutorial> tutorialss = new ArrayList<Tutorial>();
        tutorialss = tutorialRepository.findAll();
        model.addAttribute("tutorialss", tutorialss);
        return "tutorials";
    }

    @GetMapping("/tutorials/get/{id}")
    public String getTutorialById(@PathVariable("id") long id, Model model) {
        Tutorial tutorialss = tutorialRepository.findById(id).orElse(new Tutorial());
        model.addAttribute("tutorialss", tutorialss);
        return "tutorials";
    }

    @GetMapping("/tutorials/get/{name}")
    public String getTutorialById(@PathVariable("name") String name, Model model) {
        List<Tutorial> tutorialsFindByTitle = tutorialRepository.findByTitleContaining(name);
        model.addAttribute("tutorialsFindByTitle", tutorialsFindByTitle);
        return "tutorialsFindByTitle";
    }

    @GetMapping("/tutorials/created")
    public String updateTutorial(@ModelAttribute Tutorial tutorial, Model model) {
        Tutorial tutorialNew = new Tutorial();
        model.addAttribute("tutorialData", tutorialNew);
        return "updated";
    }

    @PostMapping("/tutorials/save")
    public String createTutorial(@ModelAttribute Tutorial tutorial, Model model) {
        Tutorial tutorial1 = tutorialRepository.save(tutorial);
        model.addAttribute("tutorial1", tutorial1);
        return "redirect:/api/tutorials";
    }

    @GetMapping("/tutorials/update/{id}")
    public String updateTutorial(@PathVariable("id") long id, Model model) {
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
        if (tutorialData.isPresent()) {
            model.addAttribute("tutorialData", tutorialData.get());
        } else {
            model.addAttribute("tutorialData", new Tutorial());
            model.addAttribute("fail", "false");
        }
        return "updated";
    }

    @GetMapping("/tutorials/delete/{id}")
    public String deleteTutorial(@PathVariable("id") long id) {
        tutorialRepository.deleteById(id);
        return "redirect:/api/tutorials";
    }

    @DeleteMapping("/tutorials/delete/all")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        try {
            tutorialRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/published")
    @ResponseBody
    public ResponseEntity<List<Tutorial>> findByPublished() {
        try {
            List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "loginPage";
    }

}
