package christmas.domain;


import christmas.Message;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;

public class Customer {

    private final EnumMap<Menu, Integer> myOrders;
    private final LocalDate visitDate;

    private BigDecimal totalPayment = BigDecimal.ZERO;


    public Customer(EnumMap<Menu, Integer> myOrders, LocalDate visitDate) {
        this.myOrders = myOrders;
        this.visitDate = visitDate;
    }

    public void calculateTotalPayment() {
        for (Map.Entry<Menu, Integer> order : myOrders.entrySet()) {
            Menu menu = order.getKey();
            Integer orderCount = order.getValue();

            BigDecimal price = menu.calculatePriceEachMenu(orderCount);
            totalPayment = totalPayment.add(price);
        }
    }

    public String findMyOrders() {
        return myOrders.entrySet().stream()
                .map(entry ->
                        Message.ORDER_MESSAGE_FORMAT.toFormattedString(entry.getKey().toString(), entry.getValue())
                )
                .map(str -> str + "\n")
                .toString();

    }

}
