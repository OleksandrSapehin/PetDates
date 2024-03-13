package com.example.locationsystem.repository;

import com.example.locationsystem.models.Person;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {
    Optional<Person> findByEmail(String email);
    Optional<Person> findById(int id);

    @Query("SELECT f FROM Person p JOIN p.friends f WHERE p.id = :personId")
    List<Person> findFriendsByPerson(@Param("personId") int personId);

}
