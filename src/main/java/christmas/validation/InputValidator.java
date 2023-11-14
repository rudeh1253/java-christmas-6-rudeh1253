package christmas.validation;

import christmas.error.ErrorMessage;

/**
 * 사용자의 입력을 검증하기 위한 Validator.
 * 사용자의 입력은 문자열로 이루어지기 때문에 문자열을 검증한다.
 */
public class InputValidator {
    private static final InputValidator SINGLETON = new InputValidator();

    private InputValidator() {
    }

    public static InputValidator getInstance() {
        return SINGLETON;
    }

    public void validateDate(String date) {
        if (!isInteger(date)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DATE.get());
        }
    }

    private boolean isInteger(String expectedAsInteger) {
        try {
            Integer.parseInt(expectedAsInteger);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
