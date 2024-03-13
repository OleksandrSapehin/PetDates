package com.example.locationsystem.service;

import com.example.locationsystem.models.Person;
import com.example.locationsystem.repository.PersonRepository;
import com.example.locationsystem.security.PersonDetails;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PersonDetailService implements UserDetailsService {
   private final PersonRepository personRepository;

    public PersonDetailService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> foundPerson = personRepository.findByEmail(username);
        if (foundPerson.isEmpty()){
            throw new  UsernameNotFoundException("User not found!");
        }
        return new PersonDetails(foundPerson.get());
    }

    public Person getAuthPerson(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return personDetails.getPerson();
    }

}
