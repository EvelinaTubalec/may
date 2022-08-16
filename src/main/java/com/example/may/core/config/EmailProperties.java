package com.example.may.core.config;

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
