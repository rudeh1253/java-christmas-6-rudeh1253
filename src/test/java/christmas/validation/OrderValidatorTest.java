package christmas.validation;

import static org.assertj.core.api.Assertions.*;

import christmas.error.ErrorMessage;
import christmas.error.ErrorMessageFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
}