package christmas.constant;


import christmas.annotaion.condition.DayOfWeeks;
import christmas.annotaion.condition.MenuGroupCondition;
import christmas.annotaion.condition.MinOrderPrice;
import christmas.annotaion.condition.Period;
import christmas.annotaion.condition.SpecialDays;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.function.BiFunction;

public enum EventType {

    @Period(startDate = EventDateInfo.EVENT_START_DATE, endDate = "2023-12-25")
    CHRISTMAS_SALE(
            "크리스마스 디데이 할인",
            BigDecimal.valueOf(1000),
            (times, discountPrice) -> discountPrice.add(times.multiply(BigDecimal.valueOf(100)))
    ),


    @Period(startDate = EventDateInfo.EVENT_START_DATE, endDate = EventDateInfo.EVENT_END_DATE)
    @DayOfWeeks(dayOfWeeks = {DayOfWeek.SUNDAY, DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY
    })
    @MenuGroupCondition(menuGroup = MenuGroup.DESSERT)
    WEEKDAY_SALE(
            "평일 할인",
            MenuGroup.DESSERT,
            BigDecimal.valueOf(2023),
            (times, discountPrice)
                    -> discountPrice.multiply(times.divideAndRemainder(BigDecimal.valueOf(1))[0])
    ),


    @Period(startDate = EventDateInfo.EVENT_START_DATE, endDate = EventDateInfo.EVENT_END_DATE)
    @DayOfWeeks(dayOfWeeks = {DayOfWeek.FRIDAY, DayOfWeek.SATURDAY})
    @MenuGroupCondition(
            menuGroup = MenuGroup.MAIN_DISH
    )
    WEEKEND_SALE(
            "주말 할인",
            MenuGroup.DESSERT,
            BigDecimal.valueOf(2023),
            (times, discountPrice)
                    -> discountPrice.multiply(times.divideAndRemainder(BigDecimal.valueOf(1))[0])
    ),


    @Period(startDate = EventDateInfo.EVENT_START_DATE, endDate = EventDateInfo.EVENT_END_DATE)
    @SpecialDays(days = {3, 10, 17, 24, 31, 25})
    SPECIAL_SALE(
            "특별 할인",
            BigDecimal.valueOf(1000),
            (times, discountPrice) -> discountPrice
    ),


    @Period(startDate = EventDateInfo.EVENT_START_DATE, endDate = EventDateInfo.EVENT_END_DATE)
    @MinOrderPrice(price = 120000)
    GIFT_MENU(
            "증정 이벤트",
            Menu.CHAMPAGNE.toString(),
            Menu.CHAMPAGNE.getPrice()
    );


    private final String eventName;


    private String giftName;
    private BigDecimal benefitPrice;

    private MenuGroup menuGroup;

    private BigDecimal defaultDiscount;

    private BiFunction<BigDecimal, BigDecimal, BigDecimal> discountAmountExpression;


    EventType(String eventName, String giftName, BigDecimal benefitPrice) {
        this.eventName = eventName;
        this.giftName = giftName;
        this.benefitPrice = benefitPrice;

    }

    EventType(
            String eventName,
            BigDecimal defaultDiscount,
            BiFunction<BigDecimal, BigDecimal, BigDecimal> discountAmountExpression

    ) {
        this.eventName = eventName;
        this.defaultDiscount = defaultDiscount;
        this.discountAmountExpression = discountAmountExpression;
    }

    EventType(
            String eventName,
            MenuGroup menuGroup,
            BigDecimal defaultDiscount,
            BiFunction<BigDecimal, BigDecimal, BigDecimal> discountAmountExpression

    ) {
        this.eventName = eventName;
        this.menuGroup = menuGroup;
        this.defaultDiscount = defaultDiscount;
        this.discountAmountExpression = discountAmountExpression;
    }


    public BigDecimal getBenefitAmount(Integer times) {
        if (times == null || times < 1) {
            times = 1;
        }
        return discountAmountExpression.apply(BigDecimal.valueOf(times), this.defaultDiscount);
    }

    public BigDecimal getBenefitAmount() {
        return discountAmountExpression.apply(BigDecimal.valueOf(1), this.defaultDiscount);
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public String getGifts() {
        return Message.COUNT_UNIT_FORMAT.toFormattedString(this.giftName, 1);
    }

    public BigDecimal getBenefitPrice() {
        return this.benefitPrice;
    }

    public String getEventName() {
        return eventName;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
