package com.epam.esm.service.validator;

/**
 * The interface for Tag validator.
 */
public interface TagValidator {
     /**
      * Validates name string.
      *
      * @param name the name string
      * @return true - if valid, else false.
      */
     boolean isNameValid(String name);
}
