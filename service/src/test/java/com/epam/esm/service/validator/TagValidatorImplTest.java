package com.epam.esm.service.validator;

import com.epam.esm.service.validator.impl.TagValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TagValidatorImplTest {

    @Test
    void isNameValidPositive() {
        String name = "Test string";
        boolean status = TagValidatorImpl.getInstance().isNameValid(name);
        Assertions.assertTrue(status);
    }
    @Test
    void isNameValidNegative() {
        String name = "";
        boolean status = TagValidatorImpl.getInstance().isNameValid(name);
        Assertions.assertFalse(status);
    }
}