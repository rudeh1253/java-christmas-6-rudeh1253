package christmas.domain.config;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.Set;

public class VisitDateConfig {
    public static final int DATE_MIN = 1;
    public static final int DATE_MAX = 31;
    public static final int YEAR = 2023;
    public static final Month MONTH = Month.DECEMBER;
    public static final int BASIC_D_DAY_DISCOUNT = 1000;
    public static final int D_DAY_DISCOUNT_PER_DAY = 100;
    public static final int DAY_OF_CHRISTMAS = 25;
    public static final Set<DayOfWeek> WEEKENDS = Set.of(
            DayOfWeek.FRIDAY, DayOfWeek.SATURDAY
    );
}
