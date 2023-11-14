package christmas.validation;

import christmas.domain.config.MenuConfig;
import christmas.domain.config.MenuList;
import christmas.error.ErrorMessage;
import christmas.error.ErrorMessageFormatter;

public class OrderValidator {
    private static final OrderValidator SINGLETON = new OrderValidator();

    private OrderValidator() {
    }

    public static OrderValidator getInstance() {
        return SINGLETON;
    }

    public void validateValidityOfMenus(String menuName) {
        if (!MenuList.containsGivenName(menuName)) {
            throw new IllegalArgumentException(ErrorMessageFormatter.get(ErrorMessage.INVALID_MENU_INPUT.get()));
        }
    }

    public void validateOrderQuantity(int order) {
        if (order < MenuConfig.MIN_ORDER_QUANTITY_OF_ONE_MENU) {
            throw new IllegalArgumentException(ErrorMessageFormatter.get(ErrorMessage.INVALID_MENU_INPUT.get()));
        }
    }
}
