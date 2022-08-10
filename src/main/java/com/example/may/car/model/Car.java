package com.example.may.car.model;

import com.example.may.user.model.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

/**
 * @author Evelina Tubalets
 */
@Data
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String manufacturer;

    private String model;

    private Integer maxSpeed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
