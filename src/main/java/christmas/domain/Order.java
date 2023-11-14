package christmas.domain;

import christmas.domain.config.MenuClassification;
import christmas.domain.config.Menu;
import christmas.validation.OrderValidator;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Order {
    private final Set<SingleOrder> orders;
    private final OrderValidator validator;

    private record SingleOrder(Menu menu, int quantity, MenuClassification menuClassification) {
    }

    public Order(Map<String, Integer> orders) {
        this.orders = new HashSet<>();
        this.validator = OrderValidator.getInstance();
        validate(orders);
        init(orders);
    }

    private void validate(Map<String, Integer> orders) {
        validateValidityOfMenus(orders);
        validateEachOrderQuantity(orders);
        validateNotWithOnlyNotAllowedMenu(orders);
        validateTotalQuantity(orders);
    }

    private void validateValidityOfMenus(Map<String, Integer> orders) {
        orders.keySet().forEach(this.validator::validateValidityOfMenus);
    }

    private void validateEachOrderQuantity(Map<String, Integer> orders) {
        orders.values().forEach(this.validator::validateOrderQuantity);
    }

    private void validateNotWithOnlyNotAllowedMenu(Map<String, Integer> orders) {
        this.validator.validateNotWithOnlyNotAllowedMenu(
                orders.keySet()
                        .stream()
                        .map(menuName -> Menu.getMenuByName(menuName).getClassification())
                        .toList()
        );
    }

    private void validateTotalQuantity(Map<String, Integer> orders) {
        this.validator.validateTotalQuantity(orders);
    }

    private void init(Map<String, Integer> orders) {
        for (String menuName : orders.keySet()) {
            MenuClassification classification = Menu.getMenuByName(menuName).getClassification();
            this.orders.add(
                    new SingleOrder(Menu.getMenuByName(menuName), orders.get(menuName), classification)
            );
        }
    }

    public int getTotalOrderQuantity() {
        return this.orders.stream().mapToInt(SingleOrder::quantity).sum();
    }

    public int calculateOrderAmount() {
        return this.orders.stream()
                .mapToInt(singleOrder -> singleOrder.menu().getPrice() * singleOrder.quantity())
                .sum();
    }
}
