package test.ann;


import java.util.Arrays;
import java.util.List;

public enum EventGroup {
    TOTAL_PAYMENT_DISCOUNT(
            "총금액 할인",
            Arrays.asList(
                    "CHRISTMAS_SALE", "SPECIAL_SALE"
            ),
            true,
            false
    ),
    MENU_PRICE_DISCOUNT(
            "메뉴 할인",
            Arrays.asList(
                    "WEEKDAY_SALE", "WEEKEND_SALE"
            ),
            true,
            false

    ),
    MENU_GIVEAWAY(
            "메뉴 증정",
            Arrays.asList(
                    "CHOCOLATE_CAKE", "ICE_CRAM"
            ),
            true,
            true
    ),
    SPECIAL_GIVEAWAY(
            "특별 증정",
            Arrays.asList(
                    "ZERO_COKE", "RED_WINE", "CHAMPAGNE"
            ),
            false,
            true
    );

    //혜택 관련 된 여부에 대한 인자 값 추가
    private final String name;
    private final List<String> group;

    private final boolean hasBenefitPrice;

    private final boolean hasGiveaway;

    EventGroup(String name, List<String> group, boolean hasBenefitPrice, boolean hasGiveaway) {
        this.name = name;
        this.group = group;
        this.hasBenefitPrice = hasBenefitPrice;
        this.hasGiveaway = hasGiveaway;
    }


    public static EventGroup findByEventTypeCode(String code) {
        return Arrays.stream(EventGroup.values())
                .filter(eventGroup -> eventGroup.hashMenuCode(code))
                .findAny()
                .orElseThrow(() ->
                    new NullPointerException("[ERROR] code가 해당하는 EventGroup에 없습니다.")
                );
    }

    public boolean hashMenuCode(String code) {
        return group.stream()
                .anyMatch(event -> event.equals(code));
    }

    @Override
    public String toString() {
        return "Menu{" +
                "name='" + name + '\'' +
                '}';
    }
}
