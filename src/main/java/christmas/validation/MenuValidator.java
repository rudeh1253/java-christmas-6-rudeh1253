package christmas.validation;

import christmas.domain.config.Menu;
import christmas.error.ErrorMessage;
import christmas.error.ErrorMessageFormatter;

import java.util.Set;

public class MenuValidator {
    private static final MenuValidator SINGLETON = new MenuValidator();

    private MenuValidator() {
    }

    public static MenuValidator getInstance() {
        return SINGLETON;
    }

    public void validateValidityOfMenus(String menuName) {
        if (!Menu.containsGivenName(menuName)) {
            throw new IllegalArgumentException(ErrorMessageFormatter.get(ErrorMessage.INVALID_MENU_INPUT.get()));
        }
    }
}
