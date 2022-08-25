package com.example.may.email;

import lombok.Data;

/**
 * @author Evelina Tubalets
 */
@Data
public class EmailProperties {

    private String host;

    private Integer port;

    private String username;

    private String password;
}
