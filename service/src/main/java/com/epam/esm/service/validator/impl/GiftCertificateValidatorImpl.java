package com.epam.esm.service.validator.impl;

import com.epam.esm.service.validator.GiftCertificateValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class GiftCertificateValidatorImpl implements GiftCertificateValidator {
    private static final Integer MIN_LENGTH = 1;
    private static final Double MIN_PRICE = 0.1;
    private static final Double MAX_PRICE = 10000.0;
    private static final Integer MIN_DURATION = 1;
    private static final Integer MAX_DURATION = 28;

    private static final GiftCertificateValidatorImpl instance = new GiftCertificateValidatorImpl();

    public static GiftCertificateValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean isNameValid(String name) {
        return name != null && name.length() >= MIN_LENGTH;
    }

    @Override
    public boolean isDescriptionValid(String description) {
        return description != null && description.length() >= MIN_LENGTH;
    }

    @Override
    public boolean isPriceValid(Double price) {
        return price != null && price >= MIN_PRICE && price <= MAX_PRICE;
    }

    @Override
    public boolean isDurationValid(Integer duration) {
        return duration != null && duration >= MIN_DURATION && duration <= MAX_DURATION;
    }

    @Override
    public boolean isTagsValid(List<String> tags) {
        if (tags == null) {
            return false;
        }
        if (tags.isEmpty()) {
            return true;
        }
        return tags.stream().allMatch(TagValidatorImpl.getInstance()::isNameValid);
    }

    @Override
    public boolean isDateValid(String date) {
        try {
            LocalDateTime.parse(date);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
