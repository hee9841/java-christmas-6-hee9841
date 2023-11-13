package util;

import java.util.HashSet;
import java.util.List;

public class ValidatorUtil {


    public static <T> boolean isValidListSize(List<T> inputs, int size) {
        return inputs.size() == size;
    }

    public static <T> boolean hasDuplicateNum(List<T> checkNumbers) {
        int unDuplicateSize = (new HashSet<>(checkNumbers)).size();
        return unDuplicateSize != checkNumbers.size();
    }


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
