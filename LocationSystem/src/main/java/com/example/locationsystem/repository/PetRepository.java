package com.example.locationsystem.repository;

import com.example.locationsystem.models.Person;
import com.example.locationsystem.models.Pet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet,Integer> {
    List<Pet> findPetsByOwner(Person person);
    @Transactional
    @Modifying
    void deletePetById(int petId);
}
