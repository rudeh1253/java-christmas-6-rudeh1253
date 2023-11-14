package christmas.validation;

import christmas.domain.Order;
import christmas.domain.config.MenuClassification;
import christmas.domain.config.MenuConfig;
import christmas.domain.config.MenuList;
import christmas.error.ErrorMessage;
import christmas.error.ErrorMessageFormatter;

import java.util.List;
import java.util.Map;

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

    public void validateNotWithOnlyNotAllowedMenu(List<MenuClassification> menuClassifications) {
        for (MenuClassification classification : menuClassifications) {
            if (classification != MenuConfig.NOT_ALLOWED_SINGLE_CLASSIFICATION_ORDER) {
                return;
            }
        }
        throw new IllegalArgumentException(ErrorMessageFormatter.get(ErrorMessage.INVALID_MENU_INPUT.get()));
    }

    public void validateTotalQuantity(Map<String, Integer> orders) {
        if (getValuesSum(orders) > MenuConfig.TOTAL_ORDER_QUANTITY_LIMIT) {
            throw new IllegalArgumentException(ErrorMessageFormatter.get(ErrorMessage.INVALID_MENU_INPUT.get()));
        }
    }

    private int getValuesSum(Map<String, Integer> orders) {
        return orders.values().stream().mapToInt(value -> value).sum();
    }
}
