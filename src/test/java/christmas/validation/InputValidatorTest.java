package christmas.validation;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import christmas.error.ErrorMessage;
import christmas.error.ErrorMessageFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

class InputValidatorTest {
    InputValidator validator = InputValidator.getInstance();

    @DisplayName("문자열이 정수를 나타내지 않는 경우 예외 처리 - IllegalArgumentException")
    @ValueSource(strings = { "13a", "b96", "3434 ", " 5656", "1a56", "aab", "!@", " ", "", "3.14" })
    @ParameterizedTest
    void validateDate_ThrowAnIllegalArgumentException_IfTheInputDoesNotRepresentANumber
            (String input) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validator.validateDate(input))
                .withMessage(ErrorMessageFormatter.get(ErrorMessage.INVALID_DATE.get()));
    }

    @DisplayName("문자열이 정수를 나타내지 않는 경우 예외 처리 - IllegalArgumentException")
    @Test
    void validateDate_ThrowAnIllegalArgumentException_IfNullIsPassed() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validator.validateDate(null))
                .withMessage(ErrorMessageFormatter.get(ErrorMessage.INVALID_DATE.get()));
    }

    @DisplayName("문자열이 정수를 나타내는 경우 예외를 발생시키지 않음")
    @ValueSource(strings = { "13", "0", "-1", "27", "1" })
    @ParameterizedTest
    void validateDate_NoExceptionIsThrown(String input) {
        assertThatNoException().isThrownBy(() -> validator.validateDate(input));
    }

    static Stream<List<String>> validateDuplicateOfMenuInput_ThrowIllegalArgumentException_ContainsDuplicate() {
        return Stream.of(
                List.of("양송이수프", "양송이수프"),
                List.of("티본스테이크", "티본스테이크", "양송이수프")
        );
    }

    @DisplayName("중복된 메뉴 입력 시 예외 발생 - IllegalArgumentException")
    @MethodSource
    @ParameterizedTest
    void validateDuplicateOfMenuInput_ThrowIllegalArgumentException_ContainsDuplicate(List<String> menus) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validator.validateDuplicateOfMenuInput(menus.toArray(new String[menus.size()])))
                .withMessage(ErrorMessageFormatter.get(ErrorMessage.INVALID_MENU_INPUT.get()));
    }

    static Stream<List<String>> validateDuplicateOfMenuInput_NoExceptionIsThrown_NoDuplicateIsProvided() {
        return Stream.of(
                List.of("양송이수프"),
                List.of("티본스테이크", "양송이수프"),
                List.of(""),
                List.of("양송이수프", "타파스", "시저샐러드", "티본스테이크", "바비큐립", "해산물파스타",
                        "크리스마스파스타", "초코케이크", "아이스크림", "제로콜라", "레드와인", "샴페인")
        );
    }

    @DisplayName("중복된 메뉴가 없을 경우 예외를 발생시키지 않음")
    @MethodSource
    @ParameterizedTest
    void validateDuplicateOfMenuInput_NoExceptionIsThrown_NoDuplicateIsProvided(List<String> menus) {
        assertThatNoException()
                .isThrownBy(() -> validator.validateDuplicateOfMenuInput(menus.toArray(new String[menus.size()])));
    }

    @DisplayName("메뉴가 정규표현식 /^[ㄱ-ㅎ가-힣]+-[0-9]+$/를 따르지 않을 경우 예외 발생 - IllegalArgumentException")
    @ValueSource(
            strings = { "a-123", "-123", "시저샐러드-", "포테이토-!@", "포테이토 - 135", "포테이토 -135",
            "포테이토- 135", "티몬-스테이크-2" }
    )
    @ParameterizedTest
    void validateEachMenu_ThrowsIllegalArgumentException_NotMatchRegex(String eachMenuInput) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validator.validateEachMenu(eachMenuInput))
                .withMessage(ErrorMessageFormatter.get(ErrorMessage.INVALID_MENU_INPUT.get()));
    }

    @DisplayName("메뉴가 정규표현식 /^[ㄱ-ㅎ가-힣]+-[0-9]+$/를 따를 경우 예외가 발생하지 않음")
    @ValueSource(
            strings = { "파밍-1", "시저샐러드-13", "멍-55" }
    )
    @ParameterizedTest
    void validateEachMenu_followTheRegex(String eachMenu) {
        assertThatNoException().isThrownBy(() -> validator.validateEachMenu(eachMenu));
    }
}