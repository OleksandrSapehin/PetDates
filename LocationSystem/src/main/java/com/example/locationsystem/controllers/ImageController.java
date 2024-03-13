package com.example.locationsystem.controllers;

import com.example.locationsystem.models.Image;
import com.example.locationsystem.models.Person;
import com.example.locationsystem.models.Pet;
import com.example.locationsystem.repository.ImageRepository;
import com.example.locationsystem.repository.PetRepository;
import com.example.locationsystem.service.ImageService;
import com.example.locationsystem.service.PersonDetailService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class ImageController {
    private final ImageRepository imageRepository;
    private final PersonDetailService personDetailService;
    private final ImageService imageService;

    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") int id)  {
        Image image = imageRepository.findById(id).orElse(null);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image.getBytes());
    }

    @PostMapping("/uploadImageForPerson")
    public String uploadImageForPerson(@RequestParam("file") MultipartFile file) throws IOException {
        Person person = personDetailService.getAuthPerson();
        imageService.saveImageForPerson(file,person);
        return "redirect:/myAccount";
    }

}
