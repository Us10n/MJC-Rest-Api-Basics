package com.epam.esm.service.handler;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Class that provides current date time
 */
@Component
public class DateHandler {
    /**
     *
     * @return current date Time
     */
    public LocalDateTime getCurrentDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime;
    }
}
