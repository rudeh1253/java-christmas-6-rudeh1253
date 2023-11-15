package christmas.error;

/**
 * 에러 메시지를 요구사항에 맞게 포매팅하는 클리스.
 */
public class ErrorMessageFormatter {
    private static final String COMMON_PREFIX = "[ERROR] ";

    private ErrorMessageFormatter() {
    }

    /**
     * 주어진 메시지로부터 에러 메시지를 생성해서 반환한다.
     * 에러 메시지는 "[ERROR] (message)"의 형태를 가진다.
     * @param message 표시할 메시지.
     * @return 포매팅된 메시지.
     */
    public static String get(String message) {
        return COMMON_PREFIX + message;
    }
}
