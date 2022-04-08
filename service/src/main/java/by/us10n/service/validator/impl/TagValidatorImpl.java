package by.us10n.service.validator.impl;

import by.us10n.service.validator.TagValidator;

public class TagValidatorImpl implements TagValidator {
    private static final int MIN_LENGTH = 1;
    private static final TagValidatorImpl instance = new TagValidatorImpl();

    public static TagValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean isNameValid(String name) {
        return name != null && name.length() > MIN_LENGTH;
    }
}
