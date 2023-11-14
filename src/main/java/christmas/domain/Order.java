package christmas.domain;

import christmas.domain.config.MenuClassification;
import christmas.domain.config.Menu;
import christmas.validation.OrderValidator;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Order {
    private final Set<SingleOrder> orders;
    private final OrderValidator validator;

    private record SingleOrder(Menu menu, int quantity, MenuClassification menuClassification) {

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof SingleOrder compareTarget)) {
                return false;
            }
            return this.menu == compareTarget.menu() && this.quantity == compareTarget.quantity();
        }

        @Override
        public int hashCode() {
            return this.menu().hashCode() + quantity;
        }
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Order compareTarget)) {
            return false;
        }
        return this.orders.containsAll(compareTarget.orders)
                && compareTarget.orders.containsAll(this.orders);
    }

    @Override
    public int hashCode() {
        return this.orders.hashCode();
    }

    @Override
    public String toString() {
        return this.orders.toString();
    }

    public Order getDessertOrder() {
        return filter(MenuClassification.DESSERT);
    }

    public Order getMainDishOrder() {
        return filter(MenuClassification.MAIN);
    }

    private Order filter(MenuClassification standard) {
        Map<String, Integer> extractedOrderContent = this.orders.stream()
                .filter(order -> order.menu().getClassification() == standard)
                .collect(Collectors.toMap(order -> order.menu().getName(), SingleOrder::quantity));
        if (extractedOrderContent.isEmpty()) {
            return null;
        }
        return new Order(extractedOrderContent);
    }
}
