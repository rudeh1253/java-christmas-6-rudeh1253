package christmas.util;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.view.config.OutputConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StringFormatterTest {
    StringFormatter formatter = StringFormatter.getInstance();

    @DisplayName("e.g. 3을 인자로 전달할 경우 \"12월 3일\"을 반환")
    @CsvSource({ "1,12월 1일", "3,12월 3일", "10,12월 10일", "25,12월 25일", "31,12월 31일" })
    @ParameterizedTest
    void formatVisitDate(int day, String expected) {
        assertThat(formatter.formatVisitDate(day)).isEqualTo(expected);
    }

    @DisplayName("\"<주문 메뉴> <개수>개\" 형태의 문자열 반환")
    @CsvSource({ "티본스테이크,1,티본스테이크 1개", "바비큐립,1,바비큐립 1개", "초코케이크,2,초코케이크 2개" })
    @ParameterizedTest
    void formatOrderMenu(String menuName, int quantity, String expected) {
        assertThat(formatter.formatOrderMenu(menuName, quantity)).isEqualTo(expected);
    }

    @DisplayName("천의 자리 수씩 올라갈 때마다 콤마(,)로 구분 e.g. 1,000,000원")
    @CsvSource(value = { "100:100원", "1000:1,000원", "33333:33,333원", "50000000:50,000,000원" }, delimiter = ':')
    @ParameterizedTest
    void formatMoney(int money, String expected) {
        assertThat(formatter.formatMoney(money)).isEqualTo(expected);
    }

    @DisplayName("\"<혜택 이름>: -<혜택 금액>원\"")
    @CsvSource(
            value = {
                    "크리스마스 디데이 할인;1200;크리스마스 디데이 할인: -1,200원",
                    "평일 할인;4046;평일 할인: -4,046원",
                    "특별 할인;1000;특별 할인: -1,000원",
                    "증정 이벤트;25000;증정 이벤트: -25,000원"
            }, delimiter = ';'
    )
    @ParameterizedTest
    void formatSingleBenefitHistory(String benefit, int amount, String expected) {
        assertThat(formatter.formatSingleBenefitHistory(benefit, amount))
                .isEqualTo(expected + OutputConfig.LINE_BREAKING.get());
    }
}