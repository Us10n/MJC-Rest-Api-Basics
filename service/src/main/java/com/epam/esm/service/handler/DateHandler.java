package com.epam.esm.service.handler;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateHandler {
    public LocalDateTime getCurrentDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime;
    }
}
