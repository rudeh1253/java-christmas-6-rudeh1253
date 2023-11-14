package christmas.domain;

import christmas.domain.benefit.Benefit;
import christmas.domain.benefit.Discount;
import christmas.domain.config.EventBadge;
import christmas.domain.config.Menu;
import christmas.domain.config.benefit.BenefitConfig;

import java.util.Map;

public class Result {
    private final VisitDate visitDate;
    private final Order order;
    private final Benefit benefit;

    public Result(VisitDate visitDate, Order order) {
        this.visitDate = visitDate;
        this.order = order;
        this.benefit = new Benefit(Discount.generateInstance(visitDate, order), getNumOfGiveaway(order));
    }

    private int getNumOfGiveaway(Order order) {
        if (order.calculateOrderAmount() >= BenefitConfig.STANDARD_FOR_GIVEAWAY) {
            return 1;
        }
        return 0;
    }

    public VisitDate getVisitDate() {
        return this.visitDate;
    }

    public Map<String, Integer> getOrders() {
        return this.order.getOrder();
    }

    public int getOrderAmountBeforeDiscount() {
        return this.order.calculateOrderAmount();
    }

    public boolean isQualifiedForGiveaway() {
        return this.benefit.isQualifiedForGiveaway();
    }

    public Discount getDiscount() {
        return this.benefit.getDiscount();
    }

    public int getTotalBenefitAmount() {
        return this.benefit.getFullDiscount();
    }

    public int getCostAfterDiscountApplied() {
        return this.order.calculateOrderAmount() - this.benefit.getFullDiscount();
    }

    public EventBadge getEventBadge() {
        return EventBadge.getBadgeByOrderAmount(this.order.calculateOrderAmount());
    }
}
