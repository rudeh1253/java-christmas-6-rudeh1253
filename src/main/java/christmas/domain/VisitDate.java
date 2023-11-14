package christmas.domain;

import christmas.domain.config.VisitDateConfig;
import christmas.validation.VisitDateValidator;

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

    public int calculateDiscountByDate() {
        int day = this.date.getDayOfMonth();
        if (day > VisitDateConfig.D_DAY_DISCOUNT_DUE_DAY) {
            return 0;
        }
        return VisitDateConfig.BASIC_D_DAY_DISCOUNT
                + (day - VisitDateConfig.DATE_MIN) * VisitDateConfig.D_DAY_DISCOUNT_PER_DAY;
    }

    @Override
    public String toString() {
        return this.date.toString();
    }
}
