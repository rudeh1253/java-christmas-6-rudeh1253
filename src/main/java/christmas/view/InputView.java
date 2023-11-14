package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.validation.InputValidator;

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
}
