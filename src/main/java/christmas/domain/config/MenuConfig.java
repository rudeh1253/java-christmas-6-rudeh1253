package christmas.domain.config;

/**
 * 메뉴에 관련된 상수를 모아 놓은 설정 클래스.
 */
public class MenuConfig {
    public static final String MENU_INPUT_REGEX = "^[ㄱ-ㅎ가-힣]+-[0-9]+$";
    public static final String MENU_DELIMITER = ",";
    public static final String MENU_AND_NUMBER_DELIMITER = "-";
    public static final int MIN_ORDER_QUANTITY_OF_ONE_MENU = 1;
    public static final MenuClassification NOT_ALLOWED_SINGLE_CLASSIFICATION_ORDER = MenuClassification.BEVERAGE;
    public static final int TOTAL_ORDER_QUANTITY_LIMIT = 20;
}
