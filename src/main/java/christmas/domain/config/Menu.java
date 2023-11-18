package christmas.domain.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 메뉴를 담고 있는 enum.
 */
public enum Menu {
    BUTTON_MUSHROOM_SOUP("양송이수프", 6000, MenuClassification.APPETIZER),
    TAPAS("타파스", 5500, MenuClassification.APPETIZER),
    CAESAR_SALAD("시저샐러드", 8000, MenuClassification.APPETIZER),
    T_BONE_STEAK("티본스테이크", 55000, MenuClassification.MAIN),
    BARBECUE_RIB("바비큐립", 54000, MenuClassification.MAIN),
    SEAFOOD_PASTA("해산물파스타", 35000, MenuClassification.MAIN),
    CHRISTMAS_PASTA("크리스마스파스타", 25000, MenuClassification.MAIN),
    CHOCOLATE_CAKE("초코케이크", 15000, MenuClassification.DESSERT),
    ICE_CREAM("아이스크림", 5000, MenuClassification.DESSERT),
    COKE_ZERO_SUGAR("제로콜라", 3000, MenuClassification.BEVERAGE),
    RED_WINE("레드와인", 60000, MenuClassification.BEVERAGE),
    CHAMPAGNE("샴페인", 25000, MenuClassification.BEVERAGE);

    private static final Map<String, Menu> NAME_MENU_DICT = new HashMap<>();

    static {
        for (Menu menu : Menu.values()) {
            NAME_MENU_DICT.put(menu.name, menu);
        }
    }

    private final String name;
    private final int price;
    private final MenuClassification classification;

    Menu(String name, int price, MenuClassification menuClassification) {
        this.name = name;
        this.price = price;
        this.classification = menuClassification;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public MenuClassification getClassification() {
        return this.classification;
    }

    /**
     * 주어진 이름을 가진 메뉴가 존재하는지 반환한다.
     * @param name 메뉴 이름.
     * @return name에 해당하는 메뉴가 존재하면 true, 그렇지 않으면 false를 반환한다.
     */
    public static boolean containsGivenName(String name) {
        return NAME_MENU_DICT.containsKey(name);
    }

    /**
     * 메뉴 이름으로부터 Menu 인스턴스를 찾아 반환한다.
     * @param name 메뉴 이름.
     * @return name에 해당하는 Menu 인스턴스.
     */
    public static Menu getMenuByName(String name) {
        return NAME_MENU_DICT.get(name);
    }
}
