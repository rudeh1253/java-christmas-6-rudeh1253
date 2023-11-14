package christmas.validation;

import static org.assertj.core.api.Assertions.*;

import christmas.domain.config.MenuClassification;
import christmas.error.ErrorMessage;
import christmas.error.ErrorMessageFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class OrderValidatorTest {
    OrderValidator validator = OrderValidator.getInstance();

    @DisplayName("Menu enum에 정의되지 않은 메뉴를 주문하려 할 경우 예외 발생 - IllegalArgumentException")
    @ValueSource(strings = { "", "커피", "우유", "135" })
    @ParameterizedTest
    void validateValidityOfMenus_ThrowsIllegalArgumentException_OrderNotInMenuEnum(String menu) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validator.validateValidityOfMenus(menu))
                .withMessage(ErrorMessageFormatter.get(ErrorMessage.INVALID_MENU_INPUT.get()));
    }

    @DisplayName("Menu enum에 정의된 메뉴를 주문하려 할 경우 예외가 발생하지 않음")
    @ValueSource(strings = {
            "양송이수프", "타파스", "시저샐러드", "티본스테이크", "바비큐립", "해산물파스타", "크리스마스파스타",
            "초코케이크", "아이스크림", "제로콜라", "레드와인", "샴페인"
    })
    @ParameterizedTest
    void validateValidityOfMenus_NoExceptionIsThrown(String menu) {
        assertThatNoException().isThrownBy(() -> validator.validateValidityOfMenus(menu));
    }

    @DisplayName("주문 개수가 1 미만일 경우 예외 발생 - IllegalArgumentExcpetion")
    @ValueSource(ints = { 0, -1, Integer.MIN_VALUE })
    @ParameterizedTest
    void validateOrderQuantity_ThrowsIllegalArgumentException_LessThanOne(int quantity) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validator.validateOrderQuantity(quantity))
                .withMessage(ErrorMessageFormatter.get(ErrorMessage.INVALID_MENU_INPUT.get()));
    }

    @DisplayName("주문 개수가 1 이상일 경우 예외가 발생하지 않음")
    @ValueSource(ints = { 1, 2, 13, Integer.MAX_VALUE })
    @ParameterizedTest
    void validateOrderQuantity_NoExceptionIsThrown_EqualOrMoreThanOne(int quantity) {
        assertThatNoException().isThrownBy(() -> validator.validateOrderQuantity(quantity));
    }

    static Stream<Map<String, Integer>> validateTotalQuantity_Over20InTotal() {
        return Stream.of(
                Map.of("양송이수프", 21),
                Map.of("샴페인", 3, "레드와인", 7, "시저샐러드", 11)
        );
    }

    static Stream<List<MenuClassification>> validateNotWithOnlyNotAllowedMenu_OnlyBeverages() {
        return Stream.of(
                List.of(MenuClassification.BEVERAGE),
                List.of(MenuClassification.BEVERAGE, MenuClassification.BEVERAGE)
        );
    }

    @DisplayName("음료만 주문할 경우 예외 발생 - IllegalArgumentException")
    @MethodSource
    @ParameterizedTest
    void validateNotWithOnlyNotAllowedMenu_OnlyBeverages(List<MenuClassification> classifications) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validator.validateNotWithOnlyNotAllowedMenu(classifications));
    }

    static Stream<List<MenuClassification>> validateNotWithOnlyNotAllowedMenu_NotOnlyBeverages() {
        return Stream.of(
                List.of(MenuClassification.BEVERAGE, MenuClassification.BEVERAGE, MenuClassification.MAIN),
                List.of(MenuClassification.BEVERAGE, MenuClassification.APPETIZER),
                List.of(MenuClassification.DESSERT)
        );
    }

    @DisplayName("음료만 주문하지 않을 경우 예외가 발생하지 않음")
    @MethodSource
    @ParameterizedTest
    void validateNotWithOnlyNotAllowedMenu_NotOnlyBeverages(List<MenuClassification> classifications) {
        assertThatNoException().isThrownBy(() -> validator.validateNotWithOnlyNotAllowedMenu(classifications));
    }

    @DisplayName("총 주문 개수가 20 초과일 경우 예외 발생 - IllegalArgumentException")
    @MethodSource
    @ParameterizedTest
    void validateTotalQuantity_Over20InTotal(Map<String, Integer> orderContent) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validator.validateTotalQuantity(orderContent));
    }

    static Stream<Map<String, Integer>> validateTotalQuantity_LessOrEqualTo20() {
        return Stream.of(
                Map.of("시저샐러드", 1),
                Map.of("시저샐러드", 10, "양송이수프", 10),
                Map.of("해산물파스타", 20)
        );
    }

    @DisplayName("총 주문 개수가 20 이하일 경우 예외가 발생하지 않음")
    @MethodSource
    @ParameterizedTest
    void validateTotalQuantity_LessOrEqualTo20(Map<String, Integer> orderContent) {
        assertThatNoException().isThrownBy(() -> validator.validateTotalQuantity(orderContent));
    }
}