package christmas.domain.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuListTest {

    @DisplayName("주어진 메뉴 이름이 MenuList의 그 어떤 enum value의 name value로 나타나지 않을 경우 false를 반환한다.")
    @ValueSource(strings = { "양송이", "수프", "", "콜라" })
    @ParameterizedTest
    void containsGivenName_InvalidMenuNames(String menuName) {
        assertThat(MenuList.containsGivenName(menuName)).isFalse();
    }

    @DisplayName("주어진 메뉴 이름이 MenuList의 하나 이상의 enum이 name value로 포함할 경우 true를 반환한다.")
    @ValueSource(
            strings = {
                    "양송이수프", "타파스", "시저샐러드", "티본스테이크", "바비큐립", "해산물파스타", "크리스마스파스타",
                    "초코케이크", "아이스크림", "제로콜라", "레드와인", "샴페인"
            }
    )
    @ParameterizedTest
    void containsGivenName_ValidMenuNames(String menuName) {
        assertThat(MenuList.containsGivenName(menuName)).isTrue();
    }
}