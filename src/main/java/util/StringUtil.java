package util;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class StringUtil {
    public static String formatWon(BigDecimal amount) {
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(amount) + "원";
    }

    public static String formatMonthDayKorean(LocalDate visitDate) {
        return visitDate.getMonthValue() + "월 " + visitDate.getDayOfMonth() + "일";
    }


}
