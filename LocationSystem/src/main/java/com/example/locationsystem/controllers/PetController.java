package com.example.locationsystem.controllers;

import com.example.locationsystem.models.Image;
import com.example.locationsystem.models.Person;
import com.example.locationsystem.models.Pet;
import com.example.locationsystem.service.ImageService;
import com.example.locationsystem.service.PersonDetailService;
import com.example.locationsystem.service.PetService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class PetController {
       private final PersonDetailService personDetailService;
       private final PetService petService;
       private final ImageService imageService;


    @GetMapping("/addNewPet")
    public String addPet(Model model){
        List<Pet> pets = petService.getAll();
        model.addAttribute("pets",pets);
        return "/addPet";
    }

    @PostMapping("/addPet")
    public String addPetToPerson(@RequestParam("file") MultipartFile file,
                                 @RequestParam String name, @RequestParam int age,
                                 @RequestParam String gender, @RequestParam String breed) throws IOException {
        Person person = personDetailService.getAuthPerson();
        int personId = person.getId();
        petService.createPetForPerson(file,personId,name,age,gender,breed);
        return "redirect:/myPets";
    }

    @PostMapping("/deletePet")
    public String deletePet(@RequestParam int petId){
        petService.deletePetById(petId);
        return "redirect:/myPets";
    }
   
    @GetMapping("/myPets")
    public String getPetsFromOwner(Model model){
        Person person = personDetailService.getAuthPerson();
        int personId = person.getId();
        List<Pet> pets =  petService.getPetsByOwner(personId);
        model.addAttribute("pets",pets);
        List<Image> images = new ArrayList<>();
        for (Pet pet:pets){
            List<Image> petImages = imageService.getImageByPet(pet);
            images.addAll(petImages);
        }
        model.addAttribute("images",images);
        return "/myPets";
    }
}
