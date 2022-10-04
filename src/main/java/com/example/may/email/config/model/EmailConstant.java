package com.example.may.email.config.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Evelina Tubalets
 */
@Getter
@AllArgsConstructor
public enum EmailConstant {

    MAIL_PORT_RESPONSE_FIELD("mail.smtp.port"),
    MAIL_USER_RESPONSE_FIELD("mail.user"),
    MAIL_PASSWORD_RESPONSE_FIELD("mail.password"),
    MAIL_HOST_RESPONSE_FIELD("mail.smtp.host"),
    MAIL_FROM_RESPONSE_FIELD("mail.smtp.from");

    final String value;
}
