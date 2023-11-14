package christmas.domain.benefit;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Order;
import christmas.domain.VisitDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

class DiscountTest {

    record TestCase(Discount discount, int expected) {
    }

    static Stream<TestCase> getDDayDiscount() {
        Map<String, Integer> orderCase = Map.of(
                "티본스테이크", 1
        );
        VisitDate visitDateCase1 = new VisitDate(1);
        VisitDate visitDateCase2 = new VisitDate(2);
        VisitDate visitDateCase3 = new VisitDate(25);
        VisitDate visitDateCase4 = new VisitDate(26);
        VisitDate visitDateCase5 = new VisitDate(31);
        return Stream.of(
                new TestCase(Discount.generateInstance(visitDateCase1, new Order(orderCase)), 1000),
                new TestCase(Discount.generateInstance(visitDateCase2, new Order(orderCase)), 1100),
                new TestCase(Discount.generateInstance(visitDateCase3, new Order(orderCase)), 3400),
                new TestCase(Discount.generateInstance(visitDateCase4, new Order(orderCase)), 0),
                new TestCase(Discount.generateInstance(visitDateCase5, new Order(orderCase)), 0)
        );
    }

    @DisplayName("크리스마스 디데이 할인")
    @MethodSource
    @ParameterizedTest
    void getDDayDiscount(TestCase testCase) {
        assertThat(testCase.discount.getDDayDiscount()).isEqualTo(testCase.expected);
    }

    static Stream<TestCase> getDDayDiscount_NoDiscountApplied() {
        Map<String, Integer> orderCase = Map.of(
                "양송이수프", 1
        );
        VisitDate visitDateCase1 = new VisitDate(1);
        VisitDate visitDateCase2 = new VisitDate(2);
        VisitDate visitDateCase3 = new VisitDate(25);
        VisitDate visitDateCase4 = new VisitDate(26);
        VisitDate visitDateCase5 = new VisitDate(31);
        return Stream.of(
                new TestCase(Discount.generateInstance(visitDateCase1, new Order(orderCase)), 0),
                new TestCase(Discount.generateInstance(visitDateCase2, new Order(orderCase)), 0),
                new TestCase(Discount.generateInstance(visitDateCase3, new Order(orderCase)), 0),
                new TestCase(Discount.generateInstance(visitDateCase4, new Order(orderCase)), 0),
                new TestCase(Discount.generateInstance(visitDateCase5, new Order(orderCase)), 0)
        );
    }

    @DisplayName("크리스마스 디데이 할인 - 10,000원 이하라 할인 적용 안 됨")
    @MethodSource
    @ParameterizedTest
    void getDDayDiscount_NoDiscountApplied(TestCase testCase) {
        assertThat(testCase.discount.getDDayDiscount()).isEqualTo(testCase.expected);
    }

    @DisplayName("주말 할인")
    @MethodSource
    @ParameterizedTest
    void getDayOfWeekDiscount_WeekdayDiscount(TestCase testCase) {
        assertThat(testCase.discount().getDayOfWeekDiscount()).isEqualTo(testCase.expected());
    }

    static Stream<TestCase> getDayOfWeekDiscount_WeekdayDiscount() {
        VisitDate visitDate = new VisitDate(3);
        Map<String, Integer> orderCase1 = Map.of(
                "양송이수프", 1,
                "타파스", 1
        );
        Map<String, Integer> orderCase2 = Map.of(
                "티본스테이크", 1,
                "초코케이크", 1
        );
        Map<String, Integer> orderCase3 = Map.of(
                "티본스테이크", 1,
                "초코케이크", 2
        );
        Map<String, Integer> orderCase4 = Map.of(
                "해산물파스타", 2,
                "아이스크림", 1,
                "초코케이크", 1
        );
        Map<String, Integer> orderCase5 = Map.of(
                "바비큐립", 1,
                "초코케이크", 2,
                "아이스크림", 2
        );
        return Stream.of(
                new TestCase(Discount.generateInstance(visitDate, new Order(orderCase1)), 0),
                new TestCase(Discount.generateInstance(visitDate, new Order(orderCase2)), 2023),
                new TestCase(Discount.generateInstance(visitDate, new Order(orderCase3)), 2023),
                new TestCase(Discount.generateInstance(visitDate, new Order(orderCase4)), 4046),
                new TestCase(Discount.generateInstance(visitDate, new Order(orderCase5)), 4046)
        );
    }

    static Stream<TestCase> getDayOfWeekDiscount_WeekendDiscount() {
        VisitDate visitDate = new VisitDate(23);
        Map<String, Integer> orderCase1 = Map.of(
                "양송이수프", 1,
                "타파스", 1
        );
        Map<String, Integer> orderCase2 = Map.of(
                "티본스테이크", 1,
                "초코케이크", 1
        );
        Map<String, Integer> orderCase3 = Map.of(
                "티본스테이크", 2,
                "초코케이크", 2
        );
        Map<String, Integer> orderCase4 = Map.of(
                "해산물파스타", 2,
                "바비큐립", 1,
                "초코케이크", 1
        );
        Map<String, Integer> orderCase5 = Map.of(
                "바비큐립", 1,
                "해산물파스타", 2,
                "아이스크림", 2
        );
        Map<String, Integer> orderCase6 = Map.of(
                "티본스테이크", 3,
                "바비큐립", 2,
                "해산물파스타", 1,
                "제로콜라", 1,
                "레드와인", 2
        );
        Map<String, Integer> orderCase7 = Map.of(
                "티본스테이크", 4,
                "바비큐립", 3,
                "해산물파스타", 2,
                "크리스마스파스타", 1
        );
        return Stream.of(
                new TestCase(Discount.generateInstance(visitDate, new Order(orderCase1)), 0),
                new TestCase(Discount.generateInstance(visitDate, new Order(orderCase2)), 2023),
                new TestCase(Discount.generateInstance(visitDate, new Order(orderCase3)), 2023),
                new TestCase(Discount.generateInstance(visitDate, new Order(orderCase4)), 4046),
                new TestCase(Discount.generateInstance(visitDate, new Order(orderCase5)), 4046),
                new TestCase(Discount.generateInstance(visitDate, new Order(orderCase6)), 6069),
                new TestCase(Discount.generateInstance(visitDate, new Order(orderCase7)), 8092)
        );
    }

    @DisplayName("주말 할인")
    @MethodSource
    @ParameterizedTest
    void getDayOfWeekDiscount_WeekendDiscount(TestCase testCase) {
        assertThat(testCase.discount().getDayOfWeekDiscount()).isEqualTo(testCase.expected());
    }

    static Stream<TestCase> getDayOfWeekDiscount_WeekdayDiscount_NotEnoughOrderAmount() {
        VisitDate visitDate = new VisitDate(3);
        Map<String, Integer> orderCase1 = Map.of(
                "양송이수프", 1
        );
        Map<String, Integer> orderCase2 = Map.of(
                "아이스크림", 1
        );
        return Stream.of(
                new TestCase(Discount.generateInstance(visitDate, new Order(orderCase1)), 0),
                new TestCase(Discount.generateInstance(visitDate, new Order(orderCase2)), 0)
        );
    }

    @DisplayName("평일 할인 - 주문 금액이 10,000원 이하라 할인 적용 X")
    @MethodSource
    @ParameterizedTest
    void getDayOfWeekDiscount_WeekdayDiscount_NotEnoughOrderAmount(TestCase testCase) {
        assertThat(testCase.discount().getDayOfWeekDiscount()).isEqualTo(testCase.expected());
    }

    static Stream<TestCase> getSpecialDiscount_Applied() {
        Map<String, Integer> orderCase = Map.of(
                "티본스테이크", 1
        );
        VisitDate visitDateCase1 = new VisitDate(3);
        VisitDate visitDateCase2 = new VisitDate(10);
        VisitDate visitDateCase3 = new VisitDate(17);
        VisitDate visitDateCase4 = new VisitDate(24);
        VisitDate visitDateCase5 = new VisitDate(25);
        VisitDate visitDateCase6 = new VisitDate(31);
        return Stream.of(
                new TestCase(Discount.generateInstance(visitDateCase1, new Order(orderCase)), 1000),
                new TestCase(Discount.generateInstance(visitDateCase2, new Order(orderCase)), 1000),
                new TestCase(Discount.generateInstance(visitDateCase3, new Order(orderCase)), 1000),
                new TestCase(Discount.generateInstance(visitDateCase4, new Order(orderCase)), 1000),
                new TestCase(Discount.generateInstance(visitDateCase5, new Order(orderCase)), 1000),
                new TestCase(Discount.generateInstance(visitDateCase6, new Order(orderCase)), 1000)
        );
    }

    @DisplayName("특별 할인 - 적용")
    @MethodSource
    @ParameterizedTest
    void getSpecialDiscount_Applied(TestCase testCase) {
        assertThat(testCase.discount().getSpecialDiscount()).isEqualTo(testCase.expected());
    }

    static Stream<TestCase> getSpecialDiscount_NotApplied_OutOfSpecialDay() {
        Map<String, Integer> orderCase = Map.of(
                "티본스테이크", 1
        );
        VisitDate visitDateCase1 = new VisitDate(1);
        VisitDate visitDateCase2 = new VisitDate(2);
        VisitDate visitDateCase3 = new VisitDate(26);
        VisitDate visitDateCase4 = new VisitDate(27);
        VisitDate visitDateCase5 = new VisitDate(29);
        VisitDate visitDateCase6 = new VisitDate(30);
        return Stream.of(
                new TestCase(Discount.generateInstance(visitDateCase1, new Order(orderCase)), 0),
                new TestCase(Discount.generateInstance(visitDateCase2, new Order(orderCase)), 0),
                new TestCase(Discount.generateInstance(visitDateCase3, new Order(orderCase)), 0),
                new TestCase(Discount.generateInstance(visitDateCase4, new Order(orderCase)), 0),
                new TestCase(Discount.generateInstance(visitDateCase5, new Order(orderCase)), 0),
                new TestCase(Discount.generateInstance(visitDateCase6, new Order(orderCase)), 0)
        );
    }

    @DisplayName("특별 할인 - 별이 있는 날이 아니라 적용 안 됨")
    @MethodSource
    @ParameterizedTest
    void getSpecialDiscount_NotApplied_OutOfSpecialDay(TestCase testCase) {
        assertThat(testCase.discount().getSpecialDiscount()).isEqualTo(testCase.expected());
    }

    static Stream<TestCase> getSpecialDiscount_NotEnoughOrderAmount() {
        Map<String, Integer> orderCase = Map.of(
                "양송이수프", 1
        );
        VisitDate visitDateCase1 = new VisitDate(3);
        VisitDate visitDateCase2 = new VisitDate(10);
        VisitDate visitDateCase3 = new VisitDate(17);
        VisitDate visitDateCase4 = new VisitDate(24);
        VisitDate visitDateCase5 = new VisitDate(25);
        VisitDate visitDateCase6 = new VisitDate(31);
        return Stream.of(
                new TestCase(Discount.generateInstance(visitDateCase1, new Order(orderCase)), 0),
                new TestCase(Discount.generateInstance(visitDateCase2, new Order(orderCase)), 0),
                new TestCase(Discount.generateInstance(visitDateCase3, new Order(orderCase)), 0),
                new TestCase(Discount.generateInstance(visitDateCase4, new Order(orderCase)), 0),
                new TestCase(Discount.generateInstance(visitDateCase5, new Order(orderCase)), 0),
                new TestCase(Discount.generateInstance(visitDateCase6, new Order(orderCase)), 0)
        );
    }

    @DisplayName("특별 할인 - 별이 있는 날은 맞췄으나 주문 금액이 10,000원이 안 됨")
    @MethodSource
    @ParameterizedTest
    void getSpecialDiscount_NotEnoughOrderAmount(TestCase testCase) {
        assertThat(testCase.discount().getSpecialDiscount()).isEqualTo(testCase.expected());
    }
}