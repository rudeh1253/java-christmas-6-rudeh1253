package christmas.view.config;

public enum OutputConfig {
    PROGRAM_START_MESSAGE("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다."),
    ASK_VISIT_DATE("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)"),
    ASK_MENU_AND_NUMBER("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)"),
    MONTH("월"),
    DAY("일"),
    QUANTITY("개"),
    CURRENCY_UNIT("원"),
    NUMBER_DELIMITER(","),
    RESULT_TITLE("에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"),
    ORDER_MENU("<주문 메뉴>"),
    PRESENT_LIST("<혜택 내역>"),
    CHRISTMAS_D_DAY_DISCOUNT("크리스마스 디데이 할인"),
    WEEKDAY_DISCOUNT("평일 할인"),
    WEEKEND_DISCOUNT("주말 할인"),
    SPECIAL_DISCOUNT("특별 할인"),
    GIFT_EVENT("증정 이벤트"),
    BETWEEN_DISCOUNT_HISTORY_NAME_AND_AMOUNT(": -"),
    TOTAL_DISCOUNT("<총혜택 금액>"),
    EXPECTED_TOTAL_COST("<할인 후 예상 결제 금액>"),
    DECEMBER_EVENT_BADGE("<12월 이벤트 배지>"),
    LINE_BREAKING("\n");

    private String message;

    private OutputConfig(String message) {
        this.message = message;
    }

    public String get() {
        return this.message;
    }
}
