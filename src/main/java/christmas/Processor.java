package christmas;

import christmas.domain.Order;
import christmas.domain.Result;
import christmas.domain.VisitDate;
import christmas.domain.config.EventBadge;
import christmas.view.InputView;
import christmas.view.OutputView;

import java.util.Map;

/**
 * 프로그램 흐름을 제어한다.
 */
public class Processor {
    private static final Processor SINGLETON = new Processor();

    private final InputView inputView = InputView.getInstance();
    private final OutputView outputView = OutputView.getInstance();

    private Processor() {
    }

    /**
     * Processor 객체를 반환한다. 싱글턴.
     * @return 싱글턴 객체를 반환한다.
     */
    public static Processor getInstance() {
        return SINGLETON;
    }

    /**
     * 시작 메시지를 표시한다.
     */
    public void showStartMessage() {
        outputView.printStartMessage();
    }

    /**
     * 방문 날짜를 입력받는다.
     * @return 방문 날짜를 나타내는 VisitDate 객체.
     */
    public VisitDate inputVisitDate() {
        try {
            int day = inputView.readDate();
            return new VisitDate(day);
        } catch (IllegalArgumentException e) {
            outputView.printWithLine(e.getMessage());
            return inputVisitDate();
        }
    }

    /**
     * 주문을 입력받는다.
     * @return 주문 내역을 담고 있는 Order 클래스를 반환한다.
     */
    public Order inputOrder() {
        try {
            Map<String, Integer> orders = inputView.readOrder();
            return new Order(orders);
        } catch (IllegalArgumentException e) {
            outputView.printWithLine(e.getMessage());
            return inputOrder();
        }
    }

    /**
     * 결과를 처리한다.
     * @param visitDate 방문 날짜.
     * @param order 주문 내역.
     * @return 결과가 담긴 Result 객체를 반환한다.
     */
    public Result processResult(VisitDate visitDate, Order order) {
        return new Result(visitDate, order);
    }

    /**
     * Result가 담고 있는 결과 내용을 표시한다.
     * @param result 결과를 담고 있는 Result 객체
     */
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
        outputView.printEventBadge(EventBadge.getBadgeByBenefitAmount(result.getTotalBenefitAmount()).getName());
    }
}
