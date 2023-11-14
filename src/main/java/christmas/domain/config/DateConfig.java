package christmas.domain.config;

public enum DateConfig {
    DATE_MIN(1),
    DATE_MAX(31);

    private final int date;

    DateConfig(int date) {
        this.date = date;
    }

    public int get() {
        return this.date;
    }
}
