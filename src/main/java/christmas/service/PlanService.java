package christmas.service;


import christmas.domain.Customer;
import christmas.domain.Menu;
import christmas.CheckCondition;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.time.LocalDate;
import java.util.EnumMap;

public class PlanService {

    private final CheckCondition checkCondition;
    private final InputView inputView;
    private final OutputView outputView;


    public PlanService(InputView inputView, OutputView outputView, CheckCondition checkCondition) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.checkCondition = checkCondition;
    }


    public Customer getCustomer() {
        while (true) {
            try {
                LocalDate visitDate = generateDate(inputView.readDate());
                EnumMap<Menu, Integer> orders = generateOrders(inputView.readOrders());
                return new Customer(orders, visitDate);
            } catch (IllegalArgumentException e) {
                outputView.printMessage(e.getMessage());
            }
        }
    }


    public LocalDate generateDate(String visitDay) {

        checkCondition.validateDateFormat(visitDay);
        int day = Integer.parseInt(visitDay);
        checkCondition.validateDatePeriod(day);

        return LocalDate.of(2023, 12, day);

    }

    public EnumMap<Menu, Integer> generateOrders(String inputOrders) {
        EnumMap<Menu, Integer> orders = new EnumMap<>(Menu.class);
        //1. 컴마 체크
        checkCondition.validateInputOrders(inputOrders);
        //분리
        String[] seperatedInputOrders = inputOrders.split(",");

        //2. 각 메뉴별
        for (String inputOrder : seperatedInputOrders) {
            checkCondition.checkMinOrderCount(orders);
            addOrder(inputOrder, orders);
        }

        return orders;
    }

    private void addOrder(String order, EnumMap<Menu, Integer> orders) {

        checkCondition.validateOrderFormat(order);
        String[] separatedOrder = order.split("-");

        Menu orderMenu = Menu.of(separatedOrder[0])
                .orElseThrow(() ->
                        new IllegalArgumentException("ERROR")
                );
        int orderCount = Integer.parseInt(separatedOrder[1]);

        checkCondition.isDuplicateMenu(orders, orderMenu);
        orders.put(orderMenu, orderCount);

    }


}
