package by.us10n.service.handler;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DateHandler {
    public LocalDate getCurrentDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.toLocalDate();
    }
}
