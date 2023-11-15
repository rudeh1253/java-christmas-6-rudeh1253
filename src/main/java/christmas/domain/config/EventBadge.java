package christmas.domain.config;

/**
 * 이벤트 배지 종류를 모아 놓은 enum.
 */
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

    /**
     * 총 혜택 금액으로부터 발급할 배지를 반환한다.
     * @param benefitAmount 총 혜택 금액.
     * @return 총 혜택 금액에 따른 이벤트 배지를 반환한다.
     */
    public static EventBadge getBadgeByBenefitAmount(int benefitAmount) {
        if (benefitAmount < STAR.price) {
            return NONE;
        }
        if (benefitAmount < TREE.price) {
            return STAR;
        }
        if (benefitAmount < SANTA.price) {
            return TREE;
        }
        return SANTA;
    }
}
