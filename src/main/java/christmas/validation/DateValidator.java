package christmas.validation;

import christmas.domain.config.DateConfig;
import christmas.error.ErrorMessage;
import christmas.error.ErrorMessageFormatter;

public class DateValidator {
    private static final DateValidator SINGLETON = new DateValidator();

    public static DateValidator getInstance() {
        return SINGLETON;
    }

    public void validateDate(int date) {
        if (!isInRange(date)) {
            throw new IllegalArgumentException(ErrorMessageFormatter.get(ErrorMessage.INVALID_DATE.get()));
        }
    }

    private boolean isInRange(int date) {
        return isEqualOrLargerThanMin(date) && isEqualOrSmallerThanMax(date);
    }

    private boolean isEqualOrLargerThanMin(int date) {
        return date >= DateConfig.DATE_MIN.get();
    }

    private boolean isEqualOrSmallerThanMax(int date) {
        return date <= DateConfig.DATE_MAX.get();
    }
}
