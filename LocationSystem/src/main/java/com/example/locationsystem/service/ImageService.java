package com.example.locationsystem.service;

import com.example.locationsystem.models.Image;
import com.example.locationsystem.models.Person;
import com.example.locationsystem.models.Pet;
import com.example.locationsystem.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    public void saveImageForPerson(MultipartFile multipartFile, Person person) throws IOException {
        Image image = new Image();
        image.setName(multipartFile.getOriginalFilename());
        image.setSize((int) multipartFile.getSize());
        image.setBytes(multipartFile.getBytes());
        image.setImageOwnerPerson(person);
        imageRepository.save(image);
    }
    public void saveImageForPet(MultipartFile multipartFile,Pet pet) throws IOException {
        Image image = new Image();
        image.setName(multipartFile.getOriginalFilename());
        image.setSize((int) multipartFile.getSize());
        image.setBytes(multipartFile.getBytes());
        image.setImageOwnerPet(pet);
        imageRepository.save(image);
    }
    @Transactional
    public List<Image> getImagesByPerson(Person person){
        return imageRepository.findByImageOwnerPerson(person);
    }
    @Transactional
    public List<Image> getImageByPet(Pet pet){
        return imageRepository.findByImageOwnerPet(pet);
    }
   
}
