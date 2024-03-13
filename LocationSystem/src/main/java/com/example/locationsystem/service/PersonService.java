package com.example.locationsystem.service;

import com.example.locationsystem.Exceptions.FriendAlreadyExistsException;
import com.example.locationsystem.Exceptions.PersonNotFoundException;
import com.example.locationsystem.models.Person;
import com.example.locationsystem.repository.PersonRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
   private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    public List<Person> findFriendsByPersonEntity(int personId) {
        return personRepository.findFriendsByPerson(personId);
    }
    public Optional<Person> findById(int id){
        return personRepository.findById(id);
    }
    public List<Person> findAll(){
        return personRepository.findAll();
    }


    public void addFriend(int personId, int friendId) {
        Optional<Person> personOptional = personRepository.findById(personId);
        Optional<Person> friendOptional = personRepository.findById(friendId);

        if (personOptional.isPresent() && friendOptional.isPresent()) {
            Person person = personOptional.get();
            Person friend = friendOptional.get();
            if (!person.getFriends().contains(friend)){
                person.getFriends().add(friend);
                personRepository.save(person);
            } else {
                throw new FriendAlreadyExistsException("It is already your friend");
            }

        } else {
            throw new PersonNotFoundException("Person or friend not found.");
        }
    }
    public void deleteFriend(int personId, int friendId){
        Optional<Person> person = personRepository.findById(personId);
        Optional<Person> friend = personRepository.findById(friendId);

        if (person.isPresent() && friend.isPresent()) {
            Person p = person.get();
            Person f = friend.get();
            if (p.getFriends().contains(f)) {
                p.getFriends().remove(f);
                personRepository.save(p);
            }else {
                throw new FriendAlreadyExistsException("It is not your friend");
            }
        } else {
            throw new PersonNotFoundException("Person or friend not found.");
        }
    }

    public static String getLocation() throws IOException {
        String url = "http://ip-api.com/json/?fields=status,message,country,regionName,city";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Failed to get response from server. Response code: " + responseCode);
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();

        String country = jsonObject.has("country") ? jsonObject.get("country").getAsString() : "Unknown";
        String region = jsonObject.has("regionName") ? jsonObject.get("regionName").getAsString() : "Unknown";
        String city = jsonObject.has("city") ? jsonObject.get("city").getAsString() : "Unknown";

        return country + ", " + region + ", " + city;
    }

}
