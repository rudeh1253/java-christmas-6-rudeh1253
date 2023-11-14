package christmas.domain.config;

public class MenuConfig {
    public static final String MENU_INPUT_REGEX = "^[ㄱ-ㅎ가-힣]+-[0-9]+$";
    public static final String MENU_DELIMITER = ",";
    public static final String MENU_AND_NUMBER_DELIMITER = "-";
    public static final int MIN_ORDER_QUANTITY_OF_ONE_MENU = 1;
}
