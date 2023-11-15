package christmas.constant;


import java.util.FormatterClosedException;
import java.util.IllegalFormatException;

public enum Message {
    COUNT_UNIT_FORMAT("%s %d개", true),
    NOTHING("없음", false),
    BENEFIT_DETAILS_FORMAT("%s: %s", true);


    private final String message;
    private final boolean isFormatString;

    Message(String message, boolean isFormatString) {
        this.message = message;
        this.isFormatString = isFormatString;
    }

    public String toFormattedString(Object... arg) {
        if (!this.isFormatString) {
            throw new UnsupportedOperationException("Erro");
            //객체가 해당 메서드를 지원하지 안흠
        }
        try {
            return String.format(this.message, arg);
        } catch (IllegalFormatException | FormatterClosedException e) {
            throw new IllegalArgumentException("포멧 문자의 수가 맞지않음?");
        }
    }

    @Override
    public String toString() {
        return message;
    }
}
