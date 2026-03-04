package com.example.library.model;
import jakarta.persistence.*;
import lombok.Data;// use of Lambok for reduce getter and setter

@Entity // map the class as an entity of JPA for direct mapping on the table
@Data // automatically generate getter and setter
public class Book {
    @Id // it set the primary key in the table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Incremental Id
    private Long id;

    private String title;
    private String author;


    @Column(name = "pubblication_year") // Optional set of the name of the column instead of take the name from the var
    private int year;
}
