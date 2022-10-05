package com.example.may.dog.model;

import com.example.may.animal.model.Animal;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Evelina Tubalets
 */
@Data
@Entity
public class Dog extends Animal {

    @Column
    private String breed;
}
