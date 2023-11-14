package christmas.domain;

import christmas.domain.config.DiscountConfig;

public class Discount {
    private final VisitDate visitDate;
    private final Order order;
    private final int orderAmountBeforeDiscount;

    protected Discount(VisitDate visitDate, Order order) {
        this.visitDate = visitDate;
        this.order = order;
        this.orderAmountBeforeDiscount = this.order.calculateOrderAmount();
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
    }

    public int getDDayDiscount() {
        return this.visitDate.calculateDDayDiscount();
    }

    public int getSpecialDiscount() {
        if (this.visitDate.isDayOfSpecialDiscount()) {
            return DiscountConfig.DISCOUNT_FOR_SPECIAL_DAY;
        }
        return 0;
    }
}