package christmas.validation;

import christmas.domain.config.MenuList;
import christmas.error.ErrorMessage;
import christmas.error.ErrorMessageFormatter;

public class MenuValidator {
    private static final MenuValidator SINGLETON = new MenuValidator();

    private MenuValidator() {
    }

    public static MenuValidator getInstance() {
        return SINGLETON;
    }

    public void validateValidityOfMenus(String menuName) {
        if (!MenuList.containsGivenName(menuName)) {
            throw new IllegalArgumentException(ErrorMessageFormatter.get(ErrorMessage.INVALID_MENU_INPUT.get()));
        }
    }
}
