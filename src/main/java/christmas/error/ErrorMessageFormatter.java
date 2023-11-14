package christmas.error;

public class ErrorMessageFormatter {
    private static final String COMMON_PREFIX = "[ERROR] ";

    private ErrorMessageFormatter() {
    }

    public static String get(String message) {
        return COMMON_PREFIX + message;
    }
}
