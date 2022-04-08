package by.us10n.service.validator.impl;

import by.us10n.service.validator.GiftCertificateValidator;

import java.util.List;

public class GiftCertificateValidatorImpl implements GiftCertificateValidator {
    private static final Integer MIN_LENGTH = 1;
    private static final Double MIN_PRICE = 0.1;
    private static final Double MAX_PRICE = 10000.0;

    @Override
    public boolean isNameValid(String name) {
        return name != null && name.length() > MIN_LENGTH;
    }

    @Override
    public boolean isDescriptionValid(String description) {
        return description != null && description.length() > MIN_LENGTH;
    }

    @Override
    public boolean isPriceValid(Double price) {
        return price != null && price > MIN_PRICE && price < MAX_PRICE;
    }

    @Override
    public boolean isDurationValid(Integer duration) {
        return false;
    }

    @Override
    public boolean isTagsValid(List<String> tags) {
        return false;
    }
}
