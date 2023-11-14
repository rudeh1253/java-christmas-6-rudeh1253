package christmas.domain;

import christmas.domain.config.VisitDateConfig;
import christmas.validation.VisitDateValidator;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class VisitDate {
    private final VisitDateValidator validator;
    private final LocalDate date;

    public VisitDate(int day) {
        this.validator = VisitDateValidator.getInstance();
        validate(day);
        this.date = LocalDate.of(VisitDateConfig.YEAR, VisitDateConfig.MONTH, day);
    }

    private void validate(int day) {
        validator.validateDate(day);
    }

    public int getDay() {
        return this.date.getDayOfMonth();
    }

    public int calculateDDayDiscount() {
        int day = this.date.getDayOfMonth();
        if (day > VisitDateConfig.DAY_OF_CHRISTMAS) {
            return 0;
        }
        return VisitDateConfig.BASIC_D_DAY_DISCOUNT
                + (day - VisitDateConfig.DATE_MIN) * VisitDateConfig.D_DAY_DISCOUNT_PER_DAY;
    }

    public boolean isDayOfSpecialDiscount() {
        return isSunday() || isChristmas();
    }

    private boolean isSunday() {
        return this.date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private boolean isChristmas() {
        return this.date.getDayOfMonth() == VisitDateConfig.DAY_OF_CHRISTMAS;
    }

    public boolean isWeekend() {
        return VisitDateConfig.WEEKENDS.contains(this.date.getDayOfWeek());
    }

    @Override
    public String toString() {
        return this.date.toString();
    }
}
