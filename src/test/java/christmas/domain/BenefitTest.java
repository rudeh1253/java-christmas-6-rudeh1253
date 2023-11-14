package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

class BenefitTest {

    record TestCase(Discount discount, int numOfGiveaway, int expected) {
    }

    static Stream<TestCase> getFullDiscount() {
        VisitDate weekdayAndNotSpecialBeforeChristmas = new VisitDate(5);
        VisitDate weekdayAndSpecialBeforeChristmas = new VisitDate(3);
        VisitDate weekendBeforeChristmas = new VisitDate(1);
        VisitDate weekdayAndNotSpecialAfterChristmas = new VisitDate(26);
        VisitDate weekdayAndSpecialAfterChristmas = new VisitDate(31);
        VisitDate weekendAfterChristmas = new VisitDate(29);

        Order orderWithDessertsOver10000 = new Order(Map.of(
                "양송이수프", 2, "초코케이크", 1
        ));
        Order orderWithDessertBelow10000 = new Order(Map.of(
                "아이스크림", 1
        ));
        Order orderWithMainDishesWithGiveaway = new Order(Map.of(
                "시저샐러드", 1, "티본스테이크", 1
        ));

        TestCase[] testCases = {
                new TestCase(
                        Discount.generateInstance(
                                weekdayAndNotSpecialBeforeChristmas,
                                orderWithDessertBelow10000
                        ), 0, 0
                ),
                new TestCase(
                        Discount.generateInstance(
                                weekdayAndNotSpecialBeforeChristmas,
                                orderWithDessertsOver10000
                        ), 0, 2023 + 1400
                ),
                new TestCase(
                        Discount.generateInstance(
                                weekdayAndNotSpecialBeforeChristmas,
                                orderWithMainDishesWithGiveaway
                        ), 1, 1400 + 25000
                ),
                new TestCase(
                        Discount.generateInstance(
                                weekdayAndSpecialBeforeChristmas,
                                orderWithDessertsOver10000
                        ), 0, 2023 + 1200 + 1000
                ),
                new TestCase(
                        Discount.generateInstance(
                                weekdayAndSpecialBeforeChristmas,
                                orderWithMainDishesWithGiveaway
                        ), 1, 1200 + 25000 + 1000
                ),
                new TestCase(
                        Discount.generateInstance(
                                weekendBeforeChristmas,
                                orderWithDessertsOver10000
                        ), 0, 1000
                ),
                new TestCase(
                        Discount.generateInstance(
                                weekendBeforeChristmas,
                                orderWithMainDishesWithGiveaway
                        ), 1, 1000 + 25000 + 2023
                ),
                new TestCase(
                        Discount.generateInstance(
                                weekdayAndNotSpecialAfterChristmas,
                                orderWithDessertsOver10000
                        ), 0, 2023
                ),
                new TestCase(
                        Discount.generateInstance(
                                weekdayAndNotSpecialAfterChristmas,
                                orderWithMainDishesWithGiveaway
                        ), 1, 25000
                ),
                new TestCase(
                        Discount.generateInstance(
                                weekdayAndSpecialAfterChristmas,
                                orderWithDessertsOver10000
                        ), 0, 2023 + 1000
                ),
                new TestCase(
                        Discount.generateInstance(
                                weekdayAndSpecialAfterChristmas,
                                orderWithMainDishesWithGiveaway
                        ), 1, 25000 + 1000
                ),
                new TestCase(
                        Discount.generateInstance(
                                weekendAfterChristmas,
                                orderWithDessertsOver10000
                        ), 0, 0
                ),
                new TestCase(
                        Discount.generateInstance(
                                weekendAfterChristmas,
                                orderWithMainDishesWithGiveaway
                        ), 1, 25000 + 2023
                )
        };

        return Stream.of(testCases);
    }

    @DisplayName("총 혜택 금액 계산")
    @MethodSource
    @ParameterizedTest
    void getFullDiscount(TestCase testCase) {
        assertThat(new Benefit(testCase.discount(), testCase.numOfGiveaway()).getFullDiscount())
                .isEqualTo(testCase.expected());
    }
}