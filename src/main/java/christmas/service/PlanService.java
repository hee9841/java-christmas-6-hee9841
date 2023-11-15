package christmas.service;


import christmas.constant.Badges;
import christmas.constant.BenefitType;
import christmas.constant.EventType;
import christmas.constant.Menu;
import christmas.constant.Message;
import christmas.domain.Customer;
import christmas.policy.input.CheckCondition;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import util.StringUtil;

public class PlanService {

    private final CheckCondition checkCondition;
    private final InputView inputView;
    private final OutputView outputView;

    private final Customer customer;


    public PlanService(InputView inputView, OutputView outputView, CheckCondition checkCondition) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.checkCondition = checkCondition;
        this.customer = getCustomer();
    }


    //고객 생성
    private Customer getCustomer() {
        outputView.printStartMessage();
        LocalDate visitDate = generateDate();
        EnumMap<Menu, Integer> orders = generateOrders();
        outputView.printStartPlanMessage(visitDate);
        return new Customer(orders, visitDate);
    }


    public LocalDate generateDate() {
        while (true) {
            try {
                String visitDay = inputView.readDate();
                checkCondition.validateDateFormat(visitDay);
                int day = Integer.parseInt(visitDay);
                checkCondition.validateDatePeriod(day);
                return LocalDate.of(2023, 12, day);
            } catch (IllegalArgumentException e) {
                outputView.printMessage(e.getMessage());
            }
        }


    }

    public EnumMap<Menu, Integer> generateOrders() {
        while (true) {
            try {
                String inputOrders = inputView.readOrders();
                EnumMap<Menu, Integer> orders = new EnumMap<>(Menu.class);
                //1. 컴마 체크
                checkCondition.validateInputOrders(inputOrders);
                generateOrder(inputOrders, orders);
                return orders;
            } catch (IllegalArgumentException e) {
                outputView.printMessage(e.getMessage());
            }
        }

    }

    private void generateOrder(String inputOrders, EnumMap<Menu, Integer> orders) {
        String[] seperatedInputOrders = inputOrders.split(",");

        //2. 각 메뉴별
        for (String inputOrder : seperatedInputOrders) {
            addOrder(inputOrder, orders);
            checkCondition.checkMinOrderCount(orders);
        }
    }

    private void addOrder(String order, EnumMap<Menu, Integer> orders) {

        checkCondition.validateOrderFormat(order);
        String[] separatedOrder = order.split("-");

        Menu orderMenu = Menu.of(separatedOrder[0])
                .orElseThrow(() ->
                        new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.")
                );
        int orderCount = Integer.parseInt(separatedOrder[1]);

        checkCondition.isDuplicateMenu(orders, orderMenu);
        orders.put(orderMenu, orderCount);

    }

    public void doPlan() {
        outputView.printOrders(customer.findMyOrders());
        BigDecimal totalAmountBeforeDiscount = customer.calculateTotalPayment();
        outputView.printTotalAmountBeforeDiscount(totalAmountBeforeDiscount);
        calculateBenefits(totalAmountBeforeDiscount);
    }

    private void calculateBenefits(BigDecimal totalAmountBeforeDiscount) {
        List<EventType> events = customer.getEvents();
        EnumMap<EventType, BigDecimal> benefits = customer.getBenefits(events);
        showGiftMenu(benefits);
        showBenefitDetails(benefits);
        BigDecimal benefitAmounts = getBenefitAmounts(benefits);
        showBenefitAmounts(benefitAmounts);
        showPaymentAmount(totalAmountBeforeDiscount, benefits);
        showBadge(benefitAmounts);
    }

    private void showBadge(BigDecimal benefitAmounts) {
        Badges badges = Badges.valueOfAmount(benefitAmounts);
        outputView.printBadge(badges.toString());
    }

    private void showPaymentAmount(BigDecimal totalAmountBeforeDiscount, EnumMap<EventType, BigDecimal> benefits) {
        BigDecimal totalDiscountAmount = BigDecimal.ZERO;

        for (Map.Entry<EventType, BigDecimal> entry : benefits.entrySet()) {
            BenefitType byEventTypeCode = BenefitType.findByEventTypeCode(entry.getKey().name());
            if (byEventTypeCode.equals(BenefitType.DISCOUNT)) {
                totalDiscountAmount = totalDiscountAmount.add(entry.getValue());
            }
        }
        String paymentAmount = StringUtil.formatWon(totalAmountBeforeDiscount.subtract(totalDiscountAmount));
        outputView.printPayment(paymentAmount);

    }


    private void showGiftMenu(EnumMap<EventType, BigDecimal> benefits) {
        if (benefits.containsKey(EventType.GIFT_MENU)) {
            outputView.printGiftMenu(EventType.GIFT_MENU.getGifts());
            return;
        }
        outputView.printGiftMenu(Message.NOTHING.toString());
    }

    public void showBenefitDetails(EnumMap<EventType, BigDecimal> benefits) {
        outputView.printBenefits(createBenefitDetails(benefits));
    }

    private String createBenefitDetails(EnumMap<EventType, BigDecimal> benefits) {
        if (benefits == null || benefits.isEmpty()) {
            return Message.NOTHING.toString();
        }
        StringBuilder message = new StringBuilder();
        for (Map.Entry<EventType, BigDecimal> entry : benefits.entrySet()) {
            String amount = StringUtil.formatWon(entry.getValue());
            String str = Message.BENEFIT_DETAILS_FORMAT.toFormattedString(
                    entry.getKey().getEventName(), "-" + amount);
            message.append(str).append("\n");
        }
        return message.toString();
    }

    private BigDecimal getBenefitAmounts(EnumMap<EventType, BigDecimal> benefits) {
        if (benefits == null || benefits.isEmpty()) {
            outputView.printBenefitAmount(Message.NOTHING.toString());
            return null;
        }
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (BigDecimal amount : benefits.values()) {
            totalAmount = totalAmount.add(amount);
        }
        return totalAmount;
    }

    private void showBenefitAmounts(BigDecimal totalAmount) {
        if (totalAmount == null || totalAmount.compareTo(BigDecimal.ONE) < 0) {
            outputView.printBenefitAmount(StringUtil.formatWon(BigDecimal.ZERO));
            return;
        }
        outputView.printBenefitAmount("-" + StringUtil.formatWon(totalAmount));
    }


}
