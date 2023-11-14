package christmas.view;

import static christmas.view.config.OutputConfig.*;

public class OutputView {
    private static final OutputView SINGLETON = new OutputView();

    public static OutputView getInstance() {
        return SINGLETON;
    }

    private void printWithLine(String msg) {
        System.out.println(msg);
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

    public void printResultTitle() {
        printWithLine(RESULT_TITLE.get());
        printBlankLine();
    }
}
