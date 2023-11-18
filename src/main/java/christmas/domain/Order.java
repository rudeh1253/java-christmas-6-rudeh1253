package christmas.domain;

import christmas.domain.config.MenuClassification;
import christmas.domain.config.Menu;
import christmas.validation.OrderValidator;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 주문 내역을 저장하는 클래스.
 */
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

    /**
     * 생성자.
     * @param orders 주문할 메뉴 이름, 주문할 개수가 담긴 Map 인스턴스.
     *               주문할 메뉴 이름이 미리 정의된 메뉴에 존재하지 않는 이름일 경우 IllegalArgumentException이
     *               발생한다.
     */
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

    /**
     * Order 인스턴스가 담고 있는 주문 내역을 Map 인스턴스 형태로 반환한다.
     * @return key가 주문 메뉴, value가 주문 수량인 Map 인스턴스를 반환한다.
     */
    public Map<String, Integer> getOrder() {
        return this.orders
                .stream()
                .collect(Collectors.toMap(order -> order.menu().getName(), SingleOrder::quantity));
    }

    /**
     * 주문할 음식의 개수의 총합을 반환한다.
     * @return "양송이수프" 1개, "시저샐러드" 2개를 주문했을 경우, 3을 반환한다.
     */
    public int getTotalOrderQuantity() {
        return this.orders.stream().mapToInt(SingleOrder::quantity).sum();
    }

    /**
     * 주문한 메뉴의 가지수를 반환한다.
     * @return "양송이수프" 1개, "시저샐러드" 2개를 주문했을 경우, 2를 반환한다.
     */
    public int getNumOfMenus() {
        return this.orders.size();
    }

    /**
     * 주문 내역으로부터 주문 금액 총액을 반환한다.
     * 할인이 적용되기 전 금액을 반환한다.
     * @return 주문 금액 총액.
     */
    public int calculateOrderAmount() {
        return this.orders.stream()
                .mapToInt(singleOrder -> singleOrder.menu().getPrice() * singleOrder.quantity())
                .sum();
    }

    /**
     * 비교할 두 객체가 정확히 똑같은 주문 메뉴, 각 메뉴당 주문할 개수를 계산해서 반환한다.
     */
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

    /**
     * 메뉴 분류에 따라 주문 내역 중 해당 하는 분류에 속하는 메뉴만 반환한다.
     * @param standard 메뉴 분류.
     * @return 분류된 주문 내역을 담고 있는 Order 인스턴스를 반환한다.
     */
    public Order filterByMenuClassification(MenuClassification standard) {
        return filter(standard);
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
