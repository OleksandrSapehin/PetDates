package com.example.locationsystem.controllers;
import com.example.locationsystem.models.Image;
import com.example.locationsystem.models.Person;
import com.example.locationsystem.service.ImageService;
import com.example.locationsystem.service.PersonDetailService;
import com.example.locationsystem.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class PersonController {
   private final PersonService personService;
   private final PersonDetailService personDetailService;
   private final ImageService imageService;

    @GetMapping("/mainPage")
    public String mainPage(Model model){
        Person person = personDetailService.getAuthPerson();
        List<Person> persons = personService.findAll();
        persons.removeIf(p -> p.getId() == person.getId());
        model.addAttribute("persons",persons);

        return "/mainPage";
    }

    @GetMapping("/show/{id}")
    public String getUser(Model model, @PathVariable("id") int id, String errorMessage){
        Optional<Person> person = personService.findById(id);
        model.addAttribute("person",person);
        model.addAttribute("errorMessage", errorMessage);
        return "/personInfo";
    }

    @GetMapping("/myAccount")
    public String getAuthPerson(Model model){
        Person person = personDetailService.getAuthPerson();
        model.addAttribute("authPerson",person);
        List<Image> images = imageService.getImagesByPerson(person);
        model.addAttribute("images",images);
        return "/myAccount";
    }

    @GetMapping("/myFriends")
    public String getFriends(Model model){
        Person person = personDetailService.getAuthPerson();
        List<Person> friends = personService.findFriendsByPersonEntity(person.getId());
        model.addAttribute("friends",friends);
        model.addAttribute("person",person);
        return "/friends";
    }

    @PostMapping("/addFriend")
    public String addFriend( @RequestParam int friendId,Model model,RedirectAttributes redirectAttributes) {
        try {
            Person person = personDetailService.getAuthPerson();
            int personId = person.getId();
            model.addAttribute("personId", personId);
            model.addAttribute("friendId", friendId);
            personService.addFriend(personId, friendId);
            return "redirect:/show/"+friendId;
        } catch (Exception e) {
            redirectAttributes.addAttribute("errorMessage", "Error adding friend: " + e.getMessage());
            return "redirect:/show/"+friendId;
        }
    }

    @PostMapping("/deleteFriend")
    public String deleteFriend(@RequestParam int friendId, Model model, RedirectAttributes redirectAttributes) {
        try {
            Person person = personDetailService.getAuthPerson();
            int personId = person.getId();
            model.addAttribute("personId", personId);
            model.addAttribute("friendId", friendId);
            personService.deleteFriend(personId, friendId);
            return "redirect:/show/"+friendId;
        } catch (Exception e) {
            redirectAttributes.addAttribute("errorMessage", "Error deleting friend: " + e.getMessage());
            return "redirect:/show/"+friendId;
        }
    }


}
