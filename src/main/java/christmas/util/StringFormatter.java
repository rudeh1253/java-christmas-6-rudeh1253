package christmas.util;

import static christmas.view.config.OutputConfig.BETWEEN_DISCOUNT_HISTORY_NAME_AND_AMOUNT;
import static christmas.view.config.OutputConfig.CURRENCY_UNIT;
import static christmas.view.config.OutputConfig.DAY;
import static christmas.view.config.OutputConfig.LINE_BREAKING;
import static christmas.view.config.OutputConfig.MONTH;
import static christmas.view.config.OutputConfig.NUMBER_DELIMITER;
import static christmas.view.config.OutputConfig.QUANTITY;

import christmas.domain.VisitDate;
import christmas.domain.config.VisitDateConfig;

import java.time.temporal.ChronoField;

public class StringFormatter {
    private static final StringFormatter SINGLETON = new StringFormatter();
    private static final int NUMBER_DELIMITER_GAP = 3;

    private StringFormatter() {
    }

    public static StringFormatter getInstance() {
        return SINGLETON;
    }

    public String formatVisitDate(int day) {
        return VisitDateConfig.MONTH.get(ChronoField.MONTH_OF_YEAR)
                + MONTH.get() + " " + day + DAY.get();
    }

    public String formatOrderMenu(String menuName, int quantity) {
        return menuName + " " + quantity + QUANTITY.get();
    }

    public String formatMoney(int money) {
        return formatNumber(money) + CURRENCY_UNIT.get();
    }

    private String formatNumber(int number) {
        if (number < Math.pow(10, NUMBER_DELIMITER_GAP)) {
            return String.valueOf(number);
        }
        String numberInString = String.valueOf(number);
        StringBuilder sb = new StringBuilder();
        int i;
        for (i = numberInString.length() - NUMBER_DELIMITER_GAP; i >= 0; i -= NUMBER_DELIMITER_GAP) {
            sb.insert(0, numberInString.substring(i, i + NUMBER_DELIMITER_GAP));
            sb.insert(0, NUMBER_DELIMITER.get());
        }
        sb.insert(0, numberInString.substring(0, i + NUMBER_DELIMITER_GAP));
        return sb.toString();
    }

    public String formatSingleBenefitHistory(String benefitName, int discountAmount) {
        if (discountAmount == 0) {
            return "";
        }
        return benefitName
                + BETWEEN_DISCOUNT_HISTORY_NAME_AND_AMOUNT.get()
                + formatMoney(discountAmount)
                + LINE_BREAKING.get();
    }
}
