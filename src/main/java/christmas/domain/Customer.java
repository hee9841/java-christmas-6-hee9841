package christmas.domain;


import christmas.constant.EventType;
import christmas.constant.Menu;
import christmas.constant.MenuGroup;
import christmas.constant.Message;
import christmas.policy.BenefitPolicy;
import christmas.policy.EventConditionPolicy;
import christmas.policy.EventConditionPolicyImpl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Customer {

    private final EnumMap<Menu, Integer> myOrders;
    private final LocalDate visitDate;


    public Customer(EnumMap<Menu, Integer> myOrders, LocalDate visitDate) {
        this.myOrders = myOrders;
        this.visitDate = visitDate;
    }

    public BigDecimal calculateTotalPayment() {
        BigDecimal totalPayment = BigDecimal.ZERO;

        for (Map.Entry<Menu, Integer> order : myOrders.entrySet()) {
            Menu menu = order.getKey();
            Integer orderCount = order.getValue();

            BigDecimal price = menu.calculatePriceEachMenu(orderCount);
            totalPayment = totalPayment.add(price);
        }

        return totalPayment;
    }

    public String findMyOrders() {
        return myOrders.entrySet().stream()
                .map(entry -> Message.COUNT_UNIT_FORMAT.toFormattedString(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.joining("\n"));

    }

    private EnumMap<MenuGroup, Integer> getEachMenuGroupsCount(EnumMap<Menu, Integer> menus) {
        EnumMap<MenuGroup, Integer> EachMenuGroupsCount = new EnumMap<>(MenuGroup.class);
        for (Map.Entry<Menu, Integer> menu : menus.entrySet()) {
            MenuGroup menuGroup = MenuGroup.findByMenuCode(menu.getKey().name());
            if (!EachMenuGroupsCount.containsKey(menuGroup)) {
                EachMenuGroupsCount.put(menuGroup, 0);
            }
            EachMenuGroupsCount.put(menuGroup, EachMenuGroupsCount.get(menuGroup) + menu.getValue());
        }

        return EachMenuGroupsCount;
    }

    public List<EventType> getEvents() {
        EventConditionPolicy eventConditionPolicy = new EventConditionPolicyImpl();
        return eventConditionPolicy.findEvent(visitDate, myOrders, calculateTotalPayment());
    }

    public EnumMap<EventType, BigDecimal> getBenefits(List<EventType> myEventType) {
        BenefitPolicy benefitPolicy = new BenefitPolicy();
        EnumMap<MenuGroup, Integer> myMenuGroups = getEachMenuGroupsCount(myOrders);
        return benefitPolicy.getMyBenefits(myEventType, visitDate, myMenuGroups);
    }


}
