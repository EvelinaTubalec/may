package com.example.may.email.model;

import com.example.may.email.config.EmailProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Evelina Tubalets
 */
@Data
@NoArgsConstructor
public class EmailMessage {

    private EmailProperties emailProperties;

    private List<BirthdayUser> users;
}
