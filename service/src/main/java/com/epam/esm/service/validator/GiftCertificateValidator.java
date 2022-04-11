package com.epam.esm.service.validator;

import java.util.List;

public interface GiftCertificateValidator {
    boolean isNameValid(String name);

    boolean isDescriptionValid(String description);

    boolean isPriceValid(Double price);

    boolean isDurationValid(Integer duration);

    boolean isTagsValid(List<String> tags);

    boolean isDateValid(String date);
}
