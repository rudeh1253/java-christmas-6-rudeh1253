package christmas.view;

import static christmas.view.config.OutputConfig.ASK_MENU_AND_NUMBER;
import static christmas.view.config.OutputConfig.ASK_VISIT_DATE;
import static christmas.view.config.OutputConfig.CHRISTMAS_D_DAY_DISCOUNT;
import static christmas.view.config.OutputConfig.DECEMBER_EVENT_BADGE;
import static christmas.view.config.OutputConfig.EXPECTED_TOTAL_COST;
import static christmas.view.config.OutputConfig.GIVEAWAY;
import static christmas.view.config.OutputConfig.GIVEAWAY_EVENT;
import static christmas.view.config.OutputConfig.GIVEAWAY_MENU;
import static christmas.view.config.OutputConfig.NONE;
import static christmas.view.config.OutputConfig.ORDER_AMOUNT_BEFORE_DISCOUNT;
import static christmas.view.config.OutputConfig.ORDER_MENU;
import static christmas.view.config.OutputConfig.BENEFIT_LIST;
import static christmas.view.config.OutputConfig.PROGRAM_START_MESSAGE;
import static christmas.view.config.OutputConfig.RESULT_TITLE;
import static christmas.view.config.OutputConfig.SPECIAL_DISCOUNT;
import static christmas.view.config.OutputConfig.TOTAL_BENEFIT;
import static christmas.view.config.OutputConfig.WEEKDAY_DISCOUNT;
import static christmas.view.config.OutputConfig.WEEKEND_DISCOUNT;

import christmas.domain.Order;
import christmas.domain.VisitDate;
import christmas.domain.benefit.Benefit;
import christmas.domain.benefit.Discount;
import christmas.domain.config.benefit.BenefitConfig;
import christmas.util.StringFormatter;

import java.util.Map;

public class OutputView {
    private static final OutputView SINGLETON = new OutputView();

    private final StringFormatter formatter = StringFormatter.getInstance();

    public static OutputView getInstance() {
        return SINGLETON;
    }

    public void printWithLine(String message) {
        System.out.println(message);
    }

    private void printWithoutLine(String message) {
        System.out.print(message);
    }

    private void printBlankLine() {
        System.out.println();
    }

    public void printStartMessage() {
        printWithLine(PROGRAM_START_MESSAGE.get());
    }

    public void askVisitDate() {
        printWithLine(ASK_VISIT_DATE.get());
    }

    public void askMenuAndNumber() {
        printWithLine(ASK_MENU_AND_NUMBER.get());
    }

    public void printResultTitle(int day) {
        printWithLine(formatter.formatVisitDate(day) + RESULT_TITLE.get());
        printBlankLine();
    }

    public void printOrderedMenus(Map<String, Integer> orders) {
        printWithLine(ORDER_MENU.get());
        orders.forEach((name, quantity) -> printWithLine(formatter.formatOrderMenu(name, quantity)));
        printBlankLine();
    }

    public void printOrderAmountBeforeDiscount(int orderAmount) {
        printWithLine(ORDER_AMOUNT_BEFORE_DISCOUNT.get());
        printWithLine(formatter.formatMoney(orderAmount));
        printBlankLine();
    }

    public void printGiveaway(boolean qualifiedForGiveaway) {
        printWithLine(GIVEAWAY_MENU.get());
        printWithLine(getGiveaway(qualifiedForGiveaway));
        printBlankLine();
    }

    private String getGiveaway(boolean giveawayExists) {
        if (giveawayExists) {
            return GIVEAWAY.get();
        }
        return NONE.get();
    }

    public void printBenefitHistory(int totalBenefitAmount, Discount discount,
                                    boolean qualifiedForGiveaway, VisitDate visitDate) {
        printWithLine(BENEFIT_LIST.get());
        if (totalBenefitAmount == 0) {
            printWithLine(NONE.get());
            printBlankLine();
            return;
        }
        printWithoutLine(getDDayDiscountMessage(discount.getDDayDiscount()));
        printWithoutLine(getDayOfWeekDiscount(visitDate.isWeekend(), discount.getDayOfWeekDiscount()));
        printWithoutLine(formatter.formatSingleBenefitHistory(SPECIAL_DISCOUNT.get(), discount.getSpecialDiscount()));
        int giveawayPrice = getGiveawayPrice(qualifiedForGiveaway);
        printWithoutLine(formatter.formatSingleBenefitHistory(GIVEAWAY_EVENT.get(), giveawayPrice));
        printBlankLine();
    }

    private String getDDayDiscountMessage(int discountAmount) {
        return formatter.formatSingleBenefitHistory(CHRISTMAS_D_DAY_DISCOUNT.get(), discountAmount);
    }

    private String getDayOfWeekDiscount(boolean weekend, int discountAmount) {
        if (weekend) {
            return formatter.formatSingleBenefitHistory(WEEKEND_DISCOUNT.get(), discountAmount);
        }
        return formatter.formatSingleBenefitHistory(WEEKDAY_DISCOUNT.get(), discountAmount);
    }

    private int getGiveawayPrice(boolean qualifiedForGiveaway) {
        return getOneIfQualifiedForGiveaway(qualifiedForGiveaway) * BenefitConfig.GIVEAWAY_ITEM.getPrice();
    }

    private int getOneIfQualifiedForGiveaway(boolean qualified) {
        if (qualified) {
            return 1;
        }
        return 0;
    }

    public void printFullBenefit(int amountOfBenefit) {
        printWithLine(TOTAL_BENEFIT.get());
        printWithLine("-" + formatter.formatMoney(amountOfBenefit));
        printBlankLine();
    }

    public void printCostAfterDiscountApplied(int costAfterDiscount) {
        printWithLine(EXPECTED_TOTAL_COST.get());
        printWithLine(formatter.formatMoney(costAfterDiscount));
        printBlankLine();
    }

    public void printEventBadge(String eventBadge) {
        printWithLine(DECEMBER_EVENT_BADGE.get());
        printWithLine(eventBadge);
    }
}
