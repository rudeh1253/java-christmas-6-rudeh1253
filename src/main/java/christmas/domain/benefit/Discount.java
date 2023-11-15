package christmas.domain.benefit;

import christmas.domain.Order;
import christmas.domain.VisitDate;
import christmas.domain.config.benefit.DiscountConfig;

/**
 * 각 할인 항목마다 할인 금액을 계산하는 객체.
 * 객체 내부 상태가 불변하는 immutable type이다.
 */
public class Discount {
    private final VisitDate visitDate;
    private final Order order;

    protected Discount(VisitDate visitDate, Order order) {
        this.visitDate = visitDate;
        this.order = order;
    }

    /**
     * 새로운 객체를 생성해서 반환한다.
     * @param visitDate 방문일 객체.
     * @param order 주문 내역이 담긴 객체.
     * @return Discount 객체를 생성해서 반환한다. 할인 전 총 주문 금액이 10,000원 미만일 경우 모든 개별 할인 금액으로 0원을 반환하는
     *                  Discount 객체가 반환된다.
     */
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

    /**
     * 크리스마스 디데이 할인을 반환한다.
     * @return 크리스마스 디데이 할인.
     */
    public int getDDayDiscount() {
        return this.visitDate.calculateDDayDiscount();
    }

    /**
     * 평일 할인 혹은 주말 할인이 적용된 할인 금액을 산출한다.
     * @return 평일 할인 혹은 주말 할인이 적용된 할인 금액.
     */
    public int getDayOfWeekDiscount() {
        Order extracted =
                this.order.filterByMenuClassification(DiscountConfig.whatToExtract(this.visitDate.isWeekend()));
        if (extracted == null) {
            return 0;
        }
        return extracted.getNumOfMenus()
                * DiscountConfig.SPECIAL_DISCOUNT_PER_MENU;
    }

    /**
     * 특별 할인을 산출한다.
     * @return 특별 할인이 적용되는 날일 경우 1000, 그렇지 않으면 0을 반환한다.
     */
    public int getSpecialDiscount() {
        if (this.visitDate.isDayOfSpecialDiscount()) {
            return DiscountConfig.DISCOUNT_FOR_SPECIAL_DAY;
        }
        return 0;
    }
}
