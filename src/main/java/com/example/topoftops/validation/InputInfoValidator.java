package com.example.topoftops.validation;

/**
 * Validator to validate user info
 *
 * @author Ilya Tsvetkov
 */
public class InputInfoValidator {

    private static final String REGEXP_VALID_TITLE = "^[a-zA-Zа-яА-Я-\\s\\d\\p{P}]{1,15}$";
    private static final String REGEXP_VALID_DESCRIPTION = "^[a-zA-Zа-яА-Я-\\s\\d\\p{P}]{1,40}$";
    private static final String REGEXP_VALID_SEARCH = "^[a-zA-Zа-яА-Я-\\s\\d]{0,15}$";

    private InputInfoValidator() {
    }

    /**
     * validation title
     *
     * @return boolean if no valid true otherwise false
     */
    public static boolean isValidTitle(String title) {
        boolean isValidTitle = false;
        if (title != null) {
            isValidTitle = title.matches(REGEXP_VALID_TITLE);
        }
        return isValidTitle;
    }

    /**
     * validation description
     *
     * @return boolean if no valid true otherwise false
     */
    public static boolean isValidDescription(String description) {
        boolean isValidDescription = false;
        if (description != null) {
            isValidDescription = description.matches(REGEXP_VALID_DESCRIPTION);
        }
        return isValidDescription;
    }

    /**
     * validation description
     *
     * @return boolean if no valid true otherwise false
     */
    public static boolean isValidSearch(String search) {
        boolean isValidSearch = false;
        if (search != null) {
            isValidSearch = search.matches(REGEXP_VALID_SEARCH);
        }
        return isValidSearch;
    }


}
