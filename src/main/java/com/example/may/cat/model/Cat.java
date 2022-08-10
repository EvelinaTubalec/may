package com.example.may.cat.model;

import com.example.may.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
@Entity
@Data
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String alias;

    @Column
    private LocalDate dateOfBirth;

    @JsonIgnore
    @ManyToMany(mappedBy = "cats")
    private List<User> users = new ArrayList<>();
}
