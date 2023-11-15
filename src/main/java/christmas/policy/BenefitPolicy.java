package christmas.policy;


import christmas.constant.EventDateInfo;
import christmas.constant.EventType;
import christmas.constant.MenuGroup;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.List;

public class BenefitPolicy {

    public EnumMap<EventType, BigDecimal> getMyBenefits(
            List<EventType> eventTypes,
            LocalDate visitDate,
            EnumMap<MenuGroup, Integer> menuGroups
    ) {
        EnumMap<EventType, BigDecimal> result = new EnumMap<>(EventType.class);

        for (EventType eventType : eventTypes) {
            getCristmasBenefit(visitDate, result, eventType);
            getWeekBenefit(menuGroups, result, eventType);
            getSpecialBenefit(eventType, result);
            getGiftBenefit(eventType, result);
        }

        return result;
    }


    private void getSpecialBenefit(EventType eventType, EnumMap<EventType, BigDecimal> result) {
        if (eventType.equals(EventType.SPECIAL_SALE)) {
            result.put(eventType, eventType.getBenefitAmount());
        }
    }

    private void getGiftBenefit(EventType eventType, EnumMap<EventType, BigDecimal> result) {
        if (eventType.equals(EventType.GIFT_MENU)) {
            result.put(eventType, eventType.getBenefitPrice());

        }
    }

    private void getWeekBenefit(EnumMap<MenuGroup, Integer> menuGroups, EnumMap<EventType, BigDecimal> result,
                                EventType eventType) {
        if (eventType.equals(EventType.WEEKDAY_SALE) || eventType.equals(EventType.WEEKEND_SALE)) {
            MenuGroup menuGroup = eventType.getMenuGroup();
            Integer menuGroupCount = menuGroups.get(menuGroup);
            result.put(eventType, eventType.getBenefitAmount(menuGroupCount));
        }
    }

    private void getCristmasBenefit(LocalDate visitDate, EnumMap<EventType, BigDecimal> result,
                                    EventType eventType) {
        if (eventType.equals(EventType.CHRISTMAS_SALE)) {
            LocalDate eventStartDate = LocalDate.parse(EventDateInfo.EVENT_START_DATE, DateTimeFormatter.ISO_DATE);
            Period period = Period.between(eventStartDate, visitDate);
            result.put(eventType, eventType.getBenefitAmount(period.getDays()));
        }
    }


}
