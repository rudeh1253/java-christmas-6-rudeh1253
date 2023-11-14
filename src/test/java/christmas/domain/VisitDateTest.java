package christmas.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class VisitDateTest {

    @DisplayName("1~31에 해당되지 않는 날로 Date 인스턴스를 생성할 경우 예외 발생 - IllegalArgumentException")
    @ValueSource(ints = { 0, -1, 32, Integer.MAX_VALUE, Integer.MIN_VALUE })
    @ParameterizedTest
    void construct_OutOfRange(int day) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new VisitDate(day));
    }

    @DisplayName("1~31에 해당되는 날로 Date 인스턴스를 생성할 경우 예외가 발생하지 않음")
    @ValueSource(ints = { 1, 2, 30, 31 })
    @ParameterizedTest
    void construct_WithinRange(int day) {
        assertThatNoException().isThrownBy(() -> new VisitDate(day));
    }

    @DisplayName("날짜에 따른 할인 금액 계산")
    @CsvSource({ "1,1000", "2,1100", "25,3400", "26,0", "31,0" })
    @ParameterizedTest
    void calculateDiscountByDate(int day, int expected) {
        assertThat(new VisitDate(day).calculateDiscountByDate()).isEqualTo(expected);
    }
}