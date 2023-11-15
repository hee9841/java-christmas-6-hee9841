package christmas.constant;


import java.util.Arrays;
import java.util.List;

public enum BenefitType {
    DISCOUNT(
            List.of(
                    "CHRISTMAS_SALE", "SPECIAL_SALE",
                    "WEEKDAY_SALE", "WEEKEND_SALE"
            )
    ),
    GIFT(
            List.of(
                    "GIFT_MENU"
            )
    );

    private final List<String> group;

    BenefitType(List<String> group) {
        this.group = group;
    }

    public static BenefitType findByEventTypeCode(String code) {
        return Arrays.stream(BenefitType.values())
                .filter(benefitType -> benefitType.hashMenuCode(code))
                .findAny()
                .orElseThrow(() ->
                        new NullPointerException("[ERROR] code가 해당하는 BenefitType이 없습니다.")
                );
    }


    public boolean hashMenuCode(String code) {
        return group.stream()
                .anyMatch(menu -> menu.equals(code));
    }


}
