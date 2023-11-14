package christmas;


import java.util.FormatterClosedException;
import java.util.IllegalFormatException;

public enum Message {
    ORDER_MESSAGE_FORMAT("%s %d개", true),
    TEST("DDD",false);

    private String message;
    private boolean isFormatString;

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
