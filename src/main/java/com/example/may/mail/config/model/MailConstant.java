package com.example.may.mail.config.model;

/**
 * @author Evelina Tubalets
 */
public enum MailConstant {

    MAIL_PORT_RESPONSE_FIELD("mail.smtp.port"),
    MAIL_USER_RESPONSE_FIELD("mail.user"),
    MAIL_PASSWORD_RESPONSE_FIELD("mail.password"),
    MAIL_HOST_RESPONSE_FIELD("mail.smtp.host"),
    MAIL_FROM_RESPONSE_FIELD("mail.smtp.from");

    final String value;

    MailConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
