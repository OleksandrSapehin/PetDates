package com.example.locationsystem.service;

import com.example.locationsystem.models.Person;
import com.example.locationsystem.models.Pet;
import com.example.locationsystem.repository.PersonRepository;
import com.example.locationsystem.repository.PetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final PersonRepository personRepository;
    private final ImageService imageService;

    public void createPetForPerson(MultipartFile multipartFile, int personId, String name, int age, String gender, String breed) throws IOException {
        Person person = personRepository.findById(personId).orElse(null);
        Pet pet = new Pet();
        if (person!=null){
            pet.setOwner(person);
            pet.setName(name);
            pet.setAge(age);
            pet.setGender(gender);
            pet.setBreed(breed);
            petRepository.save(pet);
            imageService.saveImageForPet(multipartFile,pet);
        }
    }

    public List<Pet> getPetsByOwner(int personId){
        Person person = personRepository.findById(personId).orElse(null);
        List<Pet> list = new ArrayList<>();
        if (person!=null){
             list = petRepository.findPetsByOwner(person);
        }
        return list;
    }
    public void deletePetById(int petId){
       petRepository.deletePetById(petId);
    }
    public List<Pet> getAll(){
        return petRepository.findAll();
    }
}
