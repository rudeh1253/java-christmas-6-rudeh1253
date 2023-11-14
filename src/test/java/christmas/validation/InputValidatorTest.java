package christmas.validation;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import christmas.error.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InputValidatorTest {
    InputValidator validator = InputValidator.getInstance();

    @DisplayName("문자열이 정수를 나타내지 않는 경우 예외 처리 - IllegalArgumentException")
    @ValueSource(strings = { "13a", "b96", "3434 ", " 5656", "1a56", "aab", "!@", " ", "", "3.14" })
    @ParameterizedTest
    void validateDate_ThrowAnIllegalArgumentException_IfTheInputDoesNotRepresentANumber
            (String input) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validator.validateDate(input))
                .withMessage(ErrorMessage.INVALID_DATE.get());
    }

    @DisplayName("문자열이 정수를 나타내지 않는 경우 예외 처리 - IllegalArgumentException")
    @Test
    void validateDate_ThrowAnIllegalArgumentException_IfNullIsPassed() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validator.validateDate(null))
                .withMessage(ErrorMessage.INVALID_DATE.get());
    }

    @DisplayName("문자열이 정수를 나타내는 경우 예외를 발생시키지 않음")
    @ValueSource(strings = { "13", "0", "-1", "27", "1" })
    @ParameterizedTest
    void validateDate_NoExceptionIsThrown(String input) {
        assertThatNoException().isThrownBy(() -> validator.validateDate(input));
    }
}