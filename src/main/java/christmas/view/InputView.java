package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.domain.config.MenuConfig;
import christmas.validation.InputValidator;

import java.util.HashMap;
import java.util.Map;

public class InputView {
    private static final InputView SINGLETON = new InputView();
    private static final InputValidator INPUT_VALIDATOR = InputValidator.getInstance();
    private static final OutputView OUTPUT_VIEW = OutputView.getInstance();

    private InputView() {
    }

    public static InputView getInstance() {
        return SINGLETON;
    }

    public int readDate() {
        OUTPUT_VIEW.askVisitDate();
        String input = Console.readLine();
        INPUT_VALIDATOR.validateDate(input);
        return Integer.parseInt(input);
    }

    public Map<String, Integer> readMenu() {
        OUTPUT_VIEW.askMenuAndNumber();
        String input = Console.readLine();
        String[] parsedInput = input.split(MenuConfig.MENU_DELIMITER);
        Map<String, Integer> orders = new HashMap<>();
        for (String each : parsedInput) {
            String[] menuAndNumber = each.split(MenuConfig.MENU_AND_NUMBER_DELIMITER);
            orders.put(menuAndNumber[0], Integer.parseInt(menuAndNumber[1]));
        }
        return orders;
    }
}
