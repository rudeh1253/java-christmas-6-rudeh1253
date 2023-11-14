package christmas.domain.config;

public enum EventBadge {
    NONE("없음", 0),
    STAR("별", 5000),
    TREE("트리", 10000),
    SANTA("산타", 20000);

    private final String name;
    private final int price;

    EventBadge(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public static EventBadge getBadgeByOrderAmount(int orderAmount) {
        if (orderAmount < STAR.price) {
            return NONE;
        }
        if (orderAmount < TREE.price) {
            return STAR;
        }
        if (orderAmount < SANTA.price) {
            return TREE;
        }
        return SANTA;
    }
}
