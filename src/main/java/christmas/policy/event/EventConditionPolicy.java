package christmas.policy.event;


import christmas.constant.EventType;
import christmas.constant.Menu;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;

public interface EventConditionPolicy {
    List<EventType> findEvent(LocalDate visitDate, EnumMap<Menu, Integer> orders, BigDecimal totalAmount);

}
