package christmas.domain;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

public enum Menu {
    MUSHROOM_SOUP( "양송이수프", new BigDecimal("6000")),
    TAPAS( "타파스", new BigDecimal("5500")),
    CAESAR_SALAD( "시저샐러드", new BigDecimal("8000")),
    T_BONE_STEAK( "티본스테이크", new BigDecimal("55000")),
    BBQ_RIPS( "바비큐립", new BigDecimal("54000")),
    SEAFOOD_PASTA( "해산물파스타", new BigDecimal("35000")),
    CHRISTMAS_PASTA( "크리스마스파스타", new BigDecimal("25000")),
    CHOCOLATE_CAKE( "초코케이크", new BigDecimal("15000")),
    ICE_CRAM( "아이스크림", new BigDecimal("5000")),
    ZERO_COKE( "제로콜라", new BigDecimal("3000")),
    RED_WINE( "레드와인", new BigDecimal("60000")),
    CHAMPAGNE( "샴페인", new BigDecimal("25000"));


    private final String name;
    private final BigDecimal price;

    Menu(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }


    public static Optional<Menu> of(String name) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.name.equals(name))
                .findFirst();
    }

    public BigDecimal calculatePriceEachMenu(Integer orderCount) {
        if (orderCount == null) {
            throw new NullPointerException("");
        }
        return price.multiply(BigDecimal.valueOf(orderCount));
    }

    @Override
    public String toString() {
        return name;
    }
}
