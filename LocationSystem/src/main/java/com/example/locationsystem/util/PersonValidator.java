package com.example.locationsystem.util;
import com.example.locationsystem.models.Person;
import com.example.locationsystem.service.PersonDetailService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonDetailService personDetailService;

    public PersonValidator(PersonDetailService personDetailService) {
        this.personDetailService = personDetailService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return  Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        try {
            personDetailService.loadUserByUsername(person.getEmail());
        } catch (UsernameNotFoundException ignored){
            return;
        }
        errors.reject("username","A person with this email already exists");
    }
}
