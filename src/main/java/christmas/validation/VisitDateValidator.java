package christmas.validation;

import christmas.domain.config.VisitDateConfig;
import christmas.error.ErrorMessage;
import christmas.error.ErrorMessageFormatter;

/**
 * VisitDate 객체와 관련된 검증 기능을 담고 있는 클래스.
 */
public class VisitDateValidator {
    private static final VisitDateValidator SINGLETON = new VisitDateValidator();

    /**
     * VisitDateValidator 객체를 반환한다. 싱글턴.
     * @return 싱글턴 객체를 반환한다.
     */
    public static VisitDateValidator getInstance() {
        return SINGLETON;
    }

    /**
     * 날자를 검증한다. VisitDateConfig.DATE_MIN ~ VisitDateConfig.DATE_MAX에 해당하지 않을 경우
     * IllegalArgumentException이 발생한다.
     * @param date 검증할 날짜.
     */
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
