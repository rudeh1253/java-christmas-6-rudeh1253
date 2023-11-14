package christmas.domain;

import christmas.domain.config.DiscountConfig;

public class Discount {
    private final VisitDate visitDate;
    private final Order order;

    protected Discount(VisitDate visitDate, Order order) {
        this.visitDate = visitDate;
        this.order = order;
    }

    public static Discount generateInstance(VisitDate visitDate, Order order) {
        if (order.calculateOrderAmount() < DiscountConfig.MIN_ORDER_AMOUNT_FOR_DISCOUNT) {
            return new BlankDiscount();
        }
        return new Discount(visitDate, order);
    }

    private static class BlankDiscount extends Discount {

        BlankDiscount() {
            super(null, null);
        }

        @Override
        public int getDDayDiscount() {
            return 0;
        }

        @Override
        public int getDayOfWeekDiscount() {
            return 0;
        }

        @Override
        public int getSpecialDiscount() {
            return 0;
        }
    }

    public int getDDayDiscount() {
        return this.visitDate.calculateDDayDiscount();
    }

    public int getDayOfWeekDiscount() {
        return this.order
                .filterByMenuClassification(DiscountConfig.whatToExtract(this.visitDate.isWeekend()))
                .getNumOfMenus()
                * DiscountConfig.SPECIAL_DISCOUNT_PER_MENU;
    }

    public int getSpecialDiscount() {
        if (this.visitDate.isDayOfSpecialDiscount()) {
            return DiscountConfig.DISCOUNT_FOR_SPECIAL_DAY;
        }
        return 0;
    }
}
