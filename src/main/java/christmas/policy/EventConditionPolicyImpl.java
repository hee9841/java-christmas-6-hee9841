package christmas.policy;


import christmas.annotaion.condition.DayOfWeeks;
import christmas.annotaion.condition.MenuGroupCondition;
import christmas.annotaion.condition.MinOrderPrice;
import christmas.annotaion.condition.Period;
import christmas.annotaion.condition.SpecialDays;
import christmas.constant.EventType;
import christmas.constant.Menu;
import christmas.constant.MenuGroup;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

public class EventConditionPolicyImpl implements EventConditionPolicy {

    @Override
    public List<EventType> findEvent(LocalDate visitDate, EnumMap<Menu, Integer> orders, BigDecimal totalAmount) {

        List<String> eventTypeNames = new ArrayList<>();
        Field[] fields = (EventType.class).getFields();
        for (Field eventTypeField : fields) {
            if (satisfyPeriod(eventTypeField, visitDate) && satisfyDayOfWeek(eventTypeField, visitDate)
                    && satisfySpecialDays(eventTypeField, visitDate) && satisfyAboutOrders(eventTypeField, orders)
                    && satisfyMinOrderPrice(eventTypeField, totalAmount)) {
                eventTypeNames.add(eventTypeField.getName());
            }
        }

        return eventTypeNames.stream()
                .map(EventType::valueOf)
                .toList();
    }


    private boolean satisfyAboutOrders(Field field, EnumMap<Menu, Integer> orders) {
        if (field.isAnnotationPresent(MenuGroupCondition.class)) {
            List<MenuGroup> orderMenuGroup = orders.keySet().stream()
                    .map(menu -> MenuGroup.findByMenuCode(menu.name()))
                    .distinct()
                    .toList();
            MenuGroup menuGroup = field.getAnnotation(MenuGroupCondition.class).menuGroup();

            return orderMenuGroup.contains(menuGroup);
        }
        return true;
    }

    private boolean satisfyPeriod(Field field, LocalDate visitDate) {
        if (field.isAnnotationPresent(Period.class)) {
            Period condition = field.getAnnotation(Period.class);
            LocalDate startDate = LocalDate.parse(condition.startDate(), DateTimeFormatter.ISO_DATE);
            LocalDate endDate = LocalDate.parse(condition.endDate(), DateTimeFormatter.ISO_DATE);

            return !visitDate.isBefore(startDate) && !visitDate.isAfter(endDate);
        }
        return true;
    }

    private boolean satisfyDayOfWeek(Field field, LocalDate visitDate) {
        if (field.isAnnotationPresent(DayOfWeeks.class)) {
            DayOfWeeks condition = field.getAnnotation(DayOfWeeks.class);
            DayOfWeek dayOfWeekOfVisitDate = visitDate.getDayOfWeek();

            List<DayOfWeek> conditions = Arrays.stream(condition.dayOfWeeks()).toList();

            return conditions.contains(dayOfWeekOfVisitDate);
        }
        return true;
    }

    private boolean satisfySpecialDays(Field field, LocalDate visitDate) {
        if (field.isAnnotationPresent(SpecialDays.class)) {
            int visitDay = visitDate.getDayOfMonth();
            int[] conditionDays = field.getAnnotation(SpecialDays.class).days();

            return Arrays.stream(conditionDays).anyMatch(condition -> condition == visitDay);
        }
        LocalDate of = LocalDate.of(2023, 12, 1);

        return true;
    }

    private boolean satisfyMinOrderPrice(Field field, BigDecimal totalAmount) {
        if (field.isAnnotationPresent(MinOrderPrice.class)) {
            long price = field.getAnnotation(MinOrderPrice.class).price();
            BigDecimal condition = BigDecimal.valueOf(price);

            return totalAmount.compareTo(condition) > 0;
        }
        return true;
    }

}
