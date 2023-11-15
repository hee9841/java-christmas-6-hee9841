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
            throw new UnsupportedOperationException("[ERROR] 해당 메서들르 지원하지 않습니다.");
        }
        try {
            return String.format(this.message, arg);
        } catch (IllegalFormatException | FormatterClosedException e) {
            throw new IllegalArgumentException("[ERROR] 포멧 문자의 수가 맞지않습니다.");
        }
    }

    @Override
    public String toString() {
        return message;
    }
}
