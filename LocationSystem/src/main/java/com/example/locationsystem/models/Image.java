package com.example.locationsystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
    @NotEmpty
   private String name;
   private int size;
   @Lob
   private byte[] bytes;
   @ManyToOne
   @JoinColumn(name = "ownerImage_id")
   private Person imageOwnerPerson;
   @ManyToOne
   @JoinColumn(name = "pet_id")
   private Pet imageOwnerPet;
}
