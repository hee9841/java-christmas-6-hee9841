package christmas.domain;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

public enum Menu {
    MUSHROOM_SOUP("애피타이저", "양송이수프", new BigDecimal("6000")),
    TAPAS("애피타이저", "타파스", new BigDecimal("5500")),
    CAESAR_SALAD("애피타이저", "시저샐러드", new BigDecimal("8000")),
    T_BONE_STEAK("메인", "티본스테이크", new BigDecimal("55000")),
    BBQ_RIPS("메인", "바비큐립", new BigDecimal("54000")),
    SEAFOOD_PASTA("메인", "해산물파스타", new BigDecimal("35000")),
    CHRISTMAS_PASTA("메인", "크리스마스파스타", new BigDecimal("25000")),
    CHOCOLATE_CAKE("디저트", "초코케이크", new BigDecimal("15000")),
    ICE_CRAM("디저트", "아이스크림", new BigDecimal("5000")),

    ZERO_COKE("음료", "제로콜라", new BigDecimal("3000")),
    RED_WINE("음료", "레드와인", new BigDecimal("60000")),
    CHAMPAGNE("음료", "샴페인", new BigDecimal("25000"));


    private final String category;
    private final String name;
    private final BigDecimal price;

    Menu(String category, String name, BigDecimal price) {
        this.category = category;
        this.name = name;
        this.price = price;
    }


    public static Optional<Menu> of(String name) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.name.equals(name))
                .findFirst();
    }

    @Override
    public String toString() {
        return "Menu{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
