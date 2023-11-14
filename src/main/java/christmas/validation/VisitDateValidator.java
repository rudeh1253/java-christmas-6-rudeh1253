package christmas.validation;

import christmas.domain.config.VisitDateConfig;
import christmas.error.ErrorMessage;
import christmas.error.ErrorMessageFormatter;

public class VisitDateValidator {
    private static final VisitDateValidator SINGLETON = new VisitDateValidator();

    public static VisitDateValidator getInstance() {
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
        return date >= VisitDateConfig.DATE_MIN;
    }

    private boolean isEqualOrSmallerThanMax(int date) {
        return date <= VisitDateConfig.DATE_MAX;
    }
}
