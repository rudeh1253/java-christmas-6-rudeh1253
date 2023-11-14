package christmas.validation;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import christmas.error.ErrorMessage;
import christmas.error.ErrorMessageFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class VisitDateValidatorTest {
    private VisitDateValidator validator = VisitDateValidator.getInstance();

    @DisplayName("날짜가 1~31에 해당하지 않을 경우 예외 발생 - IllegalArgumentException")
    @ValueSource(ints = { 32, 0, -1, Integer.MIN_VALUE, Integer.MAX_VALUE })
    @ParameterizedTest
    void validateDate_ThrowIllegalArgumentException_OutOfRange(int date) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validator.validateDate(date))
                .withMessage(ErrorMessageFormatter.get(ErrorMessage.INVALID_DATE.get()));
    }

    @DisplayName("날짜가 1~31에 해당할 경우 예외가 발생하지 않음")
    @ValueSource(ints = { 1, 2, 31 })
    @ParameterizedTest
    void validate_NoExceptionIsThrown(int date) {
        assertThatNoException().isThrownBy(() -> validator.validateDate(date));
    }
}