package com.example.locationsystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   private String name;
   private int age;
   private String gender;
   private String breed;
   @ManyToOne
   @JoinColumn(name = "owner_id")
   private Person owner;
   @OneToMany(mappedBy = "imageOwnerPet", cascade = CascadeType.ALL,fetch = FetchType.LAZY)

   private List<Image> images = new ArrayList<>();
}
