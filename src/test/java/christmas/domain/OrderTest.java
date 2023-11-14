package christmas.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

class OrderTest {

    static Stream<Map<String, Integer>> construct_OrderWithMenuNotExistInMenuList() {
        return Stream.of(
                Map.of("닝겐", 13),
                Map.of("", 15),
                Map.of("양송이수프", 5, "봉골레파스타", 10),
                Map.of("", 8, "닝겐", 7)
        );
    }

    @DisplayName("MenuList에 나타나지 않은 메뉴를 주문할 경우 예외 발생 - IllegalArgumentException")
    @MethodSource
    @ParameterizedTest
    void construct_OrderWithMenuNotExistInMenuList(Map<String, Integer> orderContent) {
        constructTestWhichExceptionThrown(orderContent);
    }

    static Stream<Map<String, Integer>> construct_OrderWithOnlyMenusExistInMenuList() {
        return Stream.of(
                Map.of("양송이수프", 13),
                Map.of("양송이수프", 12, "샴페인", 6)
        );
    }

    @DisplayName("MenuList에 있는 메뉴만 주문할 경우 예외가 발생하지 않음")
    @MethodSource
    @ParameterizedTest
    void construct_OrderWithOnlyMenusExistInMenuList(Map<String, Integer> orderContent) {
        assertThatNoException().isThrownBy(() -> new Order(orderContent));
    }

    static Stream<Map<String, Integer>> construct_ContainsZeroOrder() {
        return Stream.of(
                Map.of("양송이수프", 0),
                Map.of("양송이수프", 12, "샴페인", 0)
        );
    }

    @DisplayName("주문 개수가 0개인 주문을 포함할 경우 예외 발생 - IllegalArgumentExcpetion")
    @MethodSource
    @ParameterizedTest
    void construct_ContainsZeroOrder(Map<String, Integer> orderContent) {
        constructTestWhichExceptionThrown(orderContent);
    }

    static Stream<Map<String, Integer>> construct_ContainsOnlyBeverages() {
        return Stream.of(
                Map.of("샴페인", 1),
                Map.of("샴페인", 2, "제로콜라", 1),
                Map.of("샴페인", 1, "제로콜라", 2, "레드와인", 3)
        );
    }

    @DisplayName("음료만 주문할 경우 예외 발생 - IllegalArgumentException")
    @MethodSource
    @ParameterizedTest
    void construct_ContainsOnlyBeverages(Map<String, Integer> orderContent) {
        constructTestWhichExceptionThrown(orderContent);
    }

    static Stream<Map<String, Integer>> construct_TotalExceeds20() {
        return Stream.of(
                Map.of("양송이수프", 21),
                Map.of("양송이수프", 10, "샴페인", 20),
                Map.of("시저샐러드", 22, "해산물파스타", 22)
        );
    }

    @DisplayName("총 주문 개수가 20개를 초과할 경우 예외 발생 - IllegalArgumentException")
    @MethodSource
    @ParameterizedTest
    void construct_TotalExceeds20(Map<String, Integer> orderContent) {
        constructTestWhichExceptionThrown(orderContent);
    }

    private void constructTestWhichExceptionThrown(Map<String, Integer> orderContent) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Order(orderContent));
    }

    record TestCaseForOrder(Map<String, Integer> testCase, int expected) {
    }

    static Stream<TestCaseForOrder> getTotalOrderQuantity() {
        return Stream.of(
                new TestCaseForOrder(Map.of("양송이수프", 1, "타파스", 2, "바비큐립", 3), 6),
                new TestCaseForOrder(Map.of("양송이수프", 5, "타파스", 7, "샴페인", 2), 14)
        );
    }

    @DisplayName("주문 개수의 총합을 구한다.")
    @MethodSource
    @ParameterizedTest
    void getTotalOrderQuantity(TestCaseForOrder testCase) {
        assertThat(new Order(testCase.testCase()).getTotalOrderQuantity()).isEqualTo(testCase.expected());
    }

    static Stream<TestCaseForOrder> calculateOrderAmount() {
        return Stream.of(
                new TestCaseForOrder(Map.of("양송이수프", 1, "타파스", 2, "바비큐립", 3), 179000),
                new TestCaseForOrder(Map.of("시저샐러드", 2, "해산물파스타", 5, "레드와인", 2), 311000)
        );
    }

    @DisplayName("할인 전 주문 금액을 계산한다.")
    @MethodSource
    @ParameterizedTest
    void calculateOrderAmount(TestCaseForOrder testCase) {
        assertThat(new Order(testCase.testCase()).calculateOrderAmount()).isEqualTo(testCase.expected());
    }
}