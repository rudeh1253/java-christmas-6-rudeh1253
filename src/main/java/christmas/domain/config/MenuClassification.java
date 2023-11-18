package christmas.domain.config;

/**
 * 각 메뉴의 분류를 모아둔 enum.
 */
public enum MenuClassification {
    APPETIZER("애피타이저"),
    MAIN("메인"),
    DESSERT("디저트"),
    BEVERAGE("음료");

    private final String name;

    MenuClassification(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
