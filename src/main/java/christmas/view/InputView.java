package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.domain.config.MenuConfig;
import christmas.validation.InputValidator;

import java.util.HashMap;
import java.util.Map;

/**
 * 사용자 입력을 담당하는 뷰 클래스.
 */
public class InputView {
    private static final InputView SINGLETON = new InputView();

    private final InputValidator inputValidator = InputValidator.getInstance();
    private final OutputView outputView = OutputView.getInstance();

    private InputView() {
    }

    /**
     * InputView 객체를 반환한다. 싱글턴.
     * @return 싱글턴 객체를 반환한다.
     */
    public static InputView getInstance() {
        return SINGLETON;
    }

    /**
     * 방문 날짜를 입력받는다.
     * @return 입력된 방문 날짜를 정수형으로 변환해서 반환한다.
     */
    public int readDate() {
        outputView.askVisitDate();
        String input = Console.readLine();
        inputValidator.validateDate(input);
        return Integer.parseInt(input);
    }

    /**
     * 주문 내역을 입력받는다.
     * @return 입력된 주문 내역. key가 주문한 메뉴 이름, value가 해당 메뉴 주문 개수다.
     */
    public Map<String, Integer> readOrder() {
        outputView.askMenuAndNumber();
        String input = Console.readLine();
        String[] parsedInput = input.split(MenuConfig.MENU_DELIMITER);
        Map<String, Integer> orders = new HashMap<>();
        for (String each : parsedInput) {
            inputValidator.validateEachMenu(each);
            String[] menuAndNumber = each.split(MenuConfig.MENU_AND_NUMBER_DELIMITER);
            inputValidator.validateDuplicateOfMenuInput(orders.keySet(), menuAndNumber[0]);
            orders.put(menuAndNumber[0], Integer.parseInt(menuAndNumber[1]));
        }
        return orders;
    }
}
