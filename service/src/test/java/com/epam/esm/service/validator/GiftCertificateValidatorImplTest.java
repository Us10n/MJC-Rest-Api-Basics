package com.epam.esm.service.validator;

import com.epam.esm.service.validator.impl.GiftCertificateValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class GiftCertificateValidatorImplTest {
    @Test
    void isNameValidPositive() {
        String name = "Test string";
        boolean status = GiftCertificateValidatorImpl.getInstance().isNameValid(name);
        Assertions.assertTrue(status);
    }

    @Test
    void isNameValidNegative() {
        String name = "";
        boolean status = GiftCertificateValidatorImpl.getInstance().isNameValid(name);
        Assertions.assertFalse(status);
    }

    @Test
    void isDescriptionValidPositive() {
        String description = "Test string";
        boolean status = GiftCertificateValidatorImpl.getInstance().isDescriptionValid(description);
        Assertions.assertTrue(status);
    }

    @Test
    void isDescriptionValidNegative() {
        String description = "";
        boolean status = GiftCertificateValidatorImpl.getInstance().isDescriptionValid(description);
        Assertions.assertFalse(status);
    }

    @Test
    void isPriceValidPositive() {
        Double price = 1.0;
        boolean status = GiftCertificateValidatorImpl.getInstance().isPriceValid(price);
        Assertions.assertTrue(status);
    }

    @Test
    void isPriceValidNegative() {
        Double price = 0.0;
        boolean status = GiftCertificateValidatorImpl.getInstance().isPriceValid(price);
        Assertions.assertFalse(status);
    }

    @Test
    void isDurationValidPositive() {
        Integer duration = 1;
        boolean status = GiftCertificateValidatorImpl.getInstance().isDurationValid(duration);
        Assertions.assertTrue(status);
    }

    @Test
    void isDurationValidNegative() {
        Integer duration = -1;
        boolean status = GiftCertificateValidatorImpl.getInstance().isDurationValid(duration);
        Assertions.assertFalse(status);
    }

    @Test
    void isTagsValidPositive() {
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("ta1");
        boolean status = GiftCertificateValidatorImpl.getInstance().isTagsValid(tags);
        Assertions.assertTrue(status);
    }

    @Test
    void isTagsValidNegative1() {
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("");
        boolean status = GiftCertificateValidatorImpl.getInstance().isTagsValid(tags);
        Assertions.assertFalse(status);
    }

    @Test
    void isTagsValidNegative2() {
        boolean status = GiftCertificateValidatorImpl.getInstance().isTagsValid(null);
        Assertions.assertFalse(status);
    }

    @Test
    void isDateValidPositive() {
        String date = "2022-04-11T10:00:11.156";
        boolean status = GiftCertificateValidatorImpl.getInstance().isDateValid(date);
        Assertions.assertTrue(status);
    }

    @Test
    void isDateValidNegative() {
        String date = "2022-04-11 10.00.11.156";
        boolean status = GiftCertificateValidatorImpl.getInstance().isDateValid(date);
        Assertions.assertFalse(status);
    }
}