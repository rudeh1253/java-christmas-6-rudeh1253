package christmas.domain.config;

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
