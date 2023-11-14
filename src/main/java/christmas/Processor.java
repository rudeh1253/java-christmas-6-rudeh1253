package christmas;

import christmas.domain.Order;
import christmas.domain.Result;
import christmas.domain.VisitDate;
import christmas.domain.config.EventBadge;
import christmas.view.InputView;
import christmas.view.OutputView;

import java.util.Map;

public class Processor {
    private static final Processor SINGLETON = new Processor();

    private final InputView inputView = InputView.getInstance();
    private final OutputView outputView = OutputView.getInstance();

    private Processor() {
    }

    public static Processor getInstance() {
        return SINGLETON;
    }

    public void showStartMessage() {
        outputView.printStartMessage();
    }

    public VisitDate inputVisitDate() {
        try {
            int day = inputView.readDate();
            return new VisitDate(day);
        } catch (IllegalArgumentException e) {
            outputView.printWithLine(e.getMessage());
            return inputVisitDate();
        }
    }

    public Order inputOrder() {
        try {
            Map<String, Integer> orders = inputView.readOrder();
            return new Order(orders);
        } catch (IllegalArgumentException e) {
            outputView.printWithLine(e.getMessage());
            return inputOrder();
        }
    }

    public Result processResult(VisitDate visitDate, Order order) {
        return new Result(visitDate, order);
    }

    public void showResult(Result result) {
        outputView.printResultTitle(result.getVisitDate().getDay());
        outputView.printOrderedMenus(result.getOrders());
        outputView.printOrderAmountBeforeDiscount(result.getOrderAmountBeforeDiscount());
        outputView.printGiveaway(result.isQualifiedForGiveaway());
        outputView.printBenefitHistory(
                result.getTotalBenefitAmount(), result.getDiscount(),
                result.isQualifiedForGiveaway(), result.getVisitDate()
        );
        outputView.printFullBenefit(result.getTotalBenefitAmount());
        outputView.printCostAfterDiscountApplied(result.getCostAfterDiscountApplied());
        outputView.printEventBadge(EventBadge.getBadgeByOrderAmount(result.getTotalBenefitAmount()).getName());
    }
}
