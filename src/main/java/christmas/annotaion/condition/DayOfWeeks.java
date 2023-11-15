package christmas.annotaion.condition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.DayOfWeek;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DayOfWeeks {
    DayOfWeek[] dayOfWeeks();
}
