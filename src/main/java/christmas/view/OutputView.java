package christmas.view;


import christmas.constant.EventDateInfo;
import java.math.BigDecimal;
import java.time.LocalDate;
import util.StringUtil;

public class OutputView {
    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printStartMessage() {
        System.out.println("\n안녕하세요! 우테코 식당 " + EventDateInfo.EVENT_MONTH + "월 이벤트 플래너입니다.");
    }

    public void printStartPlanMessage(LocalDate visitDate) {
        String monthDay = StringUtil.formatMonthDayKorean(visitDate);
        System.out.println(monthDay + "에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n");
    }

    public void printOrders(String message) {
        System.out.println("<주문 메뉴>");
        System.out.println(message);
        System.out.println();
    }


    public void printTotalAmountBeforeDiscount(BigDecimal totalAmount) {
        System.out.println("<할인 전 총주문 금액>");
        System.out.println(StringUtil.formatWon(totalAmount));
        System.out.println();
    }

    public void printGiftMenu(String menu) {
        System.out.println("<증정 메뉴>");
        System.out.println(menu);
        System.out.println();
    }

    public void printBenefits(String benefits) {
        System.out.println("<혜택 내역>");
        System.out.println(benefits);
        System.out.println();
    }

    public void printBenefitAmount(String benefitAmount) {
        System.out.println("<총혜택 금액>");
        System.out.println(benefitAmount);
        System.out.println();
    }

    public void printPayment(String payment) {
        System.out.println("<할인 후 예상 결제 금액>");
        System.out.println(payment);
        System.out.println();
    }

    public void printBadge(String badge) {
        System.out.println("<12월 이벤트 배지>");
        System.out.println(badge);
    }

}
