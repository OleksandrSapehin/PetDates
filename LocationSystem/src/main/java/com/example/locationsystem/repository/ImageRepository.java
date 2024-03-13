package com.example.locationsystem.repository;

import com.example.locationsystem.models.Image;
import com.example.locationsystem.models.Person;
import com.example.locationsystem.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
     List<Image> findByImageOwnerPerson(Person person);
     List<Image> findByImageOwnerPet(Pet pet);
}
