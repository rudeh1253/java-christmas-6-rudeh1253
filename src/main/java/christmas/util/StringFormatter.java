package christmas.util;

import static christmas.view.config.OutputConfig.BETWEEN_DISCOUNT_HISTORY_NAME_AND_AMOUNT;
import static christmas.view.config.OutputConfig.CURRENCY_UNIT;
import static christmas.view.config.OutputConfig.DAY;
import static christmas.view.config.OutputConfig.LINE_BREAKING;
import static christmas.view.config.OutputConfig.MONTH;
import static christmas.view.config.OutputConfig.NUMBER_DELIMITER;
import static christmas.view.config.OutputConfig.QUANTITY;

import christmas.domain.config.VisitDateConfig;

import java.time.temporal.ChronoField;

/**
 * 문자열 포매팅 기능과 관련된 함수들을 모아 놓은 클래스.
 */
public class StringFormatter {
    private static final StringFormatter SINGLETON = new StringFormatter();
    private static final int NUMBER_DELIMITER_GAP = 3;

    private StringFormatter() {
    }

    /**
     * 인스턴스를 반환한다. 싱글턴.
     * @return 싱글턴 인스턴스를 반환한다.
     */
    public static StringFormatter getInstance() {
        return SINGLETON;
    }

    /**
     * 방문 날짜를 요구사항에 맞는 출력 형태에 맞게 정제해서 반환한다.
     * @param day 방문 날짜.
     * @return 예를 들어 day가 3일 경우 "12월 3일"이라는 문자열을 반환한다.
     */
    public String formatVisitDate(int day) {
        return VisitDateConfig.MONTH.get(ChronoField.MONTH_OF_YEAR)
                + MONTH.get() + " " + day + DAY.get();
    }

    /**
     * 주문 메뉴를 요구사항에 맞는 출력 형태에 맞게 정제해서 반환한다.
     * 메뉴 전체가 아닌 개별 메뉴에 따라 문자열을 생성해서 반환한다.
     * @param menuName 메뉴 이름.
     * @param quantity 주문 개수.
     * @return "(메뉴 이름) (개수)개"와 같은 형태로 반환한다.
     */
    public String formatOrderMenu(String menuName, int quantity) {
        return menuName + " " + quantity + QUANTITY.get();
    }

    /**
     * 주어진 금액을 요구사항에 맞는 출력 형태로 정제해서 반환한다.
     * @param money 금액.
     * @return money가 10000이라고 한다면 "10,000원"이라는 문자열을 반환한다.
     */
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

    /**
     * 혜택 내역을 반환한다.
     * 개별 혜택 내역을 하나씩 다룬다.
     * @param benefitName 혜택 이름.
     * @param discountAmount 혜택 금액.
     * @return "(혜택): -(혜택 금액)원"과 같은 형태로 반환한다. 혜택 금액은 콤마(,)로 구분된 문자열이다.
     */
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
