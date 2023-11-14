package christmas.domain.config.benefit;

import christmas.domain.config.MenuClassification;

public class DiscountConfig {
    public static final int STANDARD_FOR_GIVEAWAY = 120000;
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
