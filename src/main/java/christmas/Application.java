package christmas;

import christmas.domain.Order;
import christmas.domain.Result;
import christmas.domain.VisitDate;

/**
 * 메인 클래스.
 */
public class Application {

    /**
     * 프로그램 엔트리.
     */
    public static void main(String[] args) {
        Processor processor = Processor.getInstance();
        processor.showStartMessage();
        VisitDate visitDate = processor.inputVisitDate();
        Order order = processor.inputOrder();
        Result result = processor.processResult(visitDate, order);
        processor.showResult(result);
    }
}
