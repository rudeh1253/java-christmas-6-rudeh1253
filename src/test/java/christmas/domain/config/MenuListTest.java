package christmas.domain.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;
import java.util.stream.Stream;

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

    @DisplayName("주어진 메뉴 이름이 MenuList의 그 어떤 enum value의 name value로 나타나지 않을 경우 null을 반환한다.")
    @ValueSource(strings = { "양송이", "수프", "", "콜라" })
    @ParameterizedTest
    void getMenuByName_NotExistMenuName(String menuName) {
        assertThat(MenuList.getMenuByName(menuName)).isNull();
    }

    record SingleTestCase(String menuName, MenuList menu) {
    }

    static Stream<SingleTestCase> getMenuByName_ExistMenuName() {
        return Stream.of(
                new SingleTestCase("양송이수프", MenuList.BUTTON_MUSHROOM_SOUP),
                new SingleTestCase("타파스", MenuList.TAPAS),
                new SingleTestCase("시저샐러드", MenuList.CAESAR_SALAD),
                new SingleTestCase("티본스테이크", MenuList.T_BONE_STEAK),
                new SingleTestCase("바비큐립", MenuList.BARBECUE_RIB),
                new SingleTestCase("해산물파스타", MenuList.SEAFOOD_PASTA),
                new SingleTestCase("크리스마스파스타", MenuList.CHRISTMAS_PASTA),
                new SingleTestCase("초코케이크", MenuList.CHOCOLATE_CAKE),
                new SingleTestCase("아이스크림", MenuList.ICE_CREAM),
                new SingleTestCase("제로콜라", MenuList.COKE_ZERO_SUGAR),
                new SingleTestCase("레드와인", MenuList.RED_WINE),
                new SingleTestCase("샴페인", MenuList.CHAMPAGNE)
        );
    }

    @DisplayName("주어진 메뉴 이름이 MenuList에 존재할 경우 해당 MenuList 인스턴스를 반환한다.")
    @MethodSource
    @ParameterizedTest
    void getMenuByName_ExistMenuName(SingleTestCase testCase) {
        assertThat(MenuList.getMenuByName(testCase.menuName)).isEqualByComparingTo(testCase.menu);
    }
}