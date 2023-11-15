package christmas.validation;

import christmas.domain.config.MenuClassification;
import christmas.domain.config.MenuConfig;
import christmas.domain.config.Menu;
import christmas.error.ErrorMessage;
import christmas.error.ErrorMessageFormatter;

import java.util.List;
import java.util.Map;

/**
 * Order 객체와 관련된 검증 기능을 제공하는 클래스.
 */
public class OrderValidator {
    private static final OrderValidator SINGLETON = new OrderValidator();

    private OrderValidator() {
    }

    /**
     * OrderValidator 객체를 반환한다. 싱글턴.
     * @return 싱글턴 객체를 반환한다.
     */
    public static OrderValidator getInstance() {
        return SINGLETON;
    }

    /**
     * 메뉴 이름을 검증한다. 주어진 메뉴 이름이 Menu enum에 정의된 메뉴에 없을 경우 IllegalArgumentException이 발생한다.
     * @param menuName 검증할 메뉴 이름.
     */
    public void validateValidityOfMenus(String menuName) {
        if (!Menu.containsGivenName(menuName)) {
            throw new IllegalArgumentException(ErrorMessageFormatter.get(ErrorMessage.INVALID_MENU_INPUT.get()));
        }
    }

    /**
     * 개별 메뉴 주문의 주문 개수를 검증한다. 주문 개수가 1보다 작을 경우 IllegalArgumentException이 발생한다.
     * @param orderQuantity 검증할 주문 개수.
     */
    public void validateOrderQuantity(int orderQuantity) {
        if (orderQuantity < MenuConfig.MIN_ORDER_QUANTITY_OF_ONE_MENU) {
            throw new IllegalArgumentException(ErrorMessageFormatter.get(ErrorMessage.INVALID_MENU_INPUT.get()));
        }
    }

    /**
     * 주문한 메뉴가 MenuConfig.NOT_ALLOWED_SINGLE_CLASSIFICATION_ORDER에 정의된 분류의 메뉴로만 이루어져 있을 경우
     * IllegalArgumentException이 발생한다.
     * @param menuClassifications 검증할 각 주문 메뉴의 분류.
     */
    public void validateNotWithOnlyNotAllowedMenu(List<MenuClassification> menuClassifications) {
        for (MenuClassification classification : menuClassifications) {
            if (classification != MenuConfig.NOT_ALLOWED_SINGLE_CLASSIFICATION_ORDER) {
                return;
            }
        }
        throw new IllegalArgumentException(ErrorMessageFormatter.get(ErrorMessage.INVALID_MENU_INPUT.get()));
    }

    /**
     * 총 주문 개수를 검증한다. MenuConfig.TOTAL_ORDER_QUANTITY_LIMIT에 정의된 수보다 클 경우
     * IllegalArgumentException이 발생한다.
     * @param orders 검증할 주문. key가 주문한 메뉴 이름, value가 해당 메뉴의 주문 개수다.
     */
    public void validateTotalQuantity(Map<String, Integer> orders) {
        if (getValuesSum(orders) > MenuConfig.TOTAL_ORDER_QUANTITY_LIMIT) {
            throw new IllegalArgumentException(ErrorMessageFormatter.get(ErrorMessage.INVALID_MENU_INPUT.get()));
        }
    }

    private int getValuesSum(Map<String, Integer> orders) {
        return orders.values().stream().mapToInt(value -> value).sum();
    }
}
