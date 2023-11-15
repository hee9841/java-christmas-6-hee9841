package christmas.policy.input;


import christmas.constant.ErrorMessage;
import christmas.constant.Menu;
import java.util.EnumMap;
import util.ValidatorUtil;

public class CheckCondition {

    public void validateDateFormat(String input) {
        if (!ValidatorUtil.isNumericFormat(input)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DATE.toString());
        }
    }

    public void validateDatePeriod(int day) {
        if (!ValidatorUtil.isValidRangeNum(day, 1, 31)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DATE.toString());
        }
    }

    public void validateInputOrders(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER.toString());
        }
        if (input.charAt(0) == ',' || input.charAt(input.length() - 1) == ',') {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER.toString());
        }
    }


    public void validateOrderFormat(String order) {
        try {
            if (!isValidOrderSeparatorFormat(order)) {
                throw new IllegalArgumentException();
            }
            String[] separatedOrder = order.split("-");
            if (!isValidOrderCount(Integer.parseInt(separatedOrder[1]))) {
                throw new IllegalArgumentException();
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER.toString());
        }
    }

    private boolean isValidOrderSeparatorFormat(String order) {
        long separatorCount = order.chars().filter(c -> c == '-').count();
        if (separatorCount != 1) {
            return false;
        }
        String[] separatedOrder = order.split("-");
        return separatedOrder.length == 2;
    }


    public void isDuplicateMenu(EnumMap<Menu, Integer> orders, Menu menu) {
        //3. 이미 중복된 메뉴인지 확인
        if (orders.containsKey(menu)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER.toString());
        }
    }


    public void checkMinOrderCount(EnumMap<Menu, Integer> orders) {
        int orderCountSum = orders.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
        if (!isValidOrderCount(orderCountSum)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER.toString());
        }
    }

    private boolean isValidOrderCount(int orderCount) {
        return ValidatorUtil.isValidRangeNum(orderCount, 1, 20);
    }


}
