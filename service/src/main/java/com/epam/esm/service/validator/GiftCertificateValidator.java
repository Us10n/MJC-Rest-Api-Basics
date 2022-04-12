package com.epam.esm.service.validator;

import java.util.List;

/**
 * The interface for GiftCertificate validator.
 */
public interface GiftCertificateValidator {
    /**
     * Validates name string.
     *
     * @param name the name string
     * @return true - if valid, else false.
     */
    boolean isNameValid(String name);

    /**
     * Validates description string.
     *
     * @param description the description
     * @return true - if valid, else false.
     */
    boolean isDescriptionValid(String description);

    /**
     * Validates price.
     *
     * @param price the price
     * @return true - if valid, else false.
     */
    boolean isPriceValid(Double price);

    /**
     * Validates duration.
     *
     * @param duration the duration
     * @return true - if valid, else false.
     */
    boolean isDurationValid(Integer duration);

    /**
     * Validates tags.
     *
     * @param tags the tags
     * @return true - if valid, else false.
     */
    boolean isTagsValid(List<String> tags);

    /**
     * Validates date.
     *
     * @param date the date
     * @return true - if valid, else false.
     */
    boolean isDateValid(String date);
}
