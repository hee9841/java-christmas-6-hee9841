package christmas.domain;


import java.util.Arrays;
import java.util.List;

public enum MenuGroup {
    APPETIZER(
            "애피타이저",
            Arrays.asList(
                    "MUSHROOM_SOUP", "TAPAS", "CAESAR_SALAD"
            )
    ),
    MAIN_DISH(
            "메인",
            Arrays.asList(
                    "T_BONE_STEAK", "BBQ_RIPS",
                    "SEAFOOD_PASTA", "CHRISTMAS_PASTA"
            )

    ),
    DESSERT(
            "디저트",
            Arrays.asList(
                    "CHOCOLATE_CAKE", "ICE_CRAM"
            )
    ),
    BEVERAGE(
            "음료",
            Arrays.asList(
                    "ZERO_COKE", "RED_WINE", "CHAMPAGNE"
            )
    );

    private final String name;
    private final List<String> group;

    MenuGroup(String name, List<String> group) {
        this.name = name;
        this.group = group;
    }


    public static MenuGroup findByMenuCode(String code) {
        return Arrays.stream(MenuGroup.values())
                .filter(menuGroup -> menuGroup.hashMenuCode(code))
                .findAny()
                .orElseThrow(() ->
                    new NullPointerException("[ERROR] code가 해당하는 Menugroup이 없습니다.")
                );
    }

    public boolean hashMenuCode(String code) {
        return group.stream()
                .anyMatch(menu -> menu.equals(code));
    }

    @Override
    public String toString() {
        return "Menu{" +
                "name='" + name + '\'' +
                '}';
    }
}
