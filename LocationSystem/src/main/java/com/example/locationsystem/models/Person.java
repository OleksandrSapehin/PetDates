package com.example.locationsystem.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import static jakarta.persistence.CascadeType.ALL;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "name cannot be empty")
    private String name;
    @NotEmpty(message = "password cannot be empty")
    private String password;
    @Email(message = "email should not be empty ")
    private String email;
    private String role;
    private String location;
    @ManyToMany()
    @JoinTable(
            name = "person_friends",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<Person> friends = new ArrayList<>();
    @OneToMany(mappedBy = "owner",cascade = ALL)
    private List<Pet> pets = new ArrayList<>();
    @OneToMany(mappedBy = "imageOwnerPerson")
    private List<Image> images = new ArrayList<>();
}
