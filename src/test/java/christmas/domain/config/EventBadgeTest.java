package christmas.domain.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class EventBadgeTest {

    record TestCase(int benefitAmount, EventBadge expected) {
    }

    static Stream<TestCase> getBadgeByOrderAmount() {
        return Stream.of(
                new TestCase(0, EventBadge.NONE),
                new TestCase(1, EventBadge.NONE),
                new TestCase(4999, EventBadge.NONE),
                new TestCase(5000, EventBadge.STAR),
                new TestCase(9999, EventBadge.STAR),
                new TestCase(10000, EventBadge.TREE),
                new TestCase(19999, EventBadge.TREE),
                new TestCase(20000, EventBadge.SANTA),
                new TestCase(Integer.MAX_VALUE, EventBadge.SANTA)
        );
    }

    @DisplayName("혜택 금액에 따른 배지")
    @MethodSource
    @ParameterizedTest
    void getBadgeByOrderAmount(TestCase testCase) {
        assertThat(EventBadge.getBadgeByOrderAmount(testCase.benefitAmount()))
                .isEqualTo(testCase.expected());
    }
}