package christmas.domain;

import christmas.domain.config.VisitDateConfig;
import christmas.validation.VisitDateValidator;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * 방문 날짜를 나타내는 클래스.
 * 연도는 2023, 달은 12월로 고정된다.
 */
public class VisitDate {
    private final VisitDateValidator validator;
    private final LocalDate date;

    /**
     * 주어진 날짜로부터 VisitDate 인스턴스를 반환한다.
     * @param day 방문할 날.
     */
    public VisitDate(int day) {
        this.validator = VisitDateValidator.getInstance();
        validate(day);
        this.date = LocalDate.of(VisitDateConfig.YEAR, VisitDateConfig.MONTH, day);
    }

    private void validate(int day) {
        validator.validateDate(day);
    }

    /**
     * 방문날을 반환한다. 일수만 반환한다.
     * @return 방문할 날짜의 일.
     */
    public int getDay() {
        return this.date.getDayOfMonth();
    }

    /**
     * 크리스마스 디데이 할인 금액을 계산해서 반환한다.
     * @return 크리스마스 디데이 할인 금액.
     */
    public int calculateDDayDiscount() {
        int day = this.date.getDayOfMonth();
        if (day > VisitDateConfig.DAY_OF_CHRISTMAS) {
            return 0;
        }
        return VisitDateConfig.BASIC_D_DAY_DISCOUNT
                + (day - VisitDateConfig.DATE_MIN) * VisitDateConfig.D_DAY_DISCOUNT_PER_DAY;
    }

    /**
     * 이 객체가 나타내는 방문 날짜가 특별 할인이 적용되는 날짜인지 여부를 반환한다.
     * @return 이 객체가 나타내는 방문 날짜가 특별 할인이 적용되는 날일 경우 true, 그렇지 않으면 false를 반환한다.
     */
    public boolean isDayOfSpecialDiscount() {
        return isSunday() || isChristmas();
    }

    private boolean isSunday() {
        return this.date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private boolean isChristmas() {
        return this.date.getDayOfMonth() == VisitDateConfig.DAY_OF_CHRISTMAS;
    }

    /**
     * 이 객체가 나타내는 날짜가 주말(금, 토)인지 여부를 반환한다.
     * @return 이 객체가 나타내는 날짜가 주말일 경우 true, 그렇지 않으면 false를 반환한다.
     */
    public boolean isWeekend() {
        return VisitDateConfig.WEEKENDS.contains(this.date.getDayOfWeek());
    }

    @Override
    public String toString() {
        return this.date.toString();
    }
}
