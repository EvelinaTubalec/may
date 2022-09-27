package com.example.may.localization.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * @author Evelina Tubalets
 */
@Slf4j
@Service
@AllArgsConstructor
public class LocalizedMessageService {

    public static final String GREETING_MESSAGE = "greeting";

    private final MessageSource messageSource;

    public String getMessage() {
        final Locale locale = LocaleContextHolder.getLocale();
        log.info("The client locale is {}.", locale);
        return messageSource.getMessage(GREETING_MESSAGE, null, locale);
    }
}
