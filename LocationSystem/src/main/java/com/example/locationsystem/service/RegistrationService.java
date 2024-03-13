package com.example.locationsystem.service;

import com.example.locationsystem.models.Person;
import com.example.locationsystem.repository.PersonRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

import static com.example.locationsystem.service.PersonService.getLocation;

@Service
public class RegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;

    public RegistrationService(PasswordEncoder passwordEncoder, PersonRepository personRepository) {
        this.passwordEncoder = passwordEncoder;

        this.personRepository = personRepository;
    }
    @Transactional
    public void register(Person person){
        try {
            String location = getLocation();
            person.setLocation(location);
            person.setPassword(passwordEncoder.encode(person.getPassword()));
            person.setRole("ROLE_USER");
            personRepository.save(person);
        } catch (IOException e) {
            e.printStackTrace();
        }
       /* person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        personRepository.savePerson(person);*/
    }
}
