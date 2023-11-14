package christmas.validation;

import christmas.domain.config.MenuConfig;
import christmas.error.ErrorMessage;
import christmas.error.ErrorMessageFormatter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

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
            throw new IllegalArgumentException(ErrorMessageFormatter.get(ErrorMessage.INVALID_DATE.get()));
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

    public void validateDuplicateOfMenuInput(String[] menus) {
        Set<String> menuSet = new HashSet<>(List.of(menus));
        if (menuSet.size() != menus.length) {
            throw new IllegalArgumentException(ErrorMessageFormatter.get(ErrorMessage.INVALID_MENU_INPUT.get()));
        }
    }

    public void validateEachMenu(String eachMenu) {
        if (!Pattern.matches(MenuConfig.MENU_INPUT_REGEX, eachMenu)) {
            throw new IllegalArgumentException(ErrorMessageFormatter.get(ErrorMessage.INVALID_MENU_INPUT.get()));
        }
    }
}
