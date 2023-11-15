package christmas.domain.benefit;

import christmas.domain.config.benefit.BenefitConfig;

/**
 * 적용되는 할인 혜택을 저장하는 객체.
 * 객체 내부 상태가 불변하는 immutable type이다.
 */
public class Benefit {
    private final Discount discount;
    private final int numOfGiveaway;

    /**
     * 객체를 생성한다.
     * @param discount 할인 정보가 담긴 객체.
     * @param numOfGiveaway 증정품 개수. 할인 전 총 주문 금액이 120,000원을 넘기면 1, 그렇지 않으면 0이 전달되어야 한다.
     */
    public Benefit(Discount discount, int numOfGiveaway) {
        this.discount = discount;
        this.numOfGiveaway = numOfGiveaway;
    }

    /**
     * 총 혜책 금액을 계산해서 반환한다.
     * (총 혜택 금액) = (총 할인 금액) + (증정품 가격)
     * @return 총 할인 금액과 증정품 가격을 합산해서 반환한다.
     */
    public int getFullDiscount() {
        int dDayDiscount = this.discount.getDDayDiscount();
        int dayOfWeekDiscount = this.discount.getDayOfWeekDiscount();
        int specialDiscount = this.discount.getSpecialDiscount();
        int priceOfGiveaway = BenefitConfig.GIVEAWAY_ITEM.getPrice() * numOfGiveaway;
        return dDayDiscount + dayOfWeekDiscount + specialDiscount + priceOfGiveaway;
    }

    /**
     * 할인 객체를 반환한다.
     * @return 할인 객체. 반환되는 Discount 객체는 immutable type이다.
     */
    public Discount getDiscount() {
        return this.discount;
    }

    /**
     * 증정품이 증정되는지 여부를 반환한다.
     * @return 증정품이 증정되면 true, 그렇지 않으면 false를 반환한다.
     */
    public boolean isGiveawayGiven() {
        return this.numOfGiveaway > 0;
    }
}
