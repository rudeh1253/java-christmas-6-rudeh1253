package christmas.error;

/**
 * 에러 메시지를 담고 있는 enum.
 */
public enum ErrorMessage {
    INVALID_DATE("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    INVALID_MENU_INPUT("유효하지 않은 주문입니다. 다시 입력해 주세요.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String get() {
        return this.message;
    }
}
