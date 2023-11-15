package util;

public class ValidatorUtil {
    public static boolean isValidRangeNum(int input, int minRage, int maxRage) {
        return minRage <= input && input <= maxRage;
    }

    public static boolean isNumericFormat(String input) {
        try {
            Integer.parseInt(input);
        } catch (NullPointerException | NumberFormatException e) {
            return false;
        }
        return true;
    }

}
