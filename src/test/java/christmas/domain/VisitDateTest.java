package christmas.domain;

import static org.assertj.core.api.Assertions.*;

import christmas.domain.config.VisitDateConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @DisplayName("일요일일 경우 특별 할인이 적용됨")
    @ValueSource(ints = { 3, 10, 17, 24, 31 })
    @ParameterizedTest
    void isDayOfSpecialDiscount_CaseOfSunday(int day) {
        assertThat(new VisitDate(day).isDayOfSpecialDiscount()).isTrue();
    }

    @DisplayName("크리스마스일 경우 특별 할인이 적용됨")
    @Test
    void isDayOfSpecialDiscount_CaseOfChristmas() {
        assertThat(new VisitDate(VisitDateConfig.DAY_OF_CHRISTMAS).isDayOfSpecialDiscount()).isTrue();
    }

    @DisplayName("일요일도 아니고 크리스마스도 아닐 경우 특별 할인이 적용되지 않음")
    @ValueSource(ints = { 1, 2, 4, 8, 9, 23, 26, 27, 28, 29, 30 })
    @ParameterizedTest
    void isDayOfSpecialDiscount_normalDays(int day) {
        assertThat(new VisitDate(day).isDayOfSpecialDiscount()).isFalse();
    }

    @DisplayName("토요일, 일요일인 경우 true를 반환")
    @ValueSource(ints = { 2, 3, 23, 24, 30, 31 })
    @ParameterizedTest
    void isWeekend_Weekend(int day) {
        assertThat(new VisitDate(day).isWeekend()).isTrue();
    }

    @DisplayName("평일인 경우 false를 반환")
    @ValueSource(ints = { 4, 5, 6, 7, 8, 25, 26, 27, 28, 29 })
    @ParameterizedTest
    void isWeekend_Weekday(int day) {
        assertThat(new VisitDate(day).isWeekend()).isFalse();
    }
}