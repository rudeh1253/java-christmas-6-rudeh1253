package christmas.domain.config.benefit;

import christmas.domain.config.MenuClassification;

/**
 * 할인과 관련된 상수를 모아 놓은 설정 클래스.
 */
public class DiscountConfig {
    public static final int MIN_ORDER_AMOUNT_FOR_DISCOUNT = 10000;
    public static final int DISCOUNT_FOR_SPECIAL_DAY = 1000;
    public static final int SPECIAL_DISCOUNT_PER_MENU = 2023;

    public static MenuClassification whatToExtract(boolean weekend) {
        if (weekend) {
            return MenuClassification.MAIN;
        }
        return MenuClassification.DESSERT;
    }
}
