package christmas.domain;


import java.time.LocalDate;
import java.util.EnumMap;

public class Customer {

    private final EnumMap<Menu, Integer> myOrders;
    private final LocalDate visitDate;


    public Customer(EnumMap<Menu, Integer> myOrders, LocalDate visitDate) {
        this.myOrders = myOrders;
        this.visitDate = visitDate;
    }

}
