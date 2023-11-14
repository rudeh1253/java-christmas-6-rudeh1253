package christmas.domain.benefit;

import christmas.domain.config.benefit.BenefitConfig;

public class Benefit {
    private final Discount discount;
    private final int numOfGiveaway;

    public Benefit(Discount discount, int numOfGiveaway) {
        this.discount = discount;
        this.numOfGiveaway = numOfGiveaway;
    }

    public int getFullDiscount() {
        int dDayDiscount = this.discount.getDDayDiscount();
        int dayOfWeekDiscount = this.discount.getDayOfWeekDiscount();
        int specialDiscount = this.discount.getSpecialDiscount();
        int priceOfGiveaway = BenefitConfig.GIVEAWAY_ITEM.getPrice() * numOfGiveaway;
        return dDayDiscount + dayOfWeekDiscount + specialDiscount + priceOfGiveaway;
    }

    public Discount getDiscount() {
        return this.discount;
    }
}
