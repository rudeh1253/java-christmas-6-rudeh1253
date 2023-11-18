package christmas.validation;

import christmas.domain.config.MenuConfig;
import christmas.error.ErrorMessage;
import christmas.error.ErrorMessageFormatter;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * 사용자의 입력을 검증하기 위한 Validator.
 * 사용자의 입력은 문자열로 이루어지기 때문에 문자열을 검증한다.
 */
public class InputValidator {
    private static final InputValidator SINGLETON = new InputValidator();

    private InputValidator() {
    }

    /**
     * InputValidator 객체를 반환한다. 싱글턴.
     * @return 싱글턴 객체를 반환한다.
     */
    public static InputValidator getInstance() {
        return SINGLETON;
    }

    /**
     * 입력된 날짜를 검증한다. 입력이 정수가 아닐 경우 IllegalArgumentException이 발생한다.
     * @param date 검증할 날짜 입력.
     */
    public void validateDate(String date) {
        if (!isInteger(date)) {
            throw new IllegalArgumentException(ErrorMessageFormatter.get(ErrorMessage.INVALID_DATE.get()));
        }
    }

    private boolean isInteger(String expectedAsInteger) {
        try {
            Integer.parseInt(expectedAsInteger);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 사용자가 입력한 개별 메뉴가 MenuConfig.MENU_INPUT_REGEX에 정의된 정규표현식에 맞는 형식인지 검증한다.
     * 그렇지 않을 경우 IllegalArgumentException이 발생한다.
     * @param eachMenu 검증할 사용자 메뉴 입력.
     */
    public void validateEachMenu(String eachMenu) {
        if (!Pattern.matches(MenuConfig.MENU_INPUT_REGEX, eachMenu)) {
            throw new IllegalArgumentException(ErrorMessageFormatter.get(ErrorMessage.INVALID_MENU_INPUT.get()));
        }
    }

    /**
     * 입력된 주문 내역에서 중복된 메뉴가 있는지 검증한다. 중복된 메뉴가 있을 경우 IllegalArgumentException이 발생한다.
     * container가 toCheck를 포함하고 있을 경우 예외가 발생한다.
     * @param container 주문한 메뉴가 담긴 Set 객체.
     * @param toCheck container가 포함하고 있는지 검증할 주문 이름.
     */
    public void validateDuplicateOfMenuInput(Set<String> container, String toCheck) {
        if (container.contains(toCheck)) {
            throw new IllegalArgumentException(ErrorMessageFormatter.get(ErrorMessage.INVALID_MENU_INPUT.get()));
        }
    }
}
